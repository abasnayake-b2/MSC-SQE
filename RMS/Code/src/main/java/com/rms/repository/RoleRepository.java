package com.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rms.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT u FROM Role u WHERE u.roleName=?1")
	Role findByName(String roleName);

}
