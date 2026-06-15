package com.confiance.gateway.repository;

import com.confiance.gateway.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByCompanyCode(String companyCode);

    boolean existsByCompanyName(String companyName);

    boolean existsByCompanyNameAr(String companyNameAr);

    Optional<Tenant> findByCompanyCodeAndIsActiveTrue(String companyCode);

    List<Tenant> findAllByIsActiveTrue();

    boolean existsByCompanyCode(String companyCode);
}
