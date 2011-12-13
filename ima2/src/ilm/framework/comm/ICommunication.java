package ilm.framework.comm;

public interface ICommunication {

	public void writeToFile(String fileName, String fileContent);
	
	public String readFromFile(String fileName);
	
}
