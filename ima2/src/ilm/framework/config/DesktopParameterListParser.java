package ilm.framework.config;


import java.util.HashMap;
import java.util.Map;

public class DesktopParameterListParser implements IParameterListParser {

	@Override
	public Map<String, String> Parse(String[] parameterList) {
		Map<String, String> result = new HashMap<String, String>();
		
		for(int i = 0; i < parameterList.length; i++) {
            if ((parameterList[i].startsWith("-")) && (i+1 < parameterList.length)) {
				String key = parameterList[i].substring(1);
				String value = parameterList[i+1];
				
				result.put(key, value);
				i++;
			}
        }
		
		return result;
	}

}
