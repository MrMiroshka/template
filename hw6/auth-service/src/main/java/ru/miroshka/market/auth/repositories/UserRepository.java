package ru.miroshka.market.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miroshka.market.auth.data.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
