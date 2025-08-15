package com.manning.springdata.hibernate.chapter03.metamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(generator = "ID_GENERATOR")
  private Long id;

  @NotNull
  @Size(
          min = 2,
          max = 255,
          message = "Name is required, maximum 255 characters."
  )
  private String name;

  @Future
  private Date auctionEnd;


}