/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import javax.imageio.ImageIO;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 *
 * @author billy
 */
public class ImageControl {
    /**
     * Recoge una imagen y la transforma a String
     * @param image
     * @param type
     * @return 
     */
    public static  BufferedImage decodeToImage(String imageString){
        BufferedImage image = null;
        byte[] imageByte; 
        try {
        imageByte = Base64.decodeBase64(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
       
            BufferedImage images = ImageIO.read(bis);
        } catch (IOException ex) {
            Logger.getLogger(ImageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
//    public static String encodeToString(ArrayList<BufferedImage> image, String type) {
//        String imageString = null;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        
//        try {
//            for(int i=0;i<image.size();i++){
//                ImageIO.write(image.get(i), type, bos);
//            }
//            //ImageIO.write(image, type, bos);
//            byte[] imageBytes = bos.toByteArray();
// 
//            //BASE64Encoder encoder = new BASE64Encoder();
//            imageString = Base64.encode(imageBytes);
// 
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageString;
//    }
//    /**
//     * Recoge el string de una imagen y la transfoma en una imagen
//     * @param imageString
//     * @return 
//     */
//    public static BufferedImage decodeToImage(String imageString) {
// 
//        BufferedImage image = null;
//        byte[] imageByte;
//        try {
//            //BASE64Decoder decoder = new BASE64Decoder();
//            imageByte = Base64.decode(imageString);
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//            BufferedImage images = ImageIO.read(bis);
//            bis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return image;
//    }
    
}
