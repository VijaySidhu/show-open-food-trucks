package com.redfin.interview;

import com.redfin.interview.com.redfin.interview.entity.FoodTruck;
import com.redfin.interview.com.redfin.interview.entity.FoodTruckRepository;
import com.redfin.interview.service.FoodTruckService;
import java.util.Scanner;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class FoodTruckApplication {


  public static void main(String[] args) {

    ApplicationContext applicationContext = SpringApplication.run(FoodTruckApplication.class, args);
    FoodTruckService foodTruckService = (FoodTruckService) applicationContext
        .getBean("foodTruckService");
    int i = 1;

    boolean exit = false;
    //Initializers
    if (i == 1) {
      System.out.println("The list of food trucks that are open now in San Francisco");
      System.out
          .println("----------------------------------------------------------------------------");
      String specifiers = "%-80s %-30s %n";
      System.out.format(specifiers, "NAME", "ADDRESS");
      i++;
    }
    // Wait for user input
    Scanner scanner = new Scanner(System.in);
    while (!exit) {
      scanner.nextLine();
      Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
      Page<FoodTruck> foodTruckPage = foodTruckService.getOpenFoodTruckAtCurrentTime(pageable);
      int totalPages = foodTruckPage.getTotalPages();
      foodTruckPage.get().forEach(foodTruck -> {
        System.out.println(foodTruck.getTruckName() + " " + foodTruck.getAddress());
      });

    }
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }

  @Bean
  public FoodTruckService foodTruckService(FoodTruckRepository foodTruckRepository) {
    return new FoodTruckService(foodTruckRepository);
  }

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
  }

}
