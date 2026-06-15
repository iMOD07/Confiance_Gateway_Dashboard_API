package com.confiance.gateway.entity;

public enum HostingType {

    // Client has dedicated server + dedicated database
    DEDICATED,

    // Multiple clients share one server, each has own database
    SHARED,

    // Client hosts on their own server (on-premise)
    ON_PREMISE
}
