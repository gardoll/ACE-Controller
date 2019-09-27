package fr.gardoll.ace.controller.tools.pump;

import java.util.Optional ;
import java.util.SortedSet ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.autosampler.Passeur ;
import fr.gardoll.ace.controller.core.AbstractStateFullToolControl ;
import fr.gardoll.ace.controller.core.AbstractStateToolControl ;
import fr.gardoll.ace.controller.core.AbstractThreadControl ;
import fr.gardoll.ace.controller.core.Action ;
import fr.gardoll.ace.controller.core.ActionType ;
import fr.gardoll.ace.controller.core.CancellationException ;
import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.ParametresSession ;
import fr.gardoll.ace.controller.pump.PousseSeringue ;
import fr.gardoll.ace.controller.valves.Valves ;

public class PumpToolControl extends AbstractStateFullToolControl
{
  private static final Logger _LOG = LogManager.getLogger(PumpToolControl.class.getName());
  
  public PumpToolControl(ParametresSession parametresSession)
      throws InitializationException, InterruptedException
  {
    super(parametresSession, true, true, true) ;
  }
  
  void start(SortedSet<Integer> lines, int volume)
  {
    ParametresSession parametresSession = ParametresSession.getInstance() ;
    double volMax = parametresSession.volumeMaxSeringue();
    
    if(volume > volMax ||
       volume <= 0)
    {
      String msg = String.format("volume must be greater than zero but less than %s, got '%s' mL", volMax, volume);
      _LOG.error(msg);
      this.notifyError(msg);
      return;
    }
    
    if(lines.isEmpty())
    {
      String msg = "select at least one line";
      _LOG.error(msg);
      this.notifyError(msg);
      return;
    }
    
    for(Integer line: lines)
    {
      if(line > Valves.NB_EV_MAX)
      {
        String msg = String.format("line number cannot be greater that %s, got '%s'",
            Valves.NB_EV_MAX, line);
        _LOG.error(msg);
        this.notifyError(msg);
        return;
      }
    }
    
    try
    {
      pumpThread thread = new pumpThread(this, lines, volume);
      _LOG.debug("starting pump thread");
      this.start(thread);
    }
    catch(Exception e)
    {
      String msg = "pump thread start has crashed";
      _LOG.fatal(msg, e);
      this.handleException(msg, e);
    }
  }

  @Override
  protected void closeOperations() throws InterruptedException
  {
    _LOG.debug("drain the pump");
    this.notifyAction(new Action(ActionType.DRAIN_PUMP, Optional.empty()));
    this._pousseSeringue.reinit();
  }
}

class pumpThread extends AbstractThreadControl
{
  private static final Logger _LOG = LogManager.getLogger(pumpThread.class.getName());
  
  private int _volume ;
  private SortedSet<Integer> _lines ;

  public pumpThread(AbstractStateToolControl toolCtrl,
                    SortedSet<Integer> lines,
                    int volume)
  {
    super(toolCtrl) ;
    this._lines = lines;
    this._volume = volume;
  }

  @Override
  protected void threadLogic() throws InterruptedException,
      CancellationException, InitializationException, Exception
  {
    ParametresSession parametresSession = ParametresSession.getInstance() ;
    Passeur autosampler = parametresSession.getPasseur();
    PousseSeringue pump = parametresSession.getPousseSeringue();
    
    _LOG.debug("move arm upper limit");
    Action action = new Action(ActionType.ARM_MOVING, Optional.empty()) ;
    this._toolCtrl.notifyAction(action) ;
    autosampler.moveButeBras();//sans setOrigineBras() inclus !
    
    // XXX
    this.checkCancel();
    this.checkPause();
    
    autosampler.finMoveBras();
    autosampler.setOrigineBras(); //mettre le bras en fin de butée car attention débordement poubelle !!!
    
    // XXX
    this.checkCancel();
    this.checkPause();
    
    action = new Action(ActionType.USR_MSG, Optional.of("set maximum pump rate"));
    this._toolCtrl.notifyAction(action) ;
    pump.setDebitAspiration(parametresSession.debitMaxPousseSeringue());
    pump.setDebitRefoulement(parametresSession.debitMaxPousseSeringue());

    for (Integer line: this._lines)
    {  
      String msg = String.format("begin to clean line %s", line);
      _LOG.debug(msg);
      action = new Action(ActionType.USR_MSG, Optional.of(msg));
      this._toolCtrl.notifyAction(action);
      
      // XXX
      this.checkCancel();
      this.checkPause();
      
      action = new Action(ActionType.INFUSING, Optional.of(this._volume));
      this._toolCtrl.notifyAction(action);
      pump.rincageAspiration(this._volume, line.intValue());
      pump.finPompage();
      
      // XXX
      this.checkCancel();
      this.checkPause();
      
      action = new Action(ActionType.WITHDRAWING, Optional.of(this._volume));
      this._toolCtrl.notifyAction(action);
      pump.vidange();
      pump.finPompage();
    }

    String msg = "end";
    _LOG.debug(msg);
    action = new Action(ActionType.USR_MSG, Optional.of(msg));
    this._toolCtrl.notifyAction(action) ;
  }
}