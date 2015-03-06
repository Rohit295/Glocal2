package com.drr.glocal.services.endpoint;

import com.drr.glocal.model.TrackInfo;
import com.drr.glocal.services.persistence.Track;
import com.drr.glocal.services.persistence.TrackListener;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * Created by racastur on 31-10-2014.
 */
@RestController
@RequestMapping("/services/v1/*")
public class TrackManagementEndpoint {

    private static final Logger log = Logger.getLogger(TrackManagementEndpoint.class.getName());

    @RequestMapping(value = "users/{userId}/tracks", method = RequestMethod.POST)
    public TrackInfo createNewTrack(@PathVariable("userId") Long userId, @RequestParam("name") String name) {

        Track track = new Track();
        track.setName(name);
        track.setUserId(userId);

        ofy().save().entity(track).now();

        return track.getInfo();

    }

    @RequestMapping(value = "users/{userId}/tracks", method = RequestMethod.GET)
    public List<TrackInfo> getTracks(@PathVariable("userId") Long userId) {

        List<Track> records = ofy().load().type(Track.class).filter("userId", userId).list();
        if (records == null || records.isEmpty()) {
            return new ArrayList<TrackInfo>();
        }

        List<TrackInfo> infos = new ArrayList<TrackInfo>();
        for (Track record : records) {
            infos.add(record.getInfo());
        }

        return infos;

    }

    /**
     * Every time a Web Client comes on and becomes interested in an instance of a Track, it will register
     * itself to listen for updates on the Route
     * @param trackId
     * @param channelID
     */
    @RequestMapping(value = "users/tracks/{trackId}/{channelID}", method = RequestMethod.POST)
    public void setTrackListener(@PathVariable("trackId") Long trackId, @PathVariable("channelID") String channelID) {
    	log.log(Log.VERBOSE, getClass().getName() + ": " + "Add new Listener to track - " + trackId + 
    			"; for channel - " + channelID);
    	
    	TrackListener listener = ofy().load().type(TrackListener.class).filter("trackId", trackId).first().now();
    	if (listener != null) {
    		listener.addChannelToUpdate(channelID);
    	}
    	
    }

}
