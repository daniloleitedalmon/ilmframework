package ilm.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.model.ActionAddSubString;
import ilm.model.ActionRemoveSubString;
import ilm.model.IlmDomainModel;

public class IlmDomainGUI extends DomainGUI {

	private static final long serialVersionUID = 1L;
	private IlmDomainModel _model;
	
	public IlmDomainGUI() {
		_actionList = new HashMap<String, DomainAction>();
		ActionAddSubString addAction = new ActionAddSubString("add", "add", _model);
		ActionRemoveSubString delAction = new ActionRemoveSubString("del", "del", _model);
		_actionList.put(addAction.getName(), addAction);
		_actionList.put(delAction.getName(), delAction);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DomainObject> getSelectedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDomainModel(DomainModel model) {
		_model = (IlmDomainModel)model;
	}

}

/*
package ima.ilm.gui;

import ima.ILM_Framework.assignment.model.AssignmentState;

import ima.ILM_Framework.domainGUI.DomainGUI;

import ima.ilm.model.ILMDomainModel;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ILMDomainGUI extends DomainGUI {

    private ILMDomainModel _domainModel;


    private JEditorPane inputEditor = new JEditorPane();
    private JButton addButton = new JButton();
    private JButton removeButton = new JButton();
    private JLabel resultLabel = new JLabel();

    
    public ILMDomainGUI(ILMDomainModel domainModel) {
        _domainModel = domainModel;
    }


    protected void jbInit() {
        this.setLayout(null);
        this.setSize(new Dimension(177, 91));
        this.setBackground(new Color(0, 247, 247));
        inputEditor.setBounds(new Rectangle(5, 5, 165, 25));
        inputEditor.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    inputEditor_keyReleased(e);
                }
            });
        addButton.setText("Add");
        addButton.setBounds(new Rectangle(5, 35, 80, 25));
        addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addButton_actionPerformed(e);
                }
            });
        removeButton.setText("Remove");
        removeButton.setBounds(new Rectangle(90, 35, 80, 25));
        removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeButton_actionPerformed(e);
                }
            });
        resultLabel.setText("");
        resultLabel.setBounds(new Rectangle(5, 65, 165, 20));
        resultLabel.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    resultLabel_propertyChange(e);
                }
            });
        this.add(inputEditor);
        this.add(addButton);
        this.add(resultLabel);
        this.add(removeButton);
        
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
    }
    
    
    public void update(Observable o, Object arg) {
        if(o instanceof AssignmentState) {
            AssignmentState state = (AssignmentState)o;
            
            String temp = "";
            
            for(int i = 0; i < state.size(); i++) {
                temp = temp + state.get(i).getDescription();
            }
            
            resultLabel.setText(temp);
        }
        
        if(inputEditor.getText().length() < 1) {
            addButton.setEnabled(false);
        }
        else {
            addButton.setEnabled(true);
        }
        
        if(resultLabel.getText().length() < 1) {
            removeButton.setEnabled(false);
        }
        else {
            removeButton.setEnabled(true);
        }
    }
    

    private void addButton_actionPerformed(ActionEvent e) {
        if(inputEditor.getText().length() > 0) {
            _domainModel.AddSubString(
                                inputEditor.getText().substring(0, 1));
        }
        
        if(inputEditor.getText().length() > 1) {
            String temp = inputEditor.getText().substring(1);
            inputEditor.setText(temp);
        }
        else if(inputEditor.getText().length() == 1) {
            inputEditor.setText("");
        }
    }

    private void removeButton_actionPerformed(ActionEvent e) {
        if(resultLabel.getText().length() > 0) {
            int length = resultLabel.getText().length();
            
            _domainModel.RemoveSubString(
                                resultLabel.getText().substring(length-1));
        }
    }

    private void resultLabel_propertyChange(PropertyChangeEvent e) {
        if(resultLabel.getText().length() < 1) {
            removeButton.setEnabled(false);
        }
        else {
            removeButton.setEnabled(true);
        }
    }

    private void inputEditor_keyReleased(KeyEvent e) {
        if(inputEditor.getText().length() < 1) {
            addButton.setEnabled(false);
        }
        else {
            addButton.setEnabled(true);
        }
    }
}
*/