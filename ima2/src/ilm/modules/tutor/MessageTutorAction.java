package ilm.modules.tutor;

import javax.swing.JOptionPane;

public class MessageTutorAction extends TutorAction {

	private String _message;
	private String _title;
	
	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		this._message = message;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	protected void executeAction() {
		JOptionPane.showMessageDialog(null, _message, _title, JOptionPane.OK_OPTION);
	}

}
