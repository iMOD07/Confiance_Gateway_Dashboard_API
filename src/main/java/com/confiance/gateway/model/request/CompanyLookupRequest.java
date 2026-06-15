package com.confiance.gateway.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLookupRequest {

    @NotBlank(message = "Company code is required")
    private String companyCode;
}
