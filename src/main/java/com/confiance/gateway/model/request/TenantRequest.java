package com.confiance.gateway.model.request;

import com.confiance.gateway.entity.HostingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantRequest {

    @NotBlank(message = "Company code is required")
    private String companyCode;

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String companyNameAr;

    private String logoUrl;

    @NotNull(message = "Hosting type is required")
    private HostingType hostingType;

    @NotBlank(message = "Server URL is required")
    private String serverUrl;

    private String dbHost;
    private Integer dbPort;
    private String dbName;

    private String contactName;
    private String contactPhone;
    private String contactEmail;

    private Integer maxEmployees;
    private LocalDateTime subscriptionExpiry;
    private String notes;
}
