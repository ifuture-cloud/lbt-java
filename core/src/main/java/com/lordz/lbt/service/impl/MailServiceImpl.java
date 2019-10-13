package com.lordz.lbt.service.impl;

import com.lordz.lbt.service.MailService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ：zzz
 * @date ：Created in 7/18/19 11:37 AM
 */
@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendMail(String to, String subject, String content) {

    }

    @Override
    public void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName) {

    }

    @Override
    public void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachFilename) {

    }
}
