package ilm.framework.assignment.modules;

import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

public class ObjectListModuleGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private ObjectListModule _objectList;
	private JList _list = new JList();
	
	public ObjectListModuleGUI() {
		// TODO all user interface
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ObjectListModule) {
			_objectList = (ObjectListModule)o;
			
			// TODO need a better non-brute force way to do this
			DefaultListModel listModel = new DefaultListModel();
			for(int i = 0; i < _objectList.getObjectList().size(); i++) {
				listModel.addElement(_objectList.getObjectList().get(i).getDescription());
			}
			_list.setModel(listModel);
		}
	}

}
