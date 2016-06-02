package test;

import com.tanl.kitserver.util.email.EmailSender;

import javax.mail.MessagingException;

/**
 * Created by Administrator on 2016/4/26.
 */
public class AllTest {

	public static void main (String[] args) throws MessagingException {
		if(EmailSender.sendTo("290754291@qq.com")){
			System.out.println("发送成功！！！！");
		}

//		MailSenderInfo mailInfo = new MailSenderInfo();
//		mailInfo.setMailServerHost("smtp.163.com");
////		mailInfo.setMailServerPort("25");
//		mailInfo.setValidate(true);
//		mailInfo.setUserName("tl_friend@163.com");
//		mailInfo.setPassword("wy123456");//您的邮箱密码
//		mailInfo.setFromAddress("tl_friend@163.com");
//		mailInfo.setToAddress("290754291@qq.com");
//		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");
//		mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
//		//这个类主要来发送邮件
//		SimpleMailSender sms = new SimpleMailSender();
//		sms.sendTextMail(mailInfo);//发送文体格式
		//sms.sendHtmlMail(mailInfo);//发送html格式
	}
}

// 线程池
//	ExecutorService cache = Executors.newFixedThreadPool(10);
//for (int i = 1; i <= 100; i++) {
//final int index = i;
//
//		cache.execute(new Runnable() {
//public void run () {
//		try {
//		Thread.sleep(5000);
//
//		} catch (InterruptedException e) {
//		e.printStackTrace();
//		}
//		String threadName = Thread.currentThread().getName();
//		System.out.println(threadName + " execute " + index);
//
//		}
//		});
//		}

//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigureForPool2.class);
//
//		AccountDao accountDao = applicationContext.getBean(AccountDao.class);
//
//		List<Account> accounts = accountDao.find(1L);
//		System.out.println("id == " + accounts.get(1).getId());


//		EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("test-jpa");
//		System.out.println(entityManager.isOpen());
//		entityManager.close();
//		System.out.println(entityManager.isOpen());


//		ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfiguration.class);
//		EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		EntityTransaction entityTransaction = entityManager.getTransaction();
//		entityTransaction.begin();
//
//		Student student = new Student();
//		student.setFirstName("John");
//		student.setLastName("Smith");

//		student = entityManager.find(Student.class, 3L);
//		System.out.println(student.getFirstName());
//		entityManager.remove(student);
//		entityManager.persist(student);
//
//		entityTransaction.commit();
//		entityManager.close();

//mybatis operation
//		UserService us;
//		Reader reader = null;
//		SqlSessionFactory sqlSessionFactory = null;
//		try {
//			 reader = Resources.getResourceAsReader("conf/mybatis-config.xml");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//		UserDo user = new UserDo();
//		sqlSessionFactory.openSession().insert("insertUser");
//	}
//}
