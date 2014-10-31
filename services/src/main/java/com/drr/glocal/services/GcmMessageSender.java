package com.drr.glocal.services;

import com.drr.glocal.services.persistence.Device;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * An endpoint to send messages to devices registered with the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
public class GcmMessageSender {

    private static final Logger log = Logger.getLogger(GcmMessageSender.class.getName());

    private static final String API_KEY = System.getProperty("gcm.api.key");

    public static void sendMessage(Map<String, String> data) throws IOException {

        if (data == null || data.size() <= 0) {
            log.warning("Not sending message because it is empty");
            return;
        }

        Sender sender = new Sender(API_KEY);
        Message.Builder builder = new Message.Builder();
        for (String key : data.keySet()) {
            builder.addData(key, data.get(key));
        }
        Message msg = builder.build();

        List<Device> records = ofy().load().type(Device.class).list();
        for (Device record : records) {

            Result result = sender.send(msg, record.getGcmRegistrationId(), 5);

            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getGcmRegistrationId());
//                String canonicalRegId = result.getCanonicalRegistrationId();
//                if (canonicalRegId != null) {
//                    // if the regId changed, we have to update the datastore
//                    log.info("Registration Id changed for " + record.getGcmRegistrationId()t + " updating to " + canonicalRegId);
//                    record.setRegId(canonicalRegId);
//                    ofy().save().entity(record).now();
//                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getGcmRegistrationId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                } else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }

    }

}
