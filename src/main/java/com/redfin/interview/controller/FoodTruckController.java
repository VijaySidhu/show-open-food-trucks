package com.redfin.interview.controller;


import com.redfin.interview.com.redfin.interview.entity.FoodTruck;
import com.redfin.interview.service.FoodTruckService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodTruckController {

    private final FoodTruckService foodTruckService;

    public FoodTruckController(FoodTruckService foodTruckService) {
        this.foodTruckService = foodTruckService;
    }

    @GetMapping(path = "/openfoodtrucks")
    ResponseEntity<List<FoodTruck>> loadCharactersPage(
        @PageableDefault(page = 0, size = 50)
        @SortDefault.SortDefaults({
            @SortDefault(sort = "truckName", direction = Direction.ASC)
        }) Pageable pageable) {
        List<FoodTruck> response = foodTruckService
            .getOpenFoodTruckAtCurrentTime(pageable).getContent();
        return ResponseEntity.ok().body(response);
    }

}
