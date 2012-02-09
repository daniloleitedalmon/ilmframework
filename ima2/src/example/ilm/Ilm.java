package example.ilm;

import ilm.framework.IlmProtocol;
import ilm.framework.SystemControl;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class Ilm extends JApplet implements IlmProtocol {
 
	private static final long serialVersionUID = 1L;
	private static IlmProtocol _ilmProtocol;

	public static void main(String[] args) {

    	final SystemControl ilmControl = new SystemControl();
    	ilmControl.initialize(false, args, new IlmSystemFactory());
    	_ilmProtocol = ilmControl.getProtocol();
    	
    	try {
	    	SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                ilmControl.startDesktopGUI();
	            }
	        });
    	} catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
    
    public void init() {
    	String[] args = GetParam();
    	
    	final SystemControl ilmControl = new SystemControl();
    	ilmControl.initialize(false, args, new IlmSystemFactory());
    	_ilmProtocol = ilmControl.getProtocol();
    	
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    add(ilmControl.getAppletGUI());
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

	private String[] GetParam() {
		String[] s = new String[1];
		s[0] = "";
		return s;
	}

	@Override
	public float getEvaluation() {
		return _ilmProtocol.getEvaluation();
	}

	@Override
	public String getAnswer() {
		return _ilmProtocol.getAnswer();
	}
	
}
