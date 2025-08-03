package com.courtly.dao;

import com.courtly.entity.Court;
import org.springframework.stereotype.Repository;

@Repository
public class CourtDao extends BaseDao<Court>{
    public CourtDao(){
        super(Court.class);
    }
}
