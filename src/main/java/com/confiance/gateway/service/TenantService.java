package com.confiance.gateway.service;

import com.confiance.gateway.entity.Tenant;
import com.confiance.gateway.model.request.TenantRequest;
import com.confiance.gateway.model.response.CompanyLookupResponse;
import com.confiance.gateway.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    // ===== FLUTTER APP CALLS THIS =====
    // Look up company by code - returns only what Flutter needs
    public CompanyLookupResponse lookupByCode(String companyCode) {
        Tenant tenant = tenantRepository.findByCompanyCode(companyCode.toUpperCase().trim())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!tenant.getIsActive()) {
            throw new RuntimeException("Company subscription is inactive");
        }

        // Check subscription expiry
        String subscriptionStatus = "ACTIVE";
        if (tenant.getSubscriptionExpiry() != null
                && tenant.getSubscriptionExpiry().isBefore(LocalDateTime.now())) {
            subscriptionStatus = "EXPIRED";
        }

        log.info("Company lookup: {} -> {}", companyCode, tenant.getServerUrl());

        return CompanyLookupResponse.builder()
                .companyCode(tenant.getCompanyCode())
                .companyName(tenant.getCompanyName())
                .companyNameAr(tenant.getCompanyNameAr())
                .logoUrl(tenant.getLogoUrl())
                .serverUrl(tenant.getServerUrl())
                .hostingType(tenant.getHostingType())
                .isActive(tenant.getIsActive())
                .subscriptionStatus(subscriptionStatus)
                .build();
    }

    // ===== ADMIN OPERATIONS =====

    // Get all tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // Get active tenants only
    public List<Tenant> getActiveTenants() {
        return tenantRepository.findAllByIsActiveTrue();
    }

    // Get tenant by ID
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    // Create new tenant
    public Tenant createTenant(TenantRequest request) {
        if (tenantRepository.existsByCompanyCode(request.getCompanyCode().toUpperCase().trim())) {
            throw new RuntimeException("Company code already exists");
        }

        String nameEn = request.getCompanyName() == null ? "" : request.getCompanyName().trim().toUpperCase();
        String nameAr = request.getCompanyNameAr() == null ? "" : request.getCompanyNameAr().trim().toUpperCase();

        if (tenantRepository.existsByCompanyName(nameEn) || tenantRepository.existsByCompanyName(nameAr)) {
            throw new RuntimeException("Company Name already exists (Arabic or English)");
        }

        Tenant tenant = Tenant.builder()
                .companyCode(request.getCompanyCode().toUpperCase().trim())
                .companyName(request.getCompanyName())
                .companyNameAr(request.getCompanyNameAr())
                .logoUrl(request.getLogoUrl())
                .hostingType(request.getHostingType())
                .serverUrl(request.getServerUrl())
                .dbHost(request.getDbHost())
                .dbPort(request.getDbPort())
                .dbName(request.getDbName())
                .contactName(request.getContactName())
                .contactPhone(request.getContactPhone())
                .contactEmail(request.getContactEmail())
                .maxEmployees(request.getMaxEmployees())
                .subscriptionExpiry(request.getSubscriptionExpiry())
                .notes(request.getNotes())
                .isActive(true)
                .build();

        Tenant saved = tenantRepository.save(tenant);
        log.info("Tenant created: {} [{}] -> {}", saved.getCompanyName(), saved.getCompanyCode(), saved.getServerUrl());
        return saved;
    }

    // Update tenant
    public Tenant updateTenant(Long id, TenantRequest request) {
        Tenant tenant = getTenantById(id);

        tenant.setCompanyName(request.getCompanyName());
        tenant.setCompanyNameAr(request.getCompanyNameAr());
        tenant.setLogoUrl(request.getLogoUrl());
        tenant.setHostingType(request.getHostingType());
        tenant.setServerUrl(request.getServerUrl());
        tenant.setDbHost(request.getDbHost());
        tenant.setDbPort(request.getDbPort());
        tenant.setDbName(request.getDbName());
        tenant.setContactName(request.getContactName());
        tenant.setContactPhone(request.getContactPhone());
        tenant.setContactEmail(request.getContactEmail());
        tenant.setMaxEmployees(request.getMaxEmployees());
        tenant.setSubscriptionExpiry(request.getSubscriptionExpiry());
        tenant.setNotes(request.getNotes());
        tenant.setUpdatedAt(LocalDateTime.now());

        Tenant saved = tenantRepository.save(tenant);
        log.info("Tenant updated: {} [{}]", saved.getCompanyName(), saved.getCompanyCode());
        return saved;
    }

    // Activate / Deactivate tenant
    public Tenant toggleActive(Long id) {
        Tenant tenant = getTenantById(id);
        tenant.setIsActive(!tenant.getIsActive());
        tenant.setUpdatedAt(LocalDateTime.now());

        Tenant saved = tenantRepository.save(tenant);
        log.info("Tenant {} {}", saved.getCompanyCode(), saved.getIsActive() ? "ACTIVATED" : "DEACTIVATED");
        return saved;
    }

    // Delete tenant
    public void deleteTenant(Long id) {
        Tenant tenant = getTenantById(id);
        tenantRepository.delete(tenant);
        log.info("Tenant deleted: {} [{}]", tenant.getCompanyName(), tenant.getCompanyCode());
    }
}
