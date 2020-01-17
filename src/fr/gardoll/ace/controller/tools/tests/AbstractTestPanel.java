package fr.gardoll.ace.controller.tools.tests ;

public class AbstractTestPanel extends javax.swing.JPanel
{

  private static final long serialVersionUID = 3863282861526799243L ;

  /**
   * Creates new form TestPanel
   */
  public AbstractTestPanel()
  {
    initComponents() ;
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

    textScrollPanel = new javax.swing.JScrollPane() ;
    operationTextPane = new javax.swing.JTextPane() ;
    controlPanel = new javax.swing.JPanel() ;
    runCancelButton = new javax.swing.JButton() ;
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
        new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767)) ;
    CloseButton = new javax.swing.JButton() ;

    setPreferredSize(new java.awt.Dimension(780, 460)) ;
    setLayout(new java.awt.GridBagLayout()) ;

    textScrollPanel.setViewportView(operationTextPane) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(textScrollPanel, gridBagConstraints) ;

    controlPanel.setLayout(new java.awt.GridBagLayout()) ;

    runCancelButton.setText("run") ;
    runCancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        runCancelButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    controlPanel.add(runCancelButton, gridBagConstraints) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    controlPanel.add(filler1, gridBagConstraints) ;

    CloseButton.setText("close") ;
    CloseButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        CloseButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 2 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    controlPanel.add(CloseButton, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 0.1 ;
    gridBagConstraints.weighty = 0.1 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(controlPanel, gridBagConstraints) ;
  }// </editor-fold>

  private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    // TODO add your handling code here:
  }

  private void runCancelButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    // TODO add your handling code here:
  }

  // Variables declaration - do not modify
  private javax.swing.JButton CloseButton ;
  private javax.swing.JPanel controlPanel ;
  private javax.swing.Box.Filler filler1 ;
  private javax.swing.JTextPane operationTextPane ;
  private javax.swing.JButton runCancelButton ;
  private javax.swing.JScrollPane textScrollPanel ;
  // End of variables declaration
}
