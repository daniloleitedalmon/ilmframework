package ilm.framework.comm;

import java.io.IOException;
import java.util.ArrayList;

import ilm.framework.config.SystemConfig;

public class CommControl implements ICommunication {
	
	private SystemConfig _config;
	private IEncrypter _encrypter;
	private ICommunication _fileRW;
	
	public CommControl(SystemConfig config) {
		_config = config;
	}
	
	public void SetEncrypter(IEncrypter encrypter) {
		_encrypter = encrypter;
	}
	
	public void SetFileRW(ICommunication fileRW) {
		_fileRW = fileRW;
	}

	@Override
	public String readMetadataFile(String packageName) {
		try {
			return _fileRW.readMetadataFile(packageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> readResourceFiles(String packageName, ArrayList<String> resourceList) {
		try {
			return _fileRW.readResourceFiles(packageName, resourceList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> readAssignmentFiles(String packageName, ArrayList<String> assignmentList) {
		try {
			return _encrypter.decryptFromFile(_fileRW.readAssignmentFiles(packageName, assignmentList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void writeAssignmentPackage(String packageName, 
										String metadata,
										ArrayList<String> resourceNameList,
										ArrayList<String> resourceList, 
										ArrayList<String> assignmentNameList,
										ArrayList<String> assignmentList) {
		try {
			_fileRW.writeAssignmentPackage(packageName, metadata, resourceNameList, resourceList, 
											assignmentNameList, _encrypter.encryptFileContent(assignmentList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
