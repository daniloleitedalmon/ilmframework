package ilm.modules.tutor;

import java.util.Observable;

public abstract class TutorAction extends Observable {

	public void execute()
	{
		executeAction();
		setChanged();
		notifyObservers();
	}
	
	protected abstract void executeAction();
	private boolean _isImmediateAction;
	
	protected boolean getIsImmediateAction()
	{
		return _isImmediateAction;
	}
	protected void setIsImmediateAction(boolean option)
	{
		_isImmediateAction = option;
	}
}
