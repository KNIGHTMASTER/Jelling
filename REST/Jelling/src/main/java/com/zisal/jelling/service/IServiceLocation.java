package com.zisal.jelling.service;


import com.zisal.jelling.model.ModelLocation;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Ladies Man on 11/29/2015.
 */
public interface IServiceLocation {

    List<ModelLocation> loadAllLocation(BigInteger p_UserId);
}
