package fr.gardoll.ace.controller.tools.extraction;

import java.util.Optional ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.autosampler.Passeur ;
import fr.gardoll.ace.controller.core.AbstractPausableToolControl ;
import fr.gardoll.ace.controller.core.Action ;
import fr.gardoll.ace.controller.core.ActionType ;
import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.ParametresSession ;

public class ExtractionToolControl extends AbstractPausableToolControl
{
  private static final Logger _LOG = LogManager.getLogger(ExtractionToolControl.class.getName());
  
  public ExtractionToolControl(ParametresSession parametresSession)
                            throws InitializationException, InterruptedException
  {
    super(parametresSession, true, true, true) ;
  }

  void start(InitSession initSession)
  {
    Commandes commandes = null;
    
    try
    {
      _LOG.debug("instantiate commandes");
      commandes = new Commandes(this, initSession.protocol.colonne);
    }
    catch(Exception e)
    {
      String msg = "initialization of commandes has crashed";
      _LOG.fatal(msg, e);
      this.handleException(msg, e);
    }
    
    try
    {
      ExtractionThreadControl thread = new ExtractionThreadControl(this,
          initSession, commandes);
      _LOG.debug("starting extraction thread");
      this.start(thread);
    }
    catch(Exception e)
    {
      String msg = "extraction thread start has crashed";
      _LOG.fatal(msg, e);
      this.handleException(msg, e);
    }
  }
  
  @Override
  protected void closeOperations() throws InterruptedException
  {
    _LOG.debug("controller has nothing to do while closing the tool");
    this.notifyAction(new Action(ActionType.CLOSING, Optional.empty()));
  }
  
  //déplace le carrousel pour rendre accessible les côté du carrousel
  //qui ne le sont pas
  //attention si un thread est sur pause, l'appel par un autre thread
  //de cette fonction sera piégé dans la boucle de finMoveBras !!!
  private void presentationPasseur(int sens) throws InterruptedException,
                                                    InitializationException
  {
    Passeur passeur = ParametresSession.getInstance().getPasseur();
    
    passeur.moveButeBras();
    passeur.finMoveBras();

    if (sens >= 0)
    {
      //par la droite
      passeur.moveCarrouselRelatif(ParametresSession.NB_POSITION) ;
    }
    else
    {
      //par la gauche
      passeur.moveCarrouselRelatif( -1 * ParametresSession.NB_POSITION) ;
    }

    passeur.finMoveCarrousel();
  }
}
