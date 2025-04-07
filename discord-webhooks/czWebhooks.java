/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtendsLib;

/**
 *
 * @author chez1s
 */
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class czWebhooks {

    public static void sendWebhookRename(String username, String newName) {
        try {
            // URL của trang PHP nhận webhook
            URL url = new URL("webhook_rename.php");

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu muốn gửi đến PHP (webhook data)
            String jsonData = String.format("{\"username\": \"%s\", \"webhookType\": \"rename\", \"newName\": \"%s\"}",
                    username, newName);

            // Gửi dữ liệu qua OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra phản hồi từ server (nếu cần thiết)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendWebhookDelete(String username) {
        try {
            // URL của trang PHP nhận webhook
            URL url = new URL("webhook_deleteAcc.php");

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu muốn gửi đến PHP (webhook data)
            String jsonData = String.format("{\"username\": \"%s\", \"webhookType\": \"selfDelete\"}", username);

            // Gửi dữ liệu qua OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra phản hồi từ server (nếu cần thiết)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendWebhookCreateAccount(String username, String email, String fullName) {
        try {
            // URL của trang PHP nhận webhook
            URL url = new URL("webhook_newAcc.php"); // Sửa lại URL cho phù hợp

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu muốn gửi đến PHP (webhook data)
            String jsonData = String.format(
                    "{\"username\": \"%s\", \"email\": \"%s\", \"fullname\": \"%s\", \"webhookType\": \"accountCreated\"}",
                    username, email, fullName);

            // Gửi dữ liệu qua OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra phản hồi từ server (nếu cần thiết)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
