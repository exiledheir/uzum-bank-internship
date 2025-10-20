package uz.mkh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mkh.model.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
}
