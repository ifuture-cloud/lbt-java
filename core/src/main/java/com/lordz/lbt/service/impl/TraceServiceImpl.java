package com.lordz.lbt.service.impl;

import com.lordz.lbt.service.TraceService;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @date 19-6-18
 */
@Service
public class TraceServiceImpl implements TraceService {

    private final HttpTraceRepository httpTraceRepository;

    public TraceServiceImpl(HttpTraceRepository httpTraceRepository) {
        this.httpTraceRepository = httpTraceRepository;
    }

    @Override
    public List<HttpTrace> listHttpTraces() {
        return httpTraceRepository.findAll();
    }
}
