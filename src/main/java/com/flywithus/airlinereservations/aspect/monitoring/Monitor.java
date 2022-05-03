package com.flywithus.airlinereservations.aspect.monitoring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Monitor {

    /**
     * Threshold above which the MonitoringAspect raises a heads-up regarding method call duration
     */
    int threshold();
}