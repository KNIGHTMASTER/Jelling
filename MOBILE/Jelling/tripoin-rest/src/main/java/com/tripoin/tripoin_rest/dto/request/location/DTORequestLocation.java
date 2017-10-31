package com.tripoin.tripoin_rest.dto.request.location;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.math.BigInteger;

/**
 * Created on 11/29/2015 : 5:49 PM.
 *
 * @author <a href="fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Parcel
public class DTORequestLocation {

    @SerializedName("userId")
    private BigInteger userId;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DTORequestLocation{" +
                "userId=" + userId +
                '}';
    }
}
