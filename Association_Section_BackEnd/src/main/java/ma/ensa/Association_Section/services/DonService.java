package ma.ensa.Association_Section.services;

import ma.ensa.Association_Section.entities.Association;
import ma.ensa.Association_Section.entities.Don;
import ma.ensa.Association_Section.repositories.AssociationRepository;
import ma.ensa.Association_Section.repositories.DonRepository;
import ma.ensa.Association_Section.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonService {
    @Autowired
    private DonRepository donRepository;

    @Autowired
    private AssociationRepository associationRepository;  // Ajouter la dépendance du repository d'Association

    public Don createDon(Don don) {
        // Récupérer l'association par son ID
        Association association = associationRepository.findById(don.getAssociation().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Association non trouvée avec l'id: " + don.getAssociation().getId()));

        don.setAssociation(association);  // Associer l'objet Association au Don avant de le sauvegarder

        // Sauvegarder le Don avec l'association correctement liée
        return donRepository.save(don);
    }

    public List<Don> getDonsByUtilisateur(Long idUtilisateur) {
        return donRepository.findByIdUtilisateur(idUtilisateur);
    }
}

