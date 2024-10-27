package com.scm.proj.spring.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.proj.spring.helper.AppConstant;

@Service
public class ImageServiceImpl implements imageService{

    @Autowired
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary CloudSecret){
        this.cloudinary=CloudSecret;
    }

    String filename=UUID.randomUUID().toString();

    @Override
    public String uploadImage(MultipartFile contactPicture) {
        try {
            byte[] data= new byte[contactPicture.getInputStream().available()];
            contactPicture.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename));
                return this.getURLFromPublicId(filename);
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }  
       
    }


        // public String getURLFromPublicId(String publicId) {
    //     return cloudinary
    //     .url()
    //     .transformation(
    //         new Transformation<>()
    //         .height(AppConstant.imgHeight)
    //         .width(AppConstant.imgWidth)
    //         .crop("fill")
    //     )
    //     .generate(publicId);
    // }

    @Override
    public String getURLFromPublicId(String publicId) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstant.imgHeight)
                                .height(AppConstant.imgWidth)
                                .crop("fill"))
                .generate(publicId);

    }

    
}
