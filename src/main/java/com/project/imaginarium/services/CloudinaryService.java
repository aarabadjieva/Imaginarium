package com.project.imaginarium.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String upload (MultipartFile img) throws IOException;
}
