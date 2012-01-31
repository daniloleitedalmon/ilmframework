package ilm.framework.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

final class AssignmentParser {

	public Assignment convertStringToAssignment(DomainConverter converter, String assignmentString) {
		String proposition = getProposition(assignmentString);
		AssignmentState initialState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_INITIAL_NODE);
		AssignmentState currentState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_CURRENT_NODE);
		AssignmentState expectedState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_EXPECTED_NODE);
		HashMap<String, String> config = getConfig(assignmentString);
		HashMap<String, String> metadata = getMetadata(assignmentString);
		
		Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
		assignment.setConfig(config);
		assignment.setMetadata(metadata);
		return assignment;
	}
	
	public void setAssignmentModulesData(DomainConverter converter, String assignmentString,
								  		 HashMap<String, IlmModule> availableList) {
		int startIndex = assignmentString.indexOf("<" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">");
		int endIndex = assignmentString.indexOf("</" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">");
		String moduleListString = assignmentString.substring(startIndex + IlmProtocol.ASSIGNMENT_MODULES_NODE.length() + 2, 
															 endIndex);
		String moduleString = "";
		for(String key : availableList.keySet()) {
			startIndex = moduleListString.indexOf("<" + key);
			endIndex = moduleListString.indexOf("</" + key);
			if(endIndex != -1) {
				moduleString = moduleListString.substring(startIndex + key.length() + 2, endIndex);
			} else {
				moduleString = "";
			}
			if(availableList.get(key) instanceof AssignmentModule) {
				((AssignmentModule)availableList.get(key)).setContentFromString(converter, moduleString);
			}
		}
	}

	public AssignmentState getState(DomainConverter converter, String assignmentString, String nodeName) {
		int startIndex = assignmentString.indexOf("<" + nodeName + ">") + 2 + nodeName.length();
		int endIndex = assignmentString.indexOf("</" + nodeName + ">");
		String objectListString = assignmentString.substring(startIndex, endIndex);
		ArrayList<DomainObject> list = converter.convertStringToObject(objectListString);
		AssignmentState state = new AssignmentState();
		state.setList(list);
		return state;
	}
	
	public String getProposition(String assignmentString) {
		int startIndex = assignmentString.indexOf("<" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
		int endIndex = assignmentString.indexOf("</" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
		return assignmentString.substring(startIndex + 2 + IlmProtocol.ASSIGNMENT_PROPOSITION.length(), endIndex);
	}

	public ArrayList<String> getAssignmentFileList(String metadataFileContent) {
		int listLength = IlmProtocol.FILE_LIST_NODE.length();
		int startIndex = metadataFileContent.indexOf("<" + IlmProtocol.FILE_LIST_NODE + ">");
		int endIndex = metadataFileContent.indexOf("</" + IlmProtocol.FILE_LIST_NODE + ">");
		String fileListString = metadataFileContent.substring(startIndex, endIndex + 3 + listLength);

		ArrayList<String> assignmentFileList = new ArrayList<String>();
		int fileLength = IlmProtocol.ASSIGNMENT_FILE_NODE.length();
		endIndex = 0;
		do {
			startIndex = fileListString.indexOf("<" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">", endIndex) + 2 + fileLength;
			endIndex = fileListString.indexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">", startIndex);
			assignmentFileList.add(fileListString.substring(startIndex, endIndex));
		} while (endIndex < fileListString.length() - 6 - listLength - fileLength);
		return assignmentFileList;
	}

	public HashMap<String, String> getConfig(String metadataFileContent) {
		int listLength = IlmProtocol.CONFIG_LIST_NODE.length();
		int startIndex = metadataFileContent.indexOf("<" + IlmProtocol.CONFIG_LIST_NODE + ">");
		int endIndex = metadataFileContent.indexOf("</" + IlmProtocol.CONFIG_LIST_NODE + ">");
		String configListString = metadataFileContent.substring(startIndex, endIndex + 3 + listLength);

		HashMap<String, String> configMap = new HashMap<String, String>();
		int configLength = 0;
		endIndex = listLength;
		do {
			startIndex = configListString.indexOf("<", endIndex + 1);
			configLength = configListString.indexOf(">", startIndex) - startIndex;
			endIndex = configListString.indexOf("</", startIndex);
			configMap.put(configListString.substring(startIndex + 1, startIndex + configLength), 
						  configListString.substring(startIndex + configLength + 1, endIndex));
		} while(endIndex < configListString.length() - 6 - listLength - configLength);
		return configMap;
	}

	public HashMap<String, String> getMetadata(String metadataFileContent) {
		int listLength = IlmProtocol.METADATA_LIST_NODE.length();
		int startIndex = metadataFileContent.indexOf("<" + IlmProtocol.METADATA_LIST_NODE + ">");
		int endIndex = metadataFileContent.indexOf("</" + IlmProtocol.METADATA_LIST_NODE + ">");
		String metadataListString = metadataFileContent.substring(startIndex, endIndex + 3 + listLength);

		HashMap<String, String> metadataMap = new HashMap<String, String>();
		int metadataLength = 0;
		endIndex = listLength;
		do {
			startIndex = metadataListString.indexOf("<", endIndex + 1);
			metadataLength = metadataListString.indexOf(">", startIndex) - startIndex;
			endIndex = metadataListString.indexOf("</", startIndex);
			metadataMap.put(metadataListString.substring(startIndex + 1, startIndex + metadataLength), 
						  metadataListString.substring(startIndex + metadataLength + 1, endIndex));
		} while(endIndex < metadataListString.length() - 6 - listLength - metadataLength);
		return metadataMap;
	}

	public ArrayList<String> mergeMetadata(ArrayList<String> assignmentList, HashMap<String, String> metadata) {
		String metadataString = "";
		for(String key : metadata.keySet()) {
			metadataString += "<" + key + ">" + metadata.get(key) + "</" + key + ">";  
		}
		
		int metadataIndex = 0;
		String mergedAssignment = "";
		ArrayList<String> mergedList = new ArrayList<String>();
		for(String assignment : assignmentList) {
			metadataIndex = assignment.indexOf("<" + IlmProtocol.METADATA_LIST_NODE + ">");
			if(metadataIndex == -1) {
				metadataString = "<" + IlmProtocol.METADATA_LIST_NODE + ">" +
								 metadataString +
								 "</" + IlmProtocol.METADATA_LIST_NODE + ">";
				mergedAssignment = assignment.substring(0, assignment.indexOf("</" + IlmProtocol.PACKAGE_NODE + ">"));
				mergedAssignment += metadataString + "</" + IlmProtocol.PACKAGE_NODE + ">";
				mergedList.add(mergedAssignment);
			} else {
				int endIndex = assignment.indexOf("</" + IlmProtocol.METADATA_LIST_NODE + ">");
				mergedAssignment = assignment.substring(0, endIndex);
				mergedAssignment += metadataString + "</" + IlmProtocol.METADATA_LIST_NODE + ">" +
									assignment.substring(endIndex +	3 + IlmProtocol.METADATA_LIST_NODE.length());
				mergedList.add(mergedAssignment);
			}
		}
		return mergedList;
	}
	
	public static String convertDocToXMLString(Document doc) {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = factory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			writer.close();
			return writer.toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Document convertXMLStringToDoc(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
	        InputStream is = new ByteArrayInputStream(xmlString.getBytes());
	        return builder.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
