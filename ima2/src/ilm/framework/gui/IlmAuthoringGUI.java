package ilm.framework.gui;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextArea;

public class IlmAuthoringGUI extends AuthoringGUI {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList listCurrent;
	private JList listInitial;
	private JList listExpected;
	private JTextArea propositionArea;
	private IlmForm _configForm;
	private IlmForm _metadataForm;

	public IlmAuthoringGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel currentPanel = new JPanel();
		contentPane.add(currentPanel);
		currentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCurrent = new JLabel("Current");
		currentPanel.add(lblCurrent, BorderLayout.NORTH);
		
		listCurrent = new JList();
		currentPanel.add(listCurrent, BorderLayout.CENTER);
		
		JPanel initialPanel = new JPanel();
		contentPane.add(initialPanel);
		initialPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInitial = new JLabel("Initial");
		initialPanel.add(lblInitial, BorderLayout.NORTH);
		
		listInitial = new JList();
		initialPanel.add(listInitial, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		initialPanel.add(panel, BorderLayout.SOUTH);
		
		JButton btnInitAdd = new JButton("InitAdd");
		btnInitAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addObjectToInitial();
			}
		});
		panel.add(btnInitAdd);
		
		JButton btnInitDel = new JButton("InitDel");
		btnInitDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeObjectFromInitial();
			}
		});
		panel.add(btnInitDel);
		
		JPanel expectedPanel = new JPanel();
		contentPane.add(expectedPanel);
		expectedPanel.setLayout(new BorderLayout(0, 0));
		
		listExpected = new JList();
		expectedPanel.add(listExpected, BorderLayout.CENTER);
		
		JLabel lblExpected = new JLabel("Expected");
		expectedPanel.add(lblExpected, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		expectedPanel.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnExpAdd = new JButton("ExpAdd");
		btnExpAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addObjectToExpected();
			}
		});
		panel_1.add(btnExpAdd);
		
		JButton btnExpDel = new JButton("ExpDel");
		btnExpDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeObjectFromExpected();
			}
		});
		panel_1.add(btnExpDel);
		
		JPanel configPanel = new JPanel();
		contentPane.add(configPanel);
		configPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblProposition = new JLabel("Proposition");
		configPanel.add(lblProposition, "2, 2");
		
		propositionArea = new JTextArea();
		configPanel.add(propositionArea, "2, 4, fill, fill");
		
		JButton btnConfig = new JButton(IlmProtocol.CONFIG_LIST_NODE);
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showForm(_config, IlmProtocol.CONFIG_LIST_NODE);
			}
		});
		configPanel.add(btnConfig, "2, 14");
		
		JButton btnMetadata = new JButton(IlmProtocol.METADATA_LIST_NODE);
		btnMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showForm(_metadata, IlmProtocol.METADATA_LIST_NODE);
			}
		});
		configPanel.add(btnMetadata, "2, 16");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAndExit();
			}
		});
		configPanel.add(btnOk, "2, 18");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AssignmentState) {
			ArrayList<DomainObject> objectList = ((AssignmentState)o).getList();
			// TODO need a better non-brute force way to do this
			DefaultListModel listModel = new DefaultListModel();
			for(int i = 0; i < objectList.size(); i++) {
				// TODO need a way to verify if description is a possible primary key
				listModel.addElement(objectList.get(i).getDescription());
			}
			listCurrent.setModel(listModel);
		}
	}
	
	private void addObjectToInitial() {
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < listInitial.getModel().getSize(); i++) {
			listModel.addElement(listInitial.getModel().getElementAt(i));
		}
		int[] selectedIndices = listCurrent.getSelectedIndices();
		for(int i : selectedIndices) {
			listModel.addElement(selectedIndices[i]);
		}
		listInitial.setModel(listModel);
	}
	
	private void removeObjectFromInitial() {
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < listInitial.getModel().getSize(); i++) {
			listModel.addElement(listInitial.getModel().getElementAt(i));
		}
		int[] selectedIndices = listInitial.getSelectedIndices();
		for(int i : selectedIndices) {
			listModel.remove(i);
		}
		listInitial.setModel(listModel);
	}
	
	private void addObjectToExpected() {
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < listExpected.getModel().getSize(); i++) {
			listModel.addElement(listExpected.getModel().getElementAt(i));
		}
		int[] selectedIndices = listCurrent.getSelectedIndices();
		for(int i : selectedIndices) {
			listModel.addElement(selectedIndices[i]);
		}
		listExpected.setModel(listModel);
	}
	
	private void removeObjectFromExpected() {
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < listExpected.getModel().getSize(); i++) {
			listModel.addElement(listExpected.getModel().getElementAt(i));
		}
		int[] selectedIndices = listExpected.getSelectedIndices();
		for(int i : selectedIndices) {
			listModel.remove(i);
		}
		listExpected.setModel(listModel);		
	}
	
	private void showForm(HashMap<String, String> map, String title) {
		if(title.equals(IlmProtocol.CONFIG_LIST_NODE)) {
			if(_configForm == null) {
				_configForm = new IlmForm(_config, IlmProtocol.CONFIG_LIST_NODE);
			}
			_config = _configForm.getUpdatedMap();
			_configForm.setVisible(true);
		}
		if(title.equals(IlmProtocol.METADATA_LIST_NODE)) {
			if(_metadataForm == null) {
				_metadataForm = new IlmForm(_metadata, IlmProtocol.METADATA_LIST_NODE);
			}
			_metadata = _metadataForm.getUpdatedMap();
			_metadataForm.setVisible(true);
		}
	}

	@Override
	protected String getProposition() {
		return propositionArea.getText();
	}

	@Override
	protected AssignmentState getInitialState() {
		AssignmentState state = new AssignmentState();
		for(int i = 0; i < listInitial.getModel().getSize(); i++) {
			state.add(_domainGUI.getCurrentState().getFromDescription((String)listInitial.getModel().getElementAt(i)));
		}
		return state;
	}

	@Override
	protected AssignmentState getExpectedAnswer() {
		AssignmentState state = new AssignmentState();
		for(int i = 0; i < listExpected.getModel().getSize(); i++) {
			state.add(_domainGUI.getCurrentState().getFromDescription((String)listExpected.getModel().getElementAt(i)));
		}
		return state;
	}

	@Override
	protected HashMap<String, String> getConfig() {
		_config = _configForm.getUpdatedMap();
		return _config;
	}

	@Override
	protected HashMap<String, String> getMetadata() {
		_metadata = _metadataForm.getUpdatedMap();
		return _metadata;
	}

	private void saveAndExit() {
		// TODO Auto-generated method stub
		// pega todos os dados e coloca na atividade
		// ou não, esperar definir como a baseGUI 
		// vai chamar os métodos das AuthoringGUI
	}
	
}
