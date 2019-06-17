package fr.gardoll.ace.controller.ui;

import java.awt.Window ;
import java.text.SimpleDateFormat ;

import javax.swing.JOptionPane ;
import javax.swing.JPanel ;
import javax.swing.JToggleButton ;
import javax.swing.SwingUtilities ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.core.ParametresSession ;
import fr.gardoll.ace.controller.core.Utils ;

public abstract class AbstractJPanelObserver extends JPanel implements Observer, ControlPanel
{
  private static final long serialVersionUID = -3914638188506779210L ;
  private static final Logger _LOG = LogManager.getLogger(AbstractJPanelObserver.class.getName());
  
  protected static final SimpleDateFormat _DATE_FORMATTER = new SimpleDateFormat("HH:mm:ss");
  
  private final AbstractToolControl _ctrl ;

  abstract protected void processAction(Action action);
  
  public AbstractJPanelObserver(AbstractToolControl ctrl)
  {
    this._ctrl = ctrl;
  }
  
  protected void handleException(String msg, Exception e)
  {
    this.reportError(msg, e);
  }
  
  protected void pauseAndResume(JToggleButton pauseToggleButton)
  {
    if(pauseToggleButton.isSelected())
    {
      try
      {
        this._ctrl.pause();
        pauseToggleButton.setText("resume");
      }
      catch (Exception e)
      {
        String msg = String.format("error while pausing: %s", e.getMessage());
        _LOG.error(msg, e);
        this.handleException("error while pausing", e);
      }
    }
    else
    {
      _LOG.debug("running the resume operations");
      try
      {
        this._ctrl.unPause();
        pauseToggleButton.setText("pause");
      }
      catch (Exception e)
      {
        String msg = String.format("error while resuming operations: %s", e.getMessage());
        _LOG.error(msg, e);
        this.handleException("error while resuming", e);
      }
    }
  }
  
  protected boolean cancel()
  {
    int choice = JOptionPane.OK_OPTION;
    
    if(false == ParametresSession.getInstance().isDebug())
    {
      choice = JOptionPane.showConfirmDialog(this,
          "Do you want to cancel the running operations (and returning to the initial position) ?") ;
    }
    
    if (choice == JOptionPane.OK_OPTION)
    {
      _LOG.debug("running the panel cancelling operations") ;
      
      try
      {
        this._ctrl.cancel();
      }
      catch (Exception e)
      {
        String msg = String.format("error while cancelling: %s", e.getMessage());
        _LOG.error(msg, e);
        this.handleException("error while cancelling", e);
      }
      
      return true;
    }
    else
    {
      _LOG.debug("the panel cancelling operations has been skipped") ;
      return false;
    }
  }
  
  protected boolean close(Window parent)
  {
    int choice = JOptionPane.OK_OPTION;
    
    if(false == ParametresSession.getInstance().isDebug())
    {
      choice = JOptionPane.showConfirmDialog(this,
          "Do you want to exit (and cancel the running operations) ?") ;
    }
    
    if (choice == JOptionPane.OK_OPTION)
    {
      _LOG.debug("running the panel closing operations") ;
      try
      {
        this._ctrl.close();
        if(parent != null)
        {
          _LOG.debug("closing the parent frame and may shutdown the JVM");
          parent.dispose();
        }
      }
      catch (Exception ex)
      {
        String msg = "error while performing close operation" ;
        _LOG.fatal(String.format("%s: %s", msg, ex.getMessage()), ex) ;
        Utils.reportError(msg, ex) ;
      }
      
      return true;
    }
    else
    {
      _LOG.debug("the panel closing has been cancelled") ;
      return false;
    }
  }

  @Override
  public void majActionActuelle(Action action)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        AbstractJPanelObserver.this.processAction(action);
      }
    });
  }

  // Throwable can be null.
  @Override
  public void reportError(String msg, Throwable e)
  {
    Utils.reportError(msg, e);
  }
  
  @Override
  public void reportError(String msg)
  {
    Utils.reportError(msg, null);
  }
  
  @Override
  public void displayModalMessage(String msg)
  {
    JOptionPane.showMessageDialog(null, msg, "Information",
        JOptionPane.INFORMATION_MESSAGE);
  }
}
