package com.courtly.controller;

import com.courtly.entity.SurfaceType;
import com.courtly.service.SurfaceTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/surface-types")
public class SurfaceTypeController extends CommonController<SurfaceType, SurfaceTypeService>{
    public SurfaceTypeController(SurfaceTypeService service) {
        super(service);
    }
}
