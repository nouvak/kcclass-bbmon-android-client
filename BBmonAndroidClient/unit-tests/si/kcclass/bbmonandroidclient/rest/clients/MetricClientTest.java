package si.kcclass.bbmonandroidclient.rest.clients;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import si.kcclass.bbmonandroidclient.domain.Device;
import si.kcclass.bbmonandroidclient.domain.Metric;

public class MetricClientTest {
	private MetricClient restClient;
	
    @Before
    public void setUp() {
    	restClient = new MetricClient();
    }

	@Test
	public void testInvokeService() throws Exception {
		Map<String, String> requestProperties = new HashMap<String, String>();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("monSysId", "14");
		arguments.put("monSysCustomer", "13");
		arguments.put("devId", "14139");
		List<Metric> listMetrics = restClient.invokeService("/devices/getMetrics", 
				arguments,
				requestProperties);
		assertNotNull(listMetrics);
		assertTrue(listMetrics.size() > 0);
	}
}
