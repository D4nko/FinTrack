package pl.engineering.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineering.dto.UserTransactionCountDto;
import pl.engineering.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id BETWEEN :minId AND :maxId")
    List<User> findByIdWithLock(@Param("minId") Integer minId, @Param("maxId") Integer maxId);
    @Query("SELECT new pl.kurs.model.dto.UserTransactionCountDto(u.id, u.name, u.surname, u.age, COUNT(t)) " +
            "FROM User u " +
            "LEFT JOIN u.transactions t " +
            "GROUP BY u.id, u.name, u.surname, u.age")
    Page<UserTransactionCountDto> findAllWithTransactionCount(Pageable pageable);
}

