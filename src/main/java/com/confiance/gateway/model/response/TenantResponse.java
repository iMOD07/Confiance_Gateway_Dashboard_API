package com.confiance.gateway.model.response;

import com.confiance.gateway.entity.HostingType;
import com.confiance.gateway.entity.Tenant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {

    private Long id;
    private String companyCode;
    private String companyName;
    private String companyNameAr;
    private String logoUrl;
    private HostingType hostingType;
    private String serverUrl;

    // Contact info (safe to expose to admin)
    private String contactName;
    private String contactPhone;
    private String contactEmail;

    // Subscription
    private Integer maxEmployees;
    private LocalDateTime subscriptionExpiry;
    private Boolean isActive;
    private String notes;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DB info summary (safe version - just shows if configured, not actual credentials)
    private boolean dbConfigured;

    public static TenantResponse fromEntity(Tenant tenant) {
        return TenantResponse.builder()
                .id(tenant.getId())
                .companyCode(tenant.getCompanyCode())
                .companyName(tenant.getCompanyName())
                .companyNameAr(tenant.getCompanyNameAr())
                .logoUrl(tenant.getLogoUrl())
                .hostingType(tenant.getHostingType())
                .serverUrl(tenant.getServerUrl())
                .contactName(tenant.getContactName())
                .contactPhone(tenant.getContactPhone())
                .contactEmail(tenant.getContactEmail())
                .maxEmployees(tenant.getMaxEmployees())
                .subscriptionExpiry(tenant.getSubscriptionExpiry())
                .isActive(tenant.getIsActive())
                .notes(tenant.getNotes())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .dbConfigured(tenant.getDbHost() != null && !tenant.getDbHost().isBlank())
                .build();
    }
}
