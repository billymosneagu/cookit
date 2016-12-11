/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.prism.paint.Paint;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import control.ImageControl;
import control.PasswordHash;
import control.Uploader;
import dao.FactoriaDAO;
import dao.RecetaDAO;
import dao.UsuarioDAO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import model.Comentario;
import model.Usuario;
import org.apache.commons.codec.binary.Base64;
import org.neo4j.kernel.api.impl.index.bitmaps.Bitmap;

/**
 *
 * @author billy
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        FactoriaDAO dao = new FactoriaDAO();
        ImageControl c = new ImageControl();
      Usuario u=new Usuario();
      //u.setId();
      PasswordHash cifrado=new PasswordHash();
      ObjectMapper mapper=new ObjectMapper();
        RecetaDAO controlu=new RecetaDAO();
        UsuarioDAO cotrol=new UsuarioDAO();
        System.out.println(cotrol.activeUser(48));
     /* String s="uFWCAlF6b7nPNHNDPUoIk0otk/nDGAmCyv93oV4+BUsp";
            String d= s.replace(" ", "+");
      String usuarioDescifrado=decrypt(d, "qwertyuiopasdfgh", "qwertyuiopasdfgh");
      /*Usuario usuarioObjecto = mapper.readValue(usuarioDescifrado, new TypeReference<Usuario>() {
                });*/
        //System.out.println(controlu.getBusqueda("h"));
    }
    
    /**
     *
     * @param encryptedData
     * @param initialVectorString
     * @param secretKey
     * @return
     */
    public static String decrypt(String encryptedData, String initialVectorString, String secretKey) {

    String decryptedData = null;

    try {

        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());

        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE, skeySpec, initialVector);

        byte[] encryptedByteArray = (new org.apache.commons.codec.binary.Base64()).decode(encryptedData.getBytes());

        byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);

        decryptedData = new String(decryptedByteArray, "UTF8");

    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {

        System.out.println("Problem decrypting the data "+e.getMessage());

    }

    return decryptedData;

}


 
    
}
