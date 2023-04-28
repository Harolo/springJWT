package com.springJWT.Repository;

import com.springJWT.Enum.RoleName;
import com.springJWT.Model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Rol,Long> {
   Optional<Rol> findByName(RoleName name);
}
