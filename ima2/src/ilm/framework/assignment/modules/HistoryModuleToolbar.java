package ilm.framework.assignment.modules;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HistoryModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	private HistoryModuleGUI _window;
	private JButton button;
	
	public HistoryModuleToolbar() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		button = makeButton("history", "HISTORY", "Open the history of actions", "History window");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWindow();
			}
		});
		add(button);
		
		_window = new HistoryModuleGUI();
	}
	
	private void showWindow() {
		_window.setVisible(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(_window != null) {
			_window.setModule((HistoryModule)o);
			_window.update(o, arg);
		}
	}

}
