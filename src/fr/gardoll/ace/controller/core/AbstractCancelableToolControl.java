package fr.gardoll.ace.controller.core;

import java.util.Optional ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public abstract class AbstractCancelableToolControl extends AbstractCloseableToolControl implements ToolControl
{
  private static final Logger _LOG = LogManager.getLogger(AbstractCancelableToolControl.class.getName());
  
  public AbstractCancelableToolControl(ParametresSession parametresSession,
                                  boolean hasPump, boolean hasAutosampler,
                                  boolean hasValves)
      throws InitializationException, InterruptedException
  {
    super(parametresSession, hasPump, hasAutosampler, hasValves);
  }

  @Override
  void cancelOperations() throws InterruptedException
  {
    throw new UnsupportedOperationException("cancel operations is not implemented");
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
          // thread that is running. So it is safe to change the state here.
          AbstractCancelableToolControl.this.handleException(msg, e);
        }
      }
    } ;
    
    new Thread(r).start();
  }
  
  @Override
  void reinitOperations() throws InterruptedException
  {
    _LOG.info("reinitializing all operations");
    this.notifyAction(new Action(ActionType.REINIT, Optional.empty()));
    
    if(this._hasAutosampler)
    {
      this._passeur.reinit();
    }
    
    if(this._hasPump)
    {
      this._pousseSeringue.reinit();
    }
    
    if(this._hasValves)
    {
      // Nothing to do.
    }
    
    this.notifyAction(new Action(ActionType.REINIT_DONE, Optional.empty()));
  }
  
  @Override
  void pauseOperations() throws InterruptedException
  {
    throw new UnsupportedOperationException("pause operations is not implemented");
  }
  
  @Override
  void resumeOperations() throws InterruptedException
  {
    throw new UnsupportedOperationException("resume operations is not implemented");
  }

  protected void start(ThreadControl thread)
  {
    this.getState().start(thread);
  }
  
  @Override
  public void close()
  {
    Runnable r = new Runnable()
    {
      @Override
      public void run()
      {
        _LOG.debug("running the close operations");
        try
        {
          AbstractCancelableToolControl.this.getState().close();
        }
        catch (Exception e)
        {
          String msg = "close operations have crashed";
          _LOG.fatal(msg, e);
          // close takes place in the main thread, there isn't any operating
          // thread that is running. So it is safe to change the state here.
          AbstractCancelableToolControl.this.handleException(msg, e);
        }
      }
    } ;
    
    new Thread(r).start();
  }
  
  @Override
  public void addControlPanel(ControlPanel obs)
  {
    super.addControlPanel(obs);
    this.getState().addControlPanel(obs);
  }

  @Override
  public void removeControlPanel(ControlPanel obs)
  {
    super.removeControlPanel(obs);
    this.getState().removeControlPanel(obs);
  }
  
  @Override
  protected void handleException(String msg, Exception e)
  {
    super.handleException(msg, e);
    this.getState().crash();
  }
}
