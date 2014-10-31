package com.drr.glocal.services.model;

import java.util.List;

/**
 * Created by racastur on 01-11-2014.
 */
public class TrackInfo {

    private Long id;

    private String name;

    private UserInfo user;

    private List<TrackLocationInfo> trackLocations;

    public TrackInfo() {

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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<TrackLocationInfo> getTrackLocations() {
        return trackLocations;
    }

    public void setTrackLocations(List<TrackLocationInfo> trackLocations) {
        this.trackLocations = trackLocations;
    }
}
