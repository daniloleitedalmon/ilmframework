package ilm.framework.comm;

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
	public void writeToFile(String fileName, String fileContent) {
		_fileRW.writeToFile(fileName, _encrypter.encryptFileContent(fileContent));
	}

	@Override
	public String readFromFile(String fileName) {
		return _encrypter.decryptFromFile(_fileRW.readFromFile(fileName));
	}

}
