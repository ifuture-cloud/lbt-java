package com.lordz.lbt.auth.secret;

import com.lordz.lbt.auth.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecretServiceJWTImpl implements ISecretService {

    @Value("${token.expire:3600}")
    private int expire;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String KEY_AUTH_SECRET = "LBT:AUTH:SECRET";
    private static final String KEY_AUTH_PRIVATEKEY = "LBT:AUTH:PRIKEY";
    private static final String KEY_AUTH_PUBLICKEY = "LBT:AUTH:PUBKEY";


    @Override
    public RSAPair generatePairKey() throws Exception {
        String password;
        if (redisTemplate.hasKey(KEY_AUTH_SECRET)){
            password = redisTemplate.opsForValue().get(KEY_AUTH_SECRET);
        }else {
            password = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(KEY_AUTH_SECRET,password);
        }
        RSAPair pair = generatePairKey(password);
        redisTemplate.opsForValue().set(KEY_AUTH_PRIVATEKEY, RsaKeyHelper.toHexString(pair.getPrivateKey()));
        redisTemplate.opsForValue().set(KEY_AUTH_PUBLICKEY, RsaKeyHelper.toHexString(pair.getPublicKey()));

        return pair;
    }

    @Override
    public RSAPair getPairKey() throws Exception {
        if (redisTemplate.hasKey(KEY_AUTH_PRIVATEKEY) && redisTemplate.hasKey(KEY_AUTH_PUBLICKEY)) {
            return new RSAPair(RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(KEY_AUTH_PRIVATEKEY)),RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(KEY_AUTH_PUBLICKEY)));
        }
        return generatePairKey();
    }
    @Override
    public String generateToken(AuthInfo info) throws Exception {
        return JWTHelper.generateToken(info, getPairKey().getPrivateKey(),expire);
    }
    @Override
    public AuthInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, getPairKey().getPublicKey());
    }
    @Override
    public int getExpire(){
        return expire;
    }
}
