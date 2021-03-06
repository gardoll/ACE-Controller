package fr.gardoll.ace.controller.pump;

import java.io.Closeable;
import java.math.RoundingMode ;
import java.text.DecimalFormat ;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.com.FlowControl ;
import fr.gardoll.ace.controller.com.JSerialComm ;
import fr.gardoll.ace.controller.com.Parity ;
import fr.gardoll.ace.controller.com.SerialCom ;
import fr.gardoll.ace.controller.com.SerialComException ;
import fr.gardoll.ace.controller.com.SerialMode ;
import fr.gardoll.ace.controller.com.StopBit ;
import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.settings.ConfigurationException ;
import fr.gardoll.ace.controller.settings.GeneralSettings ;

public class InterfacePousseSeringue  implements Closeable, PumpController
{
  private static final long _OPENING_DELAY = 2000l;
  
  private static final Pattern _DELIVER_PATTERN = Pattern.compile("([0-9.]+)\\s+(u|m)l\\s+(:|>|<)");
  private static final Pattern _NA_PATTERN = Pattern.compile("NA\\s+(:|>|<)");
  
  private static final Logger _LOG = Log.CONTROLLER;
  
  private final static DecimalFormat[] _DOUBLE_FORMATTERS = new DecimalFormat[4];
  
  private final SerialCom _port;
  
  // dépendant uniquement du diametre du type de seringue utilisé.
  private int _debitMaxIntrinseque;
  
  static
  {
    String[] formats = {"#.###", "##.##", "###.#", "####"} ;
        
    for(int index=0 ; index < _DOUBLE_FORMATTERS.length ; index++)
    {
      _DOUBLE_FORMATTERS[index] = new DecimalFormat(formats[index]);
      _DOUBLE_FORMATTERS[index].setRoundingMode(RoundingMode.HALF_UP);
      _DOUBLE_FORMATTERS[index].setDecimalSeparatorAlwaysShown(false);
      _DOUBLE_FORMATTERS[index].setDecimalFormatSymbols(DECIMAL_SYMBOLS);
    }
  }
  
  // requires 0 < diametreSeringue <= DIAMETRE_MAX
  public InterfacePousseSeringue(SerialCom port)
      throws InitializationException, ConfigurationException
  {
    _LOG.debug(String.format("initializing the pump controller with the serial port %s",
        port.getId()));
    
    double diametreSeringue = GeneralSettings.instance().getDiametreSeringue();
    
    this._port = port ;
    
    // Initializing the serial port (already opened).
    try
    {
      _LOG.debug(String.format("setting the pump com port '%s'", this._port.getId()));
      this._port.setReadBufferSize(256);
      this._port.setMode(SerialMode.FULL_BLOCKING, SerialMode.FULL_BLOCKING);
      this._port.setCharset(JSerialComm.ASCII_CHARSET);
      this._port.setVitesse(9600) ;
      this._port.setByteSize (8);
      this._port.setStopBit(StopBit.ONESTOPBIT);
      this._port.setParite(Parity.NOPARITY);
      this._port.setControlFlux(FlowControl.XON_XOFF);
      this._port.setTimeOut(300) ;
      this._port.open(_OPENING_DELAY);
      
    }
    catch(SerialComException e)
    {
      String msg = "error while initializing the pump serial port";
      throw new InitializationException(msg, e);
    }
 
    try
    {
      this.dia(diametreSeringue);
    }
    catch(SerialComException e)
    {
      String msg = "error while initializing the pump";
      throw new InitializationException(msg, e);
    }
  }
  
  //traitement de la réponse de l'interface en cas d'erreur => exception.
  private void traitementReponse(String message) throws SerialComException
  {
    //_LOG.trace(String.format("ack received: '%s'", message));
    
    if (message.matches("E+"))
    {
      String msg = String.format("pump failure ('%s')", message) ;
      throw new SerialComException(msg) ;
    }
    else if (_NA_PATTERN.matcher(message).matches())
    {
      String msg = "unknown pump order" ;
      throw new SerialComException(msg) ;
    }
    else if (message == "")
    {
      String msg = "pump disconnection" ;
      throw new SerialComException(msg) ;
    }
  }
  
  //renvoie la réponse de l'interface
  private String traitementOrdre(String ordre) throws SerialComException
  {
    this._port.ecrire(ordre) ;
    String reponse = this.lectureReponse() ;
    this.traitementReponse(reponse) ;
    return reponse  ;
  }
  
  private String lectureReponse() throws SerialComException
  {
    return this._port.lire().strip();
  }
  
