package ilm.framework.assignment.modules;

import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class HistoryModuleGUI extends AssignmentModuleGUI {

	private static final long serialVersionUID = 1L;
	
	private HistoryModule _history;
	
	private JList _list = new JList();
	
	public HistoryModuleGUI() {
		// TODO all user interface
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof HistoryModule) {
			_history = (HistoryModule)o;
			DefaultListModel listModel = new DefaultListModel();
			for(int i = 0; i < _history.getHistory().size(); i++) {
				listModel.addElement(_history.getHistory().get(i).getDescription());
			}
			_list.setModel(listModel);
		}
	}

}
