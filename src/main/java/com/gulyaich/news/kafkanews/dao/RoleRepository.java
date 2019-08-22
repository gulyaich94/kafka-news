package com.gulyaich.news.kafkanews.dao;

import com.gulyaich.news.kafkanews.model.user.Role;
import com.gulyaich.news.kafkanews.model.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByType(RoleType roleType);
}
