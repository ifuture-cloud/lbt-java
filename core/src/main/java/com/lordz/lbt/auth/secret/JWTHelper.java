package com.lordz.lbt.auth.secret;

import com.lordz.lbt.auth.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;

public class JWTHelper {

    private final static String KEY_USER_ID = "userId";
    private final static String KEY_NAME = "name";

    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();
    /**
     * 密钥加密token
     *
     */
    public static String generateToken(AuthInfo info, String priKeyPath, int expire) throws Exception {

        String compactJws = Jwts.builder()
                .setSubject(info.getUsername())
                .claim(KEY_USER_ID, info.getId())
                .claim(KEY_NAME, info.getName())
                .setExpiration(new Date(Instant.now().plusSeconds(expire).toEpochMilli()))
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     */
    public static String generateToken(AuthInfo info, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(info.getUsername())
                .claim(KEY_USER_ID, info.getId())
                .claim(KEY_NAME, info.getName())
                .setExpiration(new Date(Instant.now().plusSeconds(expire).toEpochMilli()))
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }
    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }
    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static AuthInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        return new AuthInfo(body.getSubject(), String.valueOf(body.get(KEY_USER_ID)), String.valueOf(body.get(KEY_NAME)));
    }
    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static AuthInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new AuthInfo(body.getSubject(), String.valueOf(body.get(KEY_USER_ID)), String.valueOf(body.get(KEY_NAME)));
    }
}
