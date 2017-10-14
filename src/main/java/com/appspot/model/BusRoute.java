package com.appspot.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by eljah32 on 10/8/2017.
 */

@Entity
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToMany
    public List<BusNode> busNodes;

    @ManyToMany
    public List<BusStop> busStops;

    public String name;
    public String name_ru;
    public String name_tt;
    public String name_en;
}
