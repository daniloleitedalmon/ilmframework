package ilm.framework.gui;

import java.util.Collection;
import java.util.Observable;

import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.IlmModule;

public class IlmBaseGUI extends BaseGUI {

	private static final long serialVersionUID = 1L;

	public IlmBaseGUI(SystemConfig config, DomainGUI domainGUI) {
		super(config, domainGUI);
	}

	@Override
	public void initIlmModules(Collection<IlmModule> moduleList) {
		// TODO Auto-generated method stub
		// get the GUI from each module
		// set each GUI to a panel/menu
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// update comes from _config
		// check for each property if changed
		// check for language
		// check for available assignments
		// apply changes to _baseGUIPanel
	}

	@Override
	protected AuthoringGUI getAuthoringGUI() {
		return new IlmAuthoringGUI();
	}

	@Override
	protected void setAuthoringButton() {
		// TODO Auto-generated method stub
		
	}

}
