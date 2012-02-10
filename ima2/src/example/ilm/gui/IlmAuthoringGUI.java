package example.ilm.gui;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.gui.AuthoringGUI;
import ilm.framework.gui.IlmForm;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

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
import javax.swing.JTextField;

public class IlmAuthoringGUI extends AuthoringGUI {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel currentModel;
	private DefaultListModel initialModel;
	private DefaultListModel expectedModel;
	private JList listCurrent;
	private JList listInitial;
	private JList listExpected;
	private JTextArea propositionArea;
	private IlmForm _configForm;
	private IlmForm _metadataForm;
	private JTextField nameField;

	public IlmAuthoringGUI() {
		setBounds(100, 100, 600, 300);
		setTitle("Authoring Form");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel currentPanel = new JPanel();
		contentPane.add(currentPanel);
		currentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCurrent = new JLabel("Current");
		currentPanel.add(lblCurrent, BorderLayout.NORTH);
		
		currentModel = new DefaultListModel();
		listCurrent = new JList(currentModel);
		currentPanel.add(listCurrent, BorderLayout.CENTER);
		
		JPanel initialPanel = new JPanel();
		contentPane.add(initialPanel);
		initialPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInitial = new JLabel("Initial");
		initialPanel.add(lblInitial, BorderLayout.NORTH);
		
		initialModel = new DefaultListModel();
		listInitial = new JList(initialModel);
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
		
		expectedModel = new DefaultListModel();
		listExpected = new JList(expectedModel);
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
		configPanel.add(propositionArea, "2, 4, 1, 3, fill, fill");
		
		JButton btnConfig = new JButton(IlmProtocol.CONFIG_LIST_NODE);
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showForm(_config, IlmProtocol.CONFIG_LIST_NODE);
			}
		});
		
		JLabel lblName = new JLabel("Name");
		configPanel.add(lblName, "2, 8");
		
		nameField = new JTextField();
		configPanel.add(nameField, "2, 10, fill, default");
		nameField.setColumns(10);
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
				setVisible(false);
			}
		});
		configPanel.add(btnOk, "2, 18");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AssignmentState) {
			ArrayList<DomainObject> objectList = ((AssignmentState)o).getList();
			// TODO need a better non-brute force way to do this
			currentModel.clear();
			for(int i = 0; i < objectList.size(); i++) {
				// TODO need a way to verify if description is a possible primary key
				currentModel.addElement(objectList.get(i).getName());
			}
		}
	}

	@Override
	protected void initFields() {
		for(DomainObject obj : _assignment.getInitialState().getList()) {
			initialModel.addElement(obj.getName());
		}
		for(DomainObject obj : _assignment.getCurrentState().getList()) {
			currentModel.addElement(obj.getName());
		}
		if(_assignment.getExpectedAnswer() != null) {
			for(DomainObject obj : _assignment.getExpectedAnswer().getList()) {
				expectedModel.addElement(obj.getName());
			}
		}
		propositionArea.setText(_assignment.getProposition());
	}

	private void addObjectToInitial() {
		int[] selectedIndices = listCurrent.getSelectedIndices();
		for(int i = 0; i < selectedIndices.length; i++) {
			initialModel.addElement((String)currentModel.getElementAt(selectedIndices[i]));
		}
	}
	
	private void removeObjectFromInitial() {
		int[] selectedIndices = listInitial.getSelectedIndices();
		for(int i = 0; i < selectedIndices.length; i++) {
			initialModel.remove(i);
		}
	}
	
	private void addObjectToExpected() {
		int[] selectedIndices = listCurrent.getSelectedIndices();
		for(int i = 0; i < selectedIndices.length; i++) {
			expectedModel.addElement((String)currentModel.getElementAt(selectedIndices[i]));
		}
	}
	
	private void removeObjectFromExpected() {
		int[] selectedIndices = listExpected.getSelectedIndices();
		for(int i = 0; i < selectedIndices.length; i++) {
			expectedModel.remove(i);
		}		
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
	protected String getAssignmentName() {
		return nameField.getText();
	}

	@Override
	protected AssignmentState getInitialState() {
		AssignmentState state = new AssignmentState();
		for(int i = 0; i < initialModel.getSize(); i++) {
			String objDescription = (String)initialModel.getElementAt(i);
			DomainObject obj = _domainGUI.getCurrentState().getFromName(objDescription);
			state.add(obj);
		}
		return state;
	}

	@Override
	protected AssignmentState getExpectedAnswer() {
		AssignmentState state = new AssignmentState();
		for(int i = 0; i < expectedModel.getSize(); i++) {
			String objDescription = (String)expectedModel.getElementAt(i);
			DomainObject obj = _domainGUI.getCurrentState().getFromName(objDescription);
			state.add(obj);
		}
		return state;
	}

	@Override
	protected HashMap<String, String> getConfig() {
		if(_configForm == null) {
			return new HashMap<String, String>();
		}
		_config = _configForm.getUpdatedMap();
		return _config;
	}

	@Override
	protected HashMap<String, String> getMetadata() {
		if(_metadataForm == null) {
			return new HashMap<String, String>();
		}
		_metadata = _metadataForm.getUpdatedMap();
		return _metadata;
	}

}
