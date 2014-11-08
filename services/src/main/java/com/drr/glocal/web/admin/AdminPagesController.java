package com.drr.glocal.web.admin;

import com.drr.glocal.services.persistence.Track;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.drr.glocal.services.OfyService.ofy;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminPagesController {

    @RequestMapping("/admin/tracks")
    public ModelAndView getAllTracks() {

        List<Track> records = ofy().load().type(Track.class).list();
        if (records == null || records.isEmpty()) {
            records = new ArrayList<Track>();
        }

        Collections.sort(records, new Comparator<Track>() {
            @Override
            public int compare(Track o1, Track o2) {
                if (o1.getUserId() < o2.getUserId()) {
                    return -1;
                } else if (o1.getUserId() > o2.getUserId()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return new ModelAndView("admin/alltracks", "tracks", records);

    }

}
