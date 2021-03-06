package fr.gardoll.ace.controller.tools.main;

import javax.swing.JPanel ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.core.Utils ;
import fr.gardoll.ace.controller.settings.ParametresSession ;
import fr.gardoll.ace.controller.ui.AbstractToolFrame ;

public class MainToolFrame extends AbstractToolFrame
{
  private static final long serialVersionUID = 3741510571374337328L ;
  private static final Logger _LOG = Log.HIGH_LEVEL;
  
  public MainToolFrame(JPanel mainPanel)
  {
    super(mainPanel) ;
  }

  public static void main(String[] args)
  {
    _LOG.info("starting the main tool");
    
    try(ParametresSession parametresSession = ParametresSession.getInstance())
    {
      MainToolPanel mainPanel = new MainToolPanel(parametresSession);
      MainToolFrame tool = new MainToolFrame(mainPanel);
      tool.setVisible(true);
    }
    catch (Exception e)
    {
      String msg = null;
      
      if(e.getCause() instanceof InitializationException)
      {
        msg = "initialisation of the main tool has crashed";
      }
      else
      {
        msg = "main tool has crashed";
      }
      
      _LOG.fatal(msg, e);
      Utils.reportError(msg, e);
    }
    finally
    {
      _LOG.info("shutdown the main tool");
    }
  }
}
