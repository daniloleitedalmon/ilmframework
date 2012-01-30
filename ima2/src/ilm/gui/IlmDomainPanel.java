package ilm.gui;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IlmDomainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private IlmDomainGUI _gui;

	public IlmDomainPanel() {

	}
	
	void setDomainGUI(IlmDomainGUI gui) {
		_gui = gui;
	}

    private JEditorPane inputEditor = new JEditorPane();
    private JButton addButton = new JButton();
    private JButton removeButton = new JButton();
    private JLabel resultLabel = new JLabel();

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
            for(DomainObject obj : state.getList()) {
            	temp += obj.getDescription();
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
            _gui.AddSubString();
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
            _gui.RemoveSubString(); //, resultLabel.getText().substring(resultLabel.getText().length()-1)
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
    
    String getText() {
    	return inputEditor.getText();
    }

}
