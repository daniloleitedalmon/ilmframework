package ilm.framework.assignment.modules;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;
import java.awt.FlowLayout;

public class UndoRedoModuleToolbar extends IlmModuleToolbar {

	private static final long serialVersionUID = 1L;

	private UndoRedoModule _undoRedo;
	private JButton _undoButton;
	private JButton _redoButton;

	public UndoRedoModuleToolbar() {
		_undoButton = makeButton("undo", "UNDO", "Undo the last action made", "Undo action");
		add(_undoButton);
		
		_redoButton = makeButton("redo", "REDO", "Redo the last action unmade", "Redo action");
		add(_redoButton);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof UndoRedoModule) {
			_undoRedo = (UndoRedoModule)o;
			updateUndoButton();
			updateRedoButton();
		}
	}
	
	private void updateUndoButton() {
		if(_undoRedo.isUndoStackEmpty()) {
			_undoButton.setEnabled(false);
		}
		else {
			_undoButton.setEnabled(true);
		}
	}
	
	private void updateRedoButton() {
		if(_undoRedo.isRedoStackEmpty()) {
			_redoButton.setEnabled(false);
		}
		else {
			_redoButton.setEnabled(true);
		}
	}

}
