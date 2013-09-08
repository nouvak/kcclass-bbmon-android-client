package si.kcclass.bbmonandroidclient.rest.clients;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import si.kcclass.bbmonandroidclient.domain.MonitoringSystem;

@RunWith(RobolectricTestRunner.class)
public class MonitoringSystemClientTest {

	private MonitoringSystemClient restClient;
	
    @Before
    public void setUp() {
    	restClient = new MonitoringSystemClient();
    }

	@Test
	public void testInvokeService() throws Exception {
		Map<String, String> requestProperties = new HashMap<String, String>();
		List<MonitoringSystem> listMonSys = restClient.invokeService("/devices/getMonSys", 
				requestProperties);
		assertNotNull(listMonSys);
		assertTrue(listMonSys.size() > 0);
	}
}
