package fr.gardoll.ace.controller.core;

import java.util.Optional ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.settings.ConfigurationException ;
import fr.gardoll.ace.controller.settings.ParametresSession ;

public abstract class AbstractCancelableToolControl extends AbstractCloseableToolControl implements ToolControl
{
  private static final Logger _LOG = Log.HIGH_LEVEL;
  
  public AbstractCancelableToolControl(ParametresSession parametresSession,
                                  boolean hasPump, boolean hasAutosampler,
                                  boolean hasValves)
      throws InitializationException, ConfigurationException
  {
    super(parametresSession, hasPump, hasAutosampler, hasValves);
  }

  @Override
  public void cancel()
  {
    Runnable r = new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          AbstractCancelableToolControl.this.getState().askCancellation();
        }
        catch (Exception e)
        {
          String msg = "cancel has crashed";
          _LOG.fatal(msg, e);
          // Don't change the state of the running thread
          // by calling AbstractStateFullToolControl.this.handleException.
          AbstractCancelableToolControl.this.notifyError(msg, e);
        }
      }
    } ;
    
    new Thread(r).start();
  }
  
  @Override
  public void cancelOperations()
  {
    _LOG.info("running cancel procedure");
    this.notifyAction(new Action(ActionType.CANCELING, null));
    
    // First cancel.
    
    if(this._hasAutosampler)
    {
      this._passeur.cancel();
    }
    
    if(this._hasPump)
    {
      this._pousseSeringue.cancel();
    }
    
    _LOG.debug("thread is cancelled");
    this.notifyAction(new Action(ActionType.CANCEL_DONE, null));
    
    // Then reinitialize.
    this.reinitOperations();
  }
  
  @Override
  // Thread must be terminated.
  public void reinit()
  {
    Runnable r = new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          AbstractCancelableToolControl.this.getState().reinit();
        }
        catch (Exception e)
        {
          String msg = "reinitializing has crashed";
          _LOG.fatal(msg, e);
          // Reinit takes place in the main thread, there isn't any operating
          // thread control that is running. So it is safe to change the state here.
          AbstractCancelableToolControl.this.handleException(msg, e);
        }
      }
    } ;
    
    new Thread(r).start();
  }
  
  @Override
  void reinitOperations()
  {
    _LOG.info("reinitializing all devices");
    this.notifyAction(new Action(ActionType.REINIT, Optional.empty()));
    
    if(this._hasAutosampler) // Reinit autosampler and pump.
    {
      this._passeur.reinit();
      
      if(this._hasPump)
      {
        if(this._pousseSeringue.hasToDrain())
        {
          this._passeur.moveArmToTrash(); // Get the arm to the trash.
          this._passeur.finMoveBras();
          this._pousseSeringue.reinit(); // Drain the pump to the trash.
          this._passeur.moveButeBras(); // Get the arm to the top.
          this._passeur.finMoveBras();
          this._passeur.setOrigineBras();
        }
        else
        {
          _LOG.debug("pump is already drained");
        }
      }
    }
    else if(this._hasPump) // Reinit pump only.
    {
      if(this._pousseSeringue.hasToDrain())
      {
        this._pousseSeringue.reinit();
      }
      else
      {
        _LOG.debug("pump is already drained");
      }
    }
    
    if(this._hasValves)
    {
      // Nothing to do.
    }
    
    _LOG.debug("reinitialisation done");
    this.notifyAction(new Action(ActionType.REINIT_DONE, Optional.empty()));
  }
  
  @Override
  void pauseOperations()
  {
    throw new UnsupportedOperationException("pause operations is not implemented");
  }
  
  @Override
  void resumeOperations()
  {
    throw new UnsupportedOperationException("resume operations is not implemented");
  }
}
