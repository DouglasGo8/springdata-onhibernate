package com.manning.springdata.hibernate.chapter02;

import com.manning.springdata.hibernate.chapter02.model.Message;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloWorldHibernateTest {

  private static SessionFactory createSessionFactory() {
    var conf = new Configuration();
    conf.configure().addAnnotatedClass(Message.class);
    //conf.configure().configure("hibernate.cfg.xml");
    var serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(conf.getProperties()).build();
    //
    return conf.buildSessionFactory(serviceRegistry);


  }


  @Test
  public void storeLoadMessage() {
    try (var sessionFactory = createSessionFactory();
         var session = sessionFactory.openSession()) {
      session.beginTransaction();
      //
      var message = new Message();
      message.setText("Hello World, from Hibernate!");
      session.persist(message);
      session.getTransaction().commit();
      //
      session.beginTransaction();
      var criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
      // (N) Call the getResultList() method of the query object to get the results.
      // The query that is created and executed will be SELECT * FROM MESSAGE.
      criteriaQuery.from(Message.class);
      //
      var listOf = session.createQuery(criteriaQuery).getResultList();
      //
      session.getTransaction().commit();
      //
      assertAll(
              () -> assertEquals(1, listOf.size()),
              () -> assertEquals("Hello World, from Hibernate!",
                      listOf.get(0).getText())
      );
    }
  }
}
