package com.confiance.gateway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Company code - what the user enters in Flutter app
    @Column(name = "company_code", nullable = false, unique = true, length = 50)
    private String companyCode;

    // Company display name
    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    // Company name in Arabic
    @Column(name = "company_name_ar", length = 200)
    private String companyNameAr;

    // Company logo URL
    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    // Hosting type: DEDICATED, SHARED, ON_PREMISE
    @Enumerated(EnumType.STRING)
    @Column(name = "hosting_type", nullable = false, length = 20)
    private HostingType hostingType;

    // The base URL for this tenant's API server
    // e.g. https://client-a.confiance.app or http://192.168.1.50:8080
    @Column(name = "server_url", nullable = false, length = 500)
    private String serverUrl;

    // Database host (for backend routing, not exposed to Flutter)
    @Column(name = "db_host", length = 255)
    private String dbHost;

    // Database port
    @Column(name = "db_port")
    private Integer dbPort;

    // Database name
    @Column(name = "db_name", length = 100)
    private String dbName;

    // Contact person name
    @Column(name = "contact_name", length = 100)
    private String contactName;

    // Contact phone
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    // Contact email
    @Column(name = "contact_email", length = 150)
    private String contactEmail;

    // Max number of employees allowed
    @Column(name = "max_employees")
    private Integer maxEmployees;

    // Subscription expiry date
    @Column(name = "subscription_expiry")
    private LocalDateTime subscriptionExpiry;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "notes", length = 1000)
    private String notes;
}
