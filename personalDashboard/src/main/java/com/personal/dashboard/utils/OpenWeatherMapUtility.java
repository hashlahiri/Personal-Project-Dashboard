package com.personal.dashboard.utils;

import com.personal.dashboard.config.prop.OpenWeatherMapProperties;
import com.personal.dashboard.domain.mongo.openWeatherMap.CurrentWeatherCityResponse;
import com.personal.dashboard.domain.mongo.openWeatherMap.CurrentWeatherRequest;
import com.personal.dashboard.domain.mongo.openWeatherMap.CurrentWeatherResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OpenWeatherMapUtility {

    @Autowired
    private OpenWeatherMapProperties openWeatherMapProperties;

    private final String OPEN_WEATHER_CURRENT_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private final String OPEN_WEATHER_CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(OpenWeatherMapUtility.class);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Invoke OpenWeatherMap API for getting current weather by latitude and longitude
     *
     * @param currentWeatherRequest - {@link CurrentWeatherRequest}
     * @return - {@link CurrentWeatherResponse}
     */
    public CurrentWeatherResponse invokeCurrentWeatherResponseReport(CurrentWeatherRequest currentWeatherRequest) {

        CurrentWeatherResponse response = CurrentWeatherResponse.builder().build();
        try {
            response = WebClient.create(OPEN_WEATHER_CURRENT_FORECAST_URL).get().
                    uri(uriBuilder -> uriBuilder
                            .queryParam("appid", openWeatherMapProperties.getApikey())
                            .queryParam("lat", currentWeatherRequest.getLatitude())
                            .queryParam("lon", currentWeatherRequest.getLongitude())
                            .queryParam("units", currentWeatherRequest.getUnits())
                            .queryParam("lang", currentWeatherRequest.getLanguage())
                            .build() )
                    .retrieve()
                    .bodyToMono(CurrentWeatherResponse.class)
                    .block();

        } catch (Exception e) {

            LOG.error("Something went wrong inside invokeCurrentWeatherResponseReport");
            LOG.error("{} || {} , caused an issue, it would be resolved soon!", e.getMessage(), e.getClass());
        }

        return response;
    }

    /**
     * Invoke current weather by city, stateCode, countryCode response report
     *
     * @param city - String city
     * @param stateCode - String stateCode
     * @param countryCode - String countryCode
     * @return - {@link CurrentWeatherCityResponse}
     */
    public CurrentWeatherCityResponse invokeCurrentWeatherCityResponseReport(String city, String stateCode, String countryCode) {

        CurrentWeatherCityResponse response = CurrentWeatherCityResponse.builder().build();
        try {
            String qValue = city;

            // validation check for 'stateCode' to add to parameter 'q'
            if(!StringUtils.isEmpty(stateCode)) {

                qValue = qValue + "," + stateCode;
            }

            // validation check for 'countryCode' to add to parameter 'q'
            if(!StringUtils.isEmpty(countryCode)) {

                qValue = qValue + "," + countryCode;
            }

            final String finalQValue = qValue;

            response = WebClient.create(OPEN_WEATHER_CURRENT_WEATHER_URL).get().
                    uri(uriBuilder -> uriBuilder
                            .queryParam("appid", openWeatherMapProperties.getApikey())
                            .queryParam("q", finalQValue)
                            .build() )
                    .retrieve()
                    .bodyToMono(CurrentWeatherCityResponse.class)
                    .block();

        } catch (Exception e) {

            LOG.error("Something went wrong inside invokeCurrentWeatherCityResponseReport");
            LOG.error("{} || {} , caused an issue, it would be resolved soon!", e.getMessage(), e.getClass());
        }

        return response;
    }


    /**
     * Invoke current weather by zipCode and countryCode
     *
     * @param zipCode - String zipCode
     * @param countryCode - String countryCode
     * @return - {@link CurrentWeatherCityResponse}
     */
    public CurrentWeatherCityResponse invokeCurrentWeatherCityResponseByZipCodeAndCountryCodeReport(String zipCode, String countryCode) {

        CurrentWeatherCityResponse response = CurrentWeatherCityResponse.builder().build();
        try {
            response = WebClient.create(OPEN_WEATHER_CURRENT_WEATHER_URL).get().
                    uri(uriBuilder -> uriBuilder
                            .queryParam("appid", openWeatherMapProperties.getApikey())
                            .queryParam("zip", zipCode + "," + countryCode)
                            .build() )
                    .retrieve()
                    .bodyToMono(CurrentWeatherCityResponse.class)
                    .block();

        } catch (Exception e) {

            LOG.error("Something went wrong inside invokeCurrentWeatherCityResponseByZipCodeAndCountryCodeReport");
            LOG.error("{} || {} , caused an issue, it would be resolved soon!", e.getMessage(), e.getClass());
        }

        return response;
    }
}
