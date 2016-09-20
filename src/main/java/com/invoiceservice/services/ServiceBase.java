package com.invoiceservice.services;

import com.invoiceservice.services.monitor.SystemStatusMonitor;

public abstract class ServiceBase implements Service {
    protected SystemStatusMonitor systemStatusMonitor;
    
    public SystemStatusMonitor getSystemStatusMonitor() {
        return systemStatusMonitor;
    }

    public void setSystemStatusMonitor(SystemStatusMonitor systemStatusMonitor) {
        this.systemStatusMonitor = systemStatusMonitor;
    }
}
