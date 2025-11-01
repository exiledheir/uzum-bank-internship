package uz.mkh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mkh.model.entity.PersonEntity;

import java.time.LocalDate;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    boolean existsByNameAndBirthdate(String name, LocalDate birthdate);
}
