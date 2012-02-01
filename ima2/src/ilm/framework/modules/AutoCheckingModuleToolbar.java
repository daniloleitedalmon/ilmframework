package ilm.framework.modules;

import ilm.framework.IlmProtocol;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AutoCheckingModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	private JButton button;
	private IlmProtocol command;
	
	public AutoCheckingModuleToolbar(IlmProtocol protocol) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		button = makeButton("autochecking", "AUTOMATIC CHECKING", 
							"Auto evaluate of your assignment, giving you a grade", "Auto-evaluation");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showEvaluation();
			}
		});
		add(button);
		
		//human-made code
		command = protocol;
	}
	
	private void showEvaluation() {
		JOptionPane.showMessageDialog(this, "Evaluation: " + command.getEvaluation(), "Evaluation", JOptionPane.OK_OPTION);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {}

}
