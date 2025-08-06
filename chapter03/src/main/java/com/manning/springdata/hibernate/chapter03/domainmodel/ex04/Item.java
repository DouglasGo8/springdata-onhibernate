package com.manning.springdata.hibernate.chapter03.domainmodel.ex04;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class Item {
  // bad -approach
  Set<Bid> bids = new HashSet<>();

  public Set<Bid> getBids() {
    return Collections.unmodifiableSet(bids);
  }

}
