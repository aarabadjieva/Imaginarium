package project.imaginarium.service.services.impl;

import com.cloudinary.Cloudinary;
import project.imaginarium.service.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(MultipartFile img) throws IOException {
        File file = File.createTempFile("temp-file", img.getOriginalFilename());
        img.transferTo(file);
        return cloudinary.uploader().upload(file, new HashMap<String, String>())
                .get("url")
                .toString();
    }
}
