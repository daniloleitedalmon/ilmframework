package ilm.framework.modules;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AutoCheckingModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	private JButton button;
	private AutomaticCheckingModule _module;
	
	public AutoCheckingModuleToolbar(AutomaticCheckingModule module) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		button = makeButton("autochecking", "AUTOMATIC CHECKING", 
							"Auto evaluate of your assignment, giving you a grade", "Auto-evaluation");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showEvaluation();
			}
		});
		add(button);
		
		_module = module;
		_module.addObserver(this);
	}
	
	private void showEvaluation() {
		JOptionPane.showMessageDialog(this, "Evaluation: " + _module.getEvaluation(), "Evaluation", JOptionPane.OK_OPTION);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(_module.hasExpectedAnswer()) {
			button.setEnabled(true);
		}
		else {
			button.setEnabled(false);
		}
	}

}
