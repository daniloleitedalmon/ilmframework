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

public final class AssignmentControl implements IAssignment, IAssignmentOperator {

	private SystemConfig _config;
	private DomainModel _model;
	private DomainConverter _converter;
	private ICommunication _comm;
	private ArrayList<Assignment> _assignmentList;
	private HashMap<String, IlmModule> _moduleList;
	
	public AssignmentControl(SystemConfig config, ICommunication comm, DomainModel model, DomainConverter converter) {
		_config = config;
		_comm = comm;
		_model = model;
		_converter = converter;
		initIlmModuleList();
		initAssignments();
	}
	
	private void initIlmModuleList() {
		_moduleList = new HashMap<String, IlmModule>();
		IlmModule module = new AutomaticCheckingModule(this, this);
		((AutomaticCheckingModule)module).setModel(_model);
		_moduleList.put(module.getName(), module);
		module = new UndoRedoModule();
		_moduleList.put(module.getName(), module);
		module = new HistoryModule();
		_moduleList.put(module.getName(), module);
		module = new ObjectListModule();
		_moduleList.put(module.getName(), module);
	}	
	
	private void initAssignments() {
		int numberOfPackages;
		try {
			numberOfPackages = Integer.parseInt(_config.getValue(IlmProtocol.NUMBER_OF_PACKAGES));
		}
		catch(NumberFormatException e) {
			numberOfPackages = 0;
			// TODO inform the user that the number of assignment was wrong
		}
		_assignmentList = new ArrayList<Assignment>();
		if(numberOfPackages > 0) {
			for(int i = 0; i < numberOfPackages; i++) {
				String metadata = loadPackageFile(i);
				_assignmentList.addAll(createAssignments(loadAssignmentFiles(i, metadata)));
				getConfigFromMetadataFile(metadata);
			}
		}
		else {
			Assignment a = createNewAssignment();
			setModulesObservers(a);
			_assignmentList.add(a);
		}
	}

	private ArrayList<Assignment> createAssignments(ArrayList<String> stringList) {
		ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
		AssignmentParser parser = new AssignmentParser();
		for(String assignmentString : stringList) {
			Assignment a = parser.convertStringToAssignment(_converter, assignmentString);
			assignmentList.add(a);
			setModulesObservers(a);
			parser.setAssignmentModulesData(_converter, assignmentString, _moduleList);
		}
		return assignmentList;
	}
	
	private Assignment createNewAssignment() {
		AssignmentState initialState = _model.getNewAssignmentState();
		return new Assignment("", initialState, initialState, null);
	}
	
	private void setModulesObservers(Assignment assignment) {
		for(String key : _moduleList.keySet()) {
			if(_moduleList.get(key) instanceof AssignmentModule) {
				((AssignmentModule)_moduleList.get(key)).addAssignment();
				if(((AssignmentModule)_moduleList.get(key)).getObserverType() != AssignmentModule.ACTION_OBSERVER) {
					assignment.getCurrentState().addObserver((AssignmentModule)_moduleList.get(key));
				}
			}
		}
	}
	
	private String loadPackageFile(int packageIndex) {
		String packageFilePath = _config.getValue(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_" + packageIndex);
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
		HashMap<String, String> config = parser.convertStringToMap(metadataFileContent, IlmProtocol.CONFIG_LIST_NODE);
		for(String key : config.keySet()) {
			_config.setParameter(key, config.get(key));
		}
	}
	
	private ArrayList<String> loadAssignmentFiles(int packageIndex, String metadataFileContent) {
		String packageFilePath = _config.getValue(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_" + packageIndex);
		AssignmentParser parser = new AssignmentParser();
		ArrayList<String> assignmentFileList = parser.getAssignmentFileList(metadataFileContent);
		HashMap<String, String> metadata = parser.convertStringToMap(metadataFileContent, IlmProtocol.METADATA_LIST_NODE);
		try {
			ArrayList<String> assignmentList = _comm.readAssignmentFiles(packageFilePath, assignmentFileList);
			return parser.mergeMetadata(assignmentList, metadata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addIlmModule(IlmModule module) {
		_moduleList.put(module.getName(), module);
	}
	
	
	/**
	 * @see IModuleLists
	 */
	@Override
	public HashMap<String, IlmModule> getIlmModuleList() {
		return _moduleList;
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
	 * @see IAssignment
	 * 
	 * @return the number of assignments on assignment list
	 */
	@Override
	public int getNumberOfAssignments() {
		return _assignmentList.size();
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

	public void print() {
		System.out.println("Assignments: " + _assignmentList.size());
		for(Assignment a : _assignmentList) {
			a.print();
		}
		System.out.println("Modules:");
		for(String key : _moduleList.keySet()) {
			_moduleList.get(key).print();
		}
	}

	@Override
	public int openAssignmentFile(String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AssignmentState newAssignment() {
		Assignment a = createNewAssignment();
		setModulesObservers(a);
		_assignmentList.add(a);
		return a.getCurrentState();
	}

	@Override
	public void closeAssignment(int index) {
		_assignmentList.remove(index);
		removeAssignmentFromModules(index);
	}

	private void removeAssignmentFromModules(int index) {
		for(String key : _moduleList.keySet()) {
			if(_moduleList.get(key) instanceof AssignmentModule) {
				((AssignmentModule)_moduleList.get(key)).removeAssignment(index);
			}
		}
	}

	@Override
	public HashMap<String, String> getConfig(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getMetadata(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProposition(int index) {
		return _assignmentList.get(index).getProposition();
	}
	
}
