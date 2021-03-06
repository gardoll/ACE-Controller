package fr.gardoll.ace.controller.pump;

import java.io.Closeable ;
import java.io.IOException ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.com.ArduinoParaCom ;
import fr.gardoll.ace.controller.com.JSerialComm ;
import fr.gardoll.ace.controller.com.ParaComException ;
import fr.gardoll.ace.controller.com.SerialComException ;
import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.core.ThreadControl ;
import fr.gardoll.ace.controller.core.Utils ;
import fr.gardoll.ace.controller.settings.ConfigurationException ;
import fr.gardoll.ace.controller.settings.GeneralSettings ;
import fr.gardoll.ace.controller.valves.Valves ;

public class PousseSeringue implements Closeable
{
  // temps d'attente entre la détection d'une fin de pompage
  // et la fermeture des EV <=> équilibre des pressions dans les tuyaux.
  public final static long ATTENTE_FERMETURE_EV = 1000l ; // Milliseconds.
                                              
  //volume laissé entre le piston et la fin du corps de la seringue par seringue
  //ceci dut à la forme conique de la fin du corps de la seringue
  public final static double VOL_SECU = 0.5 ; 
                                
  //correction en mL dût au jeux mécanique symétrique
  //de la visse d'entrainement du pousse seringue
  //utile seulement lors d'un changement de sens de pompage
  //par la seringue
  public final static double VOL_AJUSTEMENT = 0.5 ;   
                                        
  //Attention : static !!!! volume réel pour une seringue
  private static double _volumeReel = 0.;
  
  private static final Logger _LOG = Log.HIGH_LEVEL;
  
  private final PumpController interfacePousseSeringue ;

  private final Valves valves ;

  // débit en cours
  private double debitActuelAspiration ;

  //débit en cours
  private double debitActuelRefoulement ;

  //numéro ev actuellement ouverte sert à la reprise d'une pause
  private int numEvActuelle ;  

  //flag de pause
  private boolean _pause ; 

  //info sur l'aspiration ou non du volume de sécurité, true : aspiré, false : refoulé
  private boolean flagVolSecu ; 

  //requires nombresSeringues <= 2
  //requires volumeMaxSeringue > 0
  //requires 0 < debitMaxPousseSeringue <=  debitMaxIntrinseque ( diametreSeringue )
  //requires volumeInitiale >= 0
  public PousseSeringue(PumpController interfacePousseSeringue,
                        Valves valves,
                        double volumeInitiale) throws InitializationException, ConfigurationException
  {
    _LOG.debug("initializing the pump");
    
    GeneralSettings settings = GeneralSettings.instance(); 
    
    int nombreSeringue = settings.getNbSeringue();
    double diametreSeringue = settings.getDiametreSeringue();
    double volumeMaxSeringue = settings.getVolumeMaxSeringue();
    double debitMaxPousseSeringue = settings.getDebitMaxPousseSeringue();
    
    this.interfacePousseSeringue = interfacePousseSeringue ;
    this.valves = valves ;
    // attention les débits sont par défaut ceux en mémoire du pousse seringue.
    this.debitActuelAspiration = 0. ; 
    // un débit de zero est impossible voir interfacePousseSeringue.
    this.debitActuelRefoulement = 0. ; 
                         
    // ferme ttes les ev
    try
    {
      this.valves.toutFermer(); ;
    }
    catch(ParaComException e)
    {
      String msg = "error while closing all the isolation valves";
      throw new InitializationException(msg, e);
    }
    
    this.numEvActuelle = 0 ;
    this._pause = false ;

    // ------ Conditions initiales : Seringue vidée manuellement -------

    // le volume de sécurité n'a pas été aspiré
    this.flagVolSecu = false ;

    if (nombreSeringue != 1 && nombreSeringue != 2)
    {
      String msg = String.format("unsupported number of syringe '%s'",
                                 nombreSeringue);
      throw new InitializationException(msg);
    }

    if (volumeInitiale < 0.)
    {
      String msg = String.format("the value of the initial volume '%s' cannot be negative or null",
          volumeInitiale);
      throw new InitializationException(msg);
    }
    
    if (volumeMaxSeringue <= 0.)
    {
      String msg = String.format("the value of the maximum syringe volume '%s' cannot be negative of null",
          volumeMaxSeringue);
      throw new InitializationException(msg);
    }
    
    // dépendant de la section de tuyaux utilisé et le diamètre de seringue
    // différent de débitIntrinsèque qui dépend uniquement du diamètre de la seringue
    if (debitMaxPousseSeringue <= 0.)
    {
      String msg = String.format("the value of the maximum rate '%s' cannot be negative of null", debitMaxPousseSeringue);
      throw new InitializationException(msg);
    }
    else if (debitMaxPousseSeringue > PumpController.debitMaxIntrinseque(diametreSeringue))
    {
      String msg = String.format("the value of the maximum rate '%s' cannot be greater than %s",
                                 debitMaxPousseSeringue,
                                 PumpController.debitMaxIntrinseque(diametreSeringue));
      throw new InitializationException(msg);
    }

    PousseSeringue._volumeReel = volumeInitiale ;
  }

