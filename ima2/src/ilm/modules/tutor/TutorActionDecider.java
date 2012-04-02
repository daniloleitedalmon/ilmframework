package ilm.modules.tutor;
import ilm.framework.assignment.model.*;

public interface TutorActionDecider {
	TutorAction getTutorAction(AssignmentState state, DomainAction action);
	void setData(String data);
	boolean hasAvailableTutorAction();
}
