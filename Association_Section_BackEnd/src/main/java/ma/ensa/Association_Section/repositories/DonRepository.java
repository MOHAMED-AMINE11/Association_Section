package ma.ensa.Association_Section.repositories;

import ma.ensa.Association_Section.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonRepository extends JpaRepository<Don, Long> {
    List<Don> findByIdUtilisateur(Long idUtilisateur);
}
