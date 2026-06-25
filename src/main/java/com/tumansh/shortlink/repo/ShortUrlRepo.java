package com.tumansh.shortlink.repo;

import com.tumansh.shortlink.entity.ShortUrl;
import com.tumansh.shortlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepo
        extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortCode(String shortCode);
    List<ShortUrl> findByUser(User user);

    boolean existsByShortCode(String shortCode);
}