package com.invoiceservice.services;

import com.invoiceservice.services.monitor.SystemStatusMonitor;

public interface Service {
    public SystemStatusMonitor getSystemStatusMonitor();
}
