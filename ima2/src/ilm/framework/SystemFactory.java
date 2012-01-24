package ilm.framework;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.comm.IlmAppletFileRW;
import ilm.framework.comm.IlmDesktopFileRW;
import ilm.framework.comm.IlmEncrypter;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.gui.BaseGUI;
import ilm.framework.gui.IlmBaseGUI;

public abstract class SystemFactory {

	public abstract DomainModel createDomainModel(SystemConfig config);
	
	public abstract DomainConverter createDomainConverter();
	
	public abstract DomainGUI createDomainGUI(SystemConfig config, DomainModel domainModel);
	
	public BaseGUI createBaseGUI(SystemConfig config, DomainGUI domainGUI) {
		return new IlmBaseGUI(config, domainGUI);
	}

	public CommControl createCommControl(SystemConfig config) {
		CommControl comm = new CommControl(config);
		comm.SetEncrypter(new IlmEncrypter());
		if(config.isApplet()) {
			comm.SetFileRW(new IlmAppletFileRW());
		}
		else {
			comm.SetFileRW(new IlmDesktopFileRW());
		}
		return comm;
	}

	public AssignmentControl createAssignmentControl(SystemConfig config,
													 DomainModel model,
													 DomainConverter converter) {
		AssignmentControl assignControl = new AssignmentControl(config, model, converter);
		return assignControl;
	}
	
}
