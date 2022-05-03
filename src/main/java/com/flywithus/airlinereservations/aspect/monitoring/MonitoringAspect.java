package com.flywithus.airlinereservations.aspect.monitoring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class MonitoringAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringAspect.class);
    private static final String MONITORING_LOG = "Method call [{}] with args [{}] took {}ms";
    private static final String MONITORING_OVER_THRESHOLD_LOG = "Method call [{}] with args [{}] took {}ms, {}ms more than expected";

    private static final String MONITORING_OUTPUT_VALUE_LOG = "Method [{}] returned [{}]";

    @Around("@annotation(monitor)")
    public Object monitor(ProceedingJoinPoint joinPoint, Monitor monitor)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object methodReturnObject = null;

        try {
            methodReturnObject = joinPoint.proceed(joinPoint.getArgs());
            return methodReturnObject;
        } finally {
            long methodCallDuration = System.currentTimeMillis() - start;

            String methodCallName = getMethodCallName(joinPoint);
            String args = argsToString(joinPoint.getArgs());

            logDuration(monitor.threshold(), methodCallDuration, methodCallName, args);
            logReturnObject(methodReturnObject, methodCallName);
        }
    }

    private void logDuration(long threshold, long methodCallDuration, String methodCallName, String args) {
        if (methodCallDuration > threshold) {
            LOGGER.debug(MONITORING_OVER_THRESHOLD_LOG, methodCallName, args, methodCallDuration, methodCallDuration - threshold);
        } else {
            LOGGER.debug(MONITORING_LOG, methodCallName, args, methodCallDuration);
        }
    }

    private void logReturnObject(Object methodReturnObject, String methodCallName) {
        String returnObject = getObjectStringValue(methodReturnObject);
        LOGGER.debug(MONITORING_OUTPUT_VALUE_LOG, methodCallName, returnObject);
    }

    private String getObjectStringValue(Object methodReturnObject) {

        if (Objects.nonNull(methodReturnObject)) {
            if (methodReturnObject instanceof Optional) {
                return extractStringValueFromOptional((Optional<?>) methodReturnObject);
            }  else if (methodReturnObject instanceof Iterable) {
                return extractStringValueFromIterable((Iterable<?>) methodReturnObject);
            } else {
                return methodReturnObject.toString();
            }
        }

        return null;
    }

    private String extractStringValueFromOptional(Optional<?> methodReturnObject) {
        return methodReturnObject.isPresent() ? methodReturnObject.get().toString() : "null";
    }

    private String extractStringValueFromIterable(Iterable<?> methodReturnObject) {
        List<String> values = new ArrayList<>();
        methodReturnObject.forEach((o -> values.add(o.toString())));
        return String.join(", ", values);
    }

    private String getMethodCallName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
    }

    private String argsToString(Object[] methodParameters) {
        return Arrays.stream(methodParameters).map(Object::toString).reduce("", String::join);
    }
}
