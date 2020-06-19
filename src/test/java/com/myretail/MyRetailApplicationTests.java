package com.myretail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myretail.models.Price;
import com.myretail.models.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Product.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRetailApplicationTests {
	
     @Autowired
     private TestRestTemplate restTemplate;

     @LocalServerPort 
     private String port;

     private String getRootUrl() {
         return "http://localhost:" +"8080/";
     }
     
     @Before
     public void setUp() throws Exception {
    	 String id = "1456781";
         Price price = new Price();
         price.setValue(26);
         price.setCurrencyCode("USD");
         Product product = new Product();
         product.setId(id);
         product.setName("Testing");
         product.setCurrentPrice(price);
         ResponseEntity<Product> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", product, Product.class);
     }
     
     @Test
     public void testCreateProductPass() throws JsonProcessingException{
         Product product = new Product();
         Price price = new Price();
         price.setValue(26);
         price.setCurrencyCode("USD");
         product.setId("1568");
         product.setName("Test Product");
         product.setCurrentPrice(price);
         ResponseEntity<Product> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", product, Product.class);
         assertEquals(postResponse.getBody().getId(),"1568");
     }
     
     @Test
     public void testCreateProductFailure() throws JsonProcessingException{
         Product product = new Product();
         Price price = new Price();
         price.setValue(26);
         price.setCurrencyCode("USD");
         product.setId("14234e");
         product.setName("Test Product");
         product.setCurrentPrice(price);
         ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", product, String.class);
         assertEquals("Id is not in proper format",postResponse.getBody());
     }
     
     @Test
     public void testCreateProductFailure2() throws JsonProcessingException{
         Product product = new Product();
         Price price = new Price();
         price.setValue(26);
         price.setCurrencyCode("USD");
         product.setId("142347");
         product.setName("Test Product");
         product.setCurrentPrice(price);
         ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", product, String.class);
         assertEquals("Product already present",postResponse.getBody());
     }

    
    @Test
    public void testGetProductById() {
        String response = restTemplate.getForObject(getRootUrl() + "/product/142347", String.class);
        assertFalse(response.contains("error"));
    }
     
     @Test
     public void testGetProductByIdFailure() throws JsonProcessingException {
         String response = restTemplate.getForObject(getRootUrl() + "/product/1890 ", String.class);
         assertTrue(response.equals("[]"));
     }
    
    @Test
    public void testDeleteProduct() {
        int id = 1568;
        String product = restTemplate.getForObject(getRootUrl() + "/products/" + id, String.class);
        assertNotNull(product);
        restTemplate.delete(getRootUrl() + "/product/" + id);
        try {
             product = restTemplate.getForObject(getRootUrl() + "/products/" + id, String.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }
    
    
    @Test
    public void testUpdateProductFailure() {
        String id = "14567891";
        Price price = new Price();
        price.setValue(26);
        price.setCurrencyCode("USD");
        Product product = new Product();
        product.setId(id);
        product.setCurrentPrice(price);
        restTemplate.put(getRootUrl() + "/product/" + id, product);
        String updatedProduct = restTemplate.getForObject(getRootUrl() + "/product/" + id, String.class);
        assertTrue(updatedProduct.contains("[]"));
        
    }
    
    @Test
    public void testUpdateProductPass() {
        String id = "1456781";
        Price price = new Price();
        price.setValue(26);
        price.setCurrencyCode("USD");
        Product product = new Product();
        product.setId(id);
        product.setCurrentPrice(price);
        restTemplate.put(getRootUrl() + "/product/" + id, product);
        String updatedProduct = restTemplate.getForObject(getRootUrl() + "/product/" + id, String.class);
        assertTrue(updatedProduct.contains("26"));
        
    }
    
    @After
    public void tearDown() throws Exception {
        String id = "1456781";
        restTemplate.delete(getRootUrl() + "/product/" + id);
   }

    
}
