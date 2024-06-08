package com.manning.springdata.hibernate.chapter02;


import com.manning.springdata.hibernate.chapter02.config.DBConfig;
import com.manning.springdata.hibernate.chapter02.repo.MessageRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DBConfig.class})
public class HelloWorldSpringDataJPATest {

  @Autowired
  MessageRepo repo;

  @Test
  public void storeMessage() {
    var message = new Message();
    message.setText("Hello World from Spring Data JPA!");
    repo.save(message);
    //
    var messages = (List<Message>) repo.findAll();
    log.info("Total message: {}", messages.size());
    //
    assertAll(
            () -> assertEquals(1, messages.size()),
            () -> assertEquals("Hello World from Spring Data JPA!", messages.get(0).getText())
    );
  }
}
