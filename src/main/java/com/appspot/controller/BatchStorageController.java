package com.appspot.controller;

import com.appspot.model.BusNode;
import com.appspot.model.BusRoute;
import com.appspot.model.BusStop;
import com.appspot.repo.BusNodeRepository;
import com.appspot.repo.BusRouteRepository;
import com.appspot.repo.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by eljah32 on 10/8/2017.
 */

@Controller
@RequestMapping("/bus")
@Transactional
public class BatchStorageController {
    private static Logger logger = LoggerFactory.getLogger(BatchStorageController.class);

    @Autowired
    BusNodeRepository busNodeRepository;

    @Autowired
    BusStopRepository busStopRepository;

    @Autowired
    BusRouteRepository busRouteRepository;


    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public @ResponseBody
    List<BusNode> getBusRoutes() {
        BusNode busNode=new BusNode();
        busNode.latitude=50.4;
        busNode.longitude=45.5;
        BusNode busNode2=new BusNode();
        busNode2.latitude=50.5;
        busNode2.longitude=45.3;
        busNodeRepository.save(busNode);
        busNodeRepository.save(busNode2);
        return busNodeRepository.findAll();
    }

    @RequestMapping(value = "/nodes/{latitude}", method = RequestMethod.GET)
    public @ResponseBody
    List<BusNode> getByLatitude(@PathVariable("latitude") double latitude) {
        List<BusNode> busNode=busNodeRepository.findTop1ByLatitude(latitude);
        return busNode;
    }

    @Transactional()
    @RequestMapping(value = "/stops", method = RequestMethod.GET)
    public @ResponseBody
    List<BusStop> getBusStops() {
        BusNode busNode=busNodeRepository.findTop1ByLatitude(50.5).get(0);
        //BusNode busNode3=new BusNode();
        //busNode3.latitude=50.2;
        //busNode3.longitude=45.2;
        //busNodeRepository.save(busNode3);

        BusStop busStop=new BusStop();
        busStop.busNode=busNode;
        busStop.name="Idel";
        busStop.name_en="Idel";
        busStop.name_ru="Idel";
        busStop.name_tt="Idel";

        busStopRepository.save(busStop);
        return busStopRepository.findAll();
    }
}
