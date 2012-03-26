package ilm.framework.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipFile;

public class IlmAppletFileRW implements ICommunication {

	@Override
	public String readMetadataFile(String packageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> readResourceFiles(String packageName,
			ArrayList<String> resourceList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> readAssignmentFiles(String packageName,
			ArrayList<String> assignmentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZipFile writeAssignmentPackage(String packageName, String metadata,
			ArrayList<String> resourceNameList, ArrayList<String> resourceList,
			ArrayList<String> assignmentNameList,
			ArrayList<String> assignmentList) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}


}
