package com.drr.glocal.services.endpoint;

import com.drr.glocal.services.model.TrackInfo;
import com.drr.glocal.services.model.TrackLocationInfo;
import com.drr.glocal.services.model.UserInfo;
import com.drr.glocal.services.persistence.Track;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * Created by racastur on 31-10-2014.
 */
@Api(name = "services", version = "v1", namespace = @ApiNamespace(ownerDomain = "services.glocal.drr.com", ownerName = "services.glocal.drr.com", packagePath = ""))
public class TrackManagementEndpoint {

    private static final Logger log = Logger.getLogger(TrackManagementEndpoint.class.getName());

    @ApiMethod(path = "users/{userId}/tracks", httpMethod = ApiMethod.HttpMethod.POST)
    public TrackInfo createNewTrack(@Named("userId") Long userId, @Named("name") String name) {

        Track track = new Track();
        track.setName(name);
        track.setUserId(userId);

        ofy().save().entity(track).now();

        TrackInfo info = new TrackInfo();
        info.setId(track.getId());
        info.setName(track.getName());

        UserInfo user = new UserInfo();
        user.setId(userId);

        info.setUser(user);

        info.setTrackLocations(new ArrayList<TrackLocationInfo>());

        return info;

    }

}
