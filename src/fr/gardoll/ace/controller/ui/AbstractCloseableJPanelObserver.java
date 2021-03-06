package fr.gardoll.ace.controller.ui;

import javax.swing.JOptionPane ;
import javax.swing.SwingUtilities ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.core.ToolControl ;
import fr.gardoll.ace.controller.settings.GeneralSettings ;
import fr.gardoll.ace.controller.settings.ParametresSession ;

public abstract class AbstractCloseableJPanelObserver extends AbstractJPanelObserver
{
  private static final long serialVersionUID = 6029765759371921962L ;
  
  private static final Logger _LOG = Log.HIGH_LEVEL;
  
  public AbstractCloseableJPanelObserver(ToolControl ctrl)
  {
    super(ctrl) ;
  }
  
  @Override
  public void enableClose(boolean isEnable)
  {
    this._isCloseEnable = isEnable;
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        AbstractCloseableJPanelObserver.this.enableCloseControl(isEnable);
      }
    });
  }
  
  @Override
  public boolean close()
  {
    int choice = JOptionPane.OK_OPTION;
    
    if(false == GeneralSettings.instance().isDebug() &&
       false == ParametresSession.isAutomatedTest)
    {
      choice = JOptionPane.showConfirmDialog(this,
          "Do you want to exit (and cancel the running operations) ?") ;
    }
    
    if (choice == JOptionPane.OK_OPTION)
    {
      _LOG.info("start closing the panel") ;
      
      this._ctrl.close();
      return true;
    }
    else
    {
      _LOG.debug("closing from the panel has been cancelled") ;
      return false;
    }
  }
  
  @Override
  public void enableStart(boolean isEnable)
  {  
    // Nothing to do.
  }
  
  @Override
  public void enablePause(boolean isEnable)
  {  
    // Nothing to do.
  }
  
  @Override
  public void enableResume(boolean isEnable)
  {  
    // Nothing to do.
  }
  
  @Override
  public void enableCancel(boolean isEnable)
  {  
    // Nothing to do.
  }
  
  @Override
  public void enableReinit(boolean isEnable)
  {  
    // Nothing to do.
  }
  
  @Override
  public void enableCarousel(boolean isEnable)
  {  
    // Nothing to do.
  }
}
