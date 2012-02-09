package ilm.framework.assignment;

import static org.junit.Assert.*;

import java.util.HashMap;

import ilm.framework.IlmProtocol;
import ilm.framework.comm.CommControl;
import ilm.framework.comm.IlmDesktopFileRW;
import ilm.framework.comm.IlmEncrypter;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;

import org.junit.Before;
import org.junit.Test;

import example.ilm.model.IlmDomainConverter;
import example.ilm.model.IlmDomainModel;

public class AssignmentControlTests {

	private AssignmentControl objUnderTest;
	private SystemConfig config;
	private DomainModel model;
	private DomainConverter converter;
	private CommControl comm;

	@Before
	public void setUp() {
		config = new SystemConfig(false, new HashMap<String, String>());
		model = new IlmDomainModel();
		converter = new IlmDomainConverter();
		comm = new CommControl(config);
		comm.SetEncrypter(new IlmEncrypter());
		comm.SetFileRW(new IlmDesktopFileRW());
	}
	
	@Test
	public void testConstructorSimple() {
		objUnderTest = new AssignmentControl(config, comm, model, converter);
		//objUnderTest.print();
	}
	
	@Test
	public void testConstructorOneAssignment() {
		config.setParameter(IlmProtocol.NUMBER_OF_PACKAGES, "1");
		config.setParameter(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_0", "tests/ilm/framework/assignment/assignmentOne.zip");
		objUnderTest = new AssignmentControl(config, comm, model, converter);
		//objUnderTest.print();
	}
	
	@Test
	public void testConstructorTwoAssignments() {
		config.setParameter(IlmProtocol.NUMBER_OF_PACKAGES, "1");
		config.setParameter(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_0", "tests/ilm/framework/assignment/assignmentTwo.zip");
		objUnderTest = new AssignmentControl(config, comm, model, converter);
		//objUnderTest.print();
	}
	
	@Test
	public void testConstructorTwoPackages() {
		config.setParameter(IlmProtocol.NUMBER_OF_PACKAGES, "2");
		config.setParameter(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_0", "tests/ilm/framework/assignment/assignmentTwo.zip");
		config.setParameter(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_1", "tests/ilm/framework/assignment/assignmentOne.zip");
		objUnderTest = new AssignmentControl(config, comm, model, converter);
		objUnderTest.print();
	}
	
	@Test
	public void testConstructorInitiatedAssignment() {
		fail();
	}
	
	@Test
	public void testAuthorAssignment() {
		fail();
	}
	
	@Test
	public void testSaveAssignment() {
		fail();
	}
	
}
