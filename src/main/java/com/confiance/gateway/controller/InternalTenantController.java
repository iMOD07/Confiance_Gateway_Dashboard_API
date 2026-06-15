package com.confiance.gateway.controller;

import com.confiance.gateway.entity.Tenant;
import com.confiance.gateway.model.response.ApiResponse;
import com.confiance.gateway.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Internal API — called by the Attendance Backend to resolve tenant DB info.
 * NOT exposed to Flutter or external clients.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/internal/tenant")
@RequiredArgsConstructor
public class InternalTenantController {

    private final TenantRepository tenantRepository;

    @GetMapping("/{companyCode}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTenantDbInfo(@PathVariable String companyCode) {
        Tenant tenant = tenantRepository.findByCompanyCodeAndIsActiveTrue(companyCode.toUpperCase().trim())
                .orElse(null);

        if (tenant == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Tenant not found or inactive: " + companyCode));
        }

        Map<String, Object> dbInfo = Map.of(
                "companyCode", tenant.getCompanyCode(),
                "dbHost", tenant.getDbHost() != null ? tenant.getDbHost() : "localhost",
                "dbPort", tenant.getDbPort() != null ? tenant.getDbPort() : 5432,
                "dbName", tenant.getDbName() != null ? tenant.getDbName() : "confiance_" + companyCode.toLowerCase()
        );

        log.info("Internal lookup: {} → {}", companyCode, dbInfo.get("dbName"));

        return ResponseEntity.ok(ApiResponse.success("Tenant DB info", dbInfo));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Object>> getTenantAll() {
        var tenants = tenantRepository.findAll()
                .stream()
                .map(t -> Map.of(
                        "companyCode", t.getCompanyCode(),
                        "dbHost", t.getDbHost() != null ? t.getDbHost() : "localhost",
                        "dbPort", t.getDbPort() != null ? t.getDbPort() : 5432,
                        "dbName", t.getDbName() != null ? t.getDbName() : "confiance_" + t.getCompanyCode().toLowerCase(),
                        "isActive", t.getIsActive()
                ))
                .toList();

        return ResponseEntity.ok(ApiResponse.success("All tenants", tenants));
    }
}
