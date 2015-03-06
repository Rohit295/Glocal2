package com.drr.glocal.services.manager;

import com.drr.glocal.model.RouteExecutionDTO;
import com.drr.glocal.model.RouteExecutionLocationDTO;
import com.drr.glocal.model.RouteExecutionStopDTO;
import com.drr.glocal.services.persistence.PMF;
import com.drr.glocal.services.persistence.Route;
import com.drr.glocal.services.persistence.RouteExecution;
import com.drr.glocal.services.persistence.RouteExecutionLocation;
import com.drr.glocal.services.persistence.RouteExecutionStop;
import com.drr.glocal.services.persistence.RouteStop;

import org.springframework.stereotype.Component;

import java.util.Calendar;

import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeExecutionManager")
public class RouteExecutionManager {

    public RouteExecutionDTO startRouteExecution(Long userId, Long routeId) {

        if (routeId == null) {
            // throw validation exception
            throw new IllegalArgumentException("routeId is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            Route route = pm.getObjectById(Route.class, routeId);
            if (route == null) {
                throw new IllegalArgumentException("Unknown route");
            }

            RouteExecution routeExecution = new RouteExecution();
            routeExecution.setRouteId(routeId);
            routeExecution.setRouteExecutorId(userId);
            routeExecution.setStartTime(Calendar.getInstance().getTimeInMillis());

            pm.makePersistent(routeExecution);

            return routeExecution.getDTO();

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

    public void postRouteExecutionLocation(Long userId, Long routeExecutionId, RouteExecutionLocationDTO routeExecutionLocationDTO) {

        if (routeExecutionId == null || routeExecutionLocationDTO == null) {
            // throw validation exception
            throw new IllegalArgumentException("routeExecutionId and dto are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown route execution");
            }

            RouteExecutionLocation routeExecutionLocation =
                    new RouteExecutionLocation(routeExecutionId, routeExecutionLocationDTO);

            pm.makePersistent(routeExecutionLocation);

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

    public void postRouteExecutionStop(Long userId, Long routeExecutionId, RouteExecutionStopDTO routeExecutionStopDTO) {

        if (routeExecutionId == null || routeExecutionStopDTO == null) {
            // throw validation exception
            throw new IllegalArgumentException("routeExecutionId and dto are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown route execution");
            }

            RouteExecutionStop routeExecutionStop =
                    new RouteExecutionStop(routeExecutionId, routeExecutionStopDTO);

            // TODO should we create/persist a route execution location too?

            pm.makePersistent(routeExecutionStop);

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

    public RouteExecutionDTO getRouteExecution(Long routeExecutionId) {

        if (routeExecutionId == null) {
            throw new IllegalArgumentException("route execution id is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
        if (routeExecution == null) {
            throw new IllegalArgumentException("Unknown route execution");
        }

        return routeExecution.getDTO();

    }

}
