package com.jdt.ecommerce.email;

import com.jdt.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jdt.ecommerce.email.EmailTemplates.ORDER_SUCCEED;
import static com.jdt.ecommerce.email.EmailTemplates.PAYMENT_SUCCEED;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSucceed(String to, String toName, BigDecimal amount, String reference) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        helper.setFrom("jorvandjoumpro@gmail.com");
        final String templateName = PAYMENT_SUCCEED.getTemplate();
        Map<String,Object> variables = new HashMap<>();
        variables.put("amount", amount);
        variables.put("reference", reference);
        variables.put("toName", toName);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(PAYMENT_SUCCEED.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            helper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Sent payment successfully for '{}' to '{}' with template {}", to, toName, templateName);
        }catch (MessagingException e) {
            log.warn("Warning: Cannot send Email to {}", to);
        }
    }
    @Async
    public void orderConfirmationEmail(String to, String toName, BigDecimal totalAmount, String reference, List<Product> products) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        helper.setFrom("jorvandjoumpro@gmail.com");
        final String templateName = ORDER_SUCCEED.getTemplate();
        Map<String,Object> variables = new HashMap<>();
        variables.put("totalAmount", totalAmount);
        variables.put("orderReference", reference);
        variables.put("toName", toName);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(ORDER_SUCCEED.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            helper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Sent order successfully for '{}' to '{}' with template {}", to, toName, templateName);
        }catch (MessagingException e) {
            log.warn("Warning: Cannot send Order confirmation Email to {}", to);
        }
    }

}
