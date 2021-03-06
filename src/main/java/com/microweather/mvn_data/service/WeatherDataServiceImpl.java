package com.microweather.mvn_data.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.microweather.mvn_data.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;

/**
 * WeatherDataService 实现.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	
	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";


	@Autowired
	private JedisCluster cluster;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		return this.doGetWeahter(uri);
	}
	
	private WeatherResponse doGetWeahter(String uri) {
		String key = uri;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;

		if(cluster.exists(key)){
			logger.info("Redis has data");
			strBody = cluster.get(key);
		}
		else {
			logger.info("Redis don't has data");
			// 缓存没有，抛出异常
			throw new RuntimeException("Don't has data!");
		}

		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error("Error!",e);
		}
		System.out.println(resp.getData().getCity());
		
		return resp;
	}

}
