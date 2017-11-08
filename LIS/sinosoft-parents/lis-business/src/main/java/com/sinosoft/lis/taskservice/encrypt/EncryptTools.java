/*
 * Created on 2005-12-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.lis.taskservice.encrypt;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.Arrays;
import javax.crypto.spec.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.jce.provider.*;

public class EncryptTools {
private static Logger logger = Logger.getLogger(EncryptTools.class);
	
        private PBEParameterSpec pbeParamSpec=null;
        private SecretKey pbeKey = null;
        private Cipher pbeCipher=null;
        public void init(String  initParameter) throws Exception {                /*
                 * In order to use Password-Based Encryption (PBE) as defined in PKCS
                 * #5, we have to specify a salt and an iteration count. The same salt
                 * and iteration count that are used for encryption must be used for
                 * decryption:
                 */
                //char[] passwd={'1','2','3','4','5','6'};
				java.security.Security.addProvider(new BouncyCastleProvider());
                pbeParamSpec = getParamSpec();
                pbeKey = createPBEKey(initParameter.toCharArray());           // Create PBE Cipher
                pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        }

        public byte[] Encrypt(byte[] enSource) throws Exception {
                // Initialize PBE Cipher with key and parameters
                pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
                // Encrypt the cleartext
                return  pbeCipher.doFinal(enSource);
        }

        public byte[] Decrypt(byte[] deSource) throws Exception {
                // Initialize PBE Cipher with key and parameters
                pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

                // decrypt the cleartext
                return  pbeCipher.doFinal(deSource);
        }
        /**
         * @return
         * @throws IOException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeySpecException
         */
        private SecretKey createPBEKey(char [] passwd) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
                PBEKeySpec pbeKeySpec;
                SecretKeyFactory keyFac;
                // Prompt user for encryption password.
                // Collect user password as char array (using the "readPasswd" method from above), and convert
                // it into a SecretKey object, using a PBE key
                // factory.
                pbeKeySpec = new PBEKeySpec(passwd);
                keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
                return pbeKey;
        }
        /**
         * @return
         */
        private PBEParameterSpec getParamSpec() {
                PBEParameterSpec pbeParamSpec;
                // Salt
                byte[] salt = {(byte) 0xc7, (byte) 0x73, (byte) 0x41, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xea, (byte) 0x99};
                int count = 17;        // Iteration count
                // Create PBE parameter set
                pbeParamSpec = new PBEParameterSpec(salt, count);
                return pbeParamSpec;
        }
}


