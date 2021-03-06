package fr.gardoll.ace.controller.tools.editors.protocol ;

import java.io.File ;
import java.nio.file.Path ;
import java.util.Map.Entry ;
import java.util.Optional ;
import java.util.SortedMap ;

import javax.swing.JFileChooser ;
import javax.swing.SpinnerNumberModel ;
import javax.swing.event.ChangeEvent ;
import javax.swing.event.ChangeListener ;
import javax.swing.filechooser.FileFilter ;
import javax.swing.table.AbstractTableModel ;
import javax.swing.table.TableModel ;

import org.apache.commons.lang3.tuple.Pair ;
import org.apache.logging.log4j.Logger ;

import fr.gardoll.ace.controller.core.Log ;
import fr.gardoll.ace.controller.core.Utils ;
import fr.gardoll.ace.controller.protocol.Protocol ;
import fr.gardoll.ace.controller.settings.ConfigurationException ;
import fr.gardoll.ace.controller.settings.GeneralSettings ;

public class VolumeProtocolPanel extends javax.swing.JPanel
{
  private static Logger _LOG = Log.HIGH_LEVEL;

  private static final long serialVersionUID = 1401413721442111518L ;
  private static int _DEFAULT_STEP = 1;
  
  private static String[] _TABLE_COLUMN_NAMES = 
      new String[] {"acid", "procotol", "rince", "total"};
  
  private JFileChooser _fileChooser = new JFileChooser() ;

  private Optional<Path> _protocolPath = Optional.empty();

  /**
   * Creates new form VolumeProtocolPanel
   */
  public VolumeProtocolPanel()
  {
    initComponents() ;
    initCustom();
  }

