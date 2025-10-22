package uz.mkh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mkh.model.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
}
