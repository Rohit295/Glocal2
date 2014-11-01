package com.drr.glocal.services;

import com.drr.glocal.services.persistence.Device;
import com.drr.glocal.services.persistence.Track;
import com.drr.glocal.services.persistence.TrackLocation;
import com.drr.glocal.services.persistence.User;
import com.drr.glocal.services.persistence.UserDevice;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Device.class);
        ObjectifyService.register(UserDevice.class);
        ObjectifyService.register(Track.class);
        ObjectifyService.register(TrackLocation.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
