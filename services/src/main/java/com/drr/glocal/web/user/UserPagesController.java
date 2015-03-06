package com.drr.glocal.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Master Controller for all User Pages in WIX
 * @author rohitman
 *
 */
@Controller
public class UserPagesController {

    @RequestMapping("/user/console")
    public ModelAndView getUserConsole() {
    	
    	// TODO Create a proper channel for this user. Add that to the track(s) that the user is following
    	
    	// get the current user ID, to create a Channel 
		UserService userService = UserServiceFactory.getUserService();
		String userID = "rohit295@gmail.com";
		//String userID = userService.getCurrentUser().getUserId();
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String channelID = channelService.createChannel(userID);
    			
        return new ModelAndView("user/console", "channelID", channelID);
    }

}
