package com.example.huaweiAPI;

import static org.junit.Assert.assertEquals;
import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.service.UserService;
import com.example.service.impl.TodoListServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuaweiApiApplicationTests {
	
	String Token;
	Long todolistid;
	Long userid;
	Long itemid;
	Long otherid;
	Date yesterday;
	@Autowired
	TodoListServiceImpl service;
	@Autowired
	UserService serv;
	
	
	@Before
	public void setUp() throws Exception {
		
		Token  = "e2FsZzpIUzI1Nix0eXBlOkpXVH0=.e3VzZXJpZDoxLHVzZXJuYW1lOnNhZmFndW5kb2dkdSxleHBpcmU6MTU2OTg4MzQzNjMzN30=.tIH9KWVUl83xCXotVD44lSrsq79RMzaRUDARbagJhIU=";
		todolistid = (long) 39;
		userid = (long)1;
		itemid = (long)8;
		yesterday = new Date(System.currentTimeMillis()-86400000);
		otherid = (long)7;
		System.out.println("Dün:"+yesterday.toString());
		
	}
	
    @Test
    public void testSampleService() {
        assertEquals(
                "class com.example.service.impl.TodoListServiceImpl",
                this.service.getClass().toString());
    }
	
	@Test
	public void serviceAddItemDeadlineIsYesterdayTest() {
		
		String result = service.addItem(todolistid,yesterday, "Test");
		assertEquals("failure", result);
		
	}
	@Test
	public void completeItemThatHasCondition() {
		boolean result = service.completeItem(itemid);
		assertEquals(false, result);
	}
	
	@Test
	public void completeItemThatHasNoCondition() {
		boolean result = service.completeItem(otherid);
		assertEquals(true, result);
	}
	
	@Test
	public void addPreconditionONsameRelationship() {
		
		boolean result = service.addPrecondition(itemid, 5);
		assertEquals(false, result);
		
	}
	
	@Test
	public void sameUsername() {
		
		boolean result = serv.register("test", "test");
		assertEquals(false, result);
		
	}

	

}
