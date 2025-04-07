/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtendsLib;

/**
 *
 * @author chez1s
 */

// Convert jpg, png -> svg

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class czConvertter {

    public static ImageIcon convert(String resourcePath, int width, int height, String color) {
        try (InputStream inputStream = czConvertter.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Không tìm thấy file: " + resourcePath);
            }

            // Đọc nội dung file SVG
            String svgContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Thay đổi màu fill trong SVG bằng màu mới
            svgContent = svgContent.replaceAll("fill=\"#[0-9a-fA-F]+\"", "fill=\"" + color + "\"");

            // Nếu SVG không có fill, thêm dòng này để thay đổi bất kỳ màu nào
            svgContent = svgContent.replaceAll("<path", "<path fill=\"" + color + "\"");

            // Convert nội dung SVG thành ảnh PNG
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(svgContent.getBytes()));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            Transcoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);
            transcoder.transcode(input, output);

            byte[] imageData = outputStream.toByteArray();
            return new ImageIcon(Toolkit.getDefaultToolkit().createImage(imageData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
