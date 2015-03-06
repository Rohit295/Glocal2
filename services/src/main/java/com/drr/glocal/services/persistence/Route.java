package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteDTO;
import com.drr.glocal.model.RouteLocationDTO;
import com.drr.glocal.model.RouteStopDTO;
import com.google.appengine.datanucleus.annotations.Unowned;

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
public class Route {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    @Unowned
    private Organization organization;

    @Persistent
    private String name;

    @Persistent
    private String defaultStopPurpose; // should be enum { Delivery, Pickup, Visit }

    @Persistent
    private String executionStartTime; // a one time route or a scheduled route

    @Persistent(serialized = "true")
    private List<RouteStop> routeStops;

    @Persistent(serialized = "true")
    private List<RouteLocation> routeLocations;

    public Route() {

    }

    public Route(RouteDTO dto) {
        setName(dto.getName());
        setDefaultStopPurpose(dto.getDefaultStopPurpose());
        setExecutionStartTime(dto.getExecutionStartTime());
        if (dto.getRouteStops() != null && !dto.getRouteStops().isEmpty()) {
            List<RouteStop> routeStops = new ArrayList<RouteStop>();
            for (RouteStopDTO routeStopDTO : dto.getRouteStops()) {
                routeStops.add(new RouteStop(routeStopDTO));
            }
            setRouteStops(routeStops);
        }
        if (dto.getRouteLocations() != null && !dto.getRouteLocations().isEmpty()) {
            List<RouteLocation> routeLocations = new ArrayList<RouteLocation>();
            for (RouteLocationDTO routeLocationDTO : dto.getRouteLocations()) {
                routeLocations.add(new RouteLocation(routeLocationDTO));
            }
            setRouteLocations(routeLocations);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultStopPurpose() {
        return defaultStopPurpose;
    }

    public void setDefaultStopPurpose(String defaultStopPurpose) {
        this.defaultStopPurpose = defaultStopPurpose;
    }

    public String getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public List<RouteStop> getRouteStops() {
        return routeStops;
    }

    public void setRouteStops(List<RouteStop> routeStops) {
        this.routeStops = routeStops;
    }

    public List<RouteLocation> getRouteLocations() {
        return routeLocations;
    }

    public void setRouteLocations(List<RouteLocation> routeLocations) {
        this.routeLocations = routeLocations;
    }

    public RouteDTO getDTO() {

        RouteDTO dto = new RouteDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setDefaultStopPurpose(defaultStopPurpose);
        dto.setExecutionStartTime(executionStartTime);

        List<RouteStopDTO> routeStopDTOs = new ArrayList<RouteStopDTO>();
        if (routeStops != null) {
            for (RouteStop routeStop : routeStops) {
                routeStopDTOs.add(routeStop.getDTO());
            }
        }
        dto.setRouteStops(routeStopDTOs);

        List<RouteLocationDTO> routeLocationDTOs = new ArrayList<RouteLocationDTO>();
        if (routeLocations != null) {
            for (RouteLocation routeLocation : routeLocations) {
                routeLocationDTOs.add(routeLocation.getDTO());
            }
        }
        dto.setRouteLocations(routeLocationDTOs);

        return dto;

    }
}
