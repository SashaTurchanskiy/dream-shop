package com.dailycodework.dream_shop.service.image;

import com.dailycodework.dream_shop.dto.ImageDto;
import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id) throws ResourceNotFoundException;
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId) throws ProductNotFoundException, ResourceNotFoundException;
    void updateImage(MultipartFile file, Long imageId) throws ResourceNotFoundException;

    

}
