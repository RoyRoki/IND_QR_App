package qr.indan.QRApp.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import java.nio.file.*;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRService {

    public byte[] CreateQRCode(String URL, int widht, int height) throws WriterException, IOException {
     QRCodeWriter qrcodewriter = new QRCodeWriter();
     BitMatrix bitMatrix = qrcodewriter.encode(URL, BarcodeFormat.QR_CODE, widht, height);

     ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
     MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002 , 0xFFFFC041);

     MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);

     return pngOutputStream.toByteArray();

    }

    public void createAndSave(String url, int height, int width, String path) throws WriterException, IOException {
        QRCodeWriter codeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = codeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        Path filepath = FileSystems.getDefault().getPath(path);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filepath);
    }

    public String urlToSiteName(String URL) {

        String site= URL;

     if(URL.startsWith("https://")) {
        site = URL.substring(8);
     } else if(URL.startsWith("http://")) {
        site = URL.substring(7);
     }

     site = site.replace(".com", "")
                .replace(".in", "")
                .replace(".net", "")
                .replace(".org", "")
                .replace("www", "")
                .replace(".co", "");
     site = site.replaceAll("[^a-zA-Z0-9]", " ");  
     return site.replace(" ", "");

    }

    public String URLFormate(String url) {
        if(!url.startsWith("http://") && !url.startsWith("https://") ) {
            return "https://"+url;
        }
        return url;
    }

    public Set<String> getAllQRs(String Directory) throws IOException {
      
        Set<String> allQR = new HashSet<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(Directory))) {
            for(Path path: stream) {
                if(!Files.isDirectory(path)) {
                    allQR.add(path.getFileName().toString());
                }
            }
        } 

        return allQR;
    }
}
