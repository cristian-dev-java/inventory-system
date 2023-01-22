package my.projects.inventorysystem.repository;

import my.projects.inventorysystem.model.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Integer> {
}
