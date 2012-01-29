package ilm.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;

public class IlmDomainConverter implements DomainConverter {

	@Override
	public ArrayList<DomainObject> convertStringToObject(String objectListDescription) {
		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		Document doc = convertXMLStringToDoc(objectListDescription);
		NodeList objectList = doc.getChildNodes().item(0).getChildNodes();
		for(int i = 0; i < objectList.getLength(); i++) {
			NodeList objAtt = objectList.item(i).getChildNodes();
			ObjectSubString obj = new ObjectSubString(objAtt.item(0).getTextContent(),
												  	  objAtt.item(1).getTextContent(),
												  	  objAtt.item(2).getTextContent());
			list.add(obj);
		}
		return list;
	}

	@Override
	public String convertObjectToString(ArrayList<DomainObject> objectList) {
		String objectListString = "<objects>";
		for(DomainObject obj : objectList) {
			ObjectSubString subStringObj = (ObjectSubString)obj;
			objectListString += "<objectsubstring>";
			objectListString += "<name>" + subStringObj.getName() + "</name>";
			objectListString += "<description>" + subStringObj.getDescription() + "</description>";
			objectListString += "<substring>" + subStringObj.getSubString() + "</substring>";
			objectListString += "</objectsubstring>";
		}
		objectListString += "</objects>";
		return objectListString;
	}

	@Override
	public ArrayList<DomainAction> convertStringToAction(String actionListDescription) {
		ArrayList<DomainAction> list = new ArrayList<DomainAction>();
		Document doc = convertXMLStringToDoc(actionListDescription);
		NodeList actionList = doc.getChildNodes().item(0).getChildNodes();
		for(int i = 0; i < actionList.getLength(); i++) {
			NodeList actionAtt = actionList.item(i).getChildNodes();
			if(actionList.item(i).getNodeName().equals("addaction")) {
				ActionAddSubString action = new ActionAddSubString(actionAtt.item(0).getTextContent(),
	 															   actionAtt.item(1).getTextContent()); 
				action.setSubString(actionAtt.item(2).getTextContent());
				list.add(action);
			}
			else if(actionList.item(i).getNodeName().equals("removeaction")) {
				ActionRemoveSubString action = new ActionRemoveSubString(actionAtt.item(0).getTextContent(),
						   												 actionAtt.item(1).getTextContent()); 
				action.setSubString(actionAtt.item(2).getTextContent());
				list.add(action);
			}
		}
		return list;
	}

	@Override
	public String convertActionToString(ArrayList<DomainAction> actionList) {
		String actionListString = "<actions>";
		for(DomainAction act : actionList) {
			if(act instanceof ActionAddSubString) {
				ActionAddSubString addAction = (ActionAddSubString)act;
				actionListString += "<addaction>";
				actionListString += "<name>" + addAction.getName() + "</name>";
				actionListString += "<description>" + addAction.getDescription() + "</description>";
				actionListString += "<substring>" + addAction.getSubString() + "</substring>";
				actionListString += "</addaction>";
			}
			else {
				ActionRemoveSubString delAction = (ActionRemoveSubString)act;
				actionListString += "<removeaction>";
				actionListString += "<name>" + delAction.getName() + "</name>";
				actionListString += "<description>" + delAction.getDescription() + "</description>";
				actionListString += "<substring>" + delAction.getSubString() + "</substring>";
				actionListString += "</removeaction>";
			}
			
		}
		actionListString += "</actions>";
		return actionListString;
	}

	private static Document convertXMLStringToDoc(String xmlString) {
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
