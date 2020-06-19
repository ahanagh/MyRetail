package com.myretail.controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.models.Product;
import com.myretail.repositories.ProductCatalogDBRepository;

@RestController
public class ProductCatalogController {
  private final static Logger logger = LoggerFactory.getLogger(ProductCatalogController.class);
  
  @Autowired
  private ProductCatalogDBRepository repository;
  

  @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
  public String getProductById(@PathVariable("id") String id) {
	  
	  List<Product> resultProduct = repository.searchProductCatalog(id);
	  
      ObjectMapper mapper = new ObjectMapper();
      String productJson = null;
      try {
        productJson = mapper.writeValueAsString(resultProduct);
        logger.info("ResultingJSONstring:{}",productJson);
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      }
      return productJson;
  }
  
  @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> putProductPrice(@PathVariable("id") String id,@RequestBody Product product) {
	  logger.info("Product to be updated: {}",product.getId());
	  try {
		  if(product.getId().equals(id) && product.getId().matches("[0-9]+"))
			  return new ResponseEntity<>(repository.update(product),HttpStatus.OK);
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return new ResponseEntity<>("{}",HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @PostMapping("/product")
  public ResponseEntity<?> postProduct(@RequestBody Product product) {
	  logger.info("Inside postProduct");
	  try {
		  
		  if(!product.getId().matches("[0-9]+")) {
			  logger.info("Invalid product id: {}",product.getId());
			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Id is not in proper format");
		  }
		  else if(getProductById(product.getId()).equals("[]") && product.getId().matches("[0-9]+")) {
			  logger.info("Product for saving: {}",product.getId());
		      Product productSaved = repository.save(product);
		      return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
		  }
		  else if(!getProductById(product.getId()).equals("[]")){
			  logger.info("Product already present: {}",product.getId());
			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Product already present");
		  }
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return new ResponseEntity<>("{}",HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping("/products")
  public ResponseEntity<?> postProducts(@RequestBody List<Product> products) {
	  logger.info("Inside postProducts");
	  try {
		  List<ResponseEntity> responseList = new ArrayList<>();
		  for(Product product:products) {
			  ResponseEntity response = postProduct(product);
			  responseList.add(response);
		  }
		  return new ResponseEntity<>(responseList,HttpStatus.CREATED);
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return new ResponseEntity<>("{}",HttpStatus.NOT_FOUND);
  }
  
  @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
	  try {
		  logger.info("Product with id:{} deleted",id);
		  return new ResponseEntity<>(repository.delete(id),HttpStatus.OK);
	  }
	  catch (Exception e) {
		  e.printStackTrace();
	  }
	  return new ResponseEntity<>("{}",HttpStatus.NOT_FOUND);
  }

 
}


