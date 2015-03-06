package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteExecutionStopDTO;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionStop {

    private Long routeStopId;

    private Long visitedTime;  // utc time

    public RouteExecutionStop() {

    }

    public RouteExecutionStop(Long routeExecutionId, RouteExecutionStopDTO dto) {
        setRouteStopId(dto.getRouteStopId());
        setVisitedTime(dto.getVisitedTime());
    }

    public Long getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(Long routeStopId) {
        this.routeStopId = routeStopId;
    }

    public Long getVisitedTime() {
        return visitedTime;
    }

    public void setVisitedTime(Long visitedTime) {
        this.visitedTime = visitedTime;
    }

    public RouteExecutionStopDTO getDTO() {

        RouteExecutionStopDTO dto = new RouteExecutionStopDTO();
        dto.setRouteStopId(routeStopId);
        dto.setVisitedTime(visitedTime);

        return dto;

    }

}
