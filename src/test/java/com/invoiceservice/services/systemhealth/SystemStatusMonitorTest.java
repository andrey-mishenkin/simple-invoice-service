package com.invoiceservice.services.systemhealth;

import org.junit.Before;
import org.junit.Test;

import com.invoiceservice.services.monitor.SystemStatus;
import com.invoiceservice.services.monitor.SystemStatusMonitor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SystemStatusMonitorTest {

    private SystemStatusMonitor systemStatusMonitor;

    @Before
    public void setUp() throws Exception {
        systemStatusMonitor = new SystemStatusMonitor("test component");
    }

    @Test
    public void testGetStatusOk() throws Exception {
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.OK));


        systemStatusMonitor.setStatusOk("test ok message");
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMonitor.getMessage(), is("test ok message"));


        systemStatusMonitor.setStatusOk();
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.OK));
        assertThat(systemStatusMonitor.getMessage(), isEmptyString());


    }

    @Test
    public void testGetStatusWarning() throws Exception {
        systemStatusMonitor.setStatusWarning("test warning message", new IllegalArgumentException("Unknown warning"));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMonitor.getMessage(), is("test warning message"));
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown warning"));


        systemStatusMonitor.setStatusWarning("test warning message", new IllegalArgumentException());
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMonitor.getMessage(), is("test warning message"));
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), nullValue());


        systemStatusMonitor.setStatusWarning("test warning message");
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMonitor.getMessage(), is("test warning message"));
        assertThat(systemStatusMonitor.getException(), nullValue());


        systemStatusMonitor.setStatusWarning(new IllegalArgumentException("Unknown warning"));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMonitor.getMessage(), nullValue());
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown warning"));


        systemStatusMonitor.setStatusWarning(new IllegalArgumentException("Unknown warning", new NullPointerException()));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.WARNING));
        assertThat(systemStatusMonitor.getMessage(), nullValue());
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown warning"));
        assertThat(systemStatusMonitor.getException().getCause(), instanceOf(NullPointerException.class));
        assertThat(systemStatusMonitor.getException().getCause().getMessage(), nullValue());
    }


    @Test
    public void testGetStatusError() throws Exception {
        systemStatusMonitor.setStatusError("test error message", new IllegalArgumentException("Unknown error"));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMonitor.getMessage(), is("test error message"));
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown error"));


        systemStatusMonitor.setStatusError("test error message", new IllegalArgumentException());
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMonitor.getMessage(), is("test error message"));
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), nullValue());


        systemStatusMonitor.setStatusError("test error message");
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMonitor.getMessage(), is("test error message"));
        assertThat(systemStatusMonitor.getException(), nullValue());


        systemStatusMonitor.setStatusError(new IllegalArgumentException("Unknown error"));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMonitor.getMessage(), nullValue());
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown error"));


        systemStatusMonitor.setStatusError(new IllegalArgumentException("Unknown error", new NullPointerException()));
        assertThat(systemStatusMonitor.getComponent(), is("test component"));
        assertThat(systemStatusMonitor.getSystemStatus(), is(SystemStatus.ERROR));
        assertThat(systemStatusMonitor.getMessage(), nullValue());
        assertThat(systemStatusMonitor.getException(), instanceOf(IllegalArgumentException.class));
        assertThat(systemStatusMonitor.getException().getMessage(), is("Unknown error"));
        assertThat(systemStatusMonitor.getException().getCause(), instanceOf(NullPointerException.class));
        assertThat(systemStatusMonitor.getException().getCause().getMessage(), nullValue());
    }
}
