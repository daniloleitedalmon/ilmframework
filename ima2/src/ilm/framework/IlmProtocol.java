package ilm.framework;

import java.util.zip.ZipFile;

public interface IlmProtocol {

	/**
	 * Parameters from iLM specification
	 */
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String LANGUAGE = "language";
	public static final String NUMBER_OF_PACKAGES = "iLM_PARAM_NumberOfAssigments";
	public static final String ASSIGNMENT_PACKAGE_PATH = "iLM_PARAM_AssignmentURL";
	public static final String ASSIGNMENT_CONTENT = "iLM_PARAM_Assignment";
	public static final String AUTHORING_MODE = "iLM_PARAM_Authoring";
	public static final String FEEDBACK = "iLM_PARAM_Feedback";
	public static final String SEND_ANSWER = "iLM_PARAM_SendAnswer";
	public static final String CHANGE_PAGE = "iLM_PARAM_ChangePage";
	
	/**
	 * File attributes from iLM specification
	 */
	public static final String METADATA_FILENAME = "metadata.xml";
	public static final String PACKAGE_NODE = "package";
	public static final String FILE_LIST_NODE = "files";
	public static final String ASSIGNMENT_FILE_NODE = "assignment";
	public static final String RESOURCE_FILE_NODE = "resource";
	public static final String CONFIG_LIST_NODE = "config";
	public static final String METADATA_LIST_NODE = "metadata";
	public static final String ASSIGNMENT_HEADER_NODE = "header";
	public static final String ASSIGNMENT_PROPOSITION = "proposition";
	public static final String ASSIGNMENT_INITIAL_NODE = "initial";
	public static final String ASSIGNMENT_CURRENT_NODE = "current";
	public static final String ASSIGNMENT_EXPECTED_NODE = "expected";
	public static final String ASSIGNMENT_MODULES_NODE = "modules";
	public static final String OBJECT_LIST_MODULE_NAME = "object_list";
	public static final String HISTORY_MODULE_NAME = "history";
	public static final String UNDO_REDO_MODULE_NAME = "undo_redo";
	public static final String AUTO_CHECKING_MODULE_NAME = "automatic_checking";
	
	/**
	 * Configuration attributes/properties from iLM specification
	 */
	public static final String ACTIVE_ASSIGNMENT_INDEX = "active_assignment_index"; 
	
	/**
	 * @return a number which represents the grade obtained by the
	 * 		student for this assignment (percentage of correctness)
	 */
	public float getEvaluation();
	
	/**
	 * @return a string with the converted solution created by the 
	 * 		student for this assignment
	 */
	public String getAnswer();
	
	/**
	 * @return a zip package with all the assignment data
	 */
	public ZipFile getAssignmentPackage();

}
