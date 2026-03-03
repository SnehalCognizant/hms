package com.cts.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cts.inventory.entity.Property;
import com.cts.inventory.services.PropertyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public Property addProperty(@RequestBody Property property) {
        return propertyService.addProperty(property);
    }

    @GetMapping("/all")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public Optional<Property> getProperty(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }
}
