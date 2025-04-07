package ExtendsLib;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

// Gửi ảnh lên FTP

public class czFTP {

    private final Dotenv env = Dotenv.configure().load();
    private final String SERVER = env.get("ftpHost");
    private final int PORT = 21;
    private final String USER = env.get("ftpUser");
    private final String PASS = env.get("ftpPass");

    public String[] uploadImages(BufferedImage fullHdImg, String fileName) {
        String fullHdPath = "/public_html/G-rentX/assets/gameImg/" + fileName;
        String lowResPath = "/public_html/G-rentX/assets/low_res/" + fileName;
        FTPClient ftpClient = new FTPClient();

        try {
            // Kết nối đến server FTP
            ftpClient.connect(SERVER, PORT);
            if (!ftpClient.login(USER, PASS)) {
                throw new Exception("Đăng nhập FTP thất bại! Kiểm tra USER và PASS.");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            String[] directories = { "/public_html/G-rentX/assets/gameImg", "/public_html/G-rentX/assets/low_res" };
            for (String dir : directories) {
                if (!ftpClient.changeWorkingDirectory(dir)) {
                    ftpClient.makeDirectory(dir);
                    if (!ftpClient.changeWorkingDirectory(dir)) {
                        throw new Exception("Không thể tạo hoặc truy cập thư mục: " + dir);
                    }
                }
            }

            // Resize về 360p
            BufferedImage lowResImg = new BufferedImage(640, 360, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g2d = lowResImg.createGraphics();
            g2d.drawImage(fullHdImg, 0, 0, 640, 360, null);
            g2d.dispose();

            // Chuyển cả 2 hình thành byte array
            ByteArrayOutputStream baosFullHd = new ByteArrayOutputStream();
            ByteArrayOutputStream baosLowRes = new ByteArrayOutputStream();
            ImageIO.write(fullHdImg, "jpg", baosFullHd);
            ImageIO.write(lowResImg, "jpg", baosLowRes);
            byte[] fullHdBytes = baosFullHd.toByteArray();
            byte[] lowResBytes = baosLowRes.toByteArray();

            // Upload Full HD
            ByteArrayInputStream baisFullHd = new ByteArrayInputStream(fullHdBytes);
            boolean fullHdSuccess = ftpClient.storeFile(fullHdPath, baisFullHd);
            baisFullHd.close();

            if (!fullHdSuccess) {
                throw new Exception("Upload ảnh Full HD thất bại! Đường dẫn: " + fullHdPath);
            }

            // Upload 360p
            ByteArrayInputStream baisLowRes = new ByteArrayInputStream(lowResBytes);
            boolean lowResSuccess = ftpClient.storeFile(lowResPath, baisLowRes);
            baisLowRes.close();

            if (!lowResSuccess) {
                throw new Exception("Upload ảnh 360p thất bại! Đường dẫn: " + lowResPath);
            }

            return new String[] { fullHdPath, lowResPath }; // Trả về cả 2 đường dẫn
        } catch (Exception ex) {
            ex.printStackTrace();
            return null; // Trả về null để báo lỗi
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean checkImageExists(String remotePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASS);
            ftpClient.enterLocalPassiveMode();
            return ftpClient.listFiles(remotePath).length > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
