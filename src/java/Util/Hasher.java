/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mast3
 */
public class Hasher {
    //Code possible thanks to https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    
    private String salt;
    private String hashedPassword;
    
    public Hasher(String password){
        performHash(password);
    }
    
    private void generateSalt(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            
            byte[] salt = new byte[16];
            
            //Get random salt
            sr.nextBytes(salt);
            
            //Thanks to https://mkyong.com/java/how-do-convert-byte-array-to-string-in-java/
            this.salt = new String(salt, StandardCharsets.UTF_8);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void hashPassword(String password){      
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            //Obtain hashed password
            hashedPassword = sb.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String hashPassword(String password, String salt){
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            //Obtain hashed password
            hashedPassword = sb.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hashedPassword;
    }
    
    //Use this to hash
    private void performHash(String password){
        generateSalt();
        hashPassword(password);
    }
    
    //Compare password
    public static boolean comparePassword(String hashedPassword, String password2, String salt){
        System.out.println("Comparing " + hashedPassword + " and " + hashPassword(password2, salt));
        return hashedPassword.equals(hashPassword(password2, salt));
    }
    
    //Getter and setters

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
    
    
}
