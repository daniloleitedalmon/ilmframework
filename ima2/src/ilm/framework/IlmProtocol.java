package ilm.framework;

public interface IlmProtocol {

	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String LANGUAGE = "language";
	public static final String NUMBER_OF_ASSIGNMENTS = "iLM_PARAM_NumberOfAssigments";
	public static final String ASSIGNMENT_PATH = "iLM_PARAM_AssignmentURL";
	public static final String ASSIGNMENT_CONTENT = "iLM_PARAM_Assignment";
	public static final String AUTHORING_MODE = "iLM_PARAM_Authoring";
	public static final String FEEDBACK = "iLM_PARAM_Feedback";
	public static final String SEND_ANSWER = "iLM_PARAM_SendAnswer";
	public static final String CHANGE_PAGE = "iLM_PARAM_ChangePage";
	public static final String METADATA_FILENAME = "metadata.xml";
	
	public float getEvaluation();
	
	public String getAnswer();

}
