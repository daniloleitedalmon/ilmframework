package ilm.framework.config;

import java.util.Map;

public interface IParameterListParser {

	Map<String, String> Parse(String[] parameterList);
	
}
