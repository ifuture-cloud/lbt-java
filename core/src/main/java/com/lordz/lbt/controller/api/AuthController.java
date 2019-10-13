package com.lordz.lbt.controller.api;

import com.lordz.lbt.auth.Authorized;
import com.lordz.lbt.model.params.LoginParam;
import com.lordz.lbt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;

/**
 * @author ：zzz
 */
@RestController
@RequestMapping(USER_API_PREFIX + "/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "token",method = {RequestMethod.POST})
    public Authorized token(@RequestBody  LoginParam loginParam){
        return adminService.authenticate(loginParam);
    }
}
