package ilm.framework.comm;

public interface IEncrypter {

	public String encryptFileContent(String fileContent);
	
	public String decryptFromFile(String fileContent);
	
}
