package test.trimuph.ldap;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.trimuph.ldap.LDAPOperationHandler;

@FixMethodOrder(MethodSorters.DEFAULT)
public class OperationHandlerTestCase {
	LDAPOperationHandler handler;
	
	@Before
	public void connect(){
		handler = new LDAPOperationHandler();
		handler.connect();
	}
	
	
	@Test
	public void testAdd() {
		handler.addEntry("test");
	}
	
	@Test
	public void testModify() {
		handler.modifyEntry("test");
	}
	
	@Test
	public void testSearch(){
		handler.search();
	}
	
	
	@Test
	public void testDelete() {
		handler.deleteEntry("test");
	}
	
	
	@After
	public void disconnect(){
		handler.disconnect();	
	}

}
