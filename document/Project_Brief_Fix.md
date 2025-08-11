# Project_Brief_Fix — ERDJBE26
_WebService Spring Boot + ReactJS (Bootstrap) - Enhanced with Error Prevention Guide_

**Version:** 1.1 · **Target DB:** MySQL 8.0 (utf8mb4) · **Modules:** Access Control & Menu, User Management, Product Catalog, Supplier, Audit Logs

---

## 🚨 CRITICAL FIXES & ERROR PREVENTION

### 1. JWT Authentication Configuration
**Problem:** JWT secret tidak dapat di-decode sebagai Base64
**Solution:**
```java
// JwtTokenProvider.java
private Key key() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Bukan BASE64.decode()
}
```

**application.properties:**
```properties
# JWT Secret - gunakan string biasa, bukan Base64
jwt.secret=ERDJBE26_WebServiceSpringBootReactJSBootstrapJWTSecretKeyForAuthentication
jwt.expiration-ms=86400000
```

### 2. Hibernate LazyInitializationException Fix
**Problem:** LazyInitializationException pada relasi MstUser -> MstAkses
**Solution:**
```java
// MstUser.java
@ManyToOne(fetch = FetchType.EAGER) // WAJIB EAGER untuk Security context
@JoinColumn(name = "IDAkses")
private MstAkses akses;
```

### 3. Password Hashing Consistency
**Problem:** Password hash di database tidak cocok dengan BCrypt encoder
**Solution:**
```java
// SecurityConfig.java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Consistent dengan insert.sql
}

// Regenerate password hash jika perlu:
String correctHash = passwordEncoder.encode("password");
// Update ke database: $2a$10$... (bukan hash lain)
```

### 4. UserDetailsService Implementation
**Problem:** Logika orElseGet yang kompleks menyebabkan error
**Solution:**
```java
// CustomUserDetailsService.java
@Override
public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    MstUser user = mstUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
    
    Set<GrantedAuthority> authorities = Collections.singleton(
        new SimpleGrantedAuthority(user.getAkses().getNama())
    );
    
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        authorities
    );
}
```

### 5. Repository Method
**Problem:** Method findByUsernameOrEmail tidak tersedia
**Solution:**
```java
// MstUserRepository.java
@Repository
public interface MstUserRepository extends JpaRepository<MstUser, Long> {
    Optional<MstUser> findByUsername(String username);
    Optional<MstUser> findByEmail(String email);
    Optional<MstUser> findByUsernameOrEmail(String username, String email); // TAMBAHKAN INI
}
```

### 6. Security Configuration
**Problem:** AuthenticationEntryPoint tidak dikonfigurasi
**Solution:**
```java
// SecurityConfig.java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> 
            exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // TAMBAHKAN
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
            .requestMatchers(
                "/api/v1/auth/login",
                "/api/v1/auth/debug/**", // TAMBAHKAN untuk debugging
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/api-docs/**",
                "/v3/api-docs/**"
            ).permitAll()
            .anyRequest().authenticated()
        );
    
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}
```

### 7. Global Exception Handler
**Problem:** BadCredentialsException dikembalikan sebagai 500, bukan 401
**Solution:**
```java
// GlobalExceptionHandler.java
@ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
public ResponseEntity<ErrorDetails> handleBadCredentialsException(
        BadCredentialsException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Username atau password salah. Silakan periksa kembali.",
            request.getDescription(false),
            request.getContextPath()
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED); // 401, bukan 500
}
```

---

## 📋 MANDATORY CHECKLIST BEFORE DEPLOYMENT

### Database Setup
- [ ] Jalankan `database.sql` untuk membuat struktur tabel
- [ ] Jalankan `insert.sql` untuk data awal
- [ ] Verifikasi user admin ada dengan password hash yang benar
- [ ] Pastikan foreign key constraints aktif

### Security Configuration
- [ ] JWT secret tidak menggunakan Base64 decoding
- [ ] PasswordEncoder konsisten (BCryptPasswordEncoder)
- [ ] AuthenticationEntryPoint dikonfigurasi
- [ ] Endpoint debug tersedia untuk troubleshooting

### Entity Relationships
- [ ] Semua relasi yang digunakan di Security context menggunakan EAGER fetch
- [ ] UserDetailsService implementation sederhana dan robust
- [ ] Repository methods lengkap (findByUsernameOrEmail)

### Error Handling
- [ ] GlobalExceptionHandler menangani authentication errors dengan benar
- [ ] Response codes sesuai (401 untuk authentication, 403 untuk authorization)
- [ ] Error messages user-friendly

---

## 🔧 DEBUGGING ENDPOINTS

Tambahkan endpoint berikut untuk membantu debugging (HANYA untuk development):

