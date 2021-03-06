package fr.gardoll.ace.controller.tools.main ;

import java.awt.Window ;

import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.core.Utils ;
import fr.gardoll.ace.controller.settings.ParametresSession ;
import fr.gardoll.ace.controller.tools.autosampler.AutosamplerToolFrame ;
import fr.gardoll.ace.controller.tools.editors.protocol.ProtocolFrame ;
import fr.gardoll.ace.controller.tools.editors.settings.SettingsFrame ;
import fr.gardoll.ace.controller.tools.extraction.ExtractionToolFrame ;
import fr.gardoll.ace.controller.tools.pump.PumpToolFrame ;
import fr.gardoll.ace.controller.tools.valves.ValvesToolFrame ;
import fr.gardoll.ace.controller.ui.UiUtils ;

public class MainToolPanel extends javax.swing.JPanel
{
  private static final Logger _LOG = Log.HIGH_LEVEL ;
  private static final long serialVersionUID = 1727861281958313483L ;
  private final ParametresSession _parametresSession ;

  /** Creates new form MainToolPanel */
  public MainToolPanel(ParametresSession parametresSession)
  {
    initComponents() ;
    this._parametresSession = parametresSession ;
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints ;

    tabbedPane = new javax.swing.JTabbedPane() ;
    toolsPanel = new javax.swing.JPanel() ;
    extractionButton = new javax.swing.JButton() ;
    autosamplerButton = new javax.swing.JButton() ;
    pumpButton = new javax.swing.JButton() ;
    valvesButton = new javax.swing.JButton() ;
    settingsPanel = new javax.swing.JPanel() ;
    generalSettignsjButton = new javax.swing.JButton() ;
    filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767)) ;
    filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767)) ;
    protocolButton = new javax.swing.JButton() ;
    buttonPanel = new javax.swing.JPanel() ;
    closeButton = new javax.swing.JButton() ;
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767)) ;
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767)) ;

    setPreferredSize(new java.awt.Dimension(300, 300)) ;
    setLayout(new java.awt.GridBagLayout()) ;

    toolsPanel.setLayout(new java.awt.GridBagLayout()) ;

    extractionButton.setText("extraction") ;
    extractionButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        extractionButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    toolsPanel.add(extractionButton, gridBagConstraints) ;

    autosamplerButton.setText("autosampler") ;
    autosamplerButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        autosamplerButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    toolsPanel.add(autosamplerButton, gridBagConstraints) ;

    pumpButton.setText("pump") ;
    pumpButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        pumpButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    toolsPanel.add(pumpButton, gridBagConstraints) ;

    valvesButton.setText("valves") ;
    valvesButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        valvesButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    toolsPanel.add(valvesButton, gridBagConstraints) ;

    tabbedPane.addTab("tools", toolsPanel) ;

    settingsPanel.setLayout(new java.awt.GridBagLayout()) ;

    generalSettignsjButton.setText("general") ;
    generalSettignsjButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        generalSettignsjButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    settingsPanel.add(generalSettignsjButton, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    settingsPanel.add(filler4, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    settingsPanel.add(filler5, gridBagConstraints) ;

    protocolButton.setText("protocol") ;
    protocolButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        protocolButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    settingsPanel.add(protocolButton, gridBagConstraints) ;

    tabbedPane.addTab("settings", settingsPanel) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(tabbedPane, gridBagConstraints) ;

    buttonPanel.setLayout(new java.awt.GridBagLayout()) ;

    closeButton.setText("close") ;
    closeButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        closeButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(closeButton, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(filler1, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(filler2, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 0.1 ;
    gridBagConstraints.weighty = 0.1 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(buttonPanel, gridBagConstraints) ;
  }// </editor-fold>

  private void valvesButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event valves ****");
    try
    {
      ValvesToolFrame tool ;
      tool = ValvesToolFrame.instantiate(this._parametresSession) ;
      tool.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the valves tool has crashed" ;
      }
      else
      {
        msg = "valves tool has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  private void pumpButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event pump ****");
    try
    {
      PumpToolFrame tool ;
      tool = PumpToolFrame.instantiate(this._parametresSession) ;
      tool.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the pump tool has crashed" ;
      }
      else
      {
        msg = "pump tool has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  private void autosamplerButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event autosampler ****");
    try
    {
      AutosamplerToolFrame tool ;
      tool = AutosamplerToolFrame.instantiate(this._parametresSession) ;
      tool.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the autosampler tool has crashed" ;
      }
      else
      {
        msg = "autosampler tool has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  private void extractionButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event extraction ****");
    try
    {
      ExtractionToolFrame tool ;
      tool = ExtractionToolFrame.instantiate(this._parametresSession) ;
      tool.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the extraction tool has crashed" ;
      }
      else
      {
        msg = "extraction tool has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event close ****");
    Window parent = UiUtils.getParentFrame(this) ;
    if (parent != null)
    {
      _LOG.debug("closing the parent frame and may shutdown the JVM") ;
      parent.dispose() ;
    }
  }

  private void generalSettignsjButtonActionPerformed(
      java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event general settings ****");
    try
    {
      SettingsFrame frame ;
      frame = SettingsFrame.instantiate() ;
      frame.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the settings editor has crashed" ;
      }
      else
      {
        msg = "settings editor has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  private void protocolButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event protocol ****");
    try
    {
      ProtocolFrame frame ;
      frame = ProtocolFrame.instantiate() ;
      frame.setVisible(true) ;
    }
    catch (Exception e)
    {
      String msg = null ;

      if (e.getCause() instanceof InitializationException)
      {
        msg = "intialisation of the protocol editor has crashed" ;
      }
      else
      {
        msg = "protocol editor has crashed" ;
      }

      _LOG.fatal(msg, e) ;
      Utils.reportError(msg, e) ;
    }
  }

  // Variables declaration - do not modify
  private javax.swing.JButton autosamplerButton ;
  private javax.swing.JPanel buttonPanel ;
  private javax.swing.JButton closeButton ;
  private javax.swing.JButton extractionButton ;
  private javax.swing.Box.Filler filler1 ;
  private javax.swing.Box.Filler filler2 ;
  private javax.swing.Box.Filler filler4 ;
  private javax.swing.Box.Filler filler5 ;
  private javax.swing.JButton generalSettignsjButton ;
  private javax.swing.JButton protocolButton ;
  private javax.swing.JButton pumpButton ;
  private javax.swing.JPanel settingsPanel ;
  private javax.swing.JTabbedPane tabbedPane ;
  private javax.swing.JPanel toolsPanel ;
  private javax.swing.JButton valvesButton ;
  // End of variables declaration

}
