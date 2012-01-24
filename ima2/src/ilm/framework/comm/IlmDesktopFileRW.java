package ilm.framework.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.zip.ZipFile;

public class IlmDesktopFileRW implements ICommunication {

	@Override
	public String readMetadataFile(String packageName) throws IOException {
		File sourceZipFile = new File(packageName);
		try {
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			InputStream inMetadata = zipFile.getInputStream(zipFile.getEntry("metadata.xml"));
			String metadataContent = convertInputStreamToString(inMetadata);
			zipFile.close();
			return metadataContent;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
	public void writeAssignmentPackage(String packageName, String metadata,
			ArrayList<String> resourceList, ArrayList<String> assignmentList) {
		// TODO Auto-generated method stub
		
	}


	public String convertInputStreamToString(InputStream in) {
		if(in != null) {
			StringWriter writer = new StringWriter();
			char[] buffer = new char[1024];
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				int n;
				while((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return writer.toString();
		} else {
			return "";			
		}
	}

}