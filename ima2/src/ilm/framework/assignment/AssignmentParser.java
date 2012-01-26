package ilm.framework.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

final class AssignmentParser {

	public Assignment convertStringToAssignment(DomainConverter converter,	
												String assignmentString) {
		String proposition = getProposition(assignmentString);
		AssignmentState initialState = getInitialState(converter, assignmentString);
		AssignmentState currentState = getCurrentState(converter, assignmentString);
		AssignmentState expectedState = getExpectedAnswer(converter, assignmentString);
		HashMap<String, String> config = getConfig(assignmentString);
		HashMap<String, String> metadata = getMetadata(assignmentString);
		
		Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
		assignment.setConfig(config);
		assignment.setMetadata(metadata);
		return assignment;
	}
	
	void setAssignmentModulesData(DomainConverter converter, String assignmentString,
								  HashMap<String, IlmModule> availableList) {
		HashMap<String, AssignmentModule> moduleList = new HashMap<String, AssignmentModule>();
		Document doc = convertXMLStringToDoc(assignmentString);
		Node moduleNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_MODULES_NODE).item(0);
		NodeList moduleDataNodeList = moduleNode.getChildNodes();
		for(int i = 0; i < moduleDataNodeList.getLength(); i++) {
			String nodeName = moduleDataNodeList.item(i).getNodeName();
			if(availableList.containsKey(nodeName)) {
				AssignmentModule module = (AssignmentModule)availableList.get(nodeName);
				AssignmentModule clone = (AssignmentModule)module.clone();
				clone.setContentFromString(converter, moduleDataNodeList.item(i).getTextContent());
				moduleList.put(clone.getName(), clone);
			}
		}
	}

	private AssignmentState getCurrentState(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node initialStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_CURRENT_NODE).item(0);
		return converter.convertStringToAssignment(initialStateNode.getTextContent());
	}

	private AssignmentState getExpectedAnswer(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node initialStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_EXPECTED_NODE).item(0);
		return converter.convertStringToAssignment(initialStateNode.getTextContent());
	}

	private AssignmentState getInitialState(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node initialStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_INITIAL_NODE).item(0);
		return converter.convertStringToAssignment(initialStateNode.getTextContent());
	}

	private String getProposition(String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node header = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_HEADER_NODE).item(0);
		for(int i = 0; i < header.getChildNodes().getLength(); i++) {
			String nodeName = header.getChildNodes().item(i).getNodeName();
			if(nodeName.equals(IlmProtocol.ASSIGNMENT_PROPOSITION)) {
				return header.getChildNodes().item(i).getNodeValue();
			}
		}
		return null;
	}

	public ArrayList<String> getAssignmentFileList(String metadataFileContent) {
		ArrayList<String> assignmentFileList = new ArrayList<String>();
		Document doc = convertXMLStringToDoc(metadataFileContent);
		Node fileNode = doc.getElementsByTagName(IlmProtocol.FILE_LIST_NODE).item(0);
		NodeList assignmentNodeList = fileNode.getChildNodes();
		for(int i = 0; i < assignmentNodeList.getLength(); i++) {
			if(assignmentNodeList.item(i).getNodeName().equals(IlmProtocol.ASSIGNMENT_FILE_NODE)) {
				assignmentFileList.add(assignmentNodeList.item(i).getNodeValue());
			}
		}
		return assignmentFileList;
	}

	public HashMap<String, String> getConfig(String metadataFileContent) {
		HashMap<String, String> configMap = new HashMap<String, String>();
		Document doc = convertXMLStringToDoc(metadataFileContent);
		Node configNode = doc.getElementsByTagName(IlmProtocol.CONFIG_LIST_NODE).item(0);
		NodeList valuesNodeList = configNode.getChildNodes();
		for(int i = 0; i < valuesNodeList.getLength(); i++) {
			configMap.put(valuesNodeList.item(i).getNodeName(), valuesNodeList.item(i).getNodeValue());
		}
		return configMap;
	}

	public HashMap<String, String> getMetadata(String metadataFileContent) {
		HashMap<String, String> metadataMap = new HashMap<String, String>();
		Document doc = convertXMLStringToDoc(metadataFileContent);
		Node metadataNode = doc.getElementsByTagName(IlmProtocol.METADATA_LIST_NODE).item(0);
		NodeList valuesNodeList = metadataNode.getChildNodes();
		for(int i = 0; i < valuesNodeList.getLength(); i++) {
			metadataMap.put(valuesNodeList.item(i).getNodeName(), valuesNodeList.item(i).getNodeValue());
		}
		return metadataMap;
	}

	public ArrayList<String> mergeMetadata(ArrayList<String> assignmentList, HashMap<String, String> metadata) {
		String metadataString = "";
		for(String key : metadata.keySet()) {
			metadataString.concat("<"+key+">"+metadata.get(key)+"</"+key+">");
		}
		for(String assignment : assignmentList) {
			assignment.concat(metadataString);
		}
		return null;
	}
	
	private Document convertXMLStringToDoc(String xmlString) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(xmlString));
			return db.parse(is);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
