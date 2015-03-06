package com.drr.glocal.services.endpoint;

import com.drr.glocal.services.GcmMessageSender;
import com.drr.glocal.model.TrackLocationInfo;
import com.drr.glocal.services.persistence.TrackLocation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.drr.glocal.services.OfyService.ofy;

@RestController
@RequestMapping("/services/v1/*")
public class LocationTrackingManagementEndpoint {

    private static final Logger log = Logger.getLogger(LocationTrackingManagementEndpoint.class.getName());

    @RequestMapping(value = "users/{userId}/tracks/{trackId}/locations", method = RequestMethod.POST)
    public void saveLocation(@PathVariable("userId") Long userId,
                             @PathVariable("trackId") Long trackId,
                             @RequestParam("deviceId") Long deviceId,
                             @RequestParam("timestamp") Long timestamp,
                             @RequestParam("latitude") Double latitude,
                             @RequestParam("longitude") Double longitude) {

        TrackLocation trackLocation = new TrackLocation();
        trackLocation.setTrackId(trackId);
        trackLocation.setDeviceId(deviceId);
        trackLocation.setTimestamp(timestamp);
        trackLocation.setLatitude(latitude);
        trackLocation.setLongitude(longitude);

        ofy().save().entity(trackLocation).now();

        /**
         * AT THIS POINT, UPDATE ALL INTERESTED PARTIES
         * Do this in two ways 
         * 	(1) all GCM enabled clients will get it through that
         * 	(2) all web clients however will get it through a Channel update
         */

        // TODO 1. Send GCM notification only to interested parties
        Map<String, String> data = new HashMap<String, String>();
        data.put("userId", Long.toString(userId));
        data.put("trackId", Long.toString(trackId));
        data.put("timestamp", Long.toString(timestamp));
        data.put("latitude", Double.toString(latitude));
        data.put("longitude", Double.toString(longitude));

        try {
            GcmMessageSender.sendMessage(data);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }

    }

    @RequestMapping(value = "users/{userId}/tracks/{trackId}/locations", method = RequestMethod.GET)
    public List<TrackLocationInfo> getTrackLocations(@PathVariable("userId") Long userId, @PathVariable("trackId") Long trackId) {

        List<TrackLocation> records = ofy().load().type(TrackLocation.class).filter("trackId", trackId).list();
        if (records == null || records.isEmpty()) {
            return new ArrayList<TrackLocationInfo>();
        }

        List<TrackLocationInfo> infos = new ArrayList<TrackLocationInfo>();
        for (TrackLocation location : records) {
            infos.add(location.getInfo());
        }

        return infos;

    }

}