  // transforme float en string mais
  // où , est transformée en .  (séparateur des réels)
  // et format le nombre pour qu'il n'y ait que 4 chiffres au plus.
  public static String formatage(double nombre)
  {
    // le pousse seringue n'acceptant que des nombres à 4 chiffres au plus ,
    // sans compter le séparateur décimal
    // qui doit être un point, le paramètre nombre doit être formaté en conséquence.
    
    int truncatedValue = (int)nombre;
    int index = 0;
    
    if (truncatedValue < 10)
    {
      index = 0;
    }
    else if(truncatedValue < 100)
    {
      index = 1;
    }
    else if(truncatedValue < 1000)
    {
      index = 2;
    }
    else if(truncatedValue < 10000)
    {
      index = 3; 
    }
    else
    {
      String msg = String.format("unsupported format number '%s'", nombre);
      throw new RuntimeException(msg);
    }
    
    String result = _DOUBLE_FORMATTERS[index].format(nombre);
    return result;
  }
  
  //reprise ou démarrage
  @Override
  public void run() throws SerialComException
  {
    _LOG.trace("command 'run'");
    this.traitementOrdre( "run\r" );
  }
  
  // pause or cancel
  @Override
  public void stop() throws SerialComException
  {
    _LOG.trace("command 'stop'");
    this.traitementOrdre ("stop\r");
  }
  
  // en mm requires diametre > 0
  @Override
  public void dia(double diametre) throws SerialComException, ConfigurationException
  {
    // contient le code de vérification.
    this._debitMaxIntrinseque = PumpController.debitMaxIntrinseque(diametre);
    
    String formattedDiameter = formatage(diametre);
    String ordre = String.format("dia %s\r", formattedDiameter) ;
    
    _LOG.trace(String.format("command 'dia %s'", formattedDiameter));
    this.traitementOrdre (ordre);
  }
  
  @Override
  public boolean running() throws SerialComException
  {
    String ack = this.traitementOrdre("run?\r");
    boolean result = false == ack.equals(":") ;
    return result;
  }
  
  // en mL
  //Attention ne revoie un réel que si le volume à délivré en est un.
  // Ainsi : 1. ou 1.0 ne donnera pas de réponse en réel donc la réponse sera
  // 0 puis 1 à la fin !!!! Il n'y a donc aucun intérêt.
  // Parade : passer en micro litre quand < 10 mL.
  @Override
  public double deliver() throws SerialComException
  {
    String rawMessage = this.traitementOrdre("del?\r");
    return innerDeliver(rawMessage);
  }
  
  private static double innerDeliver(String rawMessage) throws SerialComException
  {
    Matcher m = _DELIVER_PATTERN.matcher(rawMessage);
    
    double result = 0.;
    
    if(m.matches())
    {
      result = Double.valueOf(m.group(1));
      
      if(m.group(2).equals("u"))
      {
        result /= 1000. ;
      }
    }
    else
    {
      String msg = String.format("cannot interpret delivered volume '%s'", rawMessage);
      throw new SerialComException(msg);
    }
    
    return result; // The value is rounded in the method Pump::deliver.
  }
  
  // en mL/min   requires 0 < debit <= _debitMaxIntrinseque
  @Override
  public void ratei(double debit) throws SerialComException, ConfigurationException
  {
    if (debit <= 0.)
    {
      String msg = String.format("the value of the rate '%s' cannot be negative or null",
                                 debit);
      throw new ConfigurationException(msg) ;
    }
    else if (debit > this._debitMaxIntrinseque)
    {
      String msg = String.format("the value of the rate '%s' cannot be greater than %s mL/min",
                                 debit, this._debitMaxIntrinseque);
      throw new ConfigurationException(msg) ;
    }
    
    String formattedRate = formatage(debit);
    String ordre = String.format("ratei %s ml/m\r", formattedRate) ;
    _LOG.trace(String.format("command 'ratei %s ml/m'", formattedRate));
    this.traitementOrdre(ordre) ;
  }
  
  //en mL/min   requires 0 < debit <= _debitMaxIntrinseque
  @Override
  public void ratew(double debit) throws SerialComException, ConfigurationException
  {
    if (debit <= 0.)
    {
      String msg = String.format(
          "the value of the rate '%s' cannot be negative or null", debit) ;
      throw new ConfigurationException(msg) ;
    }
    else if (debit > this._debitMaxIntrinseque)
    {
      String msg = String.format(
          "the value of the rate '%s' cannot be greater than %s mL/min", debit,
          this._debitMaxIntrinseque) ;
      throw new ConfigurationException(msg) ;
    }

    String formattedRate = formatage(debit);
    String ordre = String.format("ratew %s ml/m\r", formattedRate);
    
    _LOG.trace(String.format("command 'ratew %s ml/m'", formattedRate));
    this.traitementOrdre(ordre) ;
  }
  
