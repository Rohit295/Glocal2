package com.drr.glocal.services.persistence;

/**
 * Created by racastur on 12-11-2014.
 */
public class Route {

    private Long id;
    private Long organizationId;
    private String name;
    private String defaultStopPurpose;   // should be enum { Delivery, Pickup, Visit }
    private String executionStartTime;   // a one time route or a scheduled route

}
