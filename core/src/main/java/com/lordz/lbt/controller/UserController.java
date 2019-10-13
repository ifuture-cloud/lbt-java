package com.lordz.lbt.controller;

import com.lordz.lbt.auth.annotation.Role;
import com.lordz.lbt.model.dto.UserDTO;
import com.lordz.lbt.model.params.UserParam;
import com.lordz.lbt.model.support.CreateCheck;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.lordz.lbt.model.support.LBTConst.OFFICIAL_API_PREFIX;

/**
 * @author ：zzz
 */
@RestController("OfficialUserController")
@RequestMapping(OFFICIAL_API_PREFIX + "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Role("admin")
    public UserDTO create(@RequestBody @Valid UserParam userParam) {
        return new UserDTO().convertFrom(userService.createBy(userParam));
    }
    @PostMapping("register")
    public UserDTO register(@RequestBody @Valid UserParam userParam) {
        ValidationUtils.validate(userParam, CreateCheck.class);
        return new UserDTO().convertFrom(userService.createBy(userParam));
    }
}
