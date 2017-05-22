import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chair.manager.service.ManagerService;
import com.chair.manager.service.UsersService;


public class MsmMain {
	
	public static void main(String[] args) {
		
		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		context.getBean(UsersService.class);*/
		ManagerService m=new ManagerService();
		System.out.println(m.getMd5("123456"));
	}

}
