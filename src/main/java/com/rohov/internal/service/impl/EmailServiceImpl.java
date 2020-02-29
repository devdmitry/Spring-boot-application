package com.rohov.internal.service.impl;

import com.rohov.internal.entity.User;
import com.rohov.internal.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    String from;

    final String RESET_TEMPLATE = "/templates/email-reset-token-template.vm";
    final String VERIFICATE_TEMPLATE = "templates/email-verified-profile-template.vm";
    final String INVATION_TEMPLATE = "templates/email-invation-template.vm";

    final JavaMailSender emailSender;
    final VelocityEngine velocityEngine;

    @Override
    @Async("threadExecutor")
    public void sendResetToken(final User user) {
        Map model = new HashMap();

        model.put("name", user.getEmail());
        model.put("token", user.getResetToken());

        //emailSender.send(preparator(model , user, "Reset token", RESET_TEMPLATE ));
    }

    @Override
    @Async("threadExecutor")
    public void sendVerificationToken(User user) {
        Map model = new HashMap();

        model.put("token", user.getVerificationToken());

        //emailSender.send(preparator(model, user, "Verificate your account !", VERIFICATE_TEMPLATE));
    }

    @Override
    @Async("threadExecutor")
    public void sendInvationToken(String email, String token) {
        Map model = new HashMap();

        model.put("token", token);

        emailSender.send(preparator(model, email, "Invation mail !", INVATION_TEMPLATE));
    }

    private MimeMessagePreparator preparator(Map model, String email, String subject, String template) {
        return mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            message.setSubject(subject);
            message.setTo(email);
            message.setFrom(from);

            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    template,"UTF-8" ,model);

            message.setText(text, true);
        };
    }
}
