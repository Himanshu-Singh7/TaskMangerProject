package com.task.management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.management.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}
