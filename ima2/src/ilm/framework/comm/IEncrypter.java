package ilm.framework.comm;

import java.util.ArrayList;

public interface IEncrypter {

	public ArrayList<String> encryptFileContent(ArrayList<String> fileContent);
	
	public ArrayList<String> decryptFromFile(ArrayList<String> fileContent);
	
}
