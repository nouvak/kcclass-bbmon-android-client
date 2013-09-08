package si.kcclass.bbmonandroidclient.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RestClientTest {
	
	private RestClient<String> restClient;
	
    @Before
    public void setUp() {
    	restClient = new RestClient<String>() {
			
			@Override
			protected String parseJson(String strJson) {
				return strJson;
			}
		};
    }

	@Test
	public void testInvokeService() throws Exception {
		Map<String, String> requestProperties = new HashMap<String, String>();
		String strListMonSys = restClient.invokeService("/devices/getMonSys", 
				requestProperties);
		assertNotNull(strListMonSys);
		assertEquals("[{\"mon_sys_id\":14,\"mon_sys_name\":\"monitoring_system\"}]", strListMonSys);
	}

}
