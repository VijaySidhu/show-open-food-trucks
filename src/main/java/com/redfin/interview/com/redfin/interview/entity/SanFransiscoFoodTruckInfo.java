package com.redfin.interview.com.redfin.interview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SanFransiscoFoodTruckInfo {

  @JsonProperty("applicant")
  private String truckName;

  @JsonProperty("location")
  private String address;

  @JsonProperty("start24")
  private String startTime;

  @JsonProperty("end24")
  private String endTime;

  @JsonProperty("dayofweekstr")
  private String dayOfWeek;

  @JsonProperty("locationid")
  private String locationId;

  @JsonIgnore
  private boolean isOpen;

  public String getTruckName() {
    return truckName;
  }

  public void setTruckName(String truckName) {
    this.truckName = truckName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public boolean isOpen() {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    String day = dayOfWeek;
    Date endTime = null;
    Date startTime = null;
    long startAfter = 0L;
    long endBefore = 0L;
    try {
      endTime = dateFormat.parse(this.endTime);
      startTime = dateFormat.parse(this.startTime);
      startAfter = getCurrentTime().getTime() - startTime.getTime();
      endBefore = endTime.getTime() - getCurrentTime().getTime();
    } catch (Exception e) {
    }
    if (startAfter >= 0 && endBefore >= 0 && getCurrentDay().equalsIgnoreCase(dayOfWeek)) {
      this.isOpen = true;
      System.out.println(
          "This Location ID is open " + locationId + " Truck Name " + truckName + "Address "
              + address);
    }
    return isOpen;
  }

  //Get the current time in the desired format to compare
  public Date getCurrentTime() throws ParseException {

    Calendar calendar = Calendar.getInstance();

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    String d = dateFormat.format(calendar.getTime());

    return dateFormat.parse(d);
  }

  //Get the current day in the desired format to compare
  public String getCurrentDay() {
    String days = new SimpleDateFormat("EEEE").format(new Date());
    return days;
  }
}
