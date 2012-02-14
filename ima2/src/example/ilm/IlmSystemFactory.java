package example.ilm;

import java.util.ArrayList;
import java.util.HashMap;

import example.ilm.gui.IlmAuthoringGUI;
import example.ilm.gui.IlmDomainGUI;
import example.ilm.model.IlmDomainConverter;
import example.ilm.model.IlmDomainModel;

import ilm.framework.SystemFactory;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.gui.AuthoringGUI;
import ilm.framework.modules.IlmModule;

public class IlmSystemFactory extends SystemFactory {

	@Override
	public DomainModel createDomainModel() {
		return new IlmDomainModel();
	}

	@Override
	public DomainConverter createDomainConverter() {
		return new IlmDomainConverter();
	}

	@Override
	public DomainGUI createDomainGUI(SystemConfig config, DomainModel model) {
		IlmDomainGUI domainGUI = new IlmDomainGUI();
		domainGUI.initDomainActionList(model);
		return domainGUI;
	}

	@Override
	public AuthoringGUI createAuthoringGUI(DomainGUI domainGUI,	String proposition, AssignmentState initial,
											AssignmentState current, AssignmentState expected,
											HashMap<String, String> config, HashMap<String, String> metadata) {
		AuthoringGUI gui = new IlmAuthoringGUI();
		gui.setComponents(config, domainGUI, metadata);
		gui.setAssignment(proposition, initial, current, expected);
		return gui;
	}

	protected ArrayList<IlmModule> getIlmModuleList() {
		ArrayList<IlmModule> list = new ArrayList<IlmModule>();
//		list.add(new ScriptModule());
//		list.add(new ExampleTracingTutorModule());
//		list.add(new ScormModule());
		return list;
	}
	
}