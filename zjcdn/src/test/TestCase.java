package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zicms.web.task.task.InsertFirmTask;



public class TestCase {
	
    private ClassPathXmlApplicationContext cxt = null;
    private InsertFirmTask insertFirmTask;
    
    @Before
    public void before() {
        cxt = new ClassPathXmlApplicationContext("spring-config.xml");
    }
    
    @Test
    public void insertTask() throws InterruptedException{
    	insertFirmTask = cxt.getBean("insertFirmTask", InsertFirmTask.class);
    	insertFirmTask.run();
    }
    
}
