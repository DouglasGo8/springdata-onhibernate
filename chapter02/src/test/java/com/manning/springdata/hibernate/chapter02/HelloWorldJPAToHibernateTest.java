package com.manning.springdata.hibernate.chapter02;

import com.manning.springdata.hibernate.chapter02.model.Message;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldJPAToHibernateTest {

  // Switching between JPA and Hibernate
  public static SessionFactory getSessionFactory(EntityManagerFactory emf) {
    return emf.unwrap(SessionFactory.class);
  }

  @Test
  public void storeLoadMessage() {
    var emf = Persistence.createEntityManagerFactory("chapter02");

    try (var sessionFactory = getSessionFactory(emf)) {
      var session = sessionFactory.openSession();
      session.beginTransaction();

      var message = new Message();
      var textToPersist = "Hello World from JPA to Hibernate!";

      message.setText(textToPersist);
      session.persist(message);
      //
      session.getTransaction().commit();
      session.beginTransaction();
      var criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class); // CriteriaQuery<Query>
      criteriaQuery.from(Message.class);   // SELECT * from MESSAGE
      var messages = session.createQuery(criteriaQuery).getResultList();
      session.getTransaction().commit();
      //
      assertAll(
              () -> assertEquals(1, messages.size()),
              () -> assertEquals(textToPersist, messages.get(0).getText())
      );
    }
  }
}
