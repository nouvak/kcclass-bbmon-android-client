package si.kcclass.bbmonandroidclient.rest.clients;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import si.kcclass.bbmonandroidclient.domain.Device;

@RunWith(RobolectricTestRunner.class)
public class DeviceClientTest {

	private DeviceClient restClient;
	
    @Before
    public void setUp() {
    	restClient = new DeviceClient();
    }

	@Test
	public void testInvokeService() throws Exception {
		Map<String, String> requestProperties = new HashMap<String, String>();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("monSysId", "14");
		arguments.put("monSysCustomer", "13");
		List<Device> listDevices = restClient.invokeService("/devices/getDev", 
				arguments,
				requestProperties);
		assertNotNull(listDevices);
		assertTrue(listDevices.size() > 0);
	}
}
