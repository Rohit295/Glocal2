package com.drr.glocal.services.persistence;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;

/**
 * Created by rohitman on 3/6/2015.
 */
public class TrackListener {

    @Id
    private Long id;

    @Index
    private Long trackId;

    private ArrayList<String> channelsToUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public ArrayList<String> getChannelsToUpdate() {
        return channelsToUpdate;
    }

    public void setChannelsToUpdate(ArrayList<String> channelsToUpdate) {
        this.channelsToUpdate = channelsToUpdate;
    }

    public void addChannelToUpdate(String channelToAdd) {
        channelsToUpdate.add(channelToAdd);
    }
    public void removeChannelToUpdate(String channelToRemove) {
        channelsToUpdate.remove(channelToRemove);
    }
}
