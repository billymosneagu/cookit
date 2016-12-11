

package control;  

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter; 
import java.net.HttpURLConnection; 
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL; 
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
 

 
/**
 *
 * @author billy
 */
public class Uploader
{

    /**
     *
     */
    public static final String UPLOAD_API_URL = "https://api.imgur.com/3/image";

    /**
     *
     */
    public static final String ALBUM_API_URL = "https://api.imgur.com/3/album";

    /**
     *
     */
    public static final int MAX_UPLOAD_ATTEMPTS = 3;

    
    private final static String CLIENT_ID = "c7e94ab6eed0866";

    /**
     * Recoge un archivo y lo sube a Imgru, devolviendo un JSON con todos los datos de la imagen
     * 
     * @param file
     *          The image to be uploaded to Imgur.
     * @return
     *          The JSON response from Imgur.
     */
    public static String upload(File file)
    {
        HttpURLConnection conn = getHttpConnection(UPLOAD_API_URL);
        writeToConnection(conn, "image=" + toBase64(file));
        return getResponse(conn);
    }

    /**
     * Crea un album en Imgru a partir de un lista de imagenes
     * 
     * @param imageIds
     *          A list of ids of images on Imgur.
     * @return
     *          The JSON response from Imgur.
     */
    public static String createAlbum(List<String> imageIds)
    {
        HttpURLConnection conn = getHttpConnection(ALBUM_API_URL);
        String ids = "";
        for (String id : imageIds)
        {
            if (!ids.equals(""))
            {
                ids += ",";
            }
            ids += id;
        }
        writeToConnection(conn, "ids=" + ids);
        return getResponse(conn);
    }
    
    /**
     * Convierte el archivo en un string de base64
     * 
     * @param file
     *          The file to be converted.
     * @return
     *          The file as a Base64 String.
     */
    private static String toBase64(File file)
    {
        String str="";
        try
        {
            byte[] b = new byte[(int) file.length()];
            FileInputStream fs = new FileInputStream(file);
            fs.read(b);
            fs.close();
            str=URLEncoder.encode(DatatypeConverter.printBase64Binary(b), "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return str;
    }
    
    /**
     * Crea la conexion con laAPI de Imgru
     * 
     * @param url
     *          The URL to connect to. (check Imgur API for correct URL).
     * @return
     *          The newly created HttpURLConnection.
     */
    private static HttpURLConnection getHttpConnection(String url)
    {
        HttpURLConnection conn=null;
        try
        {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
            conn.setReadTimeout(100000);
            conn.connect();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
    
    /**
     * Manda el archivo a la API
     * 
     * @param conn
     *          The connection to send the data to.
     * @param message
     *          The data to upload.
     */
    private static void writeToConnection(HttpURLConnection conn, String message)
    {
        OutputStreamWriter writer;
        try
        {
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(message);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            //throw new WebException(StatusCode.UNKNOWN_ERROR, e);
        }
    }
    
    /**
     * Recoge la respuesta de Imgru despues de mandar el archivo. En este caso es un Json con toda la informacion de la imagen.
     * 
     * @param conn
     *          The connection to listen to.
     * @return
     *          The response, usually as a JSON string.
     */
    private static String getResponse(HttpURLConnection conn)
    {
        StringBuilder str = new StringBuilder();
        BufferedReader reader;
        try
        {
//            if (conn.getResponseCode() != StatusCode.SUCCESS.getHttpCode())
//            {
//                //throw new WebException(conn.getResponseCode());
//            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (str.toString().equals(""))
        {
            //throw new WebException(StatusCode.UNKNOWN_ERROR);
        }
        return str.toString();
    }
}