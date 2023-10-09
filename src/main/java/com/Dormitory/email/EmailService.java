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
            String htmlContent = "<!DOCTYPE html>\r\n" + //
                    "<html>\r\n" + //
                    "  <head>\r\n" + //
                    "    <style>\r\n" + //
                    "      /* CSS cho phần nội dung email */\r\n" + //
                    "      body {\r\n" + //
                    "        font-family: Arial, sans-serif;\r\n" + //
                    "      }\r\n" + //
                    "      .email-container {\r\n" + //
                    "        max-width: 800px;\r\n" + //
                    "        margin: 0 auto;\r\n" + //
                    "        padding: 20px;\r\n" + //
                    "        background-color: #f0f0f0;\r\n" + //
                    "      }\r\n" + //
                    "      .header {\r\n" + //
                    "        background-color: #007bff;\r\n" + //
                    "        color: #ffffff;\r\n" + //
                    "        text-align: center;\r\n" + //
                    "        padding: 10px;\r\n" + //
                    "      }\r\n" + //
                    "      table {\r\n" + //
                    "        width: 100%;\r\n" + //
                    "        margin-bottom: 20px;\r\n" + //
                    "        border: 1px solid #ddd;\r\n" + //
                    "        border-collapse: collapse;\r\n" + //
                    "        border: none;\r\n" + //
                    "      }\r\n" + //
                    "      th,\r\n" + //
                    "      td {\r\n" + //
                    "        padding: 10px;\r\n" + //
                    "      }\r\n" + //
                    "      .footer {\r\n" + //
                    "        background-color: #007bff;\r\n" + //
                    "        color: #ffffff;\r\n" + //
                    "        text-align: center;\r\n" + //
                    "        padding: 10px;\r\n" + //
                    "      }\r\n" + //
                    "      .title {\r\n" + //
                    "        font-weight: bold;\r\n" + //
                    "      }\r\n" + //
                    "      * {\r\n" + //
                    "        margin: 0;\r\n" + //
                    "        padding: 0;\r\n" + //
                    "      }\r\n" + //
                    "    </style>\r\n" + //
                    "  </head>\r\n" + //
                    "  <body>\r\n" + //
                    "    <div class=\"email-container\">\r\n" + //
                    "      <div class=\"header\">\r\n" + //
                    "        <h3 style=\"font-weight: bold\">\r\n" + //
                    "          CHÚC MỪNG BẠN ĐĂNG KÝ PHÒNG THÀNH CÔNG\r\n" + //
                    "        </h3>\r\n" + //
                    "      </div>\r\n" + //
                    "      <table style=\"background-color: white\">\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Mã số sinh viên:</td>\r\n" + //
                    "          <td>[Mã số sinh viên của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Tên:</td>\r\n" + //
                    "          <td>[Tên của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày sinh:</td>\r\n" + //
                    "          <td>[Ngày sinh của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Giới tính:</td>\r\n" + //
                    "          <td>[Giới tính của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Lớp học:</td>\r\n" + //
                    "          <td>[Lớp học của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngành:</td>\r\n" + //
                    "          <td>[Ngành của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Email:</td>\r\n" + //
                    "          <td>[Địa chỉ email của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Số điện thoại:</td>\r\n" + //
                    "          <td>[Số điện thoại của bạn]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "      </table>\r\n" + //
                    
                    "      <h4 style=\"text-align: center\">Thông tin phòng ở</h4>\r\n" + //
                    "      <table style=\"background-color: white\">\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Loại phòng:</td>\r\n" + //
                    "          <td>[Loại phòng]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Số phòng:</td>\r\n" + //
                    "          <td>[Số phòng]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Giá:</td>\r\n" + //
                    "          <td>[Giá]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Cho phép nấu ăn:</td>\r\n" + //
                    "          <td>[Cho phép nấu ăn hay không]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Có máy lạnh:</td>\r\n" + //
                    "          <td>[Có máy lạnh hay không]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày bắt đầu ở:</td>\r\n" + //
                    "          <td>[Ngày bắt đầu thuê]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày kết thúc:</td>\r\n" + //
                    "          <td>[Ngày kết thúc thuê]</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "      </table>\r\n" + //
                    "      <div>\r\n" + //
                    "        <div>\r\n" + //
                    "          <ol\r\n" + //
                    "            style=\"\r\n" + //
                    "              font-size: 13px;\r\n" + //
                    "              color: rgb(17, 20, 23);\r\n" + //
                    "              margin: 1em 0px 1em 11px;\r\n" + //
                    "              padding: 0px;\r\n" + //
                    "              list-style-position: inside;\r\n" + //
                    "              font-family: Arial, 'Arial Unicode MS', Helvetica, sans-serif;\r\n" + //
                    "              text-align: justify;\r\n" + //
                    "            \"\r\n" + //
                    "          >\r\n" + //
                    "            <li\r\n" + //
                    "              style=\"\r\n" + //
                    "                margin: 3px 0px 0px;\r\n" + //
                    "                padding: 0px;\r\n" + //
                    "                line-height: 19.5px;\r\n" + //
                    "                overflow: visible;\r\n" + //
                    "              \"\r\n" + //
                    "            >\r\n" + //
                    "              <span style=\"font-size: 10pt\"\r\n" + //
                    "                ><strong\r\n" + //
                    "                  >Thời gian ở KTX học kỳ [học kỳ], năm học [năm học]</strong\r\n" + //
                    "                >: Tính từ ngày [thời gian ở] đến ngày [kết thúc]&nbsp;<em\r\n" + //
                    "                ></em>.</span\r\n" + //
                    "              >\r\n" + //
                    "            </li>\r\n" + //
                    "            <li\r\n" + //
                    "              style=\"\r\n" + //
                    "                margin: 3px 0px 0px;\r\n" + //
                    "                padding: 0px;\r\n" + //
                    "                line-height: 19.5px;\r\n" + //
                    "                overflow: visible;\r\n" + //
                    "              \"\r\n" + //
                    "            >\r\n" + //
                    "              <span style=\"font-size: 10pt\"\r\n" + //
                    "                ><strong>SV thực hiện đăng ký ở KTX&nbsp;</strong>trực tuyến\r\n" + //
                    "                qua<strong\r\n" + //
                    "                  >&nbsp;Hệ thống Quản lý KTX bằng tài khoản SV từ ngày ra thông\r\n" + //
                    "                  báo.</strong\r\n" + //
                    "                ></span\r\n" + //
                    "              >\r\n" + //
                    "            </li>\r\n" + //
                    "          </ol>\r\n" + //
                    "          <ol\r\n" + //
                    "            start=\"3\"\r\n" + //
                    "            style=\"\r\n" + //
                    "              font-size: 13px;\r\n" + //
                    "              color: rgb(17, 20, 23);\r\n" + //
                    "              margin: 1em 0px 1em 11px;\r\n" + //
                    "              padding: 0px;\r\n" + //
                    "              list-style-position: inside;\r\n" + //
                    "              font-family: Arial, 'Arial Unicode MS', Helvetica, sans-serif;\r\n" + //
                    "              text-align: justify;\r\n" + //
                    "            \"\r\n" + //
                    "          >\r\n" + //
                    "            <li\r\n" + //
                    "              style=\"\r\n" + //
                    "                margin: 3px 0px 0px;\r\n" + //
                    "                padding: 0px;\r\n" + //
                    "                line-height: 19.5px;\r\n" + //
                    "                overflow: visible;\r\n" + //
                    "              \"\r\n" + //
                    "            >\r\n" + //
                    "              <span style=\"font-size: 10pt\"\r\n" + //
                    "                ><strong\r\n" + //
                    "                  >Nộp phí ở KTX học kỳ 1, năm học 2023-2024. SV nộp phí từ ngày\r\n" + //
                    "                  25/7 đến ngày 31/7/2023.&nbsp;</strong\r\n" + //
                    "                >SV nộp phí cho cả học kỳ<strong>:<br /></strong\r\n" + //
                    "                ><strong>&nbsp; &nbsp;-&nbsp;</strong\r\n" + //
                    "                ><strong\r\n" + //
                    "                  >Nộp phí bằng hình thức chuyển khoản qua Ngân hàng\r\n" + //
                    "                  HDBank&nbsp;</strong\r\n" + //
                    "                >như sau:</span\r\n" + //
                    "              ><br /><br /><span style=\"font-size: 10pt\"\r\n" + //
                    "                >&nbsp; &nbsp; &nbsp; &nbsp; + Nội dung ghi cú\r\n" + //
                    "                pháp:&nbsp;<strong\r\n" + //
                    "                  ><em>Mã số SV – Tên SV – Số điện thoại.<br /></em></strong\r\n" + //
                    "                >&nbsp; &nbsp; &nbsp; &nbsp; + Số tài khoản:&nbsp;<strong\r\n" + //
                    "                  >0077 0407 0013 608<br /></strong\r\n" + //
                    "                >&nbsp; &nbsp; &nbsp; &nbsp; + Ngân hàng Phát triển TP. HCM -\r\n" + //
                    "                HDBank</span\r\n" + //
                    "              ><br /><span style=\"font-size: 10pt\"\r\n" + //
                    "                >&nbsp; &nbsp; &nbsp; &nbsp; + Tên người thụ hưởng&nbsp;<em\r\n" + //
                    "                  >(nội dung tương tự)</em\r\n" + //
                    "                >: Trường Đại học Cần Thơ.</span\r\n" + //
                    "              >\r\n" + //
                    "            </li>\r\n" + //
                    "          </ol>\r\n" + //
                    "          <p\r\n" + //
                    "            style=\"\r\n" + //
                    "              font-size: 13px;\r\n" + //
                    "              color: rgb(17, 20, 23);\r\n" + //
                    "              margin: 13px 0px;\r\n" + //
                    "              padding: 0px;\r\n" + //
                    "              font-family: Arial, 'Arial Unicode MS', Helvetica, sans-serif;\r\n" + //
                    "              text-align: justify;\r\n" + //
                    "            \"\r\n" + //
                    "          >\r\n" + //
                    "            <span style=\"font-size: 10pt\"\r\n" + //
                    "              ><strong\r\n" + //
                    "                ><em\r\n" + //
                    "                  >&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<u>Lưu ý:</u></em\r\n" + //
                    "                ></strong\r\n" + //
                    "              >&nbsp;Kiểm tra chính xác cú pháp&nbsp;<em\r\n" + //
                    "                >(<strong>mã số SV, số điện thoại</strong>)</em\r\n" + //
                    "              >&nbsp;trước khi thực hiện thao tác gửi. Nếu<strong\r\n" + //
                    "                >&nbsp;sau 2 ngày làm việc, hệ thống chưa gạch nợ</strong\r\n" + //
                    "              >&nbsp;thì chụp biên lai đã chuyển khoản thành công gửi về phòng\r\n" + //
                    "              Tài chính qua địa chỉ hộp thư điện tử:&nbsp;<em\r\n" + //
                    "                ><span\r\n" + //
                    "                  id=\"m_2341066331444042184m_-3054875867105667879m_-3483802907812393388m_343678527146922972gmail-cloak47df7c3705ff065c9cb135db8527b727\"\r\n" + //
                    "                  ><a\r\n" + //
                    "                    href=\"mailto:ptc@ctu.edu.vn\"\r\n" + //
                    "                    style=\"\r\n" + //
                    "                      color: rgb(18, 105, 181);\r\n" + //
                    "                      margin: 0px;\r\n" + //
                    "                      padding: 0px;\r\n" + //
                    "                      line-height: 20px;\r\n" + //
                    "                      text-decoration-line: none;\r\n" + //
                    "                    \"\r\n" + //
                    "                    target=\"_blank\"\r\n" + //
                    "                    >ptc@ctu.edu.vn</a\r\n" + //
                    "                  ></span\r\n" + //
                    "                ></em\r\n" + //
                    "              >&nbsp;<em>(để được gạch nợ)</em>.</span\r\n" + //
                    "            ><br /><span style=\"font-size: 10pt\"\r\n" + //
                    "              ><strong>&nbsp; &nbsp; &nbsp;- Nộp trực tiếp:&nbsp;</strong>SV có\r\n" + //
                    "              thể nộp tại các điểm giao dịch của Ngân hàng HD Bank Cần\r\n" + //
                    "              Thơ.</span\r\n" + //
                    "            >\r\n" + //
                    "          </p>\r\n" + //
                    "          <ol\r\n" + //
                    "            start=\"4\"\r\n" + //
                    "            style=\"\r\n" + //
                    "              font-size: 13px;\r\n" + //
                    "              color: rgb(17, 20, 23);\r\n" + //
                    "              margin: 1em 0px 1em 11px;\r\n" + //
                    "              padding: 0px;\r\n" + //
                    "              list-style-position: inside;\r\n" + //
                    "              font-family: Arial, 'Arial Unicode MS', Helvetica, sans-serif;\r\n" + //
                    "              text-align: justify;\r\n" + //
                    "            \"\r\n" + //
                    "          >\r\n" + //
                    "            <li\r\n" + //
                    "              style=\"\r\n" + //
                    "                margin: 3px 0px 0px;\r\n" + //
                    "                padding: 0px;\r\n" + //
                    "                line-height: 19.5px;\r\n" + //
                    "                overflow: visible;\r\n" + //
                    "              \"\r\n" + //
                    "            >\r\n" + //
                    "              <span style=\"font-size: 10pt\"\r\n" + //
                    "                ><strong\r\n" + //
                    "                  >SV không đăng ký ở tiếp trong học kỳ 1, năm học\r\n" + //
                    "                  2023-2024:&nbsp;</strong\r\n" + //
                    "                >SV phải làm vệ sinh sạch sẽ giường ở, phòng ở trước khi hoàn\r\n" + //
                    "                thành thủ tục trả chỗ. SV tự làm hoặc thuê người dọn vệ sinh, tự\r\n" + //
                    "                thanh toán chi phí và hoàn tất thủ tục trả chỗ vào ngày\r\n" + //
                    "                01/8/2023.</span\r\n" + //
                    "              >\r\n" + //
                    "            </li>\r\n" + //
                    "          </ol>\r\n" + //
                    "\r\n" + //
                    "          <div style=\"text-align: justify\">\r\n" + //
                    "            <font\r\n" + //
                    "              face=\"Arial, Arial Unicode MS, Helvetica, sans-serif\"\r\n" + //
                    "              color=\"#ff0000\"\r\n" + //
                    "              ><span style=\"font-size: 13.3333px\"\r\n" + //
                    "                ><b\r\n" + //
                    "                  >SV không ở tiếp có thể báo ngày trả chỗ vào email:&nbsp;<i\r\n" + //
                    "                    ><a href=\"mailto:lvut@ctu.edu.vn\" target=\"_blank\"\r\n" + //
                    "                      ><span class=\"il\">lvut</span>@ctu.edu.vn</a\r\n" + //
                    "                    ></i\r\n" + //
                    "                  ></b\r\n" + //
                    "                ></span\r\n" + //
                    "              ></font\r\n" + //
                    "            >\r\n" + //
                    "          </div>\r\n" + //
                    "          <div style=\"text-align: justify\">\r\n" + //
                    "            <font\r\n" + //
                    "              color=\"#111417\"\r\n" + //
                    "              face=\"Arial, Arial Unicode MS, Helvetica, sans-serif\"\r\n" + //
                    "              ><span style=\"font-size: 13.3333px\"\r\n" + //
                    "                >Xin lỗi nếu email gửi nhầm cho SV không quan tâm nội dung\r\n" + //
                    "                này.</span\r\n" + //
                    "              ></font\r\n" + //
                    "            >\r\n" + //
                    "          </div>\r\n" + //
                    "          <div style=\"text-align: justify\">\r\n" + //
                    "            <font\r\n" + //
                    "              color=\"#111417\"\r\n" + //
                    "              face=\"Arial, Arial Unicode MS, Helvetica, sans-serif\"\r\n" + //
                    "              ><span style=\"font-size: 13.3333px\">Trân trọng./.</span></font\r\n" + //
                    "            >\r\n" + //
                    "          </div>\r\n" + //
                    "        </div>\r\n" + //
                    "      </div>\r\n" + //
                    "      <div class=\"footer\">\r\n" + //
                    "        <p>Ký túc xá Đại học Cần Thơ</p>\r\n" + //
                    "        <p>Trung tâm phục vụ Sinh viên - Phòng Cộng tác sinh viên</p>\r\n" + //
                    "        <p>\r\n" + //
                    "          Điện thoại Văn phòng: 0292.3872275 - Điện thoại di động: 0975 185 994\r\n" + //
                    "          (Zalo)\r\n" + //
                    "        </p>\r\n" + //
                    "      </div>\r\n" + //
                    "    </div>\r\n" + //
                    "  </body>\r\n" + //
                    "</html>\r\n" + //
                    "";
            // htmlContent += "<div ><div style='color: #0000ff'>Chào em,<span style='font-weight: bold'>"+studentName+"</span></div>"
            // + "<div>"+email.getBody()+"</div>"
            // + "<br /><div>Đây là mail tự động, vui lòng không phản hồi qua email này. Hi vọng em có trải nghiệm tuyệt vời tại nơi đây.</div>"
            // + "<div>Thân,</div>"
            // + "</div>"
            // + "<div>"
            // + "<br />"
            // + "---<br />"
            // + "<div style='color: rgb(255, 0, 0); font-weight: bold'>"
            // + "Ký túc xá Đại học Cần Thơ"
            // + "</div>"
            // + "<div style='font-weight: bold; color: rgb(255, 0, 255)'>"
            // + "Trung tâm phục vụ Sinh viên - Phòng Cộng tác sinh viên"
            // + "</div>"
            // + "<div>ĐT Văn phòng: 0292.3872275 - DĐ: 0975 185 994 (Zalo)</div>"
            // + "</div>";
            // htmlContent += "</body></html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
