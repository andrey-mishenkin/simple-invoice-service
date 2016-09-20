package com.invoiceservice.services.systemhealth;

import org.junit.Before;
import org.junit.Test;

import com.invoiceservice.services.monitor.SystemHealthService;
import com.invoiceservice.services.monitor.SystemStatus;
import com.invoiceservice.services.monitor.SystemStatusMonitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SystemHealthServiceTest {

    private SystemStatusMonitor component00;
    private SystemStatusMonitor component01;
    private SystemStatusMonitor component02;

    private SystemHealthService systemHealthService;

    @Before
    public void setUp() throws Exception {
        systemHealthService = new SystemHealthService();

        component02 = new SystemStatusMonitor("test component02");
        component01 = new SystemStatusMonitor("test component01");
        component00 = new SystemStatusMonitor("test component00");


        Set<SystemStatusMonitor> systemStatusMonitors = new HashSet<SystemStatusMonitor>();

        systemStatusMonitors.add(component00);
        systemStatusMonitors.add(component01);
        systemStatusMonitors.add(component02);

        systemHealthService.setSystemStatusMonitors(systemStatusMonitors);
    }

    @Test
    public void testGetSystemStatus() throws Exception {
        SystemStatus status;

        systemHealthService.resetAll();
        status = systemHealthService.getSystemStatus();
        assertThat(status, is(SystemStatus.OK));


        component00.setStatusOk();
        component01.setStatusOk();
        component02.setStatusOk();
        status = systemHealthService.getSystemStatus();
        assertThat(status, is(SystemStatus.OK));

        component01.setStatusWarning("Warning");
        status = systemHealthService.getSystemStatus();
        assertThat(status, is(SystemStatus.WARNING));

        component02.setStatusError("Error", new IllegalArgumentException("Unknown error"));
        status = systemHealthService.getSystemStatus();
        assertThat(status, is(SystemStatus.ERROR));

        component02.setStatusOk();
        status = systemHealthService.getSystemStatus();
        assertThat(status, is(SystemStatus.WARNING));
    }

    @Test
    public void testGetSystemStatusEmpty() throws Exception {
        SystemStatus status;
        SystemHealthService systemStatusService = new SystemHealthService();
        status = systemStatusService.getSystemStatus();
        assertThat(status, is(SystemStatus.OK));
    }

    @Test
    public void testHasError() throws Exception {

        component00.setStatusOk();
        assertThat(systemHealthService.hasErrors(), is(false));


        component01.setStatusWarning("Warning");
        assertThat(systemHealthService.hasErrors(), is(false));

        component02.setStatusError("Error", new IllegalArgumentException("Unknown error"));
        assertThat(systemHealthService.hasErrors(), is(true));

        component02.setStatusOk();
        assertThat(systemHealthService.hasErrors(), is(false));
    }


    @Test
    public void testResetAll() throws Exception {
        Map<String, SystemStatusMonitor> systemStatusMap = systemHealthService.getSystemStatusMap();

        assertThat(systemStatusMap.get("test component00").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component01").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component02").getSystemStatus(), is(SystemStatus.OK));

        component01.setStatusWarning("Warning");
        component00.setStatusOk();

        assertThat(systemStatusMap.get("test component00").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component01").getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMap.get("test component01").getMessage(), equalTo("Warning"));
        assertThat(systemStatusMap.get("test component02").getSystemStatus(), is(SystemStatus.OK));

        systemHealthService.resetAll();
        assertThat(systemStatusMap.get("test component00").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component01").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component02").getSystemStatus(), is(SystemStatus.OK));

        component01.setStatusError("Error", new IllegalArgumentException("Unknown error"));
        assertThat(systemStatusMap.get("test component00").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component01").getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMap.get("test component01").getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMap.get("test component01").getMessage(), is("Error"));
        assertThat(systemStatusMap.get("test component02").getSystemStatus(), is(SystemStatus.OK));

        systemHealthService.resetAll();
        assertThat(systemStatusMap.get("test component00").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component01").getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMap.get("test component02").getSystemStatus(), is(SystemStatus.OK));
    }
}
