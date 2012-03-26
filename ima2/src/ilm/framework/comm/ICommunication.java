package ilm.framework.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipFile;

public interface ICommunication {

	public String readMetadataFile(String packageName) 
			throws IOException;
	
	public ArrayList<String> readResourceFiles(String packageName, ArrayList<String> resourceList)
			throws IOException;
	
	public ArrayList<String> readAssignmentFiles(String packageName, ArrayList<String> assignmentList)
			throws IOException;
	
	public ZipFile writeAssignmentPackage(String packageName, 
										String metadata, 
										ArrayList<String> resourceNameList,
										ArrayList<String> resourceList, 
										ArrayList<String> assignmentNameList,
										ArrayList<String> assignmentList)
			throws IOException;
	
}
