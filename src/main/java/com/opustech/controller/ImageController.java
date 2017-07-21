package com.opustech.controller;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opustech.model.Enterprise;
import com.opustech.model.Product;
import com.opustech.model.ProductId;
import com.opustech.repository.ProductRepository;
import com.opustech.storage.StorageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(value="/{enterprise}/{product}",produces=MediaType.IMAGE_PNG_VALUE)
	public Resource getImage(@PathVariable("enterprise") Enterprise enterprise, @PathVariable("product") String productId ){		
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		Resource resource = storageService.loadAsResource(enterprise.getCnpj()+"/"+product.getCollection().getId()+"/"+product.getImage());		
		return resource;		
	}

	@GetMapping(value="/{enterprise}/{product}/b64",produces=MediaType.IMAGE_PNG_VALUE)
	public byte[] getImages(@PathVariable("enterprise")Enterprise enterprise, @PathVariable("product") String productId ){		
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		Resource resource = storageService.loadAsResource(enterprise.getCnpj()+"/"+product.getCollection().getId()+"/"+product.getImage());
		
		try {
			byte[] base64 = Base64.encodeBase64(FileUtils.readFileToByteArray(resource.getFile()));
			return base64;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
}
