package com.lordz.lbt.event;


import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.params.UserParam;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author ：zzz
 */
@Configuration
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        //Page<User> userPage = userService.listAll(PageRequest.of(0,1));
        SpringContext.setApplicationContext((event.getApplicationContext()));
    }
}
