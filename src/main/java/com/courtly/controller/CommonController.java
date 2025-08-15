package com.courtly.controller;

import com.courtly.dto.ApiResponse;
import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;
import com.courtly.service.CommonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class CommonController<E extends BaseEntity, D extends BaseDto, S extends CommonService<D, E>> {

    protected S service;

    public CommonController(S service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<D>> save(@RequestBody @Valid D dto){
        ApiResponse<D> response = new ApiResponse<>();
        try {
            service.save(dto);
        } catch (IllegalArgumentException e){
            response.setErrors(List.of(e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<D>> update(@RequestBody D dto, @PathVariable(name = "id") Long id){
        ApiResponse<D> response = new ApiResponse<>();
        try {
            service.update(dto, id);
        } catch (IllegalArgumentException e) {
            response.setErrors(List.of(e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<D>> findById(@PathVariable(name = "id") Long id){
        ApiResponse<D> response = new ApiResponse<>();
        D dto = service.findById(id);
        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<D>>> getAll(){
        ApiResponse<List<D>> response = new ApiResponse<>();
        response.setData(service.getAll());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<D>> delete(@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
