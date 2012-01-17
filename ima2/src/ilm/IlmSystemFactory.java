package ilm;

import ilm.assignment.modules.script.ScriptModule;
import ilm.assignment.modules.tutor.ExampleTracingTutorModule;
import ilm.framework.SystemFactory;
import ilm.framework.assignment.AssignmentControl;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.gui.IlmDomainGUI;
import ilm.model.IlmDomainModel;
import ilm.modules.scorm.ScormModule;

public class IlmSystemFactory extends SystemFactory {

	@Override
	public DomainModel createDomainModel(SystemConfig config) {
		return new IlmDomainModel();
	}

	@Override
	public DomainGUI createDomainGUI(SystemConfig config, DomainModel model) {
		return new IlmDomainGUI(model);
	}

	@Override
	public AssignmentControl createAssignmentControl(SystemConfig config,
													 DomainModel model) {
		AssignmentControl assignControl = new AssignmentControl(config, model);
		assignControl.addAssignmentModule(new ScriptModule());
		assignControl.addAssignmentModule(new ExampleTracingTutorModule());
		assignControl.addIlmModule(new ScormModule(assignControl, assignControl));
		return assignControl;
	}
	
}
