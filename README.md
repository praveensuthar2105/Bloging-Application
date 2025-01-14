Blog App APIs
This project is a Spring Boot application that provides RESTful APIs for a blogging platform. It allows users to create, update, delete, and retrieve blog posts, categories, comments, and user information. The application also includes JWT-based authentication and authorization.

Features
->User registration and login
->JWT-based authentication and authorization
->CRUD operations for blog posts, categories, and comments
->Pagination and sorting for blog posts
->File upload for post images
->Swagger documentation for APIs

Technologies Used
 ->Spring Boot
 ->Spring Security
 ->Spring Data JPA
 ->Hibernate
 ->MySQL
 ->JWT (JSON Web Token)
 ->Swagger (OpenAPI)
 ->Lombok
 ->Maven

Project Structure

.gitattributes
.gitignore
.idea/
.mvn/
.vscode/
HELP.md
image/
mvnw
mvnw.cmd
pom.xml
README.md
src/
    main/
        java/
            com/codewithpraveen/blog_app_apis/
                config/
                controller/
                Entites/
                exceptions/
                payloads/
                repository/
                security/
                service/
                serviceimpl/
        resources/
            application.properties
    test/
        java/
            com/codewithpraveen/blog_app_apis/
target/


Getting Started
Prerequisites
Java 17 or higher
Maven
MySQL

Installation
 1. Clone the repository:
 git clone https://github.com/your-username/blog-app-apis.git
 cd blog-app-apis
 
 2. Configure the MySQL database:
 spring.datasource.url=jdbc:mysql://localhost:3306/blog_app_apis
spring.datasource.username=root
spring.datasource.password=your_password

3. Build the project:
./mvnw clean install

4. Run the application:
./mvnw spring-boot:run

The application will start on port 8080.

API Documentation
The API documentation is available at:

Usage
User Registration
 POST /api/users/register

 Request Body:
 {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "about": "About John Doe"
}
   User Login
{
    "username": "john.doe@example.com",
    "password": "password123"
}

Response:
{
    "token": "your_jwt_token"
}

Use the token in the Authorization header for authenticated requests:
 Authorization: Bearer your_jwt_token

 Create a Post
 POST /api/user/{userId}/category/{categoryId}/posts

Request Body:
{
    "title": "My First Post",
    "content": "This is the content of my first post.",
    "imageName": "default.jpg"
}

Get All Posts
GET /api/posts

Update a Post
PUT /api/posts/{postId}

Request Body:
{
    "title": "Updated Post Title",
    "content": "Updated content of the post.",
    "imageName": "updated.jpg"
}
Delete a Post
DELETE /api/posts/{postId}

Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.

Contact
For any questions or inquiries, please contact Praveen at 
praveensuthar1863@gmail.com

