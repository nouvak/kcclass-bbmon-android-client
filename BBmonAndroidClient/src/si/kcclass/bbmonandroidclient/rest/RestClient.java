package si.kcclass.bbmonandroidclient.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

import org.apache.http.HttpStatus;

public abstract class RestClient<T> {
	public static final String SERVER_URL = "http://kcclass-bbmon.cfapps.io";

	private HttpConnector mHttpConnector;

	public RestClient() {
		mHttpConnector = new HttpConnector();
	}

	public RestClient(Class<T> entityClass, HttpConnector httpsConnector) {
		mHttpConnector = httpsConnector;
	}

	public T invokeService(String serviceUrl,
			Map<String, String> requestProperties) throws Exception {
		return invokeService(serviceUrl, null, requestProperties);
	}

	public T invokeService(String serviceUrl, Map<String, String> arguments,
			Map<String, String> requestProperties) throws Exception {
		String requestUrl = SERVER_URL + serviceUrl;
		if (arguments != null && arguments.size() > 0) {
			String strArguments = "?";
			for (Map.Entry<String, String> entry : arguments.entrySet()) {
				strArguments += String.format("%s=%s&", entry.getKey(),
						entry.getValue());
			}
			/* remove the last & character. */
			strArguments = strArguments.substring(0, strArguments.length() - 1);
			requestUrl += strArguments;
		}
		requestProperties.put("Content-length", "0");
		HttpURLConnection restConnection = mHttpConnector.create(requestUrl,
				HttpConnector.HTTP_GET, requestProperties);
		int resposneCode = restConnection.getResponseCode();
		if (resposneCode != HttpStatus.SC_OK) {
			throw new Exception(
					String.format(
							"REST method invocation failed: requestUrl=%s, responseCode=%d",
							requestUrl, resposneCode));
		}
		InputStream getInputStream = restConnection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getInputStream, "UTF-8"));
		String strJson = reader.readLine();
		reader.close();
		restConnection.disconnect();
		return parseJson(strJson);
	}

	protected abstract T parseJson(String strJson);

}
