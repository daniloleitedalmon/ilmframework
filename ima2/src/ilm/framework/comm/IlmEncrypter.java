package ilm.framework.comm;

import java.util.ArrayList;

public class IlmEncrypter implements IEncrypter {

	@Override
	public ArrayList<String> encryptFileContent(ArrayList<String> fileContent) {
		return fileContent;
	}

	@Override
	public ArrayList<String> decryptFromFile(ArrayList<String> fileContent) {
		return fileContent;
	}

}
