package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByPrefixEquals(String prefix);

    Category findByNameEquals(String name);

    List<Category> findAllByParentIsNull();

    List<Category> findAllByParent_Id(int id);

}
