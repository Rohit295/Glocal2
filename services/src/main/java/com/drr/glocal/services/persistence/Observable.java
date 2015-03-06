package com.drr.glocal.services.persistence;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by racastur on 12-11-2014.
 */
@Entity
public class Observable {

    @Id
    private Long id;

    private String name;

    private Long routeId;

    private Long routeStopId;   // for school scenario, how can we model pick up from a stop but drop at a different stop??

    private String stopPurpose; // inherits defaultStopPurpose from Route, but can be overridden

    public Observable() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(Long routeStopId) {
        this.routeStopId = routeStopId;
    }

    public String getStopPurpose() {
        return stopPurpose;
    }

    public void setStopPurpose(String stopPurpose) {
        this.stopPurpose = stopPurpose;
    }

}