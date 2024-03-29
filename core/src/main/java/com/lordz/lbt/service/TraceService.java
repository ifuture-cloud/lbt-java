package com.lordz.lbt.service;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Trace service interface.
 *
 *
 * @date 19-6-18
 */
public interface TraceService {

    /**
     * Gets all http traces.
     *
     * @return
     */
    @NonNull
    List<HttpTrace> listHttpTraces();

}