  // précise si le pousse seringue est pausé
  public boolean isPaused()
  { 
    return this._pause;
  }

  // aspiration d'un volume exprimé en en mL.
  // requires 0 <= numEv <= NBEV_MAX
  public void aspiration(double volume, int numEv) throws ConfigurationException
  { 
    _LOG.debug(String.format("withdrawing volume '%s' from valve '%s'",
        volume, numEv));
    
    GeneralSettings settings = GeneralSettings.instance();
    
    // amélioration de l'algo pour éviter les pertes !!!
    // considère le volume d'une seringue
    volume /= settings.getNbSeringue() ;   

    if (! this.flagVolSecu)
    { 
      // le volume de sécurité n'a été aspiré  => aspiration du vol
      volume += VOL_SECU  ; 
      // vol aspiré ;
      this.flagVolSecu = true ;
    }
    
    volume += VOL_AJUSTEMENT ;

    this.algoAspiration(volume, numEv) ;
    this.finPompage(false);
    
    // ajustement à pleine vitesse
    this.setDebitRefoulement(settings.getDebitMaxPousseSeringue()) ;

    this.algoRefoulement(VOL_AJUSTEMENT, numEv) ; 
  }

  //refoulement d'un volume exprimé en en mL.
  //requires 0 <= numEv <= NBEV_MAX
  public void refoulement(double volume , int numEv) throws ConfigurationException
  {
    _LOG.debug(String.format("infusing volume '%s' to the valve '%s'",
        volume, numEv));
    volume /= GeneralSettings.instance().getNbSeringue() ;
    this.algoRefoulement(volume, numEv) ;
  }

  public void algoAspiration(double volume, int numEv) throws ConfigurationException
  { 
    _LOG.debug(String.format("running withdrawing subroutine for volume '%s' from valve '%s'",
        volume, numEv));
    //attention un ordre comme voli ou volw 0 correspond à une aspi/infusion sans fin.
    if (Utils.isNearZero(volume))
    {
      String msg = String.format("skip withdrawing subroutine for volume '%s'", volume);
      _LOG.debug(msg);
      return ;
    }

    if (numEv > Valves.NB_EV_MAX)
    {
      String msg = String.format("the value of the isolation valve '%s' cannot be greater than %s",
                                 numEv, Valves.NB_EV_MAX);
      throw new RuntimeException(msg);
    }
    
    double volumeMaxSeringue = GeneralSettings.instance().getVolumeMaxSeringue();

    if (volumeMaxSeringue < volume)
    {
      String msg = String.format("the volume '%s' to be pumped, cannot be greater than the maximum volume %s",
          volume, volumeMaxSeringue);
      throw new RuntimeException(msg);
    }

    PousseSeringue._volumeReel += volume ;
    
    try
    {
      // ouverture de l'ev numEv.
      this.valves.ouvrir(numEv);

      this.numEvActuelle = numEv ;

      this.interfacePousseSeringue.modeW();

      this.interfacePousseSeringue.volw(volume);

      _LOG.debug("run the pump");
      this.interfacePousseSeringue.run();
    }
    catch(SerialComException | ParaComException e)
    {
      String msg = String.format("error while withdrawing the volume '%s' from isolation valve '%s'",
          volume, numEv);
      throw new RuntimeException(msg, e);
    }
  }

  public void algoRefoulement(double volume, int numEv)

