package ilm.modules.tutor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.*;
import ilm.framework.modules.*;

public class TutorToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	private TutorControl _control;
	private JButton button;
	
	public TutorToolbar(TutorControl control)
	{
		_control = control;	
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		button = makeButton("tutoring", "TUTORING", 
							"asks for tutoring help", "Tutoring-Help");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tutorButtonPressed();
			}
		});
		add(button);
		_control.addObserver(this);
	}


	public void tutorButtonPressed()
	{
		_control.executeTutorAction();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(_control.hasAvailableTutorAction()) {
			button.setEnabled(true);
		}
		else {
			button.setEnabled(false);
		}
	}
	
}