  private void initCustom()
  {
    int maxColumnUtil = GeneralSettings.instance().getNbMaxColonne();
    
    SpinnerNumberModel nbColumnModel = new SpinnerNumberModel(
        GeneralSettings.DEFAULT_MIN_COLUMN, GeneralSettings.DEFAULT_MIN_COLUMN,
        maxColumnUtil, _DEFAULT_STEP) ;
    this.nbColumnSpinner.setModel(nbColumnModel) ;
    
    this._fileChooser.setDialogTitle("select column file") ;
    this._fileChooser.setCurrentDirectory(Protocol.PROTOCOL_DIR_PATH.toFile()) ;
    this._fileChooser.setMultiSelectionEnabled(false) ;
    this._fileChooser.setFileFilter(new FileFilter()
    {
      @Override
      public boolean accept(File f)
      {
        if (f.isDirectory())
        {
          return true ;
        }
        else
        {
          String fileName = f.getName() ;
          String file_extention = Utils.getFileExtention(fileName) ;
          return Protocol.PROTOCOL_FILE_EXTENTION.equals(file_extention) ;
        }
      }

      @Override
      public String getDescription()
      {
        return String.format("protocol file *.%s",
            Protocol.PROTOCOL_FILE_EXTENTION) ;
      }
    });
    
    TableModel model = new AbstractTableModel()
    {
      private static final long serialVersionUID = -4962269106929410862L ;
      
      @Override
      public String getColumnName(int col)
      {
        return _TABLE_COLUMN_NAMES[col];
      }

      @Override
      public int getRowCount()
      {
        return 0 ;
      }

      @Override
      public int getColumnCount()
      {
        return _TABLE_COLUMN_NAMES.length ;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex)
      {
        return null ;
      }
    };
    
    this.volumeTable.setModel(model);
    
    this.nbColumnSpinner.addChangeListener(new ChangeListener()
    {
      @Override
      public void stateChanged(ChangeEvent event)
      {
        if(VolumeProtocolPanel.this._protocolPath.isPresent())
        {
          try
          {
            VolumeProtocolPanel.this.nbColumnSpinner.commitEdit() ;
            Integer nbColumn = (Integer) VolumeProtocolPanel.this.nbColumnSpinner.getValue() ;
            VolumeProtocolPanel.this.fillTab(VolumeProtocolPanel.this._protocolPath.get(),
                nbColumn);
          }
          catch (Exception e)
          {
            _LOG.error(e);
          }
        }
      }
    });
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

    protocolOpenPanel = new javax.swing.JPanel() ;
    openProtocolButton = new javax.swing.JButton() ;
    protocolTextField = new javax.swing.JTextField() ;
    nbColumnPanel = new javax.swing.JPanel() ;
    nbColumnSpinner = new javax.swing.JSpinner() ;
    volumePanel = new javax.swing.JPanel() ;
    volumeScrollPane = new javax.swing.JScrollPane() ;
    volumeTable = new javax.swing.JTable() ;

    setPreferredSize(new java.awt.Dimension(780, 460)) ;
    setLayout(new java.awt.GridBagLayout()) ;

    protocolOpenPanel
        .setBorder(javax.swing.BorderFactory.createTitledBorder("Protocol")) ;
    protocolOpenPanel.setLayout(new java.awt.GridBagLayout()) ;

    openProtocolButton.setText("open") ;
    openProtocolButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        openProtocolButtonActionPerformed(evt) ;
      }
    }) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    protocolOpenPanel.add(openProtocolButton, gridBagConstraints) ;

    protocolTextField.setEditable(false) ;
    protocolTextField.setText("open protocol file") ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    protocolOpenPanel.add(protocolTextField, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 0.1 ;
    gridBagConstraints.weighty = 0.1 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(protocolOpenPanel, gridBagConstraints) ;

    nbColumnPanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Number of columns")) ;
    nbColumnPanel.setLayout(new java.awt.GridBagLayout()) ;
    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    nbColumnPanel.add(nbColumnSpinner, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 1 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 0.1 ;
    gridBagConstraints.weighty = 0.1 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(nbColumnPanel, gridBagConstraints) ;

    volumePanel.setBorder(
        javax.swing.BorderFactory.createTitledBorder("Volume in mL")) ;
    volumePanel.setLayout(new java.awt.GridBagLayout()) ;

    volumeTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] { { null, null, null, null }, { null, null, null, null },
            { null, null, null, null }, { null, null, null, null } },
        new String[] { "Title 1", "Title 2", "Title 3", "Title 4" })) ;
    volumeScrollPane.setViewportView(volumeTable) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 0 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    volumePanel.add(volumeScrollPane, gridBagConstraints) ;

    gridBagConstraints = new java.awt.GridBagConstraints() ;
    gridBagConstraints.gridx = 0 ;
    gridBagConstraints.gridy = 1 ;
    gridBagConstraints.gridwidth = 2 ;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH ;
    gridBagConstraints.weightx = 1.0 ;
    gridBagConstraints.weighty = 1.0 ;
    gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2) ;
    add(volumePanel, gridBagConstraints) ;
  }// </editor-fold>

  private void openProtocolButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    _LOG.debug("**** event protocol volume opening ****") ;

    int returnValue = this._fileChooser.showOpenDialog(this) ;
    if (returnValue == JFileChooser.APPROVE_OPTION)
    {
      File file = this._fileChooser.getSelectedFile().getAbsoluteFile() ;
      _LOG.info(String.format("selected protocol file is '%s'", file)) ;
      Path protocolPath = file.toPath();
      this._protocolPath = Optional.of(protocolPath);
      Path filename = protocolPath.getFileName() ;
      this.protocolTextField.setText(filename.toString()) ;
      Integer nbColumn = null;
      
      try
      {
        this.nbColumnSpinner.commitEdit() ;
        nbColumn = (Integer) this.nbColumnSpinner.getValue() ;
        this.fillTab(protocolPath, nbColumn);
      }
      catch(Exception e)
      {
        String msg = String.format("error while analysing the protocole '%s' with %s column(s)",
            protocolPath, nbColumn);
        _LOG.error(msg, e);
        Utils.reportError(msg, e);
      }
    }
    else
    {
      _LOG.debug("cancel protocol file openning") ;
    }
  }

  private void fillTab(Path protocolPath, int nbColumn)
      throws ConfigurationException
  {
    Protocol protocol = new Protocol(protocolPath);
    SortedMap<String, Pair<Double, Double>> analysis = protocol.analysis(nbColumn);
    final int rowCount = analysis.size();
    
    
    @SuppressWarnings("unchecked")
    final Entry<String, Pair<Double, Double>>[] entries = new Entry[rowCount]; 
    analysis.entrySet().toArray(entries);
    
    TableModel model = new AbstractTableModel()
    {
      private static final long serialVersionUID = -4962269106929410862L ;
      
      @Override
      public String getColumnName(int col)
      {
        return _TABLE_COLUMN_NAMES[col];
      }
      
      @Override
      public boolean isCellEditable(int row, int col)
      { 
        return false;
      }
      
      @Override
      public int getRowCount()
      {
        return rowCount;
      }

      @Override
      public int getColumnCount()
      {
        return _TABLE_COLUMN_NAMES.length;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex)
      {
        Object result = null;
        
        switch(columnIndex)
        {
          case 0:
          {
            result = entries[rowIndex].getKey();
            break;
          }
          
          case 1:
          {
            result = entries[rowIndex].getValue().getLeft();
            break;
          }
          
          case 2:
          {
            result = entries[rowIndex].getValue().getRight();
            break;
          }
          
          case 3:
          {
            result = entries[rowIndex].getValue().getRight() + 
                     entries[rowIndex].getValue().getLeft();
            break;
          }
        }
        
        return result;
      }
    };
    
    this.volumeTable.setModel(model);
  }

  // Variables declaration - do not modify
  private javax.swing.JPanel nbColumnPanel ;
  private javax.swing.JSpinner nbColumnSpinner ;
  private javax.swing.JButton openProtocolButton ;
  private javax.swing.JPanel protocolOpenPanel ;
  private javax.swing.JTextField protocolTextField ;
  private javax.swing.JPanel volumePanel ;
  private javax.swing.JScrollPane volumeScrollPane ;
  private javax.swing.JTable volumeTable ;
  // End of variables declaration
}
