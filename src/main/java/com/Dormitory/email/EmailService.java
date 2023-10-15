package com.Dormitory.email;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Dormitory.bill.Bill;
import com.Dormitory.contract.Contract;
import com.Dormitory.room.Room;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Email email, Student student,RoomType roomType, Room room, Sesmester sesmester,Contract contract) {
        //Basic thôi
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom("khoab1910240@gmail.com");
        // message.setTo(toEmail);
        // message.setText(body);
        // message.setSubject(subject);
        // mailSender.send(message);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        MimeMessage message = mailSender.createMimeMessage();
        String isCooked = roomType.getIsCooked() == true ? "Cho phép" : "Không cho phép";
        String isAir = roomType.getIsAirConditioned() == true ? "Có" : "Không có";
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
                    "          <td>"+student.getNumberStudent()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Tên:</td>\r\n" + //
                    "          <td>"+student.getName()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày sinh:</td>\r\n" + //
                    "          <td>"+formatter.format(student.getBirthday())+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Giới tính:</td>\r\n" + //
                    "          <td>"+student.getGender()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Lớp học:</td>\r\n" + //
                    "          <td>"+student.getClassroom()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngành:</td>\r\n" + //
                    "          <td>"+student.getMajor()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Email:</td>\r\n" + //
                    "          <td>"+student.getEmail()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Số điện thoại:</td>\r\n" + //
                    "          <td>"+student.getPhone()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "      </table>\r\n" + //
                    
                    "      <h4 style=\"text-align: center\">Thông tin phòng ở</h4>\r\n" + //
                    "      <table style=\"background-color: white\">\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Loại phòng:</td>\r\n" + //
                    "          <td>"+roomType.getName()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Số phòng:</td>\r\n" + //
                    "          <td>"+room.getNumberRoom()+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Giá:</td>\r\n" + //
                    "          <td>"+contract.getTotalPrice()+" vnđ</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Cho phép nấu ăn:</td>\r\n" + //
                    "          <td>"+isCooked+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Có máy lạnh:</td>\r\n" + //
                    "          <td>"+isAir+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày bắt đầu ở:</td>\r\n" + //
                    "          <td>"+formatter.format(sesmester.getStartDate())+"</td>\r\n" + //
                    "        </tr>\r\n" + //
                    "        <tr>\r\n" + //
                    "          <td class=\"title\">Ngày kết thúc:</td>\r\n" + //
                    "          <td>"+formatter.format(sesmester.getEndDate())+"</td>\r\n" + //
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
                    "                  >Thời gian ở KTX học kỳ "+sesmester.getSesmester()+", năm học "+sesmester.getSchoolYear()+"</strong\r\n" + //
                    "                >: Tính từ ngày "+formatter.format(sesmester.getStartDate())+" đến ngày "+formatter.format(sesmester.getEndDate())+"&nbsp;<em\r\n" + //
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
                    "                  >Nộp phí ở KTX học kỳ "+sesmester.getSesmester()+", năm học "+sesmester.getSchoolYear()+". SV nộp phí từ ngày\r\n" + //
                    "                  nhận thông báo đến hết ngày "+formatter.format(sesmester.getRegistrationEndDate())+".&nbsp;</strong\r\n" + //
                    "                >SV nộp phí cho cả học kỳ<strong>:<br /></strong\r\n" + //
                    "                ><strong\r\n" + //
                    "                  >Nộp phí bằng hình thức thanh toán trực tuyến trên hệ thống\r\n" + //
                    "                  NCB&nbsp;</strong\r\n" + //
                    "                ></span\r\n" + //
                    "              ><br /><br />\r\n" + //
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
                    "            <span style=\"font-size: 10pt\"\r\n" + //
                    "              ><strong\r\n" + //
                    "                ><em\r\n" + //
                    "                  >&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<u>Lưu ý:</u></em\r\n" + //
                    "                ></strong\r\n" + //
                    "              >&nbsp;Nếu không đóng phí đúng thời hạn, sẽ bị thêm vào <em\r\n" + //
                    "                ><b>DANH SÁCH ĐEN</b></em\r\n" + //
                    "              >&nbsp;, kết quả đăng ký phòng sẽ bị hủy và không thể đăng ký ở các kỳ kế tiếp.<strong\r\n" + //
                    "                >&nbsp;</strong\r\n" + //
                    "              >&nbsp;\r\n" + //
                    "              &nbsp;<em\r\n" + //
                    "                ></em\r\n" + //
                    "              >&nbsp;</span\r\n" + //
                    "            ><br />\r\n" + //fn
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
                    "                  >SV cũ không đăng ký ở tiếp trong học kỳ 1, năm học\r\n" + //
                    "                  2023-2024:&nbsp;</strong\r\n" + //
                    "                >SV phải làm vệ sinh sạch sẽ giường ở, phòng ở trước khi hoàn\r\n" + //
                    "                thành thủ tục trả chỗ. SV tự làm hoặc thuê người dọn vệ sinh, tự\r\n" + //
                    "                thanh toán chi phí và hoàn tất thủ tục trả chỗ vào ngày\r\n" + //
                    "                "+formatter.format(sesmester.getStartDate())+".</span\r\n" + //
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
    public void notificationBills(Email email,Student student, String roomType, Integer numberRoom,Integer electricityConsumed,Integer waterConsumed,Bill bill){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
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
                    "        <h3 style=\"font-weight: bold\">THÔNG BÁO ĐIỆN NƯỚC THÁNG "+bill.getCreatedDate().getMonthValue()+"</h3>\r\n" + //
                    "      </div>\r\n" + //
                    "      <div style=\"background-color: white\">\r\n" + //
                    "        <table>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Mã số sinh viên:</td>\r\n" + //
                    "            <td>"+student.getNumberStudent()+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Họ tên:</td>\r\n" + //
                    "            <td>"+student.getName()+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Lớp:</td>\r\n" + //
                    "            <td>"+student.getClassroom()+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Ngành:</td>\r\n" + //
                    "            <td>"+student.getMajor()+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Loại phòng/ dãy:</td>\r\n" + //
                    "            <td>"+roomType+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <tr>\r\n" + //
                    "            <td class=\"title\">Số phòng:</td>\r\n" + //
                    "            <td>"+numberRoom+"</td>\r\n" + //
                    "          </tr>\r\n" + //
                    "          <!-- Thêm các dòng thông tin sinh viên ở đây -->\r\n" + //
                    "        </table>\r\n" + //
                    "        <div style=\"padding-left: 2%; padding-right: 2%; text-align: justify\">\r\n" + //
                    "          <div>\r\n" + //
                    "            <span style=\"font-weight: bold\">Tiền điện</span> = Lượng điện năng\r\n" + //
                    "            tiêu thụ x đơn giá điện/kWh = "+electricityConsumed*1728+"đ\r\n" + //
                    "          </div>\r\n" + //
                    "          <div>\r\n" + //
                    "            <span style=\"font-weight: bold\">Tiền nước</span> = Lượng nước tiêu\r\n" + //
                    "            thụ x đơn giá nước/khối = "+waterConsumed*5973+"đ\r\n" + //
                    "          </div>\r\n" + //
                    "          <div>\r\n" + //
                    "            <span style=\"font-weight: bold\">Tổng tiền</span> = Tiền điện + Tiền\r\n" + //
                    "            nước = "+bill.getPrice()+"đ\r\n" + //
                    "          </div>\r\n" + //
                    "          <p>\r\n" + //
                    "            <span style=\"font-weight: bold\">--->Lưu ý:</span> Vui lòng liên hệ\r\n" + //
                    "            các sinh viên cùng phòng để thực hiện thanh toán tiền điện nước trên\r\n" + //
                    "            hệ thống, hạn chót là ngày\r\n" + //
                    "            <span style=\"color: red; font-weight: bold\">11/02/2021</span> . Nếu\r\n" + //
                    "            không đóng đúng hẹn sẽ bị cắt điện nước.\r\n" + //
                    "          </p>\r\n" + //
                    "        </div>\r\n" + //
                    "      </div>\r\n" + //
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
                    "            <!-- Thêm danh sách các mục thông tin ở đây -->\r\n" + //
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
                    "            <!-- Thêm nội dung lưu ý ở đây -->\r\n" + //
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
                    "            <!-- Thêm các mục thông tin khác ở đây -->\r\n" + //
                    "          </ol>\r\n" + //
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

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