  { 
    volume = Utils.round(volume);
    
    _LOG.debug(String.format("running the infusing subroutine for volume '%s' to the valve '%s'",
        volume, numEv));
    // attention un ordre comme voli ou volw 0 correspond à une aspi/infusion sans fin.
    // si y a un problème , il faut pouvoir le détecter.
    if (Utils.isNearZero(volume))
    {
      String msg = String.format("skip infusing subroutine for volume '%s'", volume);
      _LOG.debug(msg);
      return ; 
    }
                                              
    if (numEv > Valves.NB_EV_MAX)
    {
      String msg = String.format("the value of the isolation valve '%s' cannot be greater than %s",
                                 numEv, Valves.NB_EV_MAX);
      throw new RuntimeException(msg);
    }

    if (PousseSeringue._volumeReel < volume )
    {
      String msg = String.format("the volume '%s' to be delivered cannot be greater than the maximum volume %s",
          volume, PousseSeringue._volumeReel);
      throw new RuntimeException(msg);
    }

    PousseSeringue._volumeReel -= volume ;
    
    try
    {
      // ouverture de l'ev numEv
      this.valves.ouvrir(numEv);

      this.numEvActuelle = numEv ;

      this.interfacePousseSeringue.modeI();

      this.interfacePousseSeringue.voli(volume);

      _LOG.debug("run the pump");
      this.interfacePousseSeringue.run();
    }
    catch(SerialComException | ParaComException e)
    {
      String msg = String.format("error while infusing the volume '%s' to isolation valve '%s'",
          volume, numEv);
      throw new RuntimeException(msg, e);
    }
  }

  // ne rajoute pas de VOL_SECU mais ajoute VOL_AJUSTEMENT et
  // volume n'est pas divisé par nbSeringue !!!!!!!! 
  // spécialement pour l'aspiration d'un cycle de rinçage
  //volume n'est pas divisé par nbSeringue !!!
  public void rincageAspiration(double volume, int numEv) throws ConfigurationException
  {
    _LOG.debug(String.format("rinsing with volume '%s' from valve '%s'",
        volume, numEv));
    
    GeneralSettings settings = GeneralSettings.instance();
    
    // attention si volume = _volumeMaxSeringue
    // volume + VOL_AJUSTEMENT > _volumeMaxSeringue
    // voir algoAspiration
    if ((volume + VOL_AJUSTEMENT) <= settings.getVolumeMaxSeringue())
    {
      volume += VOL_AJUSTEMENT ;
    }

    this.algoAspiration(volume, numEv) ;

    this.flagVolSecu = false ;
  }

  //réglage du débit à l'aspiration en ml / min
  //requires debit <= _debitMaxPousseSeringue
  public void setDebitAspiration(double debit) throws ConfigurationException

  { 
    _LOG.debug(String.format("setting the withdrawing rate to '%s'", debit)); 
    
    GeneralSettings settings = GeneralSettings.instance();
    
    double debitMaxPousseSeringue = settings.getDebitMaxPousseSeringue();
    
    if (debit > debitMaxPousseSeringue )
    {
      String msg = String.format("the withdrawing rate '%s' cannot be greater than the maximum rate %s",
          debit, debitMaxPousseSeringue);
      throw new RuntimeException(msg);
    }

    // tient compte du nombre de seringue.
    debit = debit/settings.getNbSeringue() ;

    if (debit != this.debitActuelAspiration)
    {
      try
      {
        this.interfacePousseSeringue.ratew(debit) ;
        this.debitActuelAspiration = debit ;
      }
      catch(SerialComException e)
      {
        String msg = String.format("error while configuring the withdrawing rate '%s'",
            debit);
        throw new RuntimeException(msg, e);
      }
    }
    else
    {
      _LOG.debug(String.format("withdrawing rate is already at %s", this.debitActuelAspiration));
    }
  }

  // réglage du débit au refoulement en ml / min.
  // requires debit <= _debitMaxPousseSeringue
  public void setDebitRefoulement(double debit) throws ConfigurationException
  {  
    _LOG.debug(String.format("setting the infusion rate to '%s'", debit));
    
    GeneralSettings settings = GeneralSettings.instance();
    
    double debitMaxPousseSeringue = settings.getDebitMaxPousseSeringue();
    int nbSeringue = settings.getNbSeringue();
    
    if(debit > debitMaxPousseSeringue)
    {
      String msg = String.format("the value of the infusion rate '%s' cannot be greater than the maximum rate %s",
                                 debit, debitMaxPousseSeringue);
      throw new RuntimeException(msg);
    }

    debit = debit/nbSeringue ;

    if (debit != this.debitActuelRefoulement)
    {  
      try
      {
        this.interfacePousseSeringue.ratei(debit) ;
        this.debitActuelRefoulement = debit ;
      }
      catch(SerialComException e)
      {
        String msg = String.format("error while configuring the infusion rate '%s'",
            debit);
        throw new RuntimeException(msg, e);
      }
    }
    else
    {
      _LOG.debug(String.format("infusion rate is already at %s", this.debitActuelRefoulement));
    }
  }
  
  public void finPompage()
  {
    this.finPompage(true);
  }
  
