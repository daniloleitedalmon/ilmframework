package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.IlmModule;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
	private JButton authoringBtn; 
	private JButton newAssBtn;
	private JButton closeAssBtn; 
	private JButton openAssBtn;
	private JButton saveAssBtn;
	private int tabCount;
	
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
		
		tabCount = 0;
	}

	@Override
	protected void initAssignments() {
		if(_assignments.getNumberOfAssignments() == 1) {
			tabbedPane.setVisible(false);
			_domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
			int index = _domainGUIList.size()-1;
			_domainGUIList.get(index).setAssignment(_assignments.getProposition(0),
													_assignments.getCurrentState(0), 
													_assignments.getIlmModuleList().values());
			panel.add(_domainGUIList.get(index));
			_authoringGUIList.add(_factory.createAuthoringGUI(_domainGUIList.get(index), 
															  _assignments.getConfig(0), 
															  _assignments.getMetadata(0)));
			setActiveAssignment();
		} else {
			panel.add(tabbedPane);
			for(int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
				tabbedPane.setVisible(true);
				initAssignment(_assignments.getCurrentState(i));
			}
		}
	}

	private void initAssignment(AssignmentState curState) {
		_domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
		int index = _domainGUIList.size()-1;
		_domainGUIList.get(index).setAssignment(_assignments.getProposition(index),
												curState, 
												_assignments.getIlmModuleList().values());
		tabbedPane.addTab("assign" + (tabCount++), _domainGUIList.get(index));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		setActiveAssignment();
		_authoringGUIList.add(_factory.createAuthoringGUI(_domainGUIList.get(index), 
														  _assignments.getConfig(index), 
														  _assignments.getMetadata(index)));
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
		int index = tabbedPane.getSelectedIndex();
		if(index == -1) {
			updateAssignmentIndex(0);
		} else {
			updateAssignmentIndex(index);
		}
	}
	
	@Override
	protected void setAuthoringButton() {
		authoringBtn = makeButton("authoring", "ASSIGNMENT AUTHORING", 
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
		_authoringGUIList.get(_activeAssignment).setVisible(true);
	}

	@Override
	protected void setNewAssignmentButton() {
		newAssBtn = makeButton("newassignment", "NEW ASSIGNMENT", 
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
		if(_assignments.getNumberOfAssignments() == 1) {
			panel.removeAll();
			panel.add(tabbedPane);
			tabbedPane.setVisible(true);
			tabbedPane.addTab("assign" + (tabCount++), _domainGUIList.get(0));
			AssignmentState state = _assignments.newAssignment();
			initAssignment(state);
		}
		else {
			initAssignment(_assignments.newAssignment());
		}
		updateCloseButton();
	}

	@Override
	protected void setCloseAssignmentButton() {
		closeAssBtn = makeButton("closeassignment", "CLOSE ASSIGNMENT", 
				  				 "Close the assignment in this tab", "Close this assignment");
		toolBar.add(closeAssBtn);
		closeAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAssignment(tabbedPane.getSelectedIndex());
			}
		});
		updateCloseButton();
	}
	
	@Override
	protected void closeAssignment(int index) {
		if(_assignments.getNumberOfAssignments() == 1) {
			
		}
		else if(_assignments.getNumberOfAssignments() == 2) {
			closeActiveAssignment();
			panel.removeAll();
			panel.add(_domainGUIList.get(0));
			_domainGUIList.get(0).setVisible(true);
			updateCloseButton();
		}
		else {
			closeActiveAssignment();
		}
	}
	
	private void updateCloseButton() {
		if(_assignments.getNumberOfAssignments() == 1) {
			closeAssBtn.setEnabled(false);
		}
		else {
			closeAssBtn.setEnabled(true);
		}
	}
	
	private void closeActiveAssignment() {
		int index = _activeAssignment;
		_assignments.closeAssignment(index);
		tabbedPane.remove(index);
		_domainGUIList.remove(index);
		_authoringGUIList.remove(index);
		setActiveAssignment();
	}

	@Override
	protected void setOpenAssignmentButton() {
		openAssBtn = makeButton("openassignment", "OPEN ASSIGNMENT FILE", 
				  				"Open an assignment file", "Open an assignment");
		toolBar.add(openAssBtn);
		openAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAssignmentFile(getFileNameFromWindow("Choose file"));
			}
		});
	}

	@Override
	protected void openAssignmentFile(String fileName) {
		if(fileName == null) {
			return;
		}
		int initialIndex = _assignments.openAssignmentFile(fileName);
		for(int i = initialIndex; i < _assignments.getNumberOfAssignments(); i++) {
			if(_domainGUIList.size() == 1) {
				panel.removeAll();
				panel.add(tabbedPane);
				tabbedPane.setVisible(true);
				tabbedPane.addTab("assign" + (tabCount++), _domainGUIList.get(0));
			}
			initAssignment(_assignments.getCurrentState(i));
		}
		updateCloseButton();
	}
	
	private String getFileNameFromWindow(String option) {
		JFileChooser fc = new JFileChooser();
		int returnval = fc.showDialog(this, option);
		if(returnval == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsolutePath();
		} else if(returnval == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(this, "Error while choosing file.", "Error file", JOptionPane.OK_OPTION);
		}
		return null;
	}

	@Override
	protected void setSaveAssignmentButton() {
		saveAssBtn = makeButton("save", "SAVE ASSIGNMENT FILE", 
				  				"Save this assignment in a file", "Save an assignment");
		toolBar.add(saveAssBtn);
		saveAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAssignmentFile(getFileNameFromWindow("Choose filename"));
			}
		});
	}

	@Override
	protected void saveAssignmentFile(String fileName) {
		ArrayList<Assignment> list = new ArrayList<Assignment>();
		for(int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
			if(_authoringGUIList.get(i).getProposition().length() > 1) {
				list.add(_authoringGUIList.get(i).getAssignment());
			}
			else {
				Assignment a = new Assignment(_assignments.getProposition(i),
											  _assignments.getInitialState(i),
											  _assignments.getCurrentState(i),
											  _assignments.getExpectedAnswer(i));
				if(tabbedPane.getTabCount() == 0) {
					a.setName(IlmProtocol.ASSIGNMENT_FILE_NODE + tabCount);
				}
				else  {
					a.setName(tabbedPane.getTitleAt(i));
				}
				a.setConfig(_assignments.getConfig(i));
				a.setMetadata(_assignments.getMetadata(i));
				list.add(a);
			}
		}
		_assignments.authorAssignments(list, fileName);
	}
	
}
