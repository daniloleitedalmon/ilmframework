package ilm.framework.assignment;

import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.util.HashMap;

public interface IModulesLists {
	
	public HashMap<String, AssignmentModule> getAssignmentModuleList(int index);
	
	public HashMap<String, IlmModule> getIlmModuleList();
	
}
