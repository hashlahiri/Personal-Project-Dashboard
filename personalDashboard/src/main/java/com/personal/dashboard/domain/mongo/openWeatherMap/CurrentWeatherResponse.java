package com.personal.dashboard.domain.mongo.openWeatherMap;

import com.personal.dashboard.domain.mongo.openWeatherMap.helper.City;
import com.personal.dashboard.domain.mongo.openWeatherMap.helper.ListObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Current weather response
 *
 * NOTE: Non collection POJO
 */
@Document("currentWeather")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CurrentWeatherResponse {

    private String cod;
    private String message;
    private Integer cnt;
    private List<ListObject> list;
    private City city;
}
