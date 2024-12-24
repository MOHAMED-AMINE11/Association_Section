package ma.ensa.Association_Section.services;

import ma.ensa.Association_Section.repositories.AssociationRepository;
import ma.ensa.Association_Section.exceptions.ResourceNotFoundException;
import ma.ensa.Association_Section.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssociationService {
    @Autowired
    private AssociationRepository associationRepository;

    public List<Association> getAllAssociations() {
        return associationRepository.findAll();
    }

    public Association getAssociationById(Long id) {
        return associationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association non trouvée avec l'id: " + id));
    }
}
