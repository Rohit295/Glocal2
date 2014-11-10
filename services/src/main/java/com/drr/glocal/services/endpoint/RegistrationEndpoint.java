package com.drr.glocal.services.endpoint;

import com.drr.glocal.model.UserInfo;
import com.drr.glocal.services.persistence.Device;
import com.drr.glocal.services.persistence.User;
import com.drr.glocal.services.persistence.UserDevice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static com.drr.glocal.services.OfyService.ofy;

@RestController
@RequestMapping("/services/v1/*")
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserInfo login(@RequestParam("emailId") String emailId) {

        User user = findUserByEmailId(emailId);
        if (user == null) {
            user = new User();
            user.setEmailId(emailId);
            ofy().save().entity(user).now();
        }

        return user.getInfo();

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam("userId") Long userId,
                         @RequestParam("deviceId") Long deviceId,
                         @RequestParam("gcmRegistrationId") String gcmRegistrationId) {

        if (findDeviceByGcmRegistrationId(gcmRegistrationId) == null) {

            Device record = new Device();
            record.setId(deviceId);
            record.setGcmRegistrationId(gcmRegistrationId);

            ofy().save().entity(record).now();

        }

        // TODO this is not clear!!!
        User user = findUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User with [" + userId + "] does not exist");
        }

        UserDevice userDevice = findUserDeviceByUserId(userId);
        if (userDevice == null) {
            userDevice = new UserDevice();
            userDevice.setUserId(userId);
        }
        userDevice.setDeviceId(deviceId);

        ofy().save().entity(userDevice).now();

    }

    private User findUserByUserId(Long userId) {
        return ofy().load().type(User.class).filter("userId", userId).first().now();
    }

    private User findUserByEmailId(String emailId) {
        return ofy().load().type(User.class).filter("emailId", emailId).first().now();
    }

    private UserDevice findUserDeviceByUserId(Long userId) {
        return ofy().load().type(UserDevice.class).filter("userId", userId).first().now();
    }

    private Device findDeviceByGcmRegistrationId(String gcmRegistrationId) {
        return ofy().load().type(Device.class).filter("gcmRegistrationId", gcmRegistrationId).first().now();
    }

}