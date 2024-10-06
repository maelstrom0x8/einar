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
package io.ceze.einar.user.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @Column(name = "locations_id", columnDefinition = "bigserial")
    @SequenceGenerator(
            name = "location_seq",
            allocationSize = 1,
            initialValue = 1000,
            sequenceName = "locations_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    private Long id;

    @Column(length = 5)
    private Integer streetNumber;

    @Column(length = 100)
    private String street;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String state;

    @Column(length = 8)
    private String postalCode;

    @Column(length = 2)
    private String country;

    public Location() {}

    public Location(
            Integer streetNumber,
            String street,
            String city,
            String state,
            String postalCode,
            String country) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{"
                + "streetNumber='"
                + streetNumber
                + '\''
                + ", street='"
                + street
                + '\''
                + ", city='"
                + city
                + '\''
                + ", state='"
                + state
                + '\''
                + ", postalCode='"
                + postalCode
                + '\''
                + ", country='"
                + country
                + '\''
                + '}';
    }
}
