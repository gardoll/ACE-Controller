package fr.gardoll.ace.controller.tools.pump;

import java.util.SortedSet ;
import java.util.TreeSet ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.junit.jupiter.api.AfterEach ;
import org.junit.jupiter.api.BeforeAll ;
import org.junit.jupiter.api.BeforeEach ;
import org.junit.jupiter.api.Test ;

import fr.gardoll.ace.controller.core.ParametresSession ;
import fr.gardoll.ace.controller.ui.PausableJPanelObserverStub ;

class PumpToolTest
{
  private static final Logger _LOG = LogManager.getLogger(PumpToolTest.class.getName());
  
  PumpToolControl _ctrl = null;
  PausableJPanelObserverStub _toolPanel = null;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception
  {
    ParametresSession.isAutomatedTest = true;
  }

  @BeforeEach
  void setUp() throws Exception
  {
    _LOG.info("******************** setup");
    ParametresSession parametresSession = ParametresSession.getInstance();
    this._ctrl = new PumpToolControl(parametresSession);
    this._toolPanel = new PausableJPanelObserverStub(this._ctrl);
    this._ctrl.addControlPanel(this._toolPanel);
    this._toolPanel.waitPanel();
  }

  @AfterEach
  void tearDown() throws Exception
  {
    _LOG.info("******************** teardown");
    this._ctrl.close();
    this._toolPanel.waitPanel();
    ParametresSession.getInstance().close();
  }
  
  @Test
  void test1n()
  {
    _LOG.info("******************** test1n clean line 1");
    SortedSet<Integer> lines = new TreeSet<Integer>();
    lines.add(1);
    this._ctrl.start(lines, 5);
    this._toolPanel.waitPanel();
  }
  
  @Test
  void test2n()
  {
    _LOG.info("******************** test2n clean line 1 to 3");
    SortedSet<Integer> lines = new TreeSet<Integer>();
    lines.add(1);
    lines.add(2);
    lines.add(3);
    this._ctrl.start(lines, 5);
    this._toolPanel.waitPanel();
  }
}