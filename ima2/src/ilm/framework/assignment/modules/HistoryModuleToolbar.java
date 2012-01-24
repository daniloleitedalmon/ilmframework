package ilm.framework.assignment.modules;

import java.util.Observable;

import javax.swing.JButton;

public class HistoryModuleToolbar extends AssignmentModuleToolbar {

	private static final long serialVersionUID = 1L;
	private HistoryModuleGUI _gui;
	private JButton _button;
	
	public HistoryModuleToolbar() {
		_button = makeButton("history24", "HISTORY", "Open the history of actions", "History window");
		// TODO set action performed event to _button
		//		in this action_performed it must create a new HistoryModuleGUI
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
