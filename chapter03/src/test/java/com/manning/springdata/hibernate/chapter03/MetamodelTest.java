package com.manning.springdata.hibernate.chapter03;


import com.manning.springdata.hibernate.chapter03.metamodel.Item;
import com.manning.springdata.hibernate.chapter03.metamodel.Item_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MetamodelTest {

  private static EntityManagerFactory emf;

  @BeforeAll
  static void beforeAll() {
    emf = Persistence.createEntityManagerFactory("chapter03.metamodel");
  }


  @Test
  public void accessDynamicMetamodel() {

    var metamodel = emf.getMetamodel();
    var managedTypes = metamodel.getManagedTypes();
    var itemType = managedTypes.iterator().next();

    assertAll(() -> assertEquals(1, managedTypes.size()),
            () -> assertEquals(
                    Type.PersistenceType.ENTITY,
                    itemType.getPersistenceType()));

    var idAttribute =
            itemType.getSingularAttribute("id");
    assertFalse(idAttribute.isOptional());

    var nameAttribute =
            itemType.getSingularAttribute("name");

    assertAll(() -> assertEquals(String.class, nameAttribute.getJavaType()),
            () -> assertEquals(
                    Attribute.PersistentAttributeType.BASIC,
                    nameAttribute.getPersistentAttributeType()
            ));

    var auctionEndAttribute =
            itemType.getSingularAttribute("auctionEnd");
    assertAll(() -> assertEquals(Timestamp.class, auctionEndAttribute.getJavaType()),
            () -> assertFalse(auctionEndAttribute.isCollection()),
            () -> assertFalse(auctionEndAttribute.isAssociation())
    );

  }

  @Test
  public void accessStaticMetamodel() {
    var em = emf.createEntityManager();
    deleteItems(em);
    persistItems(em);

    var cb = em.getCriteriaBuilder();
    var query = cb.createQuery(Item.class);
    var fromItem = query.from(Item.class);
    query.select(fromItem);
    var items = em.createQuery(query).getResultList();

    assertEquals(2, items.size());
  }

  @Test
  public void testItemsPattern() {
    var em = emf.createEntityManager();
    deleteItems(em);
    persistItems(em);

    var cb = em.getCriteriaBuilder();
    var query = cb.createQuery(Item.class);
    var fromItem = query.from(Item.class);
    Path<String> namePath = fromItem.get("name");
    query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
    //
    var items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
    assertAll(() -> assertEquals(1, items.size()),
            () -> assertEquals("Item 1", items.iterator().next().getName()));
  }

  @Test
  public void testItemsPatternWithGeneratedClass() {
    EntityManager em = emf.createEntityManager();
    deleteItems(em);
    persistItems(em);

    var cb = em.getCriteriaBuilder();
    var query = cb.createQuery(Item.class);
    var fromItem = query.from(Item.class);
    // mvn clean compile generate-sources
    Path<String> namePath = fromItem.get(Item_.name);
    query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
    var items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
    assertAll(() -> assertEquals(1, items.size()),
            () -> assertEquals("Item 1", items.iterator().next().getName()));
  }

  private void deleteItems(EntityManager em) {
    em.getTransaction().begin();
    var cb = em.getCriteriaBuilder();
    var query = cb.createCriteriaDelete(Item.class);
    query.from(Item.class);
    em.createQuery(query).executeUpdate();
    em.getTransaction().commit();
  }

  private void persistItems(EntityManager em) {
    em.getTransaction().begin();
    Item item1 = new Item();
    item1.setName("Item 1");
    item1.setAuctionEnd(tomorrow());

    Item item2 = new Item();
    item2.setName("Item 2");
    item2.setAuctionEnd(tomorrow());

    em.persist(item1);
    em.persist(item2);
    em.getTransaction().commit();
  }

  private Date tomorrow() {
    return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
  }

  @AfterAll
  static void afterAll() {
    emf.close();
  }
}
