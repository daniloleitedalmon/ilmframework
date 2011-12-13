package ilm;

import ilm.assignment.modules.script.ScriptModule;
import ilm.assignment.modules.tutor.ExampleTracingTutorModule;
import ilm.framework.SystemFactory;
import ilm.framework.assignment.AssignmentControl;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.IDomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.gui.IlmDomainGUI;
import ilm.model.IlmAssignmentConverter;
import ilm.model.IlmDomainModel;
import ilm.modules.scorm.ScormModule;

public class IlmSystemFactory extends SystemFactory {

	@Override
	public DomainModel createDomainModel() {
		return new IlmDomainModel();
	}

	@Override
	public DomainGUI createDomainGUI(DomainModel model) {
		return new IlmDomainGUI(model);
	}

	@Override
	public IDomainConverter createDomainConverter() {
		return new IlmAssignmentConverter();
	}

	@Override
	public AssignmentControl createAssignmentControl(SystemConfig config,
													 IDomainConverter converter) {
		AssignmentControl assignControl = new AssignmentControl(config, converter);
		assignControl.addAssignmentModule(new ScriptModule());
		assignControl.addAssignmentModule(new ExampleTracingTutorModule());
		assignControl.addIlmModule(new ScormModule(assignControl, assignControl));
		return assignControl;
	}
	
}