```java
// AuthController.java - Debug endpoints
@GetMapping("/debug/check-admin-password")
public ResponseEntity<Map<String, Object>> checkAdminPassword() {
    // Implementasi untuk cek password hash di database
}

@PostMapping("/debug/fix-admin-password") 
public ResponseEntity<Map<String, Object>> fixAdminPassword() {
    // Implementasi untuk update password admin
}

@GetMapping("/debug/validate-token")
public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
    // Implementasi untuk validasi JWT token
}
```

**PENTING:** Hapus atau disable endpoint debug ini di production!

---

## 🚀 TESTING WORKFLOW

### 1. Authentication Flow Test
```bash
# 1. Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail": "admin", "password": "password"}'

# Expected: HTTP 200 dengan JWT token

# 2. Validate Token  
curl -X GET http://localhost:8080/api/v1/auth/debug/validate-token \
  -H "Authorization: Bearer <token>"

# Expected: HTTP 200 dengan token validation info

# 3. Access Protected Endpoint
curl -X GET http://localhost:8080/api/v1/users/test \
  -H "Authorization: Bearer <token>"

# Expected: HTTP 200 dengan success message
```

### 2. Error Scenarios Test
```bash
# 1. Wrong Credentials
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail": "admin", "password": "wrong"}'

# Expected: HTTP 401 dengan error message

# 2. No Token
curl -X GET http://localhost:8080/api/v1/users/test

# Expected: HTTP 401

# 3. Invalid Token
curl -X GET http://localhost:8080/api/v1/users/test \
  -H "Authorization: Bearer invalid-token"

# Expected: HTTP 401
```

---

## 📦 REQUIRED DEPENDENCIES

Pastikan dependencies berikut ada di `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Database -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Utilities -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 🔄 DEPLOYMENT CHECKLIST

### Pre-deployment
- [ ] Semua unit tests passing
- [ ] Integration tests passing
- [ ] Security tests passing
- [ ] Database migrations berhasil
- [ ] Environment variables set

### Production Configuration
- [ ] JWT secret diganti dengan nilai yang kuat dan unik
- [ ] Debug endpoints disabled/removed
- [ ] HTTPS enabled
- [ ] CORS dikonfigurasi dengan benar
- [ ] Rate limiting aktif
- [ ] Logging level diset ke WARN/ERROR

### Post-deployment
- [ ] Health check endpoint responsif
- [ ] Authentication flow working
- [ ] Basic CRUD operations working
- [ ] Monitoring dan alerting aktif

---

## 📚 ADDITIONAL IMPLEMENTATION GUIDE

### Controller Templates
```java
@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ADMIN')") // Role-based access
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        // Implementation
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto dto) {
        // Implementation
    }
}
```

### Service Layer Pattern
```java
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDto createUser(CreateUserDto dto) {
        // Validation
        // Conversion
        // Business logic
        // Save
        // Audit logging
        return mapper.toDto(savedEntity);
    }
}
```

---

## 🔍 TROUBLESHOOTING GUIDE

### Issue: HTTP 500 on Login
**Possible Causes:**
1. JWT secret Base64 decoding error
2. LazyInitializationException
3. Database connection issues

**Solutions:**
1. Check JWT secret configuration
2. Verify entity fetch types
3. Check database connectivity

### Issue: HTTP 401 on Protected Endpoints
**Possible Causes:**
1. Token not included in request
2. Token expired
3. Invalid token format
4. User role insufficient

**Solutions:**
1. Include Authorization header with Bearer token
2. Refresh token or re-login
3. Validate token format
4. Check user role assignments

### Issue: LazyInitializationException
**Possible Causes:**
1. Accessing lazy-loaded entities outside session
2. @Transactional not working properly

**Solutions:**
1. Use EAGER fetch for entities used in security context
2. Ensure proper transaction boundaries

---

## 📈 PERFORMANCE CONSIDERATIONS

### Database Optimization
- Index pada columns yang sering di-query (username, email)
- Proper foreign key constraints
- Connection pooling configuration

### Security Optimization
- JWT token expiration tidak terlalu lama (max 1 hour)
- Implement refresh token mechanism
- Rate limiting pada login endpoints

### Caching Strategy
- Cache user permissions/roles
- Cache menu mappings
- Use Redis untuk session storage (optional)

---

## 🔐 SECURITY BEST PRACTICES

### Password Security
- Minimum 8 characters
- BCrypt dengan cost factor 12+
- Password history (prevent reuse)
- Account lockout after failed attempts

### JWT Security
- Short expiration times
- Secure secret generation
- Token revocation mechanism
- HTTPS only transmission

### API Security
- Input validation
- Output encoding
- CORS configuration
- Rate limiting
- Request logging

---

_Document Version: 1.1 - Updated with critical fixes and prevention guidelines_
_Last Updated: August 11, 2025_
