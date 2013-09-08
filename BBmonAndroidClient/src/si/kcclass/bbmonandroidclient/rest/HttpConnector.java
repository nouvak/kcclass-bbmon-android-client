package si.kcclass.bbmonandroidclient.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class HttpConnector {
	
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";

	public HttpURLConnection create(String requestUrl,
			String httpRequestMethod, Map<String, String> requestProperties)
			throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url
				.openConnection();
		httpUrlConnection.setRequestMethod(httpRequestMethod);
		for (Entry<String, String> entry : requestProperties.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			httpUrlConnection.addRequestProperty(key, value);
		}
		httpUrlConnection.connect();
		return httpUrlConnection;
	}

}
