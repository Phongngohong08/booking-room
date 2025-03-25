package com.phongngohong08.bookingroom.services;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    String saveImageToS3(MultipartFile photo);
}