  // attente de la fin de l'aspiration ou refoulement
  public void finPompage(boolean fermeture)
  {
    _LOG.debug(String.format("waiting for the pump (close flag is '%s')", fermeture));
    boolean has_to_continue = false;
    do
    {
      try
      {
        Thread.sleep(100) ;
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
      
      try
      {
        has_to_continue = this.interfacePousseSeringue.running();
      }
      catch(SerialComException e)
      {
        String msg = "error while waiting for the pump";
        throw new RuntimeException(msg, e);
      }
    }
    while (has_to_continue) ;
    
    // équilibre de la pression dans les tuyaux.
    try
    {
      Thread.sleep(ATTENTE_FERMETURE_EV) ;
    }
    catch (InterruptedException e)
    {
      throw new RuntimeException(e);
    }

    if (fermeture)
    {
      // ferme ttes les ev  désactivation pour éviter depression dans le tube.
      this.fermetureEv();
      // pendant distribution <=> perte précison
      this.numEvActuelle = 0 ;
      // équilibre de la pression dans les tuyaux
      try
      {
        Thread.sleep(ATTENTE_FERMETURE_EV) ;
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
    }
    
    _LOG.debug("pump completed");
    
    // We can't pause or cancel during a withdraw as the volume of liquid
    // really withdrawn is not known until the end of the withdraw.
    // For the infusion case, the method deliver can help.
    // But we decided to check for pause or cancel only after the completion
    // of withdraws. As a matter of symmetry, we do the same for the infusion.
    ThreadControl.check();
  }
  
  public void cancel()
  {
    try
    {
      _LOG.debug("cancelling the pump");
      if (this.interfacePousseSeringue.running())
      {  
        _LOG.debug("stopping the pump");
        this.interfacePousseSeringue.stop();
        try
        {
          this.valves.toutFermer();
        }
        catch (ParaComException e)
        {
          throw new RuntimeException("error while closing the valves", e);
        }
      }
      else
      {
        _LOG.debug("the pump is already stopped");
      }
    }
    catch(SerialComException e)
    {
      String msg = "error while cancelling the pump operations";
      throw new RuntimeException(msg, e);
    }
  }
  
  public void reinit()
  {
    _LOG.debug("reinitializing the pump");
    this.vidange();
    this.finPompage(true);
  }
  
  // Arrête de la pompe
  public void pause()
  {
    try
    {
      _LOG.debug("pausing the pump");
      if (this.interfacePousseSeringue.running())
      {  
        _LOG.debug("stopping the pump");
        this.interfacePousseSeringue.stop();
        //ferme ttes les ev
        this.fermetureEv();
        this._pause = true ;
      }
      else
      {
        _LOG.debug("the pump is already stopped");
        this._pause = false ;
      }
    }
    catch(SerialComException e)
    {
      String msg = "error while stopping (pause) the pump";
      throw new RuntimeException(msg, e);
    }
  }

  // reprise après pause
  public void reprise()
  {  
    if (this._pause)
    { 
      try
      {
        _LOG.debug("resuming the pump");
        this.valves.ouvrir(this.numEvActuelle);
        this.interfacePousseSeringue.run();
        this._pause = false ;
      }
      catch(SerialComException | ParaComException e)
      {
        String msg = "error while resuming the pump";
        throw new RuntimeException(msg, e);
      }
    }
    else
    {
      _LOG.debug("the pump has nothing to resume");
    }
  }

  // retourne le volume déjà délivré.
  public double volumeDelivre() throws ConfigurationException
  { 
    try
    {
      double result = this.interfacePousseSeringue.deliver() * GeneralSettings.instance().getNbSeringue() ;
      result = Utils.round(result);
      _LOG.trace(String.format("getting '%s' of delivered volume", result));
      return result ;
    }
    catch(SerialComException e)
    {
      String msg = "error while getting the delivered volume";
      throw new RuntimeException(msg, e);
    }
  }

  // volume utile total des seringues.
  public double volumeRestant() throws ConfigurationException
  { 
    double volumeUtile = PousseSeringue._volumeReel ;

    if (this.flagVolSecu) 
    {
      volumeUtile -= VOL_SECU  ;
    }

    double result = (volumeUtile  * GeneralSettings.instance().getNbSeringue()) ;
    result = Utils.round(result);
    _LOG.debug(String.format("total raiming volume is '%s'", result));
    return result;                      
  }
  
  public boolean hasToDrain()
  {
    return (false == Utils.isNearZero(PousseSeringue._volumeReel));
  }

  // vide la seringue entièrement même le volume de sécurité dans le tuyau de refoulement.
  // vide entièrement la seringue, volume de sécurité compris.
  public void vidange()  
  { 
    _LOG.debug("draining the pump");
    // retour aux conditions initiales.
    this.flagVolSecu = false ;
    
    if(this.hasToDrain())
    {
      // vidange.
      this.algoRefoulement(PousseSeringue._volumeReel, Valves.NUM_EV_REFOULEMENT);
    }
    else
    {
      _LOG.debug("% the pump is already drained %");
    }
  }

  // renvoie le volume utile des n seringues moins VOL_SECU et VOL-AJUSTEMENT
  // par seringues.
  public double volumeMaxSeringueUtile() throws ConfigurationException
  {  
    GeneralSettings settings = GeneralSettings.instance();
    int nbSeringue = settings.getNbSeringue();
    double volumeMaxSeringue = settings.getVolumeMaxSeringue();
    
    //volume Max utile pour les n seringues !
    double result = (nbSeringue * (volumeMaxSeringue - VOL_SECU - VOL_AJUSTEMENT ) );
    result = Utils.round(result);
    _LOG.debug(String.format("computed the maximum util volume is '%s'", result));
    return  result ;
  }

  public static double volumeAjustement()
  {  
    return VOL_AJUSTEMENT ;
  }

  public static double volumeSecurite()
  {  
    return VOL_SECU ;
  }

  // fermeture des Ev Commandées.
  public void fermetureEv()
  {  
    _LOG.debug("closing the valves");
    try
    {
      this.valves.toutFermer() ;
    }
    catch(ParaComException e)
    {
      String msg = "error while closing all the isolation valves";
      throw new RuntimeException(msg);
    }
  }  
  
  @Override
  public void close() throws IOException
  {
    _LOG.debug("closing the pump");
    this.interfacePousseSeringue.close();
  }
  
  public static void main(String[] args)
  {
    // Windows 10: "COM5"
    // CentOS   7: "/dev/ttyUSB1"
    String pumpPortPath = "/dev/ttyUSB1"; // To be modified.
    JSerialComm pumpPort = new JSerialComm(pumpPortPath);
    
    // Windows 10: "COM6"
    // CentOS   7: "/dev/ttyUSB0"
    String paraComPortPath = "/dev/ttyUSB0"; // To be modified.
    JSerialComm paraComPort = new JSerialComm(paraComPortPath);
    
    double volumeInitiale = 0.;
    
    try(InterfacePousseSeringue pumpInt = new InterfacePousseSeringue(pumpPort);
        ArduinoParaCom paraCom = new ArduinoParaCom(paraComPort);
        Valves valves = new Valves(paraCom);
        PousseSeringue pump = new PousseSeringue(pumpInt, valves, volumeInitiale))
    {
      double volume = 3.;
      int numEv = Valves.NUM_EV_H2O;
      double rate = 10.;
      
      _LOG.info(String.format("setting rate to '%s'", rate));
      pump.setDebitAspiration(rate);
      pump.setDebitRefoulement(rate);      
      
      _LOG.info(String.format("withdrawing %s mL to valve %s", volume, numEv));
      pump.aspiration(volume, numEv);
      pump.finPompage();
      
      _LOG.info(String.format("infusing %s mL to valve %s", volume, numEv));
      pump.refoulement(volume, numEv);
      pump.finPompage();
      
      _LOG.info(String.format("withdrawing %s mL to valve %s", volume, numEv));
      pump.aspiration(volume, numEv);
      pump.finPompage();
      
      _LOG.info(String.format("infusing %s mL to valve %s", volume, numEv));
      pump.refoulement(volume, numEv);
      
      // We can't test pause when withdrawing because the algorithm of withdrawing 
      // waits until the pump is done.
      Thread.sleep(1500);
      _LOG.info("pausing");
      pump.pause();
      
      double delivered = pumpInt.deliver();
      _LOG.info(String.format("delivered %s mL", delivered));
      
      Thread.sleep(5000);
      _LOG.info("resuming");
      pump.reprise();
      pump.finPompage();
      
      delivered = pumpInt.deliver();
      _LOG.info(String.format("delivered %s mL", delivered));
      
      _LOG.info(String.format("withdrawing %s mL to valve %s", volume, numEv));
      pump.aspiration(volume, numEv);
      pump.finPompage();
      
      // We can't test cancellation when infusing or withdrawing because
      // this._realVolume is assigned before the pump realizes the operation.
      _LOG.info("cancelling");
      pump.cancel();
      
      delivered = pumpInt.deliver();
      _LOG.info(String.format("delivered %s mL", delivered));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
