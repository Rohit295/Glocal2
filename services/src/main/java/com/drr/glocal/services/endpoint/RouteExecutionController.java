package com.drr.glocal.services.endpoint;

import com.drr.glocal.model.RouteExecutionDTO;
import com.drr.glocal.model.RouteExecutionLocationDTO;
import com.drr.glocal.model.RouteExecutionStopDTO;
import com.drr.glocal.services.manager.RouteExecutionManager;

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
public class RouteExecutionController {

    private static final Long USER_ID = 1L;

    private static final Logger log = Logger.getLogger(RouteExecutionController.class.getName());

    @Autowired
    private RouteExecutionManager routeExecutionManager;

    @RequestMapping(value = "routes/{routeId}/executions", method = RequestMethod.POST)
    public RouteExecutionDTO startRouteExecution(@PathVariable("routeId") Long routeId) {
        return routeExecutionManager.startRouteExecution(USER_ID, routeId);
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/location", method = RequestMethod.POST)
    public void postRouteExecutionLocation(
            @PathVariable("routeExecutionId") Long routeExecutionId,
            @RequestBody RouteExecutionLocationDTO routeExecutionLocationDTO) {
        routeExecutionManager.postRouteExecutionLocation(USER_ID, routeExecutionId, routeExecutionLocationDTO);
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/stop", method = RequestMethod.POST)
    public void postRouteExecutionStop(
            @PathVariable("routeExecutionId") Long routeExecutionId,
            @RequestBody RouteExecutionStopDTO routeExecutionStopDTO) {
        routeExecutionManager.postRouteExecutionStop(USER_ID, routeExecutionId, routeExecutionStopDTO);
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}", method = RequestMethod.GET)
    public RouteExecutionDTO getRouteExecution(@PathVariable("routeExecutionId") Long routeExecutionId) {
        return routeExecutionManager.getRouteExecution(routeExecutionId);
    }

}
