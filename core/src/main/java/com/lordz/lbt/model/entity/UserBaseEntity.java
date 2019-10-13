package com.lordz.lbt.model.entity;

import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.SpringContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Optional;

/**
 * @author ：zzz
 * @date ：Created in 7/17/19 8:26 PM
 */
@MappedSuperclass
@Data
@ToString
@EqualsAndHashCode
public class UserBaseEntity extends BaseEntity{

    @Column(name = "create_user", columnDefinition = "BIGINT not null")
    private Long createUser;


    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        try{
            UserService userService = SpringContext.getBean(UserService.class);
            Optional<User> user = userService.getCurrentUser();
            if (user.isPresent()) {
                createUser = user.get().getId();
            }else {
                throw new ForbiddenException("Login?");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ForbiddenException("Login?");
        }

    }

}
