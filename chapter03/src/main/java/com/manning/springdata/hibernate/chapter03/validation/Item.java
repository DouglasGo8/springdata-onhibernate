package com.manning.springdata.hibernate.chapter03.validation;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Item {
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
