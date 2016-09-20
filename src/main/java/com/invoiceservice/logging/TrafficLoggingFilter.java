package com.invoiceservice.logging;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

import com.invoiceservice.model.config.AppConfiguration;

@Component
@WebFilter
public class TrafficLoggingFilter extends ServletContextRequestLoggingFilter {

    private static Logger TRAFFIC_LOGGER = LoggerFactory.getLogger("trafficLogger");

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // TODO: Extent filter to log full request response
        // Better to implement Filter interface?
        TRAFFIC_LOGGER.info(message);
    }
}
