package com.manning.springdata.hibernate.chapter02;

import com.manning.springdata.hibernate.chapter02.model.Message;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldHibernateToJPATest {

  static EntityManagerFactory createEntityManagerFactory() {
    var config = new Configuration(); // hibernate configuration auto loads *.cfg.xml
    // same of <mapping class="com.manning.springdata.hibernate.chapter02.model.Message"/>
    // config.configure().addAnnotatedClass(Message.class);
    var properties = new Properties();
    var propertyNames = config.getProperties().propertyNames();
    while (propertyNames.hasMoreElements()) {
      var element = (String) propertyNames.nextElement();
      properties.put(element, config.getProperties().getProperty(element));
    }

    return Persistence.createEntityManagerFactory("chapter02", properties);
  }


  @Test
  public void storeLoadMessage() {
    var emf = createEntityManagerFactory();
    try {
      var em = emf.createEntityManager();
      em.getTransaction().begin();
      var message = new Message();
      var textToPersist = "Hello World from JPA to Hibernate!";
      message.setText(textToPersist);
      em.persist(message);
      em.getTransaction().commit();
      // SELECT * from MESSAGE
      var messages = em.createQuery("select m from Message m", Message.class).getResultList();
      //
      assertAll(
              () -> assertEquals(1, messages.size()),
              () -> assertEquals(textToPersist, messages.get(0).getText())
      );
      //em.close();
    } finally {
      emf.close();
    }
  }
}
