package com.Dormitory.email;

import java.time.LocalDate;

import javax.swing.Spring;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Email email, String studentName) {
        //Basic thôi
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom("khoab1910240@gmail.com");
        // message.setTo(toEmail);
        // message.setText(body);
        // message.setSubject(subject);
        // mailSender.send(message);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("Dormitory@gmail.com");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());

            // Tạo nội dung HTML với các thẻ và CSS tùy chỉnh
            String htmlContent = "<html><body>";
            htmlContent += "<div ><div style='color: #0000ff'>Chào em,<span style='font-weight: bold'>"+studentName+"</span></div>"
            + "<div>"+email.getBody()+"</div>"
            + "<br /><div>Hi vọng em có trải nghiệm tuyệt vời tại nơi đây</div>"
            + "<div>Thân,</div>"
            + "</div>"
            + "<div>"
            + "<br />"
            + "---<br />"
            + "<div style='color: rgb(255, 0, 0); font-weight: bold'>"
            + "Ký túc xá Đại học Cần Thơ"
            + "</div>"
            + "<div style='font-weight: bold; color: rgb(255, 0, 255)'>"
            + "Trung tâm phục vụ Sinh viên - Phòng Cộng tác sinh viên"
            + "</div>"
            + "<div>ĐT Văn phòng: 0292.3872275 - DĐ: 0975 185 994 (Zalo)</div>"
            + "</div>";
            htmlContent += "</body></html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
