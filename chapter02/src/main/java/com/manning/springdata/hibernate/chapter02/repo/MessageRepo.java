package com.manning.springdata.hibernate.chapter02.repo;

import com.manning.springdata.hibernate.chapter02.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {
}
