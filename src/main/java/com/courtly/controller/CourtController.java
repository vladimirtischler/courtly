package com.courtly.controller;

import com.courtly.entity.Court;
import com.courtly.service.CourtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/courts")
public class CourtController {

    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveCourt(@RequestBody Court court){
        courtService.save(court);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Object> updateCourt(@RequestBody Court court){
        courtService.update(court);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getCourt(@PathVariable(name = "id") Long id){
        Court court = courtService.findById(id);
        if (court == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(court);
    }

    @GetMapping()
    public ResponseEntity<Object> getCourt(){
        return ResponseEntity.ok(courtService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCourt(@PathVariable(name = "id") Long id){
        courtService.delete(id);
        return ResponseEntity.ok().build();
    }
}
