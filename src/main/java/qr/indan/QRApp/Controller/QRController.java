package qr.indan.QRApp.Controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import qr.indan.QRApp.Service.QRService;
import qr.indan.QRApp.model.AllQR;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;


@Controller
@RequestMapping("/QRApp")
public class QRController {

    @Autowired          
    private QRService qrService;
    @Autowired
    private AllQR allQR;


    private static final String QR_Path = "./src/main/resources/static/uploads";

    @GetMapping("/")
    
    public String staString(Model model) throws WriterException, IOException {

        allQR.setAllQRs(QR_Path);
      
        model.addAttribute("myqr", null);
        model.addAttribute("allQR", allQR.getAllQRs());

     return "index";
    }


    @PostMapping("/generateQRCode")
    public String generateQRCode(@RequestParam("url") String url, Model model) throws WriterException, IOException {
     
      url = qrService.URLFormate(url);

     byte[] image = qrService.CreateQRCode(url, 500, 500);
     String qrcode = Base64.getEncoder().encodeToString(image);

     model.addAttribute("myqr", qrcode);
     model.addAttribute("url", url);
     model.addAttribute("site", qrService.urlToSiteName(url));
     allQR.setAllQRs(QR_Path);
     model.addAttribute("allQR", allQR.getAllQRs());

     return "index";

    }

    @PostMapping("/saveQRCode")
    public String saveQRCode(@RequestParam("url") String url, Model model) {

      String site = qrService.urlToSiteName(url);
      try {
        qrService.createAndSave(url, 500, 500, QR_Path+"/"+site+".png");
        byte[] image = qrService.CreateQRCode(url, 500, 500);
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("myqr", qrcode);
        model.addAttribute("url", url);
        model.addAttribute("site", site);
        model.addAttribute("msg","QR Code Saved");
        allQR.setAllQRs(QR_Path);
        model.addAttribute("allQR", allQR.getAllQRs());

      } catch(WriterException | IOException e) {
        e.printStackTrace();
        model.addAttribute("msg","QR Code failed to save");
      }
      return "index";
    }


    
}
