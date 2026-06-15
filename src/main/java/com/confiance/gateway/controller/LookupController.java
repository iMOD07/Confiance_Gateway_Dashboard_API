package com.confiance.gateway.controller;

import com.confiance.gateway.model.request.CompanyLookupRequest;
import com.confiance.gateway.model.response.ApiResponse;
import com.confiance.gateway.model.response.CompanyLookupResponse;
import com.confiance.gateway.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
public class LookupController {

    private final TenantService tenantService;

    // POST /api/v1/lookup/company
    // Flutter sends company code, gets back server URL + company info
    @PostMapping("/company")
    public ResponseEntity<ApiResponse<CompanyLookupResponse>> lookupCompany(
            @Valid @RequestBody CompanyLookupRequest request) {
        try {
            CompanyLookupResponse response = tenantService.lookupByCode(request.getCompanyCode());
            return ResponseEntity.ok(ApiResponse.success("Company found", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }

    // GET /api/v1/lookup/company/{code}
    // Alternative: lookup by path variable
    @GetMapping("/company/{code}")
    public ResponseEntity<ApiResponse<CompanyLookupResponse>> lookupByCode(@PathVariable String code) {
        try {
            CompanyLookupResponse response = tenantService.lookupByCode(code);
            return ResponseEntity.ok(ApiResponse.success("Company found", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }
}
