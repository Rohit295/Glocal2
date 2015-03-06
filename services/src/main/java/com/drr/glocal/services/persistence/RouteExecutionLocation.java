package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteExecutionLocationDTO;

import java.util.Calendar;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 12-11-2014.
 */
@PersistenceCapable
public class RouteExecutionLocation {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long routeExecutionId;

    @Persistent(serialized = "true")
    private Location location;

    @Persistent
    private Long timestamp;    // utc time

    @Persistent
    private Long deviceId;

    public RouteExecutionLocation() {

    }

    public RouteExecutionLocation(Long routeExecutionId, RouteExecutionLocationDTO dto) {
        setRouteExecutionId(routeExecutionId);
        setLocation(new Location(dto.getLocation()));
        setTimestamp(Calendar.getInstance().getTimeInMillis());
        setDeviceId(dto.getDeviceId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteExecutionId() {
        return routeExecutionId;
    }

    public void setRouteExecutionId(Long routeExecutionId) {
        this.routeExecutionId = routeExecutionId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public RouteExecutionLocationDTO getDTO() {

        RouteExecutionLocationDTO dto = new RouteExecutionLocationDTO();
        dto.setId(id);
        dto.setDeviceId(deviceId);
        dto.setLocation(location.getDTO());
        dto.setTimestamp(timestamp);

        return dto;

    }

}
