package tk.mybatis.springboot.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource({ "classpath:properties.properties" })
public class Properties {

	@Value("${crf.http.maxTotal}")
	private int httpMaxTotal;
	@Value("${crf.http.maxPerRoute}")
	private int httpMaxPerRoute;
	@Value("${crf.http.connectTimeout}")
	private int httpConnectTimeout;
	@Value("${crf.http.readTimeout}")
	private int httpReadTimeout;
	@Value("${crf.http.connectionRequestTimeout}")
	private int httpConnectionRequestTimeout;

	public int getHttpMaxTotal() {
		return httpMaxTotal;
	}

	public void setHttpMaxTotal(int httpMaxTotal) {
		this.httpMaxTotal = httpMaxTotal;
	}

	public int getHttpMaxPerRoute() {
		return httpMaxPerRoute;
	}

	public void setHttpMaxPerRoute(int httpMaxPerRoute) {
		this.httpMaxPerRoute = httpMaxPerRoute;
	}

	public int getHttpConnectTimeout() {
		return httpConnectTimeout;
	}

	public void setHttpConnectTimeout(int httpConnectTimeout) {
		this.httpConnectTimeout = httpConnectTimeout;
	}

	public int getHttpReadTimeout() {
		return httpReadTimeout;
	}

	public void setHttpReadTimeout(int httpReadTimeout) {
		this.httpReadTimeout = httpReadTimeout;
	}

	public int getHttpConnectionRequestTimeout() {
		return httpConnectionRequestTimeout;
	}

	public void setHttpConnectionRequestTimeout(int httpConnectionRequestTimeout) {
		this.httpConnectionRequestTimeout = httpConnectionRequestTimeout;
	}
	

}
