package ma.ensa.Association_Section.controllers;

import ma.ensa.Association_Section.entities.Association;
import ma.ensa.Association_Section.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/associations")
public class AssociationController {
    @Autowired
    private AssociationService associationService;

    @GetMapping
    public ResponseEntity<List<Association>> getAllAssociations() {
        return ResponseEntity.ok(associationService.getAllAssociations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Association> getAssociationById(@PathVariable Long id) {
        return ResponseEntity.ok(associationService.getAssociationById(id));
    }
}
