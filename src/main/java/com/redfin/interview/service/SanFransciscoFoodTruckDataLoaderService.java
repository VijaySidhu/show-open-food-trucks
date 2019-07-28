package com.redfin.interview.service;

import com.redfin.interview.com.redfin.interview.entity.FoodTruck;
import com.redfin.interview.com.redfin.interview.entity.FoodTruckRepository;
import com.redfin.interview.com.redfin.interview.entity.SanFransiscoFoodTruckInfo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

/**
 * This is scheduled service and will be called at fixed rate in case if there is any change in schedule SFO Food truck API will be
 * called again and we will have accurate data.
 * Created by VijaySidhu on 7/21/2019.
 */

@Configuration
@EnableScheduling
public class SanFransciscoFoodTruckDataLoaderService implements InitializingBean {


  private final RestTemplate restTemplate;

  private final String sfoUri;

  @Autowired
  private FoodTruckRepository foodTruckRepository;

  public SanFransciscoFoodTruckDataLoaderService(RestTemplate restTemplate,
      @Value("${sfo.uri}") String sfoUri) {
    this.restTemplate = restTemplate;
    this.sfoUri = sfoUri;
  }

  /**
   * This method maps repsponse of SFO Food Tuck Web Service to FoodTruck Response that will be display as output
   * @param foodTruckEntities
   * @return
   */
  private static List<FoodTruck> mapToFoodTruck(
      List<SanFransiscoFoodTruckInfo> foodTruckEntities) {
    List<FoodTruck> foodTruckReponses = foodTruckEntities.stream()
        .filter(p->p.isOpen())
        .collect(
            Collectors.mapping(
                p -> new FoodTruck(p.getTruckName(), p.getAddress()),
                Collectors.toList()));
    return foodTruckReponses;
  }

  // TODO We can read this expression from properties files
  // Scheduled Call to food truck API
  @Scheduled(cron="0 0 * ? * *")
  public List<SanFransiscoFoodTruckInfo> loadFoodTruckInfo() {
    ResponseEntity<SanFransiscoFoodTruckInfo[]> sfoFoodTrucks = getSFOData();
    return Arrays.asList(sfoFoodTrucks.getBody());

  }

  //This method call food truck API
  private ResponseEntity<SanFransiscoFoodTruckInfo[]> getSFOData() {
    ResponseEntity<SanFransiscoFoodTruckInfo[]> sfoFoodTrucks = restTemplate
        .getForEntity(sfoUri, SanFransiscoFoodTruckInfo[].class);
    if (foodTruckRepository.count() >= 1L) {
      foodTruckRepository.deleteAll();
    }
    foodTruckRepository.saveAll(mapToFoodTruck(Arrays.asList(sfoFoodTrucks.getBody())));
    return sfoFoodTrucks;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    getSFOData();
  }
}
