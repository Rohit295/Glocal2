package com.drr.glocal.services.endpoint;

import com.drr.glocal.services.persistence.Device;
import com.drr.glocal.services.persistence.User;
import com.drr.glocal.services.persistence.UserDevice;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "services", version = "v1", namespace = @ApiNamespace(ownerDomain = "services.glocal.drr.com", ownerName = "services.glocal.drr.com", packagePath = ""))
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    @ApiMethod(name = "register", path = "register", httpMethod = ApiMethod.HttpMethod.POST)
    public void register(@Named("userId") Long userId, @Named("deviceId") Long deviceId, @Named("gcmRegistrationId") String gcmRegistrationId) {

        if (findDeviceByGcmRegistrationId(gcmRegistrationId) == null) {

            Device record = new Device();
            record.setId(deviceId);
            record.setGcmRegistrationId(gcmRegistrationId);

            ofy().save().entity(record).now();

        }

        // TODO this is not clear!!!
        User user = findUserByUserId(userId);
        if (user == null) {
            user = new User();
            ofy().save().entities(user).now();
        }

        UserDevice userDevice = findUserDeviceByUserId(userId);
        if (userDevice == null) {
            userDevice = new UserDevice();
            userDevice.setUserId(userId);
        }
        userDevice.setDeviceId(deviceId);

        ofy().save().entities(userDevice).now();

    }

    private User findUserByUserId(Long userId) {
        return ofy().load().type(User.class).filter("userId", userId).first().now();
    }

    private UserDevice findUserDeviceByUserId(Long userId) {
        return ofy().load().type(UserDevice.class).filter("userId", userId).first().now();
    }

    private Device findDeviceByGcmRegistrationId(String gcmRegistrationId) {
        return ofy().load().type(Device.class).filter("gcmRegistrationId", gcmRegistrationId).first().now();
    }

}