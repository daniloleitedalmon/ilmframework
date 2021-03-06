package ilm.framework.comm;

import ilm.framework.IlmProtocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class IlmDesktopFileRW implements ICommunication {

	@Override
	public String readMetadataFile(String packageName) throws IOException {
		File sourceZipFile = new File(packageName);
		try {
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			InputStream inMetadata = zipFile.getInputStream(zipFile.getEntry(IlmProtocol.METADATA_FILENAME));
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
	public ArrayList<String> readResourceFiles(String packageName, ArrayList<String> resourceList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> readAssignmentFiles(String packageName, 
												 ArrayList<String> assignmentFileList) throws IOException {
		File sourceZipFile = new File(packageName);
		try {
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			InputStream in;
			ArrayList<String> assignmentContentList = new ArrayList<String>();
			for(String fileName : assignmentFileList) {
				in = zipFile.getInputStream(zipFile.getEntry(fileName));
				assignmentContentList.add(convertInputStreamToString(in));
			}
			zipFile.close();
			return assignmentContentList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ZipFile writeAssignmentPackage(String packageName, 
										String metadata,
										ArrayList<String> resourceNameList,
										ArrayList<String> resourceList, 
										ArrayList<String> assignmentNameList,
										ArrayList<String> assignmentList) {
		writeFile(metadata, IlmProtocol.METADATA_FILENAME);
		for(int i = 0; i < assignmentNameList.size(); i++) {
			writeFile(assignmentList.get(i), assignmentNameList.get(i));
		}
		
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(packageName));
			BufferedReader in = new BufferedReader(new FileReader(IlmProtocol.METADATA_FILENAME));
			zos.putNextEntry(new ZipEntry(IlmProtocol.METADATA_FILENAME));
			int c;
			while ((c = in.read()) != -1)
		        zos.write(c);
			in.close();
			zos.closeEntry();
			for(String fileName : assignmentNameList) {
				in = new BufferedReader(new FileReader(fileName));
				zos.putNextEntry(new ZipEntry(fileName));
				while ((c = in.read()) != -1)
			        zos.write(c);
				in.close();
				zos.closeEntry();
			}
			zos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Return something meaningfull
		return null;
	}


	private void writeFile(String content, String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
