package ilm.framework.gui;

import java.util.Collection;
import java.util.Observable;

import ilm.framework.IlmProtocol;
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
	public void initIlmModules(Collection<IlmModule> moduleList) {
		for(IlmModule module : moduleList) {
			toolBar.add(module.getGUI());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// update comes from _config
		// check for each property if changed
		// check for language
		// apply changes
	}

	private void setActiveAssignment() {
		setActiveAssignment(tabbedPane.getSelectedIndex());
	}
	
	@Override
	protected AuthoringGUI getAuthoringGUI() {
		AuthoringGUI gui = new IlmAuthoringGUI();
		gui.setDomainGUI(_domainGUIList.get(_activeAssignment));
		return gui;
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
	protected void initAssignments() {
		if(_assignments.getNumberOfAssignments() <= 1) {
			tabbedPane.setVisible(false);
			panel.add(_domainGUIList.get(0));
			_activeAssignment = 0;
		} else {
			for(int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
				addNewAssignment(_assignments.getCurrentState(i));
			}
			setActiveAssignment(Integer.parseInt(_config.getValue(IlmProtocol.ACTIVE_ASSIGNMENT_INDEX)));
		}
	}

	@Override
	protected void initAssignment(AssignmentState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setNewAssignmentButton() {
		JButton newAssBtn = makeButton("newassignment", "NEW ASSIGNMENT", 
				  "Open an assignment in a new tab", "Start a new assignment");
		toolBar.add(newAssBtn);
		newAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startNewAssignment();
			}
		});
	}
	
	private void startNewAssignment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setCloseAssignmentButton() {
		JButton closeAssBtn = makeButton("closeassignment", "CLOSE ASSIGNMENT", 
				  "Close an assignment in this tab", "Close this assignment");
		toolBar.add(closeAssBtn);
		closeAssBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAssignment();
			}
		});
	}
	
	private void closeAssignment() {
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
	
	private String getFileNameFromWindow() {
		// TODO Auto-generated method stub
		return null;
	}

}
