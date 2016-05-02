package test;

import com.tanl.kitserver.model.bean.User;
import com.tanl.kitserver.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Administrator on 2016/4/26.
 */
public class AllTest {
	public static void main(String[] args) {
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
		UserService us;
		Reader reader = null;
		SqlSessionFactory sqlSessionFactory = null;
		try {
			 reader = Resources.getResourceAsReader("conf/mybatis-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		User user = new User();
		user.setName("cc");
		user.setAge(22);
		sqlSessionFactory.openSession().insert("insertUser");
	}
}
