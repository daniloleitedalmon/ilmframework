package ilm.framework.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.IlmModule;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class IlmBaseGUI extends BaseGUI {

	private static final long serialVersionUID = 1L;
	private JToolBar toolBar;
	private JPanel panel;
	private JTabbedPane tabbedPane;

	public IlmBaseGUI() {
		setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		add(toolBar, BorderLayout.NORTH);
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setActiveAssignment();
			}
		});
		panel.add(tabbedPane);
	}

	@Override
	protected void initAssignments() {
		if(_assignments.getNumberOfAssignments() <= 1) {
			tabbedPane.setVisible(false);
			
			_domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
			int index = _domainGUIList.size()-1;
			_domainGUIList.get(index).setAssignment(_assignments.getProposition(0),
													_assignments.getCurrentState(0), 
													_assignments.getIlmModuleList().values());
			add(_domainGUIList.get(index));
			
			_authoringGUIList.add(_factory.createAuthoringGUI(_domainGUIList.get(index), 
															  _assignments.getConfig(0), 
															  _assignments.getMetadata(0)));
			updateAssignmentIndex(0);
		} else {
			for(int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
				tabbedPane.setVisible(true);
				initAssignment(_assignments.getCurrentState(i), _assignments.getConfig(i), _assignments.getMetadata(i));
			}
			updateAssignmentIndex(tabbedPane.getSelectedIndex());
		}
	}
	
	private void initAssignment(AssignmentState curState, HashMap<String, String> config, HashMap<String, String> metadata) {
		_domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
		int index = _domainGUIList.size()-1;
		_domainGUIList.get(index).setAssignment(_assignments.getProposition(0),
												curState, _assignments.getIlmModuleList().values());
		tabbedPane.addTab("", _domainGUIList.get(index));
		
		_authoringGUIList.add(_factory.createAuthoringGUI(_domainGUIList.get(index), config, metadata));
	}

	@Override
	public void initToolbar(Collection<IlmModule> moduleList) {
		addToolBarButtons();
		for(IlmModule module : moduleList) {
			toolBar.add(module.getGUI());
		}
	}

	private void addToolBarButtons() {
		setAuthoringButton();
		setNewAssignmentButton();
		setCloseAssignmentButton();
		setOpenAssignmentButton();
		setSaveAssignmentButton();
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// update comes from _config
		// check for each property if changed
		// check for language
		// apply changes
	}

	protected void setActiveAssignment() {
		updateAssignmentIndex(tabbedPane.getSelectedIndex());
	}
	

	@Override
	protected void setAuthoringButton() {
		JButton authoringBtn = makeButton("authoring", "ASSIGNMENT AUTHORING", 
										  "Open assignment authoring window", "Start authoring");
		toolBar.add(authoringBtn);
		authoringBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startAuthoring();
			}
		});
	}
	
	@Override
	protected void startAuthoring() {
		/*if(_authoringGUIList.get(_activeAssignment) == null) {
			_authoringGUIList.add(getAuthoringGUI());
		}
		_authoringGUIList.get(_activeAssignment).setDomainGUI(_domainGUIList.get(_activeAssignment));
		_authoringGUIList.get(_activeAssignment).setVisible(true);*/
	}

	@Override
	protected void setNewAssignmentButton() {
		JButton newAssBtn = makeButton("newassignment", "NEW ASSIGNMENT", 
				  "Open an assignment in a new tab", "Start a new assignment");
		toolBar.add(newAssBtn);
		newAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewAssignment();
			}
		});
	}
	
	@Override
	protected void addNewAssignment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setCloseAssignmentButton() {
		JButton closeAssBtn = makeButton("closeassignment", "CLOSE ASSIGNMENT", 
				  "Close an assignment in this tab", "Close this assignment");
		toolBar.add(closeAssBtn);
		closeAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAssignment(tabbedPane.getSelectedIndex());
			}
		});
	}
	
	@Override
	protected void closeAssignment(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setOpenAssignmentButton() {
		JButton openAssBtn = makeButton("openassignment", "OPEN ASSIGNMENT FILE", 
				  "Open an assignment file", "Open an assignment");
		toolBar.add(openAssBtn);
		openAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAssignmentFile(getFileNameFromWindow());
			}
		});
	}

	@Override
	protected void openAssignmentFile(String fileName) {
		int initialIndex = _assignments.openAssignmentFile(fileName);
		for(int i = initialIndex; i < _assignments.getNumberOfAssignments(); i++) {
			initAssignment(_assignments.getCurrentState(i), _assignments.getConfig(i), _assignments.getMetadata(i));
		}
	}
	
	private String getFileNameFromWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setSaveAssignmentButton() {
		JButton saveAssBtn = makeButton("save", "SAVE ASSIGNMENT FILE", 
				  "Save this assignment in a file", "Save an assignment");
		toolBar.add(saveAssBtn);
		saveAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAssignmentFile(getFileNameFromWindow());
			}
		});
	}

	@Override
	protected void saveAssignmentFile(String fileName) {
		// TODO Auto-generated method stub
		
	}
	
}
