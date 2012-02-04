package ilm;

import java.util.ArrayList;

import ilm.framework.SystemFactory;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.IlmModule;
import ilm.gui.IlmDomainGUI;
import ilm.model.IlmDomainConverter;
import ilm.model.IlmDomainModel;

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
		domainGUI.setDomainModel(model);
		return domainGUI;
	}
	
	protected ArrayList<IlmModule> getIlmModuleList() {
		ArrayList<IlmModule> list = new ArrayList<IlmModule>();
//		list.add(new ScriptModule());
//		list.add(new ExampleTracingTutorModule());
//		list.add(new ScormModule());
		return list;
	}
	
}
