package com.titaniumarmor.promociones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.titaniumarmor.promociones_service.model.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long>{

}
