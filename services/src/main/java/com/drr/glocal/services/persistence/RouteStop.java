package com.drr.glocal.services.persistence;

import com.drr.glocal.model.RouteStopDTO;

import java.io.Serializable;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteStop implements Serializable {

    private String name;

    private String address; // This should be a class ideally

    private Location location;

    public RouteStop() {

    }

    public RouteStop(RouteStopDTO dto) {
        setName(dto.getName());
        setAddress(dto.getAddress());
        if (dto.getLocation() != null) {
            setLocation(new Location(dto.getLocation()));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RouteStopDTO getDTO() {

        RouteStopDTO dto = new RouteStopDTO();

        dto.setName(name);
        dto.setAddress(address);
        if (location != null) {
            dto.setLocation(location.getDTO());
        }

        return dto;

    }
}
