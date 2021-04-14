package medical.education.service;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;

@Service
public class ImageServiceImpl implements ImageService{
//  @Value("${image.upload-dir}")
//  private String imagePath;

  private final Path imageLocation = Paths.get("uploads");

  @Override
  public ResponseEntity<byte[]> getImage(String fileName,
      HttpServletRequest httpServletRequest) throws IOException {
    Resource resource = loadFileAsResource(fileName);
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(ByteStreams.toByteArray(resource.getInputStream()), headers, HttpStatus.OK);
  }

  public Resource loadFileAsResource(String fileName) {
    try {
      File f = imageLocation.toFile();
      if (!f.exists()) {
        f.mkdirs();
      }

//      imageLocation = Paths.get(imagePath);
//      System.out.println(imagePath);
      System.out.println(imageLocation);
      System.out.println(fileName);

      Path filePath = this.imageLocation.resolve(fileName+".png").normalize();
      System.out.println(filePath.toUri());
      Resource resource = new UrlResource(filePath.toUri());
      System.out.println(resource.getFilename());
      System.out.println(resource.getURL()+"  __url");
      System.out.println(resource.getURI()+"  __uri");
      if (resource.exists()) {
        System.out.println(resource.toString());
        return resource;
      } else {
        System.out.println("not found");
        throw new BaseException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      System.out.println(ex.getMessage()+"------");
      throw new BaseException("File not found " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
      throw new BaseException("IOException" + fileName);
    }
  }
}
