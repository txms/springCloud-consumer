package com.bitsun.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableCaching
public class ConsumerApplication {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
		serializeConfig.put(Long.class, ToStringSerializer.instance);

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializeConfig(serializeConfig);

		fastConverter.setFastJsonConfig(fastJsonConfig);

		/**
		 *
		 *  fastjson默认对json序列化的时候进行循环引用的检测，从而避免了出现StackOverFlow异常,但是这里关闭了循环的检测，需要注意不要进行循环引用
		 *  可以在字段或者getter方法上使用@JSONField(serialize=false)注解来设置某些域不进行序列化，从而避免循环引用的情况发生。
		 *   关闭循环引用检测功能后，在序列化的时候会出现StackOverFlow异常，这时需要用户处理好属性之间是否有循环引用的情况出现，这里解决了返回ref的问题
		 */
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	@LoadBalanced
	@Bean
	public AsyncRestTemplate asyncRestTemplate(){
		return new AsyncRestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
