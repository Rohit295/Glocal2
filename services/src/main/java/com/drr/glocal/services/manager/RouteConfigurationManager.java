package com.drr.glocal.services.manager;

import com.drr.glocal.model.RouteDTO;
import com.drr.glocal.model.RouteStopDTO;
import com.drr.glocal.services.persistence.PMF;
import com.drr.glocal.services.persistence.Route;
import com.drr.glocal.services.persistence.RouteStop;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeConfigurationManager")
public class RouteConfigurationManager {

    public RouteDTO createUpdateRoute(RouteDTO routeDTO) {

        if (routeDTO == null) {
            // throw validation exception
            throw new IllegalArgumentException("route is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {

            boolean newRoute = false;

            Route route;
            if (routeDTO.getId() != null && routeDTO.getId() > 0) {

                route = pm.getObjectById(Route.class, routeDTO.getId());
                if (route == null) {
                    throw new IllegalArgumentException("Unknown route");
                }

                // TODO check if the route re-naming clashes with a different route's name

                route.setName(routeDTO.getName());
                route.setDefaultStopPurpose(routeDTO.getDefaultStopPurpose());
                route.setExecutionStartTime(routeDTO.getExecutionStartTime());

            } else {
                newRoute = true;
                route = new Route(routeDTO);
            }

            if (newRoute) {
                pm.makePersistent(route);
            }

            return route.getDTO();

        } catch (Exception e) {
            // TODO
            e.printStackTrace();
            throw new RuntimeException("Unknown error");
        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public void addRouteStops(Long routeId, List<RouteStopDTO> routeStopDTOs) {

        if (routeId == null || routeStopDTOs == null || routeStopDTOs.isEmpty()) {
            throw new IllegalArgumentException("route and one or more route stops are required.");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            Route route = pm.getObjectById(Route.class, routeId);
            if (route == null) {
                throw new IllegalArgumentException("Unknown route");
            }

            for (RouteStopDTO routeStopDTO : routeStopDTOs) {
                route.getRouteStops().add(new RouteStop(routeStopDTO));
            }

        } catch (Exception e) {
            // TODO
            throw new RuntimeException("Unknown error");
        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public List<RouteDTO> getAllRoutes() {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Extent<Route> routeExtent = pm.getExtent(Route.class);
        if (routeExtent == null) {
            return new ArrayList<RouteDTO>();
        }

        List<RouteDTO> dtos = new ArrayList<RouteDTO>();
        for (Route route : routeExtent) {
            dtos.add(route.getDTO());
        }

        return dtos;

    }

    public RouteDTO getRoute(Long routeId, boolean includeRouteStops) {

        if (routeId == null) {
            throw new IllegalArgumentException("route id is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Route route = pm.getObjectById(Route.class, routeId);
        if (route == null) {
            throw new IllegalArgumentException("Unknown route");
        }

        return route.getDTO();

    }

}
