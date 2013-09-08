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

import si.kcclass.bbmonandroidclient.domain.Customer;

@RunWith(RobolectricTestRunner.class)
public class CustomerClientTest {
	
	private CustomerClient restClient;
	
    @Before
    public void setUp() {
    	restClient = new CustomerClient();
    }

	@Test
	public void testInvokeService() throws Exception {
		Map<String, String> requestProperties = new HashMap<String, String>();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("monSysId", "14");
		List<Customer> listMonSys = restClient.invokeService("/devices/getMonSysCustomer", 
				arguments,
				requestProperties);
		assertNotNull(listMonSys);
		assertTrue(listMonSys.size() > 0);
	}

}
