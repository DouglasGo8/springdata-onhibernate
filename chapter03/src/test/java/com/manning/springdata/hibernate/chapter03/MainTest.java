package com.manning.springdata.hibernate.chapter03;

import com.manning.springdata.hibernate.chapter03.domainmodel.ex02.User;
import com.manning.springdata.hibernate.chapter03.domainmodel.ex03.Bid;
import com.manning.springdata.hibernate.chapter03.domainmodel.ex03.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MainTest {


  @Test
  @Disabled
  public void userPOJO() {

    // persistence

    var user = new User();
    var name = "John bla";

    user.setName(name);

    log.info("{}", user.getName());

    assertEquals(name, user.getName());


  }


  @Test
  public void testBidsAndItem() {

    var item = new Item();
    var bid = new Bid();

    item.addBid(bid);

    assertNotNull(item.getBids());
    assertNotNull(bid.getItem());

    var exception = assertThrows(IllegalStateException.class, () -> {
      // Same bid instance shouldn't be added again
      item.addBid(bid);
    });

    assertTrue(exception.getMessage().contains("Bid is already assigned to an Item"));
  }
}
