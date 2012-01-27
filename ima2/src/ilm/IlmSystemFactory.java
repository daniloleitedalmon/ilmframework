package ilm;

import ilm.framework.SystemFactory;
import ilm.framework.assignment.AssignmentControl;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.gui.IlmDomainGUI;
import ilm.model.IlmDomainConverter;
import ilm.model.IlmDomainModel;

public class IlmSystemFactory extends SystemFactory {

	@Override
	public DomainModel createDomainModel(SystemConfig config) {
		return new IlmDomainModel();
	}

	@Override
	public DomainConverter createDomainConverter() {
		return new IlmDomainConverter();
	}

	@Override
	public DomainGUI createDomainGUI(SystemConfig config, DomainModel model) {
		IlmDomainGUI domainGUI = new IlmDomainGUI();
		domainGUI.setDomainModel(model);
		return domainGUI;
	}

	@Override
	public AssignmentControl createAssignmentControl(SystemConfig config,
													 DomainModel model,
													 DomainConverter converter) {
		AssignmentControl assignControl = new AssignmentControl(config, model, converter);
//		assignControl.addIlmModule(new ScriptModule());
//		assignControl.addIlmModule(new ExampleTracingTutorModule());
//		assignControl.addIlmModule(new ScormModule(assignControl, assignControl));
		return assignControl;
	}
	
}
