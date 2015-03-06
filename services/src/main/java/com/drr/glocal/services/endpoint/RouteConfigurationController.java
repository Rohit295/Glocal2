package com.drr.glocal.services.endpoint;

import com.drr.glocal.model.RouteDTO;
import com.drr.glocal.services.manager.RouteConfigurationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by racastur on 31-10-2014.
 */
@RestController
@RequestMapping("/services/v1/*")
public class RouteConfigurationController {

    private static final Logger log = Logger.getLogger(RouteConfigurationController.class.getName());

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @RequestMapping(value = "routes", method = RequestMethod.POST)
    public RouteDTO createNewRoute(@RequestBody RouteDTO routeDTO) {
        return routeConfigurationManager.createUpdateRoute(routeDTO);
    }

    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public List<RouteDTO> getAllRoutes() {
        return routeConfigurationManager.getAllRoutes();
    }

    @RequestMapping(value = "routes/{routeId}", method = RequestMethod.GET)
    public RouteDTO getRoute(@PathVariable("routeId") Long routeId) {
        return routeConfigurationManager.getRoute(routeId, false);
    }

}
