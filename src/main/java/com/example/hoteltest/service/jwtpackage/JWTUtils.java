//package com.example.hoteltest.service.jwtpackage;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//public class JWTUtils {
//
//    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 7; // 7 days
//    private final SecretKey key;
//
//    public JWTUtils() {
//        String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
//
//        // Base64 encode the secret string and create the key
//        byte[] decodedKey = Base64.getEncoder().encode(secretString.getBytes(StandardCharsets.UTF_8));
//        this.key = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());  // HMAC-SHA256
//    }
//
//    // Generates a JWT token
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key)  // Use the correctly encoded key
//                .compact();
//    }
//
//    // Extracts the username from the JWT token
//    public String extractUsername(String token) {
//        return extractClaims(token, Claims::getSubject);
//    }
//
//    // Extracts claims from the JWT token
//    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)  // Use the same key for validation
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claimsResolver.apply(claims);
//    }
//
//    // Validates the token against user details
//    public boolean isValidToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    // Checks if the token is expired
//    private boolean isTokenExpired(String token) {
//        return extractClaims(token, Claims::getExpiration).before(new Date());
//    }
//}
