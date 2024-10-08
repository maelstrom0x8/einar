/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ceze.einar.space.domain.model;

import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

@Entity
@Table(name = "spaces")
public class Space {

  @Id
  @Column(name = "space_id", columnDefinition = "bigserial")
  @SequenceGenerator(
      name = "space_sequence",
      sequenceName = "spaces_id_seq",
      initialValue = 1000,
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "space_sequence")
  private Long id;

  private String description;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User createdBy;

  @Column(precision = 10, scale = 3)
  private BigDecimal price;

  private Currency currency;

  private SpaceType type;

  public Space() {}

  public Space(
      String description, Location location, User createdBy, BigDecimal price, SpaceType type) {
    this.description = description;
    this.location = location;
    this.createdBy = createdBy;
    this.price = price;
    this.type = type;

    //      @TODO: fix this
    Locale locale = Arrays.stream(Locale.getAvailableLocales())
        .filter(loc -> loc.getCountry().equals(location.getCountry()))
        .findFirst()
        .orElseThrow();
    this.currency = Currency.getInstance(locale);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public @NotNull String getDescription() {
    return description;
  }

  public void setDescription(@NotNull String description) {
    this.description = description;
  }

  public @NotNull Location getLocation() {
    return location;
  }

  public void setLocation(@NotNull Location location) {
    this.location = location;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
