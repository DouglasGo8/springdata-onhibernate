package com.manning.springdata.hibernate.chapter03.domainmodel.ex03;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class Item {
  private final Set<Bid> bids = new HashSet<>();

  public Set<Bid> getBids() {
    return Collections.unmodifiableSet(bids);
  }

  public void addBid(Bid bid) {
    if (bid == null)
      throw new NullPointerException("Can't add null Bid");
    if (bid.getItem() == this)
      throw new IllegalStateException("Bid is already assigned to an Item");
    bids.add(bid);
    bid.setItem(this);
  }

}
