package com.redfin.interview.com.redfin.interview.entity;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface FoodTruckRepository extends CrudRepository<FoodTruck, Long> {

  @Query("select c from FoodTruck c ")
  Page<FoodTruck> findAllPage(Pageable pageable);

  void save(List<SanFransiscoFoodTruckInfo> sanFransiscoFoodTruckInfos);
}
