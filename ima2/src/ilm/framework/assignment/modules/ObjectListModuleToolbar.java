package ilm.framework.assignment.modules;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;

public class ObjectListModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	private ObjectListModuleGUI _gui;
	private JButton _button;
	
	public ObjectListModuleToolbar() {
		_button = makeButton("objectlist24", "OBJECT LIST", "Open the list of objects", "Object List window");
		// TODO set action performed event to _button
		//		in this action_performed it must create a new ObjectListModuleGUI
		//		set it as observer of this object
		//		and set it visible
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(_gui != null) {
			_gui.update(o, arg);
		}
	}

}
