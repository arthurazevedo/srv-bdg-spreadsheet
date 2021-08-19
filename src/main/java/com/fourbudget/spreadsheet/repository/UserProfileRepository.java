package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findById(Long id);

    @Override
    boolean existsById(Long id);
}
