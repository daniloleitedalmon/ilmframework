package ilm.framework.assignment.modules;

import java.util.Observer;

import javax.swing.JPanel;

public abstract class AssignmentModule implements Observer, Cloneable {

	protected String _name;
	protected JPanel _gui;
	
    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

	public String getName() {
		return _name;
	}

	public JPanel getGUI() {
		return _gui;
	}
	
}
