package com.appspot.model;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;

/**
 * Created by eljah32 on 10/8/2017.
 */

@Entity
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    public String key;

    @OneToOne
    public BusNode busNode;

    public String name;
    public String name_ru;
    public String name_tt;
    public String name_en;
}