  // en mL seulement 4 caractères sans compter la virgule.
  // requires volume > 0
  @Override
  public void voli(double volume) throws SerialComException, ConfigurationException
  {
    String ordre = forgeVolOrder("voli", volume);
    String msg = String.format("command '%s'", ordre.substring(0, ordre.length()-1));
    _LOG.trace(msg);
    this.traitementOrdre(ordre) ;
  }
  
  // en mL seulement 4 caractères sans compter la virgule.
  // requires volume > 0
  @Override
  public void volw(double volume) throws SerialComException, ConfigurationException
  {
    String ordre = forgeVolOrder("volw", volume);
    String msg = String.format("command '%s'", ordre.substring(0, ordre.length()-1));
    _LOG.trace(msg);
    this.traitementOrdre(ordre) ;
  }
  
  public static String forgeVolOrder(String command, double volume)
    throws ConfigurationException
  {
    if (volume <= 0.)
    {
      String msg = String.format("the value of the volume '%s' cannot be negative or null",
                                 volume);
      throw new ConfigurationException(msg) ;
    }

    String ordre = null;

    if (volume < 10.)
    {
      // permet le suivie du volume délivré si <1 ml voir la fonction deliver.
      ordre = String.format("%s %s ul\r", command, formatage(volume * 1000.));
    }
    else
    {
      ordre = String.format("%s %s ml\r", command, formatage(volume));
    }
    
    return ordre;
  }
  
  @Override
  public void modeI() throws SerialComException
  {
    _LOG.trace("command 'mode i'");
    this.traitementOrdre("mode i\r") ; 
  }
  
  @Override
  public void modeW() throws SerialComException
  {
    _LOG.trace("command 'mode w'");
    this.traitementOrdre("mode w\r") ;
  }
  
  @Override
  public String getPortPath()
  {
    return this._port.getPath();
  }
  
  @Override
  public void close()
  {
    _LOG.debug(String.format("closing pump controller with port id '%s'", this._port.getId()));
    this._port.close() ;
  }
  
  public static void main(String[] args)
  {
    // Windows 10: "COM5"
    // CentOS   7: "/dev/ttyUSB1"
    String portPath = "/dev/ttyUSB1"; // To be modified.
    JSerialComm port = new JSerialComm(portPath);
    
    try(InterfacePousseSeringue pumpInt = new InterfacePousseSeringue(port))
    {
      boolean isRunning = pumpInt.running();
      _LOG.info(String.format("is runnning: %s", isRunning)) ;
      
      _LOG.info("set mode infusion") ;
      pumpInt.modeI();
      
      double ratei = 15. ;
      _LOG.debug(String.format("set ratei to %s", ratei));
      pumpInt.ratei(ratei);
      
      double voli = 11. ;
      _LOG.info(String.format("set the voli to %s", voli));
      pumpInt.voli(voli);
      
      _LOG.info("run");
      pumpInt.run();
      
      isRunning = pumpInt.running();
      _LOG.info(String.format("is runnning: %s", isRunning)) ;
      
      Thread.sleep(1000);
      
      double deliver = pumpInt.deliver();
      _LOG.info(String.format("deliver: %s", deliver));
      
      Thread.sleep(1000);
      
      _LOG.info("stopping pump");
      pumpInt.stop();
      
      isRunning = pumpInt.running();
      _LOG.info(String.format("is runnning: %s", isRunning)) ;
      
      _LOG.info("set mode withdrawal") ;
      pumpInt.modeW();
      
      double ratew = 1. ;
      _LOG.debug(String.format("set ratew to %s", ratew));
      pumpInt.ratew(ratew);
      
      double volw = 9. ;
      _LOG.info(String.format("set the volw to %s", volw));
      pumpInt.volw(volw);
      
      _LOG.info("run");
      pumpInt.run();
      
      isRunning = pumpInt.running();
      _LOG.info(String.format("is runnning: %s", isRunning)) ;
      
      Thread.sleep(1200);
      
      deliver = pumpInt.deliver();
      _LOG.info(String.format("deliver: %s", deliver));
      
      Thread.sleep(1000);
      
      _LOG.info("stopping pump");
      pumpInt.stop();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
