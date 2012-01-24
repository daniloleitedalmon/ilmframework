package ilm.framework.comm;

import java.io.IOException;
import java.util.ArrayList;

public interface ICommunication {

	public String readMetadataFile(String packageName) throws IOException;
	
	public ArrayList<String> readResourceFiles(String packageName, ArrayList<String> resourceList);
	
	public ArrayList<String> readAssignmentFiles(String packageName, ArrayList<String> assignmentList);
	
	public void writeAssignmentPackage(String packageName, 
										String metadata, 
										ArrayList<String> resourceList,
										ArrayList<String> assignmentList);
	
}
