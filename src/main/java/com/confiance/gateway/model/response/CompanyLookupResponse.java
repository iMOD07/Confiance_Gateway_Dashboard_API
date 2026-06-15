package com.confiance.gateway.model.response;

import com.confiance.gateway.entity.HostingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLookupResponse {

    // Company info
    private String companyCode;
    private String companyName;
    private String companyNameAr;
    private String logoUrl;

    // Where Flutter should connect
    private String serverUrl;
    private HostingType hostingType;

    // Subscription status
    private Boolean isActive;
    private String subscriptionStatus;
}
