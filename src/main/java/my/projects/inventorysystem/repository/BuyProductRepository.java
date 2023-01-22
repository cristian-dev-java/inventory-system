package my.projects.inventorysystem.repository;

import my.projects.inventorysystem.model.BuyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyProductRepository extends JpaRepository<BuyProduct, Integer> {
}
