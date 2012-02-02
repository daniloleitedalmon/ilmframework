package ilm.framework.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

public class IlmForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HashMap<String, String> _map;
	private HashMap<String, JTextField> _inputMap; 

	public IlmForm(HashMap<String, String> map, String title) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane = createForm(map, title);
		setContentPane(contentPane);
		_map = new HashMap<String, String>();
	}

	private JPanel createForm(HashMap<String, String> map, String title) {
		setTitle(title);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMap();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		_inputMap = new HashMap<String, JTextField>();
		FormLayout layout = new FormLayout("l:p, 2dlu, p:g", "");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();
		for(String key : map.keySet()) {
			_inputMap.put(key, new JTextField(map.get(key)));
			builder.append("&" + key + ": ", _inputMap.get(key));
		}
		builder.append(ButtonBarFactory.buildOKCancelBar(okButton, cancelButton));
		return builder.getPanel();
	}
	
	public HashMap<String, String> getUpdatedMap() {
		return _map;
	}

	private void updateMap() {
		for(String key : _inputMap.keySet()) {
			_map.put(key, _inputMap.get(key).getText());
		}
	}
	
	/**
	 * only for testing
	 * @param newMap
	 */
	public void changeFieldValues(HashMap<String, String> newMap) {
		for(String key : _inputMap.keySet()) {
			_inputMap.get(key).setText(newMap.get(key));
		}
		updateMap();
	}
	
}
