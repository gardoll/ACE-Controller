package fr.gardoll.ace.controller.tools.autosampler ;

import java.io.File ;
import java.util.Date ;

import javax.swing.JFileChooser ;
import javax.swing.filechooser.FileFilter ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.column.Colonne ;
import fr.gardoll.ace.controller.core.InitializationException ;
import fr.gardoll.ace.controller.core.ParametresSession ;
import fr.gardoll.ace.controller.core.Utils ;
import fr.gardoll.ace.controller.ui.AbstractJPanelObserver ;
import fr.gardoll.ace.controller.ui.Action ;
import fr.gardoll.ace.controller.ui.ControlPanel ;
import fr.gardoll.ace.controller.ui.Observer ;
import fr.gardoll.ace.controller.ui.UiUtils ;

public class AutosamplerToolPanel extends AbstractJPanelObserver
    implements ControlPanel, Observer
{
  private static final long serialVersionUID = -3286878572452437372L ;
  
  private static final Logger _LOG = LogManager.getLogger(AutosamplerToolPanel.class.getName());

  private final AutosamplerToolControl _ctrl ;

  /**
   * Creates new form AutosamplerToolPanel
   */
  public AutosamplerToolPanel(AutosamplerToolControl ctrl)
  {
    super(ctrl);
    this._ctrl = ctrl;
    initComponents() ;
    initCustom();
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

    logPanel = new javax.swing.JPanel() ;
    logTextScrollPane = new javax.swing.JScrollPane() ;
    logTextArea = new javax.swing.JTextArea() ;
    buttonPanel = new javax.swing.JPanel() ;
    cancelButton = new javax.swing.JButton() ;
    pauseToggleButton = new javax.swing.JToggleButton() ;
    closeButton = new javax.swing.JButton() ;
    buttonFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0)) ;
    carouselPanel = new javax.swing.JPanel() ;
    positionPanel = new javax.swing.JPanel() ;
    positionLabel = new javax.swing.JLabel() ;
    positionSpinner = new javax.swing.JSpinner() ;
    positionButton = new javax.swing.JButton() ;
    arrowPanel = new javax.swing.JPanel() ;
    leftButton = new javax.swing.JButton() ;
    rightButton = new javax.swing.JButton() ;
    manualPanel = new javax.swing.JPanel() ;
    manualButton = new javax.swing.JButton() ;
    armPanel = new javax.swing.JPanel() ;
    freePositionPanel = new javax.swing.JPanel() ;
    freePositionLabel = new javax.swing.JLabel() ;
    freePositionSpinner = new javax.swing.JSpinner() ;
    freePositionButton = new javax.swing.JButton() ;
    vibrationPanel = new javax.swing.JPanel() ;
    vibrationButton = new javax.swing.JButton() ;
    refPositionPanel = new javax.swing.JPanel() ;
    toTopStopLabel = new javax.swing.JLabel() ;
    toTopStopButton = new javax.swing.JButton() ;
    aboveColumnLabel = new javax.swing.JLabel() ;
    openFileChooserButton = new javax.swing.JButton() ;
    aboveColumnButton = new javax.swing.JButton() ;
    toTrashBinLabel = new javax.swing.JLabel() ;
    toTrashBinButton = new javax.swing.JButton() ;

    setLayout(new java.awt.GridBagLayout()) ;

    logPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Log",
        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Lucida Grande", 0, 15))) ; // NOI18N
    logPanel.setLayout(new java.awt.GridBagLayout()) ;

    logTextScrollPane.setHorizontalScrollBarPolicy(
        javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS) ;
    logTextScrollPane.setVerticalScrollBarPolicy(
        javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS) ;

    logTextArea.setEditable(false);
    logTextArea.setColumns(20) ;
    logTextArea.setLineWrap(true);
    logTextArea.setRows(5) ;
    logTextArea.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        logTextAreaMouseClicked(evt) ;
      }
    }) ;
    logTextScrollPane.setViewportView(logTextArea) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.ipadx = 221 ;
    gridBagConstraints.ipady = 61 ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    logPanel.add(logTextScrollPane, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.gridwidth = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(logPanel, gridBagConstraints) ;

    buttonPanel.setLayout(new java.awt.GridBagLayout()) ;

    cancelButton.setText("cancel") ;
    cancelButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        cancelButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(cancelButton, gridBagConstraints) ;

    pauseToggleButton.setText("pause") ;
    pauseToggleButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        pauseToggleButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(pauseToggleButton, gridBagConstraints) ;

    closeButton.setText("close") ;
    closeButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        closeButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 3 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(closeButton, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    buttonPanel.add(buttonFiller, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 2 ;
    gridBagConstraints.gridwidth = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(buttonPanel, gridBagConstraints) ;

    carouselPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder(null, "Carousel controls",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Lucida Grande", 0, 15))) ; // NOI18N
    carouselPanel.setLayout(new java.awt.GridBagLayout()) ;

    positionPanel
        .setBorder(javax.swing.BorderFactory.createTitledBorder("Position")) ;
    positionPanel.setPreferredSize(new java.awt.Dimension(100, 50)) ;
    positionPanel.setLayout(new java.awt.GridBagLayout()) ;

    positionLabel.setText("position number") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.gridwidth = 2 ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    positionPanel.add(positionLabel, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    positionPanel.add(positionSpinner, gridBagConstraints) ;

    positionButton.setText("go") ;
    positionButton.setPreferredSize(new java.awt.Dimension(34, 26)) ;
    positionButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        positionButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    positionPanel.add(positionButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    carouselPanel.add(positionPanel, gridBagConstraints) ;

    arrowPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Quarter circle")) ;
    arrowPanel.setLayout(new java.awt.GridBagLayout()) ;

    leftButton.setText("<<") ;
    leftButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        leftButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    arrowPanel.add(leftButton, gridBagConstraints) ;

    rightButton.setText(">>") ;
    rightButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        rightButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    arrowPanel.add(rightButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    carouselPanel.add(arrowPanel, gridBagConstraints) ;

    manualPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Manual setting")) ;
    manualPanel.setLayout(new java.awt.GridBagLayout()) ;

    manualButton.setText("activate") ;
    manualButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        manualButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    manualPanel.add(manualButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    carouselPanel.add(manualPanel, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(carouselPanel, gridBagConstraints) ;

    armPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
        "Arm controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Lucida Grande", 0, 15))) ; // NOI18N
    armPanel.setLayout(new java.awt.GridBagLayout()) ;

    freePositionPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Free position")) ;
    freePositionPanel.setLayout(new java.awt.GridBagLayout()) ;

    freePositionLabel.setText("distance in millimeter") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.gridwidth = 2 ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    freePositionPanel.add(freePositionLabel, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    freePositionPanel.add(freePositionSpinner, gridBagConstraints) ;

    freePositionButton.setText("go") ;
    freePositionButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        freePositionButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    freePositionPanel.add(freePositionButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    armPanel.add(freePositionPanel, gridBagConstraints) ;

    vibrationPanel
        .setBorder(javax.swing.BorderFactory.createTitledBorder("Vibration")) ;
    vibrationPanel.setLayout(new java.awt.GridBagLayout()) ;

    vibrationButton.setText("activate") ;
    vibrationButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        vibrationButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    vibrationPanel.add(vibrationButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    armPanel.add(vibrationPanel, gridBagConstraints) ;

    refPositionPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Referenced position")) ;
    refPositionPanel.setLayout(new java.awt.GridBagLayout()) ;

    toTopStopLabel.setText("to top stop:") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(toTopStopLabel, gridBagConstraints) ;

    toTopStopButton.setText("go") ;
    toTopStopButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        toTopStopButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(toTopStopButton, gridBagConstraints) ;

    aboveColumnLabel.setText("above column:") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(aboveColumnLabel, gridBagConstraints) ;

    openFileChooserButton.setText("open") ;
    openFileChooserButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        openFileChooserButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(openFileChooserButton, gridBagConstraints) ;

    aboveColumnButton.setText("go") ;
    aboveColumnButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        aboveColumnButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(aboveColumnButton, gridBagConstraints) ;

    toTrashBinLabel.setText("to trash bin:") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 2 ;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(toTrashBinLabel, gridBagConstraints) ;

    toTrashBinButton.setText("go") ;
    toTrashBinButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        toTrashBinButtonMouseClicked(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    refPositionPanel.add(toTrashBinButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    armPanel.add(refPositionPanel, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(armPanel, gridBagConstraints) ;
  }// </editor-fold>

  private void closeButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this.close(UiUtils.getParentFrame(this));
  }

  private void positionButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    Integer position = null;
    try
    {
      this.positionSpinner.commitEdit();
      position = (Integer) this.positionSpinner.getValue();
    }
    catch(Exception e)
    {
      _LOG.error(String.format("error while fetching the position spinner value: %s", e.getMessage()));
      this.positionSpinner.setValue(0);
      return;
    }
    
    this._ctrl.carouselGoPosition(position);
  }

  private void leftButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.carouselTurnLeft();
  }

  private void rightButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.carouselTurnRight();
  }

  private void manualButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.carouselFreeMove();
  }

  private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this.cancel();
  }

  private void pauseToggleButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this.pauseAndResume(this.pauseToggleButton);
  }

    private void freePositionButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    Integer position = null;
    try
    {
      this.freePositionSpinner.commitEdit();
      position = (Integer) this.freePositionSpinner.getValue();
    }
    catch(Exception e)
    {
      _LOG.error(String.format("error while fetching the position spinner value: %s", e.getMessage()));
      this.freePositionSpinner.setValue(0);
      return;
    }
    
    this._ctrl.armFreeMove(position);
  }

  private void vibrationButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.vibrate();
  }

  private void toTopStopButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.armGoButee();
  }

  private void openFileChooserButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    int returnValue = fileChooser.showOpenDialog(this);
    if(returnValue == JFileChooser.APPROVE_OPTION)
    {
      File file = fileChooser.getSelectedFile().getAbsoluteFile();
      _LOG.info(String.format("selected column file: '%s'", file));
      try
      {
        this._ctrl.openColumn(file.toPath());
      }
      catch (InitializationException e)
      {
        String msg = "error while openning column file";
        _LOG.error(String.format("%s: %s", msg, e.getMessage()));
        this.handleException(msg, e);
      }
    }
    else
    {
      _LOG.debug("cancel column file openning");
    }
  }

  private void aboveColumnButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.armGoColonne();
  }

  private void toTrashBinButtonMouseClicked(java.awt.event.MouseEvent evt)
  {
    this._ctrl.armGoTrash();
  }

  private void logTextAreaMouseClicked(java.awt.event.MouseEvent evt)
  {
    if(evt.getClickCount() == 2)
    {
      logTextArea.setText(null);
    }
  }

  // Variables declaration - do not modify
  private javax.swing.JButton aboveColumnButton ;
  private javax.swing.JLabel aboveColumnLabel ;
  private javax.swing.JPanel armPanel ;
  private javax.swing.JPanel arrowPanel ;
  private javax.swing.Box.Filler buttonFiller ;
  private javax.swing.JPanel buttonPanel ;
  private javax.swing.JButton cancelButton ;
  private javax.swing.JPanel carouselPanel ;
  private javax.swing.JButton closeButton ;
  private javax.swing.JButton freePositionButton ;
  private javax.swing.JLabel freePositionLabel ;
  private javax.swing.JPanel freePositionPanel ;
  private javax.swing.JSpinner freePositionSpinner ;
  private javax.swing.JButton leftButton ;
  private javax.swing.JPanel logPanel ;
  private javax.swing.JTextArea logTextArea ;
  private javax.swing.JScrollPane logTextScrollPane ;
  private javax.swing.JButton manualButton ;
  private javax.swing.JPanel manualPanel ;
  private javax.swing.JButton openFileChooserButton ;
  private javax.swing.JToggleButton pauseToggleButton ;
  private javax.swing.JButton positionButton ;
  private javax.swing.JLabel positionLabel ;
  private javax.swing.JPanel positionPanel ;
  private javax.swing.JSpinner positionSpinner ;
  private javax.swing.JPanel refPositionPanel ;
  private javax.swing.JButton rightButton ;
  private javax.swing.JButton toTopStopButton ;
  private javax.swing.JLabel toTopStopLabel ;
  private javax.swing.JButton toTrashBinButton ;
  private javax.swing.JLabel toTrashBinLabel ;
  private javax.swing.JButton vibrationButton ;
  private javax.swing.JPanel vibrationPanel ;
  // End of variables declaration
  
  private final JFileChooser fileChooser = new JFileChooser();
  
  @Override
  public void enableControl(boolean isEnable)
  {
    aboveColumnButton.setEnabled(isEnable);
    freePositionButton.setEnabled(isEnable);
    leftButton.setEnabled(isEnable);
    manualButton.setEnabled(isEnable);
    openFileChooserButton.setEnabled(isEnable);
    positionButton.setEnabled(isEnable);
    rightButton.setEnabled(isEnable);
    toTopStopButton.setEnabled(isEnable);
    toTrashBinButton.setEnabled(isEnable);
    vibrationButton.setEnabled(isEnable);
    closeButton.setEnabled(isEnable);
    
    // Disable cancel and pause when controls are enable.
    cancelButton.setEnabled(! isEnable);
    pauseToggleButton.setEnabled( ! isEnable);
  }

  private void initCustom()
  {
    this.fileChooser.setDialogTitle("select column file");
    this.fileChooser.setCurrentDirectory(ParametresSession.COLUMN_DIR_PATH.toFile());
    this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    this.fileChooser.setMultiSelectionEnabled(false);
    this.fileChooser.setFileFilter(new FileFilter() 
    {
      @Override
      public boolean accept(File f)
      {
        String fileName = f.getName();
        String file_extention = Utils.getFileExtention(fileName);
        return Colonne.COLUMN_FILE_EXTENTION.equals(file_extention);
      }

      @Override
      public String getDescription()
      {
        return String.format("column file *.%s", Colonne.COLUMN_FILE_EXTENTION);
      }
    });
    
    this.enableControl(true);
  }
  
  private void addToUi(String msg)
  {
    this.logTextArea.append(msg);
  }
  
  @Override
  protected void processAction(Action action)
  {
    String msg = null;
    
    switch(action.type)
    {
      case ARM_MOVING:
      {
        msg = "arm is moving";
        break ;
      }
      
      case ARM_END_MOVING:
      {
        msg = "arm reached the position";
        break;
      }
      
      case CANCEL:
      {
        msg = "cancel";
        break ;
      }
      
      case CAROUSEL_MOVING:
      {
        msg = String.format("carousel is moving to position %s", action.data);
        break ;
      }
      
      case CAROUSEL_RELATIVE_MOVING:
      {
        msg = String.format("carousel is moving %s positions", action.data);
        break;
      }
      
      case CAROUSEL_END_MOVING:
      {
        msg = "carousel reached the position";
        break;
      }
      
      case PAUSE:
      {
        msg = "pause";
        break ;
      }
      
      case RESUME:
      {
        msg = "resuming";
        break ;
      }
      
      case WAIT_CANCEL:
      {
        msg = "waiting for cancellation";
        break ;
      }
      
      case WAIT_PAUSE:
      {
        msg = "waiting for pause";
        break ;
      }

      case WITHDRAWING:
      case INFUSING:
      case END:
      default:
      {
        _LOG.debug(String.format("nothing to do with action type '%s'", action.type));
        return ;
      }
    }
    
    this.addToUi(String.format("%s > %s\n", _DATE_FORMATTER.format(new Date()), msg));
  }
}