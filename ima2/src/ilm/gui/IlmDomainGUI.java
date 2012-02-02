package ilm.gui;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.model.ActionAddSubString;
import ilm.model.ActionRemoveSubString;
import ilm.model.IlmDomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class IlmDomainGUI extends DomainGUI {

	private static final long serialVersionUID = 1L;
	private IlmDomainModel _model;
	private JTextField textField;
	private JLabel lblLabel;
	private JButton btnAdd;
	private JButton btnDel; 
	
	public IlmDomainGUI() {
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				updateAddButton();
			}
		});
		
		btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addSubString();
			}
		});
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(btnAdd);
		
		btnDel = new JButton("del");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSubString();
			}
		});
		add(btnDel);
		add(textField);
		textField.setColumns(10);
		
		lblLabel = new JLabel("label");
		lblLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				updateRemoveButton();
			}
		});
		add(lblLabel);
		
		//human-made code
		_actionList = new HashMap<String, DomainAction>();
		ActionAddSubString addAction = new ActionAddSubString("add", "add");
		ActionRemoveSubString delAction = new ActionRemoveSubString("del", "del");
		addAction.setDomain(_model);
		delAction.setDomain(_model);
		_actionList.put(addAction.getName(), addAction);
		_actionList.put(delAction.getName(), delAction);
	}

	@Override
	public ArrayList<DomainObject> getSelectedObjects() {
		return _state.getList();
	}

	@Override
	public AssignmentState getCurrentState() {
		return _state;
	}

	@Override
	public void setDomainModel(DomainModel model) {
		_model = (IlmDomainModel)model;
	}

    @Override
    public void update(Observable o, Object arg) {
    	if(o instanceof AssignmentState) {
            AssignmentState state = (AssignmentState)o;
            String temp = "";
            for(DomainObject obj : state.getList()) {
            	temp += obj.getDescription();
            }
            lblLabel.setText(temp);
        }
    	updateRemoveButton();
    	updateAddButton();
    }

	private void removeSubString() {
		if(lblLabel.getText().length() > 0) {
			ActionRemoveSubString action = (ActionRemoveSubString)_actionList.get("del");
			action.setSubString(lblLabel.getText().substring(0, 1));
            action.setDescription("del: " + action.getSubString());
            action.execute();
        }
	}

	private void addSubString() {
		if(textField.getText().length() > 0) {
			ActionAddSubString action = (ActionAddSubString)_actionList.get("add");
            action.setSubString(textField.getText().substring(0, 1));
            action.setDescription("add: " + action.getSubString());
            action.execute();
        }
        if(textField.getText().length() > 1) {
            String temp = textField.getText().substring(1);
            textField.setText(temp);
        }
        else if(textField.getText().length() == 1) {
            textField.setText("");
        }
	}

    private void updateRemoveButton() {
    	if(lblLabel.getText().length() < 1) {
            btnDel.setEnabled(false);
        }
        else {
            btnDel.setEnabled(true);
        }
    }
    
    private void updateAddButton() {
    	if(textField.getText().length() < 1) {
            btnAdd.setEnabled(false);
        }
        else {
            btnAdd.setEnabled(true);
        }
    }
    
}
