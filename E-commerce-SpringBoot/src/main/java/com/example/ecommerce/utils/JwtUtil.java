//package com.example.ecommerce.utils;
//
//import com.example.ecommerce.dto.UserDTO;
//import com.example.ecommerce.entities.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.cglib.core.internal.Function;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public String generateToken (User userDTO){
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDTO);
//    }
//    private String createToken (Map<String, Object> claims, User userDto){
//
//        claims.put("role",userDto.getUserRole());
//        claims.put("username",userDto.getName());
//        claims.put("displayName",userDto.getName());
//        claims.put("email",userDto.getEmail());
//        claims.put("id",userDto.getId());
//        claims.put("password",userDto.getPassword());
//        claims.put("Image",userDto.getImg());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDto.getName())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                .signWith(secretKey,SignatureAlgorithm.HS256)
//                .compact();
//    }
//
////    private Key getSignKey()  {
////        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
////        return Keys.hmacShaKeyFor(keyBytes);
////    }
//
//    public String extractUsername(String token) {
//
//        return extractClaims(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//
//    private Claims extractAllClaims(String token){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(secretKey)
//                .s
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Boolean isTokenExpired (String token){
//        return extractExpiration(token).before(new Date());
//
//    }
//    public Date extractExpiration (String token) {
//        return extractClaims(token, Claims::getExpiration);
//
//
//    }
//    public Boolean validateToken (String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//
//    }
//
//
//
//            }
//
package com.example.ecommerce.utils;

import com.example.ecommerce.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
//public class JwtUtil {
////    public static final String SECRET = "123456";
//private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public String generateToken (User userDTO){
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDTO);
//    }
//    private String createToken (Map<String, Object> claims, User userDto){
//
//        claims.put("role",userDto.getUserRole());
//        claims.put("username",userDto.getName());
//        claims.put("displayName",userDto.getName());
//        claims.put("email",userDto.getEmail());
//        claims.put("id",userDto.getId());
//        claims.put("password",userDto.getPassword());
//        claims.put("Image",userDto.getImg());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDto.getName())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                .signWith(SignatureAlgorithm.HS256,getSignKey()).compact();
//    }
//
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey.getEncoded());
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String extractUsername(String token) {
//        return extractClaims(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//
//    private Claims extractAllClaims(String token){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Boolean isTokenExpired (String token){
//        return extractExpiration(token).before(new Date());
//
//    }
//    public Date extractExpiration (String token) {
//        return extractClaims(token, Claims::getExpiration);
//
//
//    }
//    public Boolean validateToken (String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//
//    }
//
//
//}


public class JwtUtil {
    private String jwtSecret = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    private Key getSigningKey() {
//        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//    private final Key secretKey =  Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(User userDTO) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDTO);
    }

    private String createToken(Map<String, Object> claims, User userDto) {
        claims.put("role", userDto.getUserRole());
        claims.put("username", userDto.getName());
        claims.put("displayName", userDto.getName());
        claims.put("email", userDto.getEmail());
        claims.put("id", userDto.getId());
        claims.put("password", userDto.getPassword());
        claims.put("Image", userDto.getImg());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDto.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

}
