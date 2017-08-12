package org.openstack4j.api.compute;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openstack4j.api.AbstractTest;
import org.openstack4j.model.compute.ext.Service.Status;
import org.openstack4j.model.compute.ext.Service.State;

import org.testng.annotations.Test;

/**
 * Test cases for Nova Services function
 * 
 * @author Yin Zhang
 */
@Test(suiteName = "Services")
public class ServiceTests extends AbstractTest {

    private static final String JSON_SERVICES = "/compute/services.json";
    private static final String JSON_ENABLE_SERVICE = "/compute/enable_services.json";
    private static final String JSON_DISABLE_SERVICE = "/compute/disable_services.json";

    public void serviceListingTest() throws Exception {
        respondWith(JSON_SERVICES);

        List<? extends org.openstack4j.model.compute.ext.Service> services = osv3().compute().services().list();
        assertEquals(4, services.size());

        org.openstack4j.model.compute.ext.Service s = services.get(0);
        assertEquals("nova-scheduler", s.getBinary());
        assertEquals("host1", s.getHost());
        assertEquals(Status.DISABLED, s.getStatus());
        assertEquals(State.UP, s.getState());
        assertEquals("internal", s.getZone());
        assertEquals("test1", s.getDisabledReason());
    }
    
    public void enableServiceTest() throws Exception {
        respondWith(JSON_ENABLE_SERVICE);

        org.openstack4j.model.compute.ext.Service s = osv3().compute().services().enableService("nova-compute", "some_host");
        assertEquals("nova-compute", s.getBinary());
        assertEquals("some_host", s.getHost());
        assertEquals(Status.ENABLED, s.getStatus());
    }
    
    public void disableServiceTest() throws Exception {
        respondWith(JSON_DISABLE_SERVICE);

        org.openstack4j.model.compute.ext.Service s = osv3().compute().services().enableService("nova-compute", "some_host");
        assertEquals("nova-compute", s.getBinary());
        assertEquals("some_host", s.getHost());
        assertEquals(Status.DISABLED, s.getStatus());
    }

    @Override
    protected Service service() {
        return Service.COMPUTE;
    }

}