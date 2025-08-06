package com.courtly.controller;

import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;
import com.courtly.service.CommonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class CommonController<E extends BaseEntity, D extends BaseDto, S extends CommonService<D, E>> {

    private S service;

    public CommonController(S service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody D dto){
        try {
            service.save(dto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody D dto, @PathVariable(name = "id") Long id){
        try {
            service.update(dto, id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable(name = "id") Long id){
        D dto = service.findById(id);
        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
