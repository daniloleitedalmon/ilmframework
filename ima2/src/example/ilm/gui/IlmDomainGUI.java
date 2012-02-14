package example.ilm.gui;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import example.ilm.model.ActionAddSubString;
import example.ilm.model.ActionRemoveSubString;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class IlmDomainGUI extends DomainGUI {

	/**
	 * @attribute serial version due to javax.swing specification
	 * @attribute five javax.swing widgets
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JLabel lblLabel;
	private JButton btnAdd;
	private JButton btnDel; 
	private JLabel lblProposition;
	
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
		
		lblProposition = new JLabel("proposition");
		add(lblProposition);
		add(btnAdd);
		btnAdd.setEnabled(false);
		
		btnDel = new JButton("del");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSubString();
			}
		});
		add(btnDel);
		btnDel.setEnabled(false);
		
		add(textField);
		textField.setColumns(10);
		
		lblLabel = new JLabel("");
		lblLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				updateRemoveButton();
			}
		});
		add(lblLabel);
	}

	/**
	 * Initialization of the action list. It gets the
	 * DomainModel and sets it for each possible action.
	 * In this case there are only two. The name of each
	 * action is used as key for the action list.
	 */
	@Override
	public void initDomainActionList(DomainModel model) {
		_actionList = new HashMap<String, DomainAction>();
		ActionAddSubString addAction = new ActionAddSubString("add", "add");
		ActionRemoveSubString delAction = new ActionRemoveSubString("del", "del");
		addAction.setDomainModel(model);
		delAction.setDomainModel(model);
		_actionList.put(addAction.getName(), addAction);
		_actionList.put(delAction.getName(), delAction);
	}
	
	/**
	 * Initialization of the features which are dependent
	 * of the assignment being shown. In this case it just
	 * set the proposition label text and calls update.
	 */
	@Override
	protected void initDomainGUI() {
		lblProposition.setText(_proposition);
		update(null, null);
	}

	/**
	 * @return the list of objects of the current assignment
	 * state. As there is no "selection" of objects in this
	 * simple example of iLM, it just return all objects.
	 */
	@Override
	public ArrayList<DomainObject> getSelectedObjects() {
		return _state.getList();
	}

	/**
	 * Usually called by the assignment's state which this
	 * is an observer. This method updates the content of
	 * lblLabel and updates the state of the buttons.
	 */
    @Override
    public void update(Observable o, Object arg) {
    	if(o instanceof AssignmentState) {
            _state = (AssignmentState)o;
        }
        String temp = "";
    	for(DomainObject obj : _state.getList()) {
        	temp += obj.getDescription();
        }
        lblLabel.setText(temp);
    	updateRemoveButton();
    	updateAddButton();
    }

    /**
     * Method called when remove_button is pressed.
     * It gets the text from lblLabel and adds it
     * to the action, then it executes.
     */
	private void removeSubString() {
		if(lblLabel.getText().length() > 0) {
			ActionRemoveSubString action = (ActionRemoveSubString)_actionList.get("del");
			action.setSubString(lblLabel.getText().substring(lblLabel.getText().length()-1));
            action.setDescription("del: " + lblLabel.getText().substring(lblLabel.getText().length()-1));
            action.execute();
        }
		updateRemoveButton();
	}

	/**
	 * Method called when add_button is pressed.
	 * It gets the text from textField and adds it
	 * to the action, then it executes.
	 */
	private void addSubString() {
		if(textField.getText().length() > 0) {
			ActionAddSubString action = (ActionAddSubString)_actionList.get("add");
            action.setSubString(textField.getText().substring(0, 1));
            action.setDescription("add: " + textField.getText().substring(0, 1));
            action.execute();
        }
        if(textField.getText().length() > 1) {
            String temp = textField.getText().substring(1);
            textField.setText(temp);
        }
        else if(textField.getText().length() == 1) {
            textField.setText("");
        }
        updateAddButton();
	}

	/**
	 * Updates remove_button's state. If there is no
	 * text in lblLabel, there is no text to remove, so
	 * it is disabled, otherwise it is enabled.
	 */
    private void updateRemoveButton() {
    	if(lblLabel.getText().length() < 1) {
            btnDel.setEnabled(false);
        }
        else {
            btnDel.setEnabled(true);
        }
    }
    
    /**
     * Updates add_button's state. If there is no
     * text in textField, there is no text to add, so
     * it is disabled, otherwise it is enabled.
     */
    private void updateAddButton() {
    	if(textField.getText().length() < 1) {
            btnAdd.setEnabled(false);
        }
        else {
            btnAdd.setEnabled(true);
        }
    }

}
