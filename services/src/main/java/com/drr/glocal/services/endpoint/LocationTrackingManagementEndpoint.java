package com.drr.glocal.services.endpoint;

import com.drr.glocal.services.GcmMessageSender;
import com.drr.glocal.services.persistence.TrackLocation;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * Created by racastur on 31-10-2014.
 */
@Api(name = "services", version = "v1", namespace = @ApiNamespace(ownerDomain = "services.glocal.drr.com", ownerName = "services.glocal.drr.com", packagePath = ""))
public class LocationTrackingManagementEndpoint {

    private static final Logger log = Logger.getLogger(LocationTrackingManagementEndpoint.class.getName());

    @ApiMethod(name = "saveLocation", path = "users/{userId}/tracks/{trackId}/locations", httpMethod = ApiMethod.HttpMethod.POST)
    public void saveLocation(@Named("userId") Long userId,
                             @Named("trackId") Long trackId,
                             @Named("deviceId") Long deviceId,
                             @Named("timestamp") Long timestamp,
                             @Named("latitude") Double latitude,
                             @Named("longitude") Double longitude) {

        TrackLocation trackLocation = new TrackLocation();
        trackLocation.setTrackId(trackId);
        trackLocation.setDeviceId(deviceId);
        trackLocation.setTimestamp(timestamp);
        trackLocation.setLatitude(latitude);
        trackLocation.setLongitude(longitude);

        ofy().save().entity(trackLocation).now();

        // TODO send GCM notification only to interested parties

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

    @ApiMethod(name = "getTrackLocations", path = "users/{userId}/tracks/{trackId}/locations", httpMethod = ApiMethod.HttpMethod.GET)
    public List<TrackLocation> getTrackLocations(@Named("userId") Long userId,
                                                 @Named("trackId") Long trackId) {

        List<TrackLocation> records = ofy().load().type(TrackLocation.class).filter("trackId", trackId).list();
        if (records == null || records.isEmpty()) {
            return new ArrayList<TrackLocation>();
        }

        return records;

    }

}
