package com.lordz.lbt.auth.secret;

import com.lordz.lbt.auth.AuthInfo;
import com.lordz.lbt.auth.secret.RsaKeyHelper;

import java.util.Map;

public interface ISecretService {

    default RSAPair generatePairKey(String password) throws Exception {
        Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(password);
        return new RSAPair(keyMap.get("pri"),keyMap.get("pub"));
    }

    RSAPair generatePairKey() throws Exception;

    RSAPair getPairKey() throws Exception;

    String generateToken(AuthInfo info) throws Exception;

    AuthInfo getInfoFromToken(String token) throws Exception;

    int getExpire();


    class RSAPair {
        private byte[] privateKey;
        private byte[] publicKey;

        public byte[] getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(byte[] privateKey) {
            this.privateKey = privateKey;
        }

        public byte[] getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(byte[] publicKey) {
            this.publicKey = publicKey;
        }

        public RSAPair(byte[] privateKey, byte[] publicKey) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        }
    }
}
