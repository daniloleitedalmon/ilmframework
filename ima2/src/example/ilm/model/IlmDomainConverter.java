package example.ilm.model;

import java.util.ArrayList;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;

public class IlmDomainConverter implements DomainConverter {

	@Override
	public ArrayList<DomainObject> convertStringToObject(String string) {
		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		if(string.length() == 0 || string.equals("<objects></objects>")) {
			return list;
		}
		
		int startIndex, endIndex = 0;
		String name, description, substring = "";
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

	@Override
	public String convertObjectToString(ArrayList<DomainObject> objectList) {
		String objectListString = "<objects>";
		for(DomainObject obj : objectList) {
			objectListString += obj.toXMLString();
		}
		objectListString += "</objects>";
		return objectListString;
	}

	@Override
	public ArrayList<DomainAction> convertStringToAction(String string) {
		ArrayList<DomainAction> list = new ArrayList<DomainAction>();
		if(string.length() == 0) {
			return list;
		}
		
		int startIndex, endIndex = 0;
		String name, description, substring = "";
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

	@Override
	public String convertActionToString(ArrayList<DomainAction> actionList) {
		String actionListString = "<actions>";
		for(DomainAction act : actionList) {
			actionListString += act.toXMLString();
		}
		actionListString += "</actions>";
		return actionListString;
	}
	
}
