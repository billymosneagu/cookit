/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Cookit.Servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conection.DBConnection;
import control.PasswordHash;
import dao.FactoriaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import org.apache.commons.codec.binary.Base64;
import org.neo4j.driver.v1.exceptions.ClientException;

/**
 *
 * @author billy
 */
@WebServlet(name = "ServletUsuario", urlPatterns = {"/ServletUsuario"})
public class ServletUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet requesto
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String op = request.getParameter("op");
        FactoriaDAO control = new FactoriaDAO();
        ObjectMapper mapper = new ObjectMapper();
        PasswordHash cifrado = new PasswordHash();
        switch (op) {
            case "GetUsuario": {
                Usuario usuario = (Usuario) request.getAttribute("usuario");
                Usuario usuarioDB = control.getUsuario(usuario.getEmail());
                
                if (usuario.getEmail().equals(usuarioDB.getEmail())) {
                    if (cifrado.validatePassword(usuario.getContrasenna(),usuarioDB.getContrasenna())) {
                        if(control.activeUser(usuarioDB.getId())==true){
                            request.setAttribute("usuarioDB", usuarioDB);
                        }else{
                            request.setAttribute("usuarioDB", new Usuario());
                            response.getWriter().write("Usuario no activado");
                        }
                        
                    } else {
                        request.setAttribute("usuarioDB", new Usuario());
                        response.getWriter().write("Contraseña o nombre de usuario no valido");
                    }
                } else {
                    request.setAttribute("usuarioDB", new Usuario());
                    response.getWriter().write("Contraseña o nombre de usuario no valido");
                }
                break;
            }
            case "GetUsuarioById": {
                int usuarioID = (int) request.getAttribute("usuarioID");
                Usuario usuario = control.getUsuarioById(usuarioID);
                request.setAttribute("usuarioDB", usuario);
                break;

            }
            case "GetUsuarioByIdReceta": {
                int recetaID = (int) request.getAttribute("recetaID");
                Usuario usuario = control.getUsuarioByIdReceta(recetaID);
                request.setAttribute("usuarioDB", usuario);
                break;
            }
            case "followUsuario": {
                int id = (int) request.getAttribute("usuarioID");
                int idSeguido = (int) request.getAttribute("seguidoID");
                control.seguirUsuario(id, idSeguido);
                break;
            }
            case "unfollowUsuario": {
                int id = (int) request.getAttribute("usuarioID");
                int idSeguido = (int) request.getAttribute("seguidoID");
                control.dejarDeSeguir(id, idSeguido);
                break;
            }
            case "modificarUsuario": {
                Usuario usuario = (Usuario) request.getAttribute("usuario");
                control.updateUsuario(usuario.getId(), usuario);
                break;
            }
            case "addUsuario": {
                Usuario usuario = (Usuario) request.getAttribute("usuarioPHP");

                try {
                    usuario.setContrasenna(cifrado.createHash(usuario.getContrasenna()));
                    control.addUsuario(usuario);
                    usuario = control.getUsuario(usuario.getEmail());
                    String usuarioString = mapper.writeValueAsString(usuario.getId());
                    byte[] usuarioCifrada = cifrado.cifra(usuarioString, "clave");
                    String usuarioBase64 = new String(Base64.encodeBase64(usuarioCifrada));
                    generateAndSendEmail(usuario.getEmail(), "Acceda al siguiente enlace para activar su cuenta\n"
                            + "http://192.168.1.42:8080/CookitServer/ServletUsuario?op=activateUser&usuarioID=" + usuarioBase64);
                    response.getWriter().write("Se ha enviado un email a su correo electronico para activar la cuenta");
                } catch (MessagingException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    try {
                        mapper.writeValue(response.getOutputStream(), "Error en la base de datos");
                    } catch (IOException ex1) {
                        Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (ClientException ex) {

                    try {
                        response.getWriter().write("Al parecer este usuario ya esta registrado");
                        // mapper.writeValue(response.getOutputStream(), "Al parecer este usuario ya esta registrado");
                    } catch (IOException ex1) {
                        Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "activateUser": {
                int idUsuario = (int) request.getAttribute("usuarioID");
                control.activate(idUsuario);

                try {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/CuentaActivada.jsp");
                    dispatcher.forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "cambiarContrasenna": {
                try {
                    String pass = request.getParameter("pass");
                    String email = request.getParameter("email");
                    control.cambiarContrasenna(email, cifrado.createHash(pass));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/CambioContrasena.jsp");
                    dispatcher.forward(request, response);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            }
            case "validarEmail": {
                String email = request.getAttribute("emailPHP")+"";
                Usuario usuario = control.getUsuario(email);
                try {
                    if (usuario.getId() != 0) {
                        String spaces=request.getParameter("emailPHP").replaceAll(" ", "+");
                        generateAndSendEmail(email, "http://192.168.1.42:8080/CookitServer/ChangePass.jsp?emailPHP=" + spaces);
                        response.getWriter().write("Se ha enviado un email con el procedimiento a seguir para cambiar de contraseña.");
                        
                    }else{
                        response.getWriter().write("El usuario introducido no se encuentra registrado.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }
    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage generateMailMessage;

    public static void generateAndSendEmail(String email, String mensaje) throws AddressException, MessagingException {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject("Validacion de cuanta");
        String emailBody = mensaje;
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "cookit.validator@gmail.com", "cookitqwerty");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        
                
        return "Short description";
    }// </editor-fold>

}
