package ma.ensa.Association_Section.services;

import ma.ensa.Association_Section.entities.Don;
import ma.ensa.Association_Section.repositories.DonRepository;
import ma.ensa.Association_Section.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonService {
    @Autowired
    private DonRepository donRepository;

    public Don createDon(Don don) {
        return donRepository.save(don);
    }

    public List<Don> getDonsByUtilisateur(Long idUtilisateur) {
        return donRepository.findByIdUtilisateur(idUtilisateur);
    }
}
