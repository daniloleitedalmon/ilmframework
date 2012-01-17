package ilm.framework.assignment;

import ilm.framework.comm.ICommunication;
import ilm.framework.domain.DomainConverter;

public interface IAssignmentOperator {

	public DomainConverter getConverter();
	
	public ICommunication getFileRW();
	
}
