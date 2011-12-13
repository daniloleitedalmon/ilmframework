package ilm.framework.assignment;

import ilm.framework.comm.ICommunication;
import ilm.framework.domain.IDomainConverter;

public interface IAssignmentOperator {

	public IDomainConverter getConverter();
	
	public ICommunication getFileRW();
	
}
