package com.PWD.WeekendTask.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PWD.WeekendTask.dao.ProductRepo;
import com.PWD.WeekendTask.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping
	public Iterable<Product> getAllProduct(){
		return productRepo.findAll();
	}
	
//	@PostMapping
//	public Product addProduct(@RequestBody Product product) {
//		return productRepo.save(product);
//	}
	@PostMapping("/createProduct")
	public String uploadFile(@RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
//		Product product = new ObjectMapper().readValue(userString, Product.class );
//		System.out.println("PRODUKNAME: "+ product.getProductName());
		
		String fileExtension = file.getContentType().split("/")[1];
		System.out.println(fileExtension);
		String newFileName = "PRODUK-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/products/download/").path(fileName).toUriString();
		
//		product.setImageProduct(fileDownloadUri);
//		productRepo.save(product);
		
		return fileDownloadUri;
	}
	
}
