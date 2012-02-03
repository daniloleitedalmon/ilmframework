package ilm.framework.assignment.modules;

import ilm.framework.modules.IlmModule;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		list = new JList();
		contentPane.add(list, BorderLayout.CENTER);
	}
	
	public void setModule(IlmModule module) {
		_history = (HistoryModule)module;
		setTitle(_history.getName());
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
