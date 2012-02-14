package example.ilm.model;

import java.util.ArrayList;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;

public class IlmDomainConverter implements DomainConverter {

	/**
	 * Converts a string to a list of DomainObject
	 * @param a string containing a list of objects in a XML form.
	 * @return a list of DomainObject
	 * 
	 * This method looks for each instance of "<objectsubstring>" and for its
	 * fields, creating an ObjectSubString with a name, a description and a substring,
	 * and then add it to a list
	 * 
     * @throws it is not yet implemented but this method may in the future
     * throw exceptions due to parsing errors
	 */
	@Override
	public ArrayList<DomainObject> convertStringToObject(String string) {
		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		if(string.length() == 0 || string.equals("<objects></objects>")) {
			return list;
		}
		
		int startIndex, endIndex = 0;
		String name, description, substring = "";
		try {
			do {
				startIndex = string.indexOf("<objectsubstring>", endIndex);
				name = string.substring(string.indexOf("<name>", startIndex) + "<name>".length(), 
						string.indexOf("</name>", startIndex));
				description = string.substring(string.indexOf("<description>", startIndex) + "<description>".length(), 
											   string.indexOf("</description>", startIndex));
				substring = string.substring(string.indexOf("<substring>", startIndex) + "<substring>".length(), 
						   					 string.indexOf("</substring>", startIndex));
				endIndex = string.indexOf("</objectsubstring>", startIndex);
				list.add(new ObjectSubString(name, description, substring));
			} while (endIndex < string.lastIndexOf("</objectsubstring>"));
			return list;
		}
		catch (Exception e) {
			// TODO warn the user that the conversion gone wrong
			return new ArrayList<DomainObject>();
		}
	}

	/**
	 * Converts a list of DomainObject to a string
     * @param objectList
     * @return a string containing the description of the list of DomainObjects.
     * 
     * This method sets the header of the XML string as a list of objects and then
     * calls toString for each object in the list received as parameter
	 */
	@Override
	public String convertObjectToString(ArrayList<DomainObject> objectList) {
		String objectListString = "<objects>";
		for(DomainObject obj : objectList) {
			objectListString += obj.toString();
		}
		objectListString += "</objects>";
		return objectListString;
	}

	/**
	 * Converts a string to a list of DomainAction
     * @param actionListDescription
     * @return a list of DomainActions created using the description. Return an empty
	 * list if the string is empty or incompatible.
	 * 
	 * This method looks for each instance of "action>" and for its fields, creating an 
	 * ActionAddSubstring or a ActionRemoveSubstring with a name, a description and a substring,
	 * and then add it to a list
	 * 
     * @throws it is not yet implemented but this method may in the future
     * throw exceptions due to parsing errors
	 */
	@Override
	public ArrayList<DomainAction> convertStringToAction(String string) {
		ArrayList<DomainAction> list = new ArrayList<DomainAction>();
		if(string.length() == 0) {
			return list;
		}
		
		int startIndex, endIndex = 0;
		String name, description, substring = "";
		try {
			do {
				startIndex = string.indexOf("action>", endIndex);
				name = string.substring(string.indexOf("<name>", startIndex) + "<name>".length(), 
						string.indexOf("</name>", startIndex));
				description = string.substring(string.indexOf("<description>", startIndex) + "<description>".length(), 
											   string.indexOf("</description>", startIndex));
				substring = string.substring(string.indexOf("<substring>", startIndex) + "<substring>".length(), 
						   					 string.indexOf("</substring>", startIndex));
				endIndex = string.indexOf("action>", startIndex + 1) + 1;
				
				if(string.substring(startIndex - "add".length(), startIndex).equals("add")) {
					list.add(new ActionAddSubString(name, description, substring));
				} else {
					list.add(new ActionRemoveSubString(name, description, substring));
				}
			} while (endIndex < string.lastIndexOf("</addaction>") |
					 endIndex < string.lastIndexOf("</removeaction>"));
			return list;
		}
		catch (Exception e) {
			// TODO warn the user that the conversion gone wrong
			return new ArrayList<DomainAction>();
		}
	}

	/**
	 * Converts a list of DomainAction to a string
     * @param actionList
     * @return a string containing the description of the list of DomainActions
     * 
     * This method  sets the header of the XML string as a list of objects and then
     * calls toString for each object in the list received as parameter
	 */
	@Override
	public String convertActionToString(ArrayList<DomainAction> actionList) {
		String actionListString = "<actions>";
		for(DomainAction act : actionList) {
			actionListString += act.toString();
		}
		actionListString += "</actions>";
		return actionListString;
	}
	
}
