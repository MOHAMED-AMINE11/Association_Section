package ma.ensa.Association_Section.repositories;

import ma.ensa.Association_Section.entities.Association;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<Association, Long> {
    // Méthodes personnalisées si nécessaire
}
