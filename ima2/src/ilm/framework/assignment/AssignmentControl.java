package ilm.framework.assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.modules.HistoryModule;
import ilm.framework.assignment.modules.ObjectListModule;
import ilm.framework.assignment.modules.UndoRedoModule;
import ilm.framework.comm.ICommunication;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.AutomaticCheckingModule;
import ilm.framework.modules.IlmModule;

public final class AssignmentControl implements IAssignment, IAssignmentOperator, IModulesLists {

	private SystemConfig _config;
	private DomainModel _model;
	private DomainConverter _converter;
	private ICommunication _comm;
	private ArrayList<Assignment> _assignmentList;
	private HashMap<String, IlmModule> _ilmModuleList;
	
	public AssignmentControl(SystemConfig config, DomainModel model, DomainConverter converter) {
		_config = config;
		_model = model;
		_converter = converter;
		initIlmModuleList();
		initAssignments();
	}
	
	private void initIlmModuleList() {
		_ilmModuleList = new HashMap<String, IlmModule>();
		IlmModule module = new AutomaticCheckingModule(this, this);
		_ilmModuleList.put(module.getName(), module);
		module = new UndoRedoModule();
		_ilmModuleList.put(module.getName(), module);
		module = new HistoryModule();
		_ilmModuleList.put(module.getName(), module);
		module = new ObjectListModule();
		_ilmModuleList.put(module.getName(), module);
	}	
	
	private void initAssignments() {
		int numberOfPackages;
		try {
			numberOfPackages = Integer.parseInt(_config.getValue(IlmProtocol.NUMBER_OF_ASSIGNMENTS));
		}
		catch(NumberFormatException e) {
			numberOfPackages = 0;
			// TODO inform the user that the number of assignment was wrong
		}
		
		if(numberOfPackages > 0) {
			for(int i = 0; i < numberOfPackages; i++) {
				String metadata = loadMetadataFile(i);
				getConfigFromMetadataFile(metadata);
				_assignmentList.addAll(createAssignments(loadAssignmentFiles(i, metadata)));
			}
		}
		else {
			_assignmentList.add(createNewAssignment());
		}
		for(Assignment a : _assignmentList) {
			initAssignmentModules(a);
		}
	}

	private ArrayList<Assignment> createAssignments(ArrayList<String> stringList) {
		ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
		AssignmentParser parser = new AssignmentParser();
		for(String assignmentString : stringList) {
			assignmentList.add(parser.convertStringToAssignment(_converter, assignmentString));
		}
		return assignmentList;
	}
	
	private Assignment createNewAssignment() {
		AssignmentState initialState = _model.getNewAssignmentState();
		return new Assignment("", initialState, initialState, null);
	}
	
	private void initAssignmentModules(Assignment assignment) {
		for(String moduleName : _ilmModuleList.keySet()) {
			if(_ilmModuleList.get(moduleName) instanceof AssignmentModule) {
				AssignmentModule module = (AssignmentModule)_ilmModuleList.get(moduleName);
				if(module.getObserverType() != AssignmentModule.ACTION_OBSERVER) {
					assignment.getCurrentState().addObserver(module);
					module.addAssignment();
				}
			}
		}
	}
	
	private String loadMetadataFile(int packageIndex) {
		String packageFilePath = _config.getValue(IlmProtocol.NUMBER_OF_ASSIGNMENTS + "_" + packageIndex);
		try {
			return _comm.readMetadataFile(packageFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void getConfigFromMetadataFile(String metadataFileContent) {
		AssignmentParser parser = new AssignmentParser();
		HashMap<String, String> config = parser.getConfig(metadataFileContent);
		for(String key : config.keySet()) {
			_config.setParameter(key, config.get(key));
		}
	}
	
	private ArrayList<String> loadAssignmentFiles(int packageIndex, String metadataFileContent) {
		String packageFilePath = _config.getValue(IlmProtocol.NUMBER_OF_ASSIGNMENTS + "_" + packageIndex);
		AssignmentParser parser = new AssignmentParser();
		HashMap<String, String> metadata = parser.getMetadata(metadataFileContent);
		ArrayList<String> assignmentFileList = parser.getAssignmentFileList(metadataFileContent);
		try {
			ArrayList<String> assignmentList = _comm.readAssignmentFiles(packageFilePath, assignmentFileList);
			return parser.mergeMetadata(assignmentList, metadata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public void setCommProtocol(ICommunication commProtocol) {
		_comm = commProtocol;
	}

	public void addIlmModule(IlmModule module) {
		_ilmModuleList.put(module.getName(), module);
	}
	
	
	/**
	 * @see IModuleLists
	 */
	@Override
	public HashMap<String, IlmModule> getIlmModuleList() {
		return _ilmModuleList;
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getCurrentState(int index) {
		return _assignmentList.get(index).getCurrentState();
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getInitialState(int index) {
		return _assignmentList.get(index).getInitialState();
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getExpectedAnswer(int index) {
		return _assignmentList.get(index).getExpectedAnswer();
	}
	
	/**
	 * @see IAssignment
	 * 
	 * @return the index of newly created assignment
	 * 		requested by AuthoringGUI in BaseGUI
	 */
	@Override
	public int authorAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		// set in _config a new parameter regarding the authored assignment
		return 0;
	}

	/**
	 * @see IAssignmentOperator
	 * 
	 * @return the converter of file content to domain objects and actions
	 * 		requested by the iLM Modules
	 */
	@Override
	public DomainConverter getConverter() {
		return _converter;
	}

	/**
	 * @see IAssignmentOperator
	 * 
	 * @return the reader and writer of files
	 * 		requested by the iLM Modules
	 */
	@Override
	public ICommunication getFileRW() {
		return _comm;
	}

}
