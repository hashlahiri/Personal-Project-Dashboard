package com.personal.dashboard.repository.currentWeatherCity;

import com.personal.dashboard.domain.openWeatherMap.CurrentWeatherCityResponse;

public interface CurrentWeatherCityRepositoryCustom {

    public CurrentWeatherCityResponse getByCityStateCountry(String city, String stateCode, String countryCode);
}