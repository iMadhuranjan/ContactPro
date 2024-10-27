package com.scm.proj.spring.services;

import org.springframework.web.multipart.MultipartFile;


public interface imageService {
    public String uploadImage(MultipartFile contactPicture);

    public String getURLFromPublicId(String publicId);

}

