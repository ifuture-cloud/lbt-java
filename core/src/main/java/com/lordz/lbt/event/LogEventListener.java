package com.lordz.lbt.event;

import com.lordz.lbt.model.entity.Log;
import com.lordz.lbt.model.params.LogParam;
import com.lordz.lbt.service.LogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Log event listener.
 */
@Component
public class LogEventListener {

    private final LogService logService;

    public LogEventListener(LogService logService) {
        this.logService = logService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(LogParam logParam) {
        Log logToCreate = logParam.convertTo();
        logService.create(logToCreate);
    }
}
