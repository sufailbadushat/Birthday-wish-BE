# EOUS-birthday-wish-Application

# Diffrenz-Assignment

## 1. Getting Started
### Prerequisites
```
- Java
- Node
- Angular CLI
- MySQL
```
### Clone these Repositories
- Front End : Angular
```
- git clone https://github.com/sufailbadushat/EOUS-birthday-wish-FE.git
```
- Back End : Spring Boot
```
- git clone https://github.com/sufailbadushat/EOUS-birthday-wish-BE.git
```
## 2. Database Setup
 - Create a new database named **eous_birthdaydb**. 
 - or Configure the database setup in application.properties/yml based on your database properties.
   
## 3. Available Endpoints

### Normal Endpoints:
```
- **GET** /admin/getAll: Returns all the employess details.
- **GET** /admin/birthday: Returns all the birthday employees.
- **POST** /auth/signIn: For logging into the system and returns details about the currently loggedIn user.
```
### Server Sent Event (SSE):
```
- **GET** /sse/subscribe?userId={userId}: To subscribe to SSE based on the employee ID.
- **GET** /sse/sendEvent?userId={userId}&desc={msg}: To send SSE(msg) to the user based on the employee ID.
```
   
## 4. Working 

### Start both Angular and SpringBoot code
Make sure the application successfully compiled and started without any error.

### 1. Admin Login
- LogIn as admin
- Admin can see all the employees present and a tab to show all the birthday employees.
### 2. User Login
- LogIn as Birthday employee on private tab or any other browser.
### 3. Admin sending wishes
- From the ADMIN account, it is possible to send birthday messages to the specific employee who is currently logged in
- ADMIN has the option to send either custom message or,
- If no custom message is provided, the system will atomatically send hardcoded message to the user.

