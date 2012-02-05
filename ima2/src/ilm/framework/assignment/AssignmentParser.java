package ilm.framework.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.util.ArrayList;
import java.util.HashMap;

final class AssignmentParser {

	/**
	 * CONVERSÃO DE STRING PARA ATIVIDADE (LEITURA DE ARQUIVOS)
	 * @param converter
	 * @param assignmentString
	 * @return
	 */
	public Assignment convertStringToAssignment(DomainConverter converter, String assignmentString) {
		String proposition = getProposition(assignmentString);
		AssignmentState initialState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_INITIAL_NODE);
		AssignmentState currentState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_CURRENT_NODE);
		AssignmentState expectedState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_EXPECTED_NODE);
		HashMap<String, String> config = convertStringToMap(assignmentString, IlmProtocol.CONFIG_LIST_NODE);
		HashMap<String, String> metadata = convertStringToMap(assignmentString, IlmProtocol.METADATA_LIST_NODE);
		
		Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
		assignment.setConfig(config);
		assignment.setMetadata(metadata);
		return assignment;
	}

	public String getProposition(String assignmentString) {
		int startIndex = assignmentString.indexOf("<" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
		int endIndex = assignmentString.indexOf("</" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
		return assignmentString.substring(startIndex + 2 + IlmProtocol.ASSIGNMENT_PROPOSITION.length(), endIndex);
	}

	public AssignmentState getState(DomainConverter converter, String assignmentString, String nodeName) {
		AssignmentState state = new AssignmentState();
		int startIndex = assignmentString.indexOf("<" + nodeName + ">") + 2 + nodeName.length();
		int endIndex = assignmentString.indexOf("</" + nodeName + ">");
		if(startIndex == -1 | endIndex == -1) {
			return state;
		}
		String objectListString = assignmentString.substring(startIndex, endIndex);
		ArrayList<DomainObject> list = converter.convertStringToObject(objectListString);
		state.setList(list);
		return state;
	}

	public HashMap<String, String> convertStringToMap(String xmlContent, String nodeName) {
		int listLength = nodeName.length();
		int startIndex = xmlContent.indexOf("<" + nodeName + ">");
		int endIndex = xmlContent.indexOf("</" + nodeName + ">");
		String listString = xmlContent.substring(startIndex, endIndex + 3 + listLength);

		HashMap<String, String> map = new HashMap<String, String>();
		int nodeLength = 0;
		endIndex = listLength;
		do {
			startIndex = listString.indexOf("<", endIndex + 1);
			nodeLength = listString.indexOf(">", startIndex) - startIndex;
			endIndex = listString.indexOf("</", startIndex);
			map.put(listString.substring(startIndex + 1, startIndex + nodeLength), 
						  listString.substring(startIndex + nodeLength + 1, endIndex));
			// TODO define a better threshold - differences when there are breaklines chars
		} while(endIndex < listString.lastIndexOf("</" + nodeName + ">") - nodeLength - 4);
		return map;
	}

	/**
	 * CONVERSÃO DE ATIVIDADE PARA STRING (GRAVAÇÃO DE ARQUIVOS)
	 * @param converter
	 * @param assignment
	 * @return
	 */
	public String convertAssignmentToString(DomainConverter converter, Assignment assignment) {
		String string = "<" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">";
		string += "<proposition>" + assignment.getProposition() + "</proposition>";
		string += "<initial>" + converter.convertObjectToString(assignment.getInitialState().getList()) + "</initial>";
		if(assignment.getCurrentState().getList().size() > 0) {
			string += "<current>" + converter.convertObjectToString(assignment.getCurrentState().getList()) + "</current>";
		} else {
			string += "<current/>";
		}
		if(assignment.getExpectedAnswer() != null && assignment.getExpectedAnswer().getList().size() > 0) {
			string += "<expected>" + converter.convertObjectToString(assignment.getExpectedAnswer().getList()) + "</expected>";
		} else {
			string += "<expected/>";
		}
		string += "<config>" + convertMapToString(assignment.getConfig()) + "</config>";
		string += "<metadata>" + convertMapToString(assignment.getMetadata()) + "</metadata>";
		string += "</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">";
		return string;
	}
	
	/**
	 * Convert a HashMap<String, String> to a XMLString with its contents.
	 * The order of the parameters is inverse alphabetical order of the keys.
	 * @param map - the HashMap<String, String> to be converted
	 * @return - the String containing the map content in XML form
	 */
	public String convertMapToString(HashMap<String, String> map) {
		String string = "";
		for(String key : map.keySet()) {
			string += "<" + key + ">" + map.get(key) + "</" + key + ">";
		}
		return string;
	}

	/**
	 * COLETA/ESCRITA DE DADOS DO ARQUIVO DE METADADOS
	 * @param metadataFileContent
	 * @return
	 */
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
		} while (endIndex < fileListString.lastIndexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">"));
		return assignmentFileList;
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
	
	public String createMetadataFileContent(ArrayList<Assignment> list, String config) {
		String string = "<package>";
		if(list.size() < 1) {
			return null;
		}
		string += "<files>";
		for(Assignment a : list) {
			string += "<assignment>" + a.getName() + "</assignment>";
		}
		string += "</files></config>" + config + "</config><metadata>";
		HashMap<String, String> mergedMetadata = new HashMap<String, String>();
		for(Assignment a : list) {
			mergedMetadata = mergeMap(mergedMetadata, a.getMetadata());
		}
		string += convertMapToString(mergedMetadata) + "</metadata></package>";
		return string;
	}

	private HashMap<String, String> mergeMap(HashMap<String, String> mergedMap, HashMap<String, String> map) {
		for(String key : map.keySet()) {
			if(!mergedMap.containsKey(key)) {
				mergedMap.put(key, map.get(key));
			}
		}
		return mergedMap;
	}

	/**
	 * CONVERSÃO DE DADOS DOS MÓDULOS DE ATIVIDADES
	 * @param converter
	 * @param assignmentString
	 * @param availableList
	 */
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
	
	public String getAssignmentModulesData(DomainConverter converter, String assignmentString, 
										   HashMap<String, IlmModule> availableList) {
		String string = "<" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">";
		for(String key : availableList.keySet()) {
			if(availableList.get(key) instanceof AssignmentModule) {
				string += ((AssignmentModule)availableList.get(key)).getStringContent(converter);
			}
		}
		string += "</" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">";
		int index = assignmentString.lastIndexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">");
		return assignmentString.substring(0, index-1) + string + assignmentString.substring(index);
	}

}
