package com.manning.springdata.hibernate.chapter02;

import com.manning.springdata.hibernate.chapter02.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.persistence.Persistence;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j

/*@SpringBootTest(classes = {
        com.manning.springdata.hibernate.chapter02.config.DBConfig.class
})*/
public class HelloWorldJPATest {

  @Test
  public void storeLoadMessage() {

    var emf = Persistence.createEntityManagerFactory("chapter02");

    try {
      var em = emf.createEntityManager();
      em.getTransaction().begin();

      Message message = new Message();
      message.setText("Hello World!");

      em.persist(message);

      em.getTransaction().commit();
      //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')

      em.getTransaction().begin();

      var messages = em.createQuery("select m from Message m", Message.class).getResultList();
      //SELECT * from MESSAGE

      messages.get(messages.size() - 1).setText("Hello World from JPA!");

      em.getTransaction().commit();
      //UPDATE MESSAGE set TEXT = 'Hello World from JPA!' where ID = 1

      assertAll(
              () -> assertEquals(1, messages.size()),
              () -> assertEquals("Hello World from JPA!", messages.get(0).getText())
      );

      em.close();

    } finally {
      emf.close();
    }
  }
}
