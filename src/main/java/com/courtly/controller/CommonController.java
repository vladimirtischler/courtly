package com.courtly.controller;

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

public abstract class CommonController<E extends BaseEntity, S extends CommonService<E>> {

    private S service;

    public CommonController(S service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody E entity){
        try {
            service.save(entity);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody E entity, @PathVariable(name = "id") Long id){
        try {
            service.update(entity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<E> findById(@PathVariable(name = "id") Long id){
        E entity = service.findById(id);
        if (entity == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    @GetMapping()
    public ResponseEntity<List<E>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<E> delete(@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
