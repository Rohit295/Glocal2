package com.drr.glocal.services.persistence;

import com.drr.glocal.services.model.UserInfo;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by racastur on 31-10-2014.
 */
@Entity
public class User {

    @Id
    private Long id;

    @Index
    private String emailId;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public UserInfo getInfo() {

        UserInfo info = new UserInfo();
        info.setId(id);
        info.setEmailId(emailId);

        return info;

    }

}
