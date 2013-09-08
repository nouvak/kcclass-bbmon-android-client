package si.kcclass.bbmonandroidclient.rest;

import static junit.framework.Assert.*;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import si.kcclass.bbmonandroidclient.rest.HttpConnector;

@RunWith(RobolectricTestRunner.class)
public class HttpConnectorTest {
	
	private HttpConnector httpConnector;

    @Before
    public void setUp() {
    	httpConnector = new HttpConnector();
    }

    @Test
    public void testCreate() throws Exception {
    	Map<String, String> requestProperties = new HashMap<String, String>();
    	HttpURLConnection connection = httpConnector.create(
    			"http://kcclass-bbmon.cfapps.io/devices/getMonSys", 
    			HttpConnector.HTTP_GET, requestProperties);
    	assertNotNull(connection);
    	assertTrue(connection.getResponseCode() == HttpStatus.SC_OK);
    }

}
