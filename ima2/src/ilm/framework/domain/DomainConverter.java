package ilm.framework.domain;

import java.util.ArrayList;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;

public interface DomainConverter {
    
    public ArrayList<DomainObject> convertStringToObject(String objectListDescription);
    
    public String convertObjectToString(ArrayList<DomainObject> objectList);
    
    public ArrayList<DomainAction> convertStringToAction(String actionListDescription);
    
    public String convertActionToString(ArrayList<DomainAction> actionList);

}

