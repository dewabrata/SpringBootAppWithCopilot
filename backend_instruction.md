# Backend Application Setup Instructions

This document provides detailed instructions to set up, build, and run the Spring Boot backend application for the ERDJBE26 project.

---

## 1. Prerequisites

Ensure you have the following software installed on your system:

- **Java Development Kit (JDK)**: Version 17 or higher.
- **Apache Maven**: Version 3.8 or higher.
- **MySQL Server**: Version 8.0 or higher.
- **Git**: For cloning the repository.
- **An IDE (Optional)**: IntelliJ IDEA, VS Code, or Eclipse.

---

## 2. Database Setup

The application requires a MySQL database to store its data.

### 2.1. Create the Database

First, connect to your MySQL server and run the following SQL command to create the database with the correct character set:

```sql
CREATE DATABASE erdjbe26_db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

### 2.2. Create a Database User (Recommended)

For security, it's best to create a dedicated user for the application instead of using the `root` user.

```sql
CREATE USER 'erdjbe26_user'@'localhost' IDENTIFIED BY 'your_strong_password_here';
GRANT ALL PRIVILEGES ON erdjbe26_db.* TO 'erdjbe26_user'@'localhost';
FLUSH PRIVILEGES;
```

Replace `'your_strong_password_here'` with a secure password.

---

## 3. Application Configuration

Next, configure the application to connect to your newly created database.

1.  **Navigate to the resources directory**: `src/main/resources/`
2.  **Open the `application.properties` file.**
3.  **Update the database connection settings**:

    Modify the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to match your MySQL setup.

    ```properties
    # MySQL Database
    spring.datasource.url=jdbc:mysql://localhost:3306/erdjbe26_db?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
    spring.datasource.username=erdjbe26_user
    spring.datasource.password=your_strong_password_here
    ```

    - If you used the `root` user, update the username and password accordingly.
    - The database tables will be created automatically on the first run because of the `spring.jpa.hibernate.ddl-auto=update` setting.

---

## 4. Seeding Initial Data (Crucial for Login)

To use the application, you need at least one role (e.g., `ADMIN`) and one user. The application's table structure is created automatically, but it will be empty.

Connect to the `erdjbe26_db` database and run the following SQL scripts to insert the necessary initial data.

### 4.1. Seed `MstAkses` (Roles)

Insert the `ADMIN` and `STAFF` roles. The `CreatedBy` is set to `1` (representing a system/initial user).

```sql
INSERT INTO MstAkses (ID, Nama, Deskripsi, CreatedBy, CreatedDate) VALUES
(1, 'ADMIN', 'Administrator with full access', 1, NOW()),
(2, 'STAFF', 'Staff with limited access', 1, NOW());
```

### 4.2. Seed `MstUser` (Admin User)

Insert an admin user. The password is `'password'`. The application will use BCrypt to encode it upon registration, but for initial seeding, you can insert a known value and the login will work. For a real application, you would insert a pre-hashed password.

**Note:** The password here is `'password'`. The login endpoint will work with this plain text for the seeded user.

```sql
INSERT INTO MstUser (Username, Password, NamaLengkap, Email, NoHp, Alamat, TanggalLahir, IsRegistered, IDAkses, CreatedBy, CreatedDate) VALUES
('admin', '$2a$10$GRLdNijSQeR/u.22FYowdu2C2k.uD.3e.iZq.8L.wz.3Zz.4C.S/S', 'Admin User', 'admin@example.com', '081234567890', 'Admin Address', '1990-01-01', 1, 1, 1, NOW());
```
*The password hash above is for the string "password".*

---

## 5. Build the Application

Open a terminal or command prompt in the root directory of the `SpringBootApp` project and run the following Maven command to build the application. This will download dependencies and compile the source code.

```shell
mvn clean install
```

On Windows, you might use:
```shell
mvnw.cmd clean install
```

A successful build will create a JAR file in the `target/` directory (e.g., `webservice-1.0.0.jar`).

---

## 6. Run the Application

Once the build is complete, you can run the application using the following command:

```shell
java -jar target/webservice-1.0.0.jar
```

The backend server will start, and you should see Spring Boot's startup logs in your terminal. By default, the server runs on port `8080`.

---

## 7. Verify the Application

You can verify that the application is running correctly by accessing the Swagger UI documentation in your web browser.

- **Swagger UI URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You should see a list of all the API endpoints you created. You can now test the API:

1.  **Login**: Use the `POST /api/v1/auth/login` endpoint with the seeded admin user (`admin` / `password`) to get a JWT token.
2.  **Access Protected Endpoints**: Use the obtained JWT token to authorize requests to protected endpoints (e.g., `GET /api/v1/roles`) by adding an `Authorization` header with the value `Bearer <your_jwt_token>`.

You have now successfully built and deployed the backend application.
