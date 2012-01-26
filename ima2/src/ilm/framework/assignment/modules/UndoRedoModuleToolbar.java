package ilm.framework.assignment.modules;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;

public class UndoRedoModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;
	
	private UndoRedoModule _undoRedo;
	
	private JButton _undoButton = new JButton();
	private JButton _redoButton = new JButton();

	public UndoRedoModuleToolbar() {
		// TODO all user interface
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof UndoRedoModule) {
			_undoRedo = (UndoRedoModule)o;
			if(_undoRedo.isUndoStackEmpty()) {
				_undoButton.setEnabled(false);
			}
			else {
				_undoButton.setEnabled(true);
			}
			if(_undoRedo.isRedoStackEmpty()) {
				_redoButton.setEnabled(false);
			}
			else {
				_redoButton.setEnabled(true);
			}
		}
	}

}
