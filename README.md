# MyRetail
This is a service for CRUD operations for retail website.

The product details are stored in mongo DB.

Test cases have been written for various positive and negative scenarios.
The following are a list of test cases-
1. Trying to insert product with invalid id
2. Trying to insert product which is already present.
3. Trying to update a non-existent product's price.
4. Trying to update an existing product.
5. Trying to get a product with a name which contains lots of special characters.
6. Trying to get a non-existent product.
7. Trying to insert a product which is not present.
8. Trying to delete a product which is not present.
9. Trying to delete an existing product.
10. Trying to insert a batch of products by first checking whether each of them exist or not.