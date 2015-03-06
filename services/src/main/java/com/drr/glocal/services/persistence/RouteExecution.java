package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteExecutionDTO;
import com.drr.glocal.model.RouteExecutionLocationDTO;
import com.drr.glocal.model.RouteExecutionStopDTO;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 12-11-2014.
 */
@PersistenceCapable
public class RouteExecution {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long routeId;

    @Persistent(serialized = "true")
    private List<RouteExecutionStop> routeExecutionStops;

    @Persistent(serialized = "true")
    private List<RouteExecutionLocation> routeExecutionLocations;

    @Persistent
    private Long startTime;  // utc time

    @Persistent
    private Long endTime;    // utc time

    @Persistent
    private Long routeExecutorId;

    public RouteExecution() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public List<RouteExecutionStop> getRouteExecutionStops() {
        return routeExecutionStops;
    }

    public void setRouteExecutionStops(List<RouteExecutionStop> routeExecutionStops) {
        this.routeExecutionStops = routeExecutionStops;
    }

    public List<RouteExecutionLocation> getRouteExecutionLocations() {
        return routeExecutionLocations;
    }

    public void setRouteExecutionLocations(List<RouteExecutionLocation> routeExecutionLocations) {
        this.routeExecutionLocations = routeExecutionLocations;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getRouteExecutorId() {
        return routeExecutorId;
    }

    public void setRouteExecutorId(Long routeExecutorId) {
        this.routeExecutorId = routeExecutorId;
    }

    public RouteExecutionDTO getDTO() {

        RouteExecutionDTO dto = new RouteExecutionDTO();
        dto.setId(id);
        dto.setRouteId(routeId);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);

        List<RouteExecutionStopDTO> stopDTOs = new ArrayList<RouteExecutionStopDTO>();
        if (routeExecutionStops != null) {
            for (RouteExecutionStop stop : routeExecutionStops) {
                stopDTOs.add(stop.getDTO());
            }
        }
        dto.setRouteExecutionStops(stopDTOs);

        List<RouteExecutionLocationDTO> locationDTOs = new ArrayList<RouteExecutionLocationDTO>();
        if (routeExecutionLocations != null) {
            for (RouteExecutionLocation location : routeExecutionLocations) {
                locationDTOs.add(location.getDTO());
            }
        }
        dto.setRouteExecutionLocations(locationDTOs);

        return dto;

    }

}
