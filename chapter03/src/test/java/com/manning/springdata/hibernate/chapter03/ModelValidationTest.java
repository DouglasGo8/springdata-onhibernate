package com.manning.springdata.hibernate.chapter03;


import com.manning.springdata.hibernate.chapter03.validation.Item;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelValidationTest {

  @Test
  public void validateItem() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      var item = new Item();
      item.setName("Some Item");
      item.setAuctionEnd(new Date());
      var violations = validator.validate(item);
      var violation = violations.iterator().next();
      String failedPropertyName = violation.getPropertyPath().iterator().next().getName();
      //
      assertAll(() -> assertEquals(1, violations.size()),
              () -> assertEquals("auctionEnd", failedPropertyName),
              () -> {
                if (Locale.getDefault().getLanguage().equals("en"))
                  assertEquals("must be a future date", violation.getMessage());
              });
    }

  }
}
