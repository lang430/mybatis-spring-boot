package tk.mybatis.springboot.conf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class AppConfig {

	@Autowired
	private Properties properties;
	@Bean
	public ObjectMapper ObjectMapper() {
		ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		mapper.findAndRegisterModules();
		return mapper;
	}


	
	/**
	 * HttpClient 配置
	 **/
	@Bean
	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
		// 长连接保持30秒
		PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30,
				TimeUnit.SECONDS);
		pollingConnectionManager.setMaxTotal(properties.getHttpMaxTotal());
		pollingConnectionManager.setDefaultMaxPerRoute(properties.getHttpMaxPerRoute());

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
		// 保持长连接配置，需要在头添加Keep-Alive
		httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
		// RequestConfig.Builder builder = RequestConfig.custom();
		// builder.setConnectionRequestTimeout(200);
		// builder.setConnectTimeout(5000);
		// builder.setSocketTimeout(5000);
		// RequestConfig requestConfig = builder.build();
		// httpClientBuilder.setDefaultRequestConfig(requestConfig);

		HttpClient httpClient = httpClientBuilder.build();

		// httpClient连接配置，底层是配置RequestConfig
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		clientHttpRequestFactory.setConnectTimeout(properties.getHttpConnectTimeout());
		clientHttpRequestFactory.setReadTimeout(properties.getHttpReadTimeout());
		clientHttpRequestFactory.setConnectionRequestTimeout(properties.getHttpConnectionRequestTimeout());
		// clientHttpRequestFactory.setBufferRequestBody(false);
		return clientHttpRequestFactory;
	}

	@Primary
	@Bean
	public RestTemplate customRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		List<HttpMessageConverter<?>> converters2 = new LinkedList<>();
		converters.forEach(c -> {
			if (c instanceof StringHttpMessageConverter) {
				converters2.add(new StringHttpMessageConverter(Charset.forName(StandardCharsets.UTF_8.name())));
			} else {
				converters2.add(c);
			}
		});
		DefaultUriTemplateHandler handler = new DefaultUriTemplateHandler();
		handler.setStrictEncoding(true);
		restTemplate.setUriTemplateHandler(handler);
		restTemplate.setMessageConverters(converters2);
		restTemplate.setMessageConverters(converters2);
		return restTemplate;
	}
	
}
