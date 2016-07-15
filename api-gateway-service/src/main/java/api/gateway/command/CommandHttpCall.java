package api.gateway.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class CommandHttpCall extends HystrixCommand<String> {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(CommandHttpCall.class);

	private final String url;
	private final boolean keepAlive;

	public CommandHttpCall(String url,boolean keepAlive) {
		super(
				Setter.withGroupKey(
						HystrixCommandGroupKey.Factory
								.asKey("hystrix.command.http"))
						.andCommandKey(
								HystrixCommandKey.Factory
										.asKey("hystrix.command.http"))
						.andThreadPoolKey(
								HystrixThreadPoolKey.Factory
										.asKey("hystrix.command.http"))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties
										.Setter()
										.withCircuitBreakerRequestVolumeThreshold(
												2)
										.withCircuitBreakerSleepWindowInMilliseconds(
												60 * 1000)
										.withFallbackEnabled(true)
										.withExecutionIsolationThreadInterruptOnTimeout(
												true)
										.withExecutionTimeoutInMilliseconds(
												5000)));
		this.url = url;
		this.keepAlive=keepAlive;
	}

	@Override
	protected String run() throws Exception {
		logger.info("Execution of Command: url={}", url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		if(keepAlive){
			httpGet.addHeader("Connection", "Keep-Alive"); 
		}
		try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
			HttpEntity entity = (HttpEntity) response.getEntity();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(entity.getContent()));
			String total = "";
			String line = bufferedReader.readLine();
			while (line != null) {
				total += line;
				line = bufferedReader.readLine();
			}
			return total;
		}

	}

	@Override
	protected String getFallback() {
		return "failbackFor" + url;
	}

}
