package ilm.framework.assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.comm.ICommunication;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;
import ilm.framework.modules.assignment.HistoryModule;
import ilm.framework.modules.assignment.ObjectListModule;
import ilm.framework.modules.assignment.UndoRedoModule;
import ilm.framework.modules.operation.AutomaticCheckingModule;

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
		initModuleList();
		initAssignments();
	}
	
	
	private void initModuleList() {
		_moduleList = new HashMap<String, IlmModule>();
		AutomaticCheckingModule module = new AutomaticCheckingModule(this, this);
		module.setModel(_model);
		addModule(module);
		addModule(new UndoRedoModule());
		addModule(new HistoryModule());
		addModule(new ObjectListModule());
	}
	
	public void addModule(IlmModule module) {
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
				String packageFileName = _config.getValue(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_" + i);
				getConfigFromMetadataFile(loadMetadataFile(packageFileName));
				_assignmentList.addAll(createAssignments(loadAssignmentFiles(packageFileName)));
			}
		}
		else {
			_assignmentList.add(createNewAssignment());
		}
	}

	private ArrayList<Assignment> createAssignments(ArrayList<String> stringList) {
		ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
		AssignmentParser parser = new AssignmentParser();
		int n = getNumberOfAssignments();
		for(int i = 0; i < stringList.size(); i++) {
			Assignment a = parser.convertStringToAssignment(_converter, stringList.get(i));
			assignmentList.add(a);
			if(a.getExpectedAnswer() == null || a.getExpectedAnswer().getList().size() < 1 ||
					!a.getInitialState().equals(a.getCurrentState())) {
				parser.setAssignmentModulesData(_converter, stringList.get(i), _moduleList, i + n);
			}
			else {
				addAssignmentToModules();
			}
			setModulesAssignment(a);
		}
		return assignmentList;
	}

	private Assignment createNewAssignment() {
		AssignmentState initialState = _model.getNewAssignmentState();
		Assignment a = new Assignment("", initialState, initialState, null);
		addAssignmentToModules();
		setModulesAssignment(a);
		return a;
	}

	private void addAssignmentToModules() {
		for(String key : _moduleList.keySet()) {
			if(_moduleList.get(key) instanceof AssignmentModule) {
				((AssignmentModule)_moduleList.get(key)).addAssignment();
			}
		}
	}
	
	private void setModulesAssignment(Assignment assignment) {
		for(String key : _moduleList.keySet()) {
			if(_moduleList.get(key) instanceof AssignmentModule) {
				if(((AssignmentModule)_moduleList.get(key)).getObserverType() != AssignmentModule.ACTION_OBSERVER) {
					assignment.getCurrentState().addObserver((AssignmentModule)_moduleList.get(key));
				}
				if(((AssignmentModule)_moduleList.get(key)).getObserverType() != AssignmentModule.OBJECT_OBSERVER) {
					((AssignmentModule)_moduleList.get(key)).setDomainModel(_model);
					((AssignmentModule)_moduleList.get(key)).setActionObservers(_moduleList.values());
				}
				((AssignmentModule)_moduleList.get(key)).setState(assignment.getCurrentState());
			}
		}
	}
	
	private String loadMetadataFile(String packageFileName) {
		try {
			return _comm.readMetadataFile(packageFileName);
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
	
	private ArrayList<String> loadAssignmentFiles(String packageFileName) {
		AssignmentParser parser = new AssignmentParser();
		String metadataFileContent = loadMetadataFile(packageFileName);
		ArrayList<String> assignmentFileList = parser.getAssignmentFileList(metadataFileContent);
		HashMap<String, String> metadata = parser.convertStringToMap(metadataFileContent, IlmProtocol.METADATA_LIST_NODE);
		try {
			ArrayList<String> assignmentList = _comm.readAssignmentFiles(packageFileName, assignmentFileList);
			return parser.mergeMetadata(assignmentList, metadata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * @see IAssignment
	 * 
	 * @return the index of newly created assignment
	 * 		requested by AuthoringGUI in BaseGUI
	 */
	@Override
	public void saveAssignmentPackage(ArrayList<Assignment> assignmentList, String fileName) {
		AssignmentParser parser = new AssignmentParser();
		String metadataFileContent = parser.createMetadataFileContent(assignmentList, _config.toString());
		ArrayList<String> assignmentNameList = parser.getAssignmentFileList(metadataFileContent);
		ArrayList<String> assignmentContentList = new ArrayList<String>();
		String assignmentContent = "";
		for(int i = 0; i < assignmentList.size(); i++) {
			assignmentContent = parser.convertAssignmentToString(_converter, assignmentList.get(i));
			if(assignmentList.get(i).getExpectedAnswer() == null || assignmentList.get(i).getExpectedAnswer().getList().size() < 1 ||
					!assignmentList.get(i).getInitialState().equals(assignmentList.get(i).getCurrentState())) {
				assignmentContent = parser.getAssignmentModulesData(_converter, assignmentContent, _moduleList, i);
			}
			assignmentContentList.add(assignmentContent);
		}
		try {
			_comm.writeAssignmentPackage(fileName, metadataFileContent, null, null, assignmentNameList, assignmentContentList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public int openAssignmentPackage(String fileName) {
		int initIndex = _assignmentList.size();
		_assignmentList.addAll(createAssignments(loadAssignmentFiles(fileName)));
		getConfigFromMetadataFile(loadMetadataFile(fileName));
		return initIndex;
	}

	@Override
	public AssignmentState newAssignment() {
		Assignment a = createNewAssignment();
		_assignmentList.add(a);
		return a.getCurrentState();
	}

	@Override
	public void closeAssignment(int index) {
		_assignmentList.remove(index);
		for(String key : _moduleList.keySet()) {
			if(_moduleList.get(key) instanceof AssignmentModule) {
				((AssignmentModule)_moduleList.get(key)).removeAssignment(index);
			}
		}
	}

	
	@Override
	public int getNumberOfAssignments() {
		return _assignmentList.size();
	}

	@Override
	public HashMap<String, IlmModule> getIlmModuleList() {
		return _moduleList;
	}

	@Override
	public AssignmentState getCurrentState(int index) {
		return _assignmentList.get(index).getCurrentState();
	}

	@Override
	public AssignmentState getInitialState(int index) {
		return _assignmentList.get(index).getInitialState();
	}

	@Override
	public AssignmentState getExpectedAnswer(int index) {
		return _assignmentList.get(index).getExpectedAnswer();
	}

	@Override
	public HashMap<String, String> getConfig(int index) {
		return _assignmentList.get(index).getConfig();
	}

	@Override
	public HashMap<String, String> getMetadata(int index) {
		return _assignmentList.get(index).getMetadata();
	}

	@Override
	public String getProposition(int index) {
		return _assignmentList.get(index).getProposition();
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

}
