/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtendsLib;

/**
 *
 * @author chez1s
 */
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

// Dùng để gửi email

public class czEmail {

    private final String username;
    private final String password;

    public czEmail() {
        Dotenv env = Dotenv.configure().load();
        username = env.get("emailUsername");
        password = env.get("emailPassword");
        if (username == null || password == null) {
            System.out.println("Vui lòng thiết lập biến môi trường đúng cho emailUsername và emailPassword");
        }
    }

    // Hàm gửi email
    public void sendMail(int i, String email, String name) {
        if (username == null || password == null) {
            System.out.println("Lỗi: Biến môi trường chưa được thiết lập.");
            return;
        }

        // Thiết lập thông tin cấu hình SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session với thông tin xác thực
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage để gửi email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim().toLowerCase()));
            message.setSubject("G-RentX Confirmation");

            // Cấu hình nội dung email dựa trên tham số i
            String content = "<html>"
                    + "<body style='font-family: Arial, sans-serif;'>"
                    + "<div style='width: 100%; max-width: 600px; margin: auto; padding: 20px; background-color: #f8f9fa; border-radius: 8px;'>"
                    + "<h2 style='color: #333;'>Xác thực tài khoản G-RentX</h2>"
                    + "<p style='font-size: 16px;'>Chào " + name + "!</p>"
                    + "<p style='font-size: 16px;'>Mã OTP của bạn là: <strong style='color: #007bff;'>" + i
                    + "</strong></p>"
                    + "<p style='font-size: 16px;'>Vui lòng nhập mã OTP này để xác thực.</p>"
                    + "<hr>"
                    + "<p style='font-size: 14px; color: #888;'>Nếu bạn không yêu cầu tạo tài khoản tại G-RentX, vui lòng bỏ qua email này.</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(content, "text/html; charset=utf-8");

            // Gửi email
            // session.setDebug(true);
            Transport.send(message);
            System.out.println("Email đã được gửi thành công đến: " + email);
        } catch (MessagingException e) {
            System.out.println("Err : " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Err : " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new czEmail().sendMail(293473, "toptrainghieml@gmail.com", "toptrainghieml@gmail.com");
    }

}
