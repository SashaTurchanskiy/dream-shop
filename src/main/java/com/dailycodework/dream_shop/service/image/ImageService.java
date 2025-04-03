package com.dailycodework.dream_shop.service.image;

import com.dailycodework.dream_shop.dto.ImageDto;
import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Image;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.ImageRepo;
import com.dailycodework.dream_shop.service.product.IProductService;
import com.dailycodework.dream_shop.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{

    private final ImageRepo imageRepo;
    private final IProductService iProductService;
    private final ProductService productService;

    public ImageService(ImageRepo imageRepo, IProductService iProductService, ProductService productService) {
        this.imageRepo = imageRepo;
        this.iProductService = iProductService;
        this.productService = productService;
    }

    @Override
    public Image getImageById(Long id) throws ResourceNotFoundException {
        return imageRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Image not found" + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo :: delete,
                () -> new ResourceNotFoundException("Image not found"));

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) throws ResourceNotFoundException {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileName(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/" + image.getId();
                String downloadUri = buildDownloadUrl + image.getId();
                image.setDownloadUri(downloadUri);
                Image savedImage = imageRepo.save(image);

                savedImage.setDownloadUri(buildDownloadUrl + savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUri(savedImage.getDownloadUri());
                savedImageDto.add(imageDto);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) throws ResourceNotFoundException {
        Image image = getImageById(imageId);
        try {
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }


    }
}
