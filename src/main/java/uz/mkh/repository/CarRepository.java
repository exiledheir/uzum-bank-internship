package uz.mkh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mkh.model.entity.CarEntity;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    List<CarEntity> findCarByOwnerIdIs(Long id);

    @Query("select count(distinct function('split_part',c.model,'-',1)) from CarEntity c")
    Long countDistinctVendor();
}
