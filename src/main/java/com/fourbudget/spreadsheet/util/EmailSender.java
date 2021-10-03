package com.fourbudget.spreadsheet.util;

import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Project project) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail);
            helper.setTo(project.getEmail());
            helper.setSubject( "Orçamento" );
            helper.setText(getMessage(project), true);
            mailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMessage(Project project) {
        String message = "<h1>Olá " + project.getEmail() + ",</h1>" +
                "<h2>Aqui está o seu orçamento: </h2>";

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        for (Item item : project.getItemsList()) {
            message += "<p><b>" + item.getSale().getName() + " x" + item.getQuantity() + "</b> -- " + nf.format(item.getSale().getPrice()) + " -- " + nf.format(item.getItemPrice()) + "</p>";
        }

        message += "<h4> Desconto: " + nf.format(project.getDiscount()) + "</h4> " +
                "<h3>Total: " + nf.format(project.getTotalPrice()) + "</h3>";

        return message;
    }
}
