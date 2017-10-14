package com.appspot.model;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by eljah32 on 10/8/2017.
 */

@Entity
public class BusNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;
    @org.datanucleus.api.jpa.annotations.Index(unique = "false", name="LAT")
    public double latitude;
    @org.datanucleus.api.jpa.annotations.Index(unique = "false", name="LON")
    public double longitude;

}
