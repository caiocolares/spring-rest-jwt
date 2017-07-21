package com.opustech.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.opustech.model.Enterprise;
import com.opustech.model.Feature;
import com.opustech.model.Flow;
import com.opustech.model.Product;
import com.opustech.model.ProductId;
import com.opustech.model.Sector;
import com.opustech.repository.FeatureRepository;
import com.opustech.repository.FlowRepository;
import com.opustech.repository.ProductRepository;
import com.opustech.service.AuthorizationService;
import com.opustech.storage.StorageService;

@CrossOrigin(origins="*")
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private ProductRepository productRepository;
		
	@Autowired
	private FeatureRepository  featureRepository;
	
	@Autowired
	private FlowRepository flowRepository;
	
	@Autowired
	private AuthorizationService authorizarionService;
	
		
	@Transactional(readOnly=false)
	@PostMapping("/{product}/sector")
	public ResponseEntity<?> addSector(@PathVariable("product") String productId, @RequestBody Sector sector){
		Enterprise enterprise = authorizarionService.getEnterprise();
		
		ProductId id = new ProductId(productId, enterprise.getId());
		
		Product product = productRepository.findOne(id);
		
		//System.out.println( product.getFlow());
		
		Flow flow = new Flow();
		flow.setPosition(0);
		
		Date start = new Date();
		
		if(product.getFlow() != null &&  !product.getFlow().isEmpty()){
			int size = product.getFlow().size() ;
			start = product.getFlow().get(size-1).getExpectedEnd();
			flow.setPosition(size);
		}
		
		flow.setSector(sector, start);
		flow.setProduct(product);
		
		flowRepository.save(flow);
		
		return ResponseEntity.ok("Fluxo adicionado com sucesso");
	}
		
	
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Product product){
		
		Enterprise enterprise = authorizarionService.getEnterprise();
		
		product.getId().setEnterpriseId(enterprise.getId());
		
		Product saved = productRepository.save(product);
		
		return ResponseEntity.created(URI.create("/product/"+saved.getId().getProductId())).build();
	}
	
	@Transactional
	@PostMapping(path="/{product}/image/add",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addImage(@PathVariable("product") String productId ,
									  @RequestParam("file") MultipartFile file){
					
		Enterprise enterprise = authorizarionService.getEnterprise();
		
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		product.getImages().add(file.getOriginalFilename());
		storageService.store(file,enterprise.getCnpj()+"/"+product.getCollection().getId());		
		
		return ResponseEntity.ok().build();
				
	}

	@Transactional
	@PostMapping(path="/{product}/image",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> setImage(@PathVariable("product") String productId ,
									  @RequestParam("file") MultipartFile file){
		
		Enterprise enterprise = authorizarionService.getEnterprise();		
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		product.setImage(file.getOriginalFilename());
		storageService.store(file,enterprise.getCnpj()+"/"+product.getCollection().getId());		

		return ResponseEntity.ok().build();		
	}
	
	

	
	@Transactional
	@PostMapping(path="/{product}/addfeature")
	public @ResponseBody Feature addFeature(@PathVariable("product") String productId,
											String specification, String values){
		
		//System.out.println(result.getAllErrors());
		Enterprise enterprise = authorizarionService.getEnterprise();
		
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		
		List<Feature> features = featureRepository.findByProductAndName(product, specification);	
		
		Feature feature = new Feature();
		if(!features.isEmpty()){
			feature = features.get(0);
		}
		feature.setName(specification);
		
		for(String s :  values.split(",")){
			if(!s.isEmpty()){
				feature.getValues().add(s);
			}
		}
				
		
		feature.setProduct(product);
		
		Feature saved = featureRepository.save(feature);
		
		return saved;
	}
	
	@Transactional(readOnly=true)
	@GetMapping(path="/{product}")
	public ResponseEntity<Product> find(@PathVariable("product") String productId){
		Enterprise enterprise = authorizarionService.getEnterprise();
		Product product = productRepository.findOne(new ProductId(productId,enterprise.getId()));
		
		ResponseEntity<Product> response = ResponseEntity.ok(product);
		
		return response;
		
	}

	@Transactional(readOnly=true)
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		Enterprise enterprise = authorizarionService.getEnterprise();
		List<Product> products = productRepository.findByEnteprise(enterprise.getId());
		
		return ResponseEntity.ok(products);
		
		
	}
	
}
