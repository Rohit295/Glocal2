package com.drr.glocal.services.persistence;

import java.util.List;

/**
 * Created by racastur on 12-11-2014.
 */
public class Observer {

    private Long id;
    private String name;
    private String contactInfo;      // ideally an email id or sms # or some communication identifier
    private List<Long> observableId; // one parent may be interested in multiple children

}
