package com.invoiceservice.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoiceservice.model.config.CategotryRateConfig;
import com.invoiceservice.services.monitor.SystemHealthService;
import com.invoiceservice.services.monitor.SystemStatus;
import com.invoiceservice.services.monitor.SystemStatusMonitor;

/**
 * Invoice resource - provides main tax calc functionality
 */
@Component
@Path("health")
public class SystemHealthResource {

    @Autowired
    SystemHealthService systemHealthService;
    
    /**
     * Get system health 
     * @return one of - OK, WARNING, ERROR  
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSystemHealth() {
        systemHealthService.getSystemStatus();
        return systemHealthService.getSystemStatus().toString();
    }

    /**
     * Get system health with details by component  
     * @return   
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSystemHealthDetail() {
        Map<String, SystemStatusMonitor> systemStatusMap = systemHealthService.getSystemStatusMap();
        Iterator<String> it = systemStatusMap.keySet().iterator();
        StringBuilder result = new StringBuilder();
        while(it.hasNext()) {
            String key = it.next();
            SystemStatusMonitor monitor = systemStatusMap.get(key);
            result.append(monitor.getComponent() + ": " + monitor.getSystemStatus().toString());
            if (monitor.getMessage() != null) {
                result.append(": " + monitor.getMessage());
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Get system health with details by component  
     * @return   
     */
    @GET
    @Path("/detail/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SystemStatusMonitor> getSystemHealthDetailJson() {
        List<SystemStatusMonitor> result = new ArrayList<SystemStatusMonitor>();
        result.addAll(systemHealthService.getSystemStatusMap().values());
        return result;
    }

}
