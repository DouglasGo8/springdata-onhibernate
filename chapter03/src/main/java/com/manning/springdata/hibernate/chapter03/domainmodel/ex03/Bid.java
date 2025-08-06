package com.manning.springdata.hibernate.chapter03.domainmodel.ex03;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Bid {

  private Item item;

  void setItem(Item item) {
    this.item = item;
  }

}
