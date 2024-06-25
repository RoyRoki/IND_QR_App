package qr.indan.QRApp.model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component //for dependenci injection
public class AllQR {  
    
    Set<String> allQR = new HashSet<>();

    public void setAllQRs(String Directory) throws IOException {
        allQR.clear();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(Directory))) {
            for(Path path: stream) {
                if(!Files.isDirectory(path)) {
                    allQR.add(path.getFileName().toString().replace(".png", ""));
                }
            }
        } 
    }

    public Set<String> getAllQRs() {
        return this.allQR;
    }
}
