package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteLocationDTO;
import com.drr.glocal.model.RouteStopDTO;

import java.io.Serializable;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteLocation implements Serializable {

    private Location location;

    public RouteLocation() {

    }

    public RouteLocation(RouteLocationDTO dto) {
        if (dto.getLocation() != null) {
            setLocation(new Location(dto.getLocation()));
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RouteLocationDTO getDTO() {

        RouteLocationDTO dto = new RouteLocationDTO();

        if (location != null) {
            dto.setLocation(location.getDTO());
        }

        return dto;

    }
}
