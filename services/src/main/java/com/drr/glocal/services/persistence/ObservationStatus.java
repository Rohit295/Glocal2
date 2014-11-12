package com.drr.glocal.services.persistence;

/**
 * Created by racastur on 12-11-2014.
 */
public class ObservationStatus {

    private Long id;
    private Long observableId;
    private Long routeExecutionStopId;
    private String status;    // could be a boolean, but ideally an enum??
    private String notes;     // free form field for the executor to put down notes/comments

}
