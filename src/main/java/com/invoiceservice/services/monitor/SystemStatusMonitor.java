package com.invoiceservice.services.monitor;

import com.invoiceservice.services.Service;

/**
 * System status monitor for service implemented {@link Service} 
 */
public class SystemStatusMonitor {
    private final String component;

    private SystemStatus systemStatus;
    private String message;
    private Throwable exception;

    public SystemStatusMonitor(final String component) {
        this.component = component;
        this.systemStatus = SystemStatus.OK;
    }

    public SystemStatusMonitor(final Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    public void setStatusOk(String message) {
        setState(SystemStatus.OK, message);
    }

    public void setStatusOk() {
        setState(SystemStatus.OK, "");
    }

    public void setStatusWarning(String message) {
        setState(SystemStatus.WARNING, message);
    }

    public void setStatusWarning(Throwable e) {
        setState(SystemStatus.WARNING, e);
    }

    public void setStatusWarning(String message, Throwable e) {
        setState(SystemStatus.WARNING, message, e);
    }

    public void setStatusError(String message) {
        setState(SystemStatus.ERROR, message);
    }

    public void setStatusError(Throwable e) {
        setState(SystemStatus.ERROR, e);
    }

    public void setStatusError(String message, Throwable e) {
        setState(SystemStatus.ERROR, message, e);
    }

    synchronized private void setState(SystemStatus systemStatus, String message, Throwable exception) {
        this.systemStatus = systemStatus;
        this.message = message;
        this.exception = exception;
    }

    private void setState(SystemStatus systemStatus, String message) {
        setState(systemStatus, message, null);
    }

    private void setState(SystemStatus systemStatus, Throwable exception) {
        setState(systemStatus, null, exception);
    }

    public String getComponent() {
        return component;
    }

    public SystemStatus getSystemStatus() {
        return systemStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "SystemStatusMonitor{" +
                "component='" + component + '\'' +
                ", systemStatus=" + systemStatus +
                ", message='" + message + '\'' +
                ", exception=" + exception +
                '}';
    }
}
