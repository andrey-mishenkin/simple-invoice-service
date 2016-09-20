package com.invoiceservice.services.monitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
/**
 * System Health Service. 
 * Collects (by injecting) all SystemStatusMonitor instances and provide appropriate info for app services status
 */
@Service
public class SystemHealthService {

    private final Map<String, SystemStatusMonitor> systemStatusMonitorMap = new ConcurrentHashMap<String, SystemStatusMonitor>();

    @Autowired
    private ApplicationContext appContext;
    
    @PostConstruct 
    /**
     * For some reason for Spring4 @Autowired for setSystemStatusMonitors(..) does't work, only one bean initiated. For 
     * Use this hack 
     */
    public void init() {
        Map<String, SystemStatusMonitor> monitors = appContext.getBeansOfType(SystemStatusMonitor.class);
        Set<SystemStatusMonitor> monitorsSet = new HashSet<SystemStatusMonitor>();
        monitorsSet.addAll(monitors.values());
        setSystemStatusMonitors(monitorsSet);
    }
    
    public void setSystemStatusMonitors(Set<SystemStatusMonitor> systemStatusMonitors) {
        for (SystemStatusMonitor systemStatusMonitor : systemStatusMonitors) {
            this.systemStatusMonitorMap.put(systemStatusMonitor.getComponent(), systemStatusMonitor);
        }
    }

    public Map<String, SystemStatusMonitor> getSystemStatusMap() {
        return Collections.unmodifiableSortedMap(new TreeMap<String, SystemStatusMonitor>(systemStatusMonitorMap));
    }

    public SystemStatus getSystemStatus() {
        SystemStatus result = SystemStatus.OK;
        for (SystemStatusMonitor value : systemStatusMonitorMap.values()) {
            final SystemStatus systemStatus = value.getSystemStatus();
            if (result.ordinal() < systemStatus.ordinal()) {
                result = systemStatus;
            }
        }
        return result;
    }

    public boolean hasErrors() {
        for (Map.Entry<String, SystemStatusMonitor> stringStateEntry : systemStatusMonitorMap.entrySet()) {
            SystemStatusMonitor systemStatusMonitor = stringStateEntry.getValue();
            if (SystemStatus.ERROR.equals(systemStatusMonitor.getSystemStatus())) {
                return true;
            }
        }
        return false;
    }

    public void resetAll() {
        for (Map.Entry<String, SystemStatusMonitor> stringSystemStatusMonitorEntry : systemStatusMonitorMap.entrySet()) {
            final SystemStatusMonitor systemStatusMonitor = stringSystemStatusMonitorEntry.getValue();
            // Reset only not OK
            if (!SystemStatus.OK.equals(systemStatusMonitor.getSystemStatus())) {
                systemStatusMonitor.setStatusOk();
            }
        }
    }
}