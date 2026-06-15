package com.confiance.gateway.controller;

import com.confiance.gateway.entity.Tenant;
import com.confiance.gateway.model.request.TenantRequest;
import com.confiance.gateway.model.response.ApiResponse;
import com.confiance.gateway.model.response.TenantResponse;
import com.confiance.gateway.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/tenants")
@RequiredArgsConstructor
public class AdminTenantController {

    private final TenantService tenantService;

    // GET /api/v1/admin/tenants
    @GetMapping
    public ResponseEntity<ApiResponse<List<TenantResponse>>> getAllTenants() {
        List<TenantResponse> tenants = tenantService.getAllTenants()
                .stream()
                .map(TenantResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("All tenants", tenants));
    }

    // GET /api/v1/admin/tenants/active
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<TenantResponse>>> getActiveTenants() {
        List<TenantResponse> tenants = tenantService.getActiveTenants()
                .stream()
                .map(TenantResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Active tenants", tenants));
    }

    // GET /api/v1/admin/tenants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantResponse>> getTenantById(@PathVariable Long id) {
        try {
            Tenant tenant = tenantService.getTenantById(id);
            return ResponseEntity.ok(ApiResponse.success("Tenant found", TenantResponse.fromEntity(tenant)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, e.getMessage()));
        }
    }

    // POST /api/v1/admin/tenants
    @PostMapping
    public ResponseEntity<ApiResponse<TenantResponse>> createTenant(@Valid @RequestBody TenantRequest request) {
        try {
            Tenant tenant = tenantService.createTenant(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created("Tenant created", TenantResponse.fromEntity(tenant)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }

    // PUT /api/v1/admin/tenants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantResponse>> updateTenant(@PathVariable Long id,
                                                                     @Valid @RequestBody TenantRequest request) {
        try {
            Tenant tenant = tenantService.updateTenant(id, request);
            return ResponseEntity.ok(ApiResponse.success("Tenant updated", TenantResponse.fromEntity(tenant)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }

    // PATCH /api/v1/admin/tenants/{id}/toggle
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<TenantResponse>> toggleActive(@PathVariable Long id) {
        try {
            Tenant tenant = tenantService.toggleActive(id);
            String msg = tenant.getIsActive() ? "Tenant activated" : "Tenant deactivated";
            return ResponseEntity.ok(ApiResponse.success(msg, TenantResponse.fromEntity(tenant)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, e.getMessage()));
        }
    }

    // DELETE /api/v1/admin/tenants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTenant(@PathVariable Long id) {
        try {
            tenantService.deleteTenant(id);
            return ResponseEntity.ok(ApiResponse.success("Tenant deleted"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, e.getMessage()));
        }
    }
}
