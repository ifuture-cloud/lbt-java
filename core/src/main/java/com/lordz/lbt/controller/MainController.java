package com.lordz.lbt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;

/**
 * @author ：zzz
 */
@Controller
@RequestMapping("")
public class MainController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String index () {
        return "Open Blog API Gateway";
    }

    @GetMapping(USER_API_PREFIX)
    @ResponseBody
    public String uapi () {
        return "Open Blog UAPI Gateway";
    }
}
