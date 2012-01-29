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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	
	public void setAssignmentModulesData(DomainConverter converter, String assignmentString,
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

	public AssignmentState getCurrentState(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node currentStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_CURRENT_NODE).item(0);
		ArrayList<DomainObject> list = converter.convertStringToObject(currentStateNode.getTextContent());
		return new AssignmentState(list);
	}

	public AssignmentState getExpectedAnswer(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node expectedStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_EXPECTED_NODE).item(0);
		ArrayList<DomainObject> list = converter.convertStringToObject(expectedStateNode.getTextContent());
		return new AssignmentState(list);
	}

	public AssignmentState getInitialState(DomainConverter converter, String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node initialStateNode = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_INITIAL_NODE).item(0);
		ArrayList<DomainObject> list = converter.convertStringToObject(initialStateNode.getTextContent());
		return new AssignmentState(list);
	}

	public String getProposition(String assignmentString) {
		Document doc = convertXMLStringToDoc(assignmentString);
		Node header = doc.getElementsByTagName(IlmProtocol.ASSIGNMENT_HEADER_NODE).item(0);
		for(int i = 0; i < header.getChildNodes().getLength(); i++) {
			String nodeName = header.getChildNodes().item(i).getNodeName();
			if(nodeName.equals(IlmProtocol.ASSIGNMENT_PROPOSITION)) {
				return header.getChildNodes().item(i).getTextContent();
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
				assignmentFileList.add(assignmentNodeList.item(i).getTextContent());
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
			configMap.put(valuesNodeList.item(i).getNodeName(), valuesNodeList.item(i).getTextContent());
		}
		return configMap;
	}

	public HashMap<String, String> getMetadata(String metadataFileContent) {
		HashMap<String, String> metadataMap = new HashMap<String, String>();
		Document doc = convertXMLStringToDoc(metadataFileContent);
		Node metadataNode = doc.getElementsByTagName(IlmProtocol.METADATA_LIST_NODE).item(0);
		NodeList valuesNodeList = metadataNode.getChildNodes();
		for(int i = 0; i < valuesNodeList.getLength(); i++) {
			metadataMap.put(valuesNodeList.item(i).getNodeName(), valuesNodeList.item(i).getTextContent());
		}
		return metadataMap;
	}

	public ArrayList<String> mergeMetadata(ArrayList<String> assignmentList, HashMap<String, String> metadata) {
		ArrayList<String> mergedList = new ArrayList<String>();
		for(String assignment : assignmentList) {
			Document doc = convertXMLStringToDoc(assignment);
			Node metadataNode = doc.getElementsByTagName(IlmProtocol.METADATA_LIST_NODE).item(0);
			for(String key : metadata.keySet()) {
				if(doc.getElementsByTagName(key).getLength() == 0) {
					Node newNode = doc.createElement(key);
					newNode.setTextContent(metadata.get(key));
					metadataNode.appendChild(newNode);
				}
			}
			mergedList.add(convertDocToXMLString(doc));
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
