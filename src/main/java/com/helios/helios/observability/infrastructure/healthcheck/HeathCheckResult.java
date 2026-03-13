package com.helios.helios.observability.infrastructure.healthcheck;

public class HeathCheckResult {

    private final boolean up;

    public HeathCheckResult(boolean up) {
        this.up = up;
    }

    public boolean isUp() {
        return up;
    }

}
