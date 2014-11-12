package com.drr.glocal.services.persistence;

/**
 * Created by racastur on 12-11-2014.
 */
public class Observable {

    private Long id;
    private String name;
    private Long routeStopId;   // for school scenario, how can we model pick up from a stop but drop at a different stop??
    private String stopPurpose; // inherits defaultStopPurpose from Route, but can be overridden

}
