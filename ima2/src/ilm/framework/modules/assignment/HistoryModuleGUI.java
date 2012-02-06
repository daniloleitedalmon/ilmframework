package ilm.framework.modules.assignment;

import ilm.framework.IlmProtocol;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

public class HistoryModuleGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private HistoryModule _history;
	private JPanel contentPane;
	private JList list;

	public HistoryModuleGUI() {
		setBounds(100, 100, 200, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		list = new JList();
		contentPane.add(list, BorderLayout.CENTER);
		setTitle(IlmProtocol.HISTORY_MODULE_NAME);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof HistoryModule) {
			_history = (HistoryModule)o;
			DefaultListModel listModel = new DefaultListModel();
			for(int i = 0; i < _history.getHistory().size(); i++) {
				listModel.addElement(_history.getHistory().get(i).getDescription());
			}
			list.setModel(listModel);
		}
	}

}
