package com.manning.springdata.hibernate.chapter03.domainmodel.ex04;

import lombok.Getter;

@Getter
public class Bid {

  private Item item;

  public Bid() {
  }

  public Bid(Item item) {
    this.item = item;
    item.bids.add(this); // Bidirectional
  }

}
