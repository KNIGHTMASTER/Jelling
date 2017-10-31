package com.zisal.jelling.web.rest;

import com.zisal.jelling.constant.ApplicationConstant.LOG;
import com.zisal.jelling.dto.DTORequestLocation;
import com.zisal.jelling.model.ModelLocation;
import com.zisal.jelling.service.IServiceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ladies Man on 11/29/2015.
 */
@RestController
public class RestLocation {

    @Autowired
    IServiceLocation iServiceLocation;

    Logger logger = LoggerFactory.getLogger(RestLocation.class);

    @RequestMapping(value = "/rest/load/location/", method = RequestMethod.POST)
    @ResponseBody
    public List<ModelLocation> loadAllLocation(@RequestBody DTORequestLocation dtoRequestLocation){
        logger.info(LOG.INFO, "User Logged in  "+dtoRequestLocation.getUserId());
        return iServiceLocation.loadAllLocation(dtoRequestLocation.getUserId());
    }
}
