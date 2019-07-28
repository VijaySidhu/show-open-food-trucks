package com.redfin.interview.com.redfin.interview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food_truck")
public class FoodTruck {

  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;

  private String truckName;

  private String address;

  public FoodTruck() {
  }

  public FoodTruck(String truckName, String address) {
    this.truckName = truckName;
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTruckName() {
    return truckName;
  }

  public void setTruckName(String truckName) {
    this.truckName = truckName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
