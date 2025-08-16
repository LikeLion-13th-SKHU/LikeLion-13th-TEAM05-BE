package com.likelion.artipick.domain.culture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CultureRepository extends JpaRepository<Culture, Long> {
    boolean existsBySeq(String seq);
    Optional<Culture> findBySeq(String seq);
}
