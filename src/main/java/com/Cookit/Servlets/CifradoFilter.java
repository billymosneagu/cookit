/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Cookit.Servlets;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import conection.DBConnection;
import control.PasswordHash;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import model.Comentario;
import model.Receta;
import model.Usuario;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author billy
 */
@WebFilter(filterName = "CifradoFilter", urlPatterns = {"/*"})
public class CifradoFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    /**
     *
     */
    public CifradoFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CifradoFilter:DoBeforeProcessing");
        }
        DBConnection.getConnection();
        ObjectMapper mapper = new ObjectMapper();
        PasswordHash cifrado = new PasswordHash();
        String usuarioPHPString=request.getParameter("usuarioPHP");
        String emailPHPString=request.getParameter("emailPHP");
        String usuarioString = request.getParameter("usuario");
        String usuarioIdString = request.getParameter("usuarioID");
        String seguidoIdString = request.getParameter("seguidoID");
        String recetaID = request.getParameter("recetaID");
        String comentarioID = request.getParameter("comentarioID");
        String receta = request.getParameter("receta");
        String comentario = request.getParameter("comentario");
        String valoracion =request.getParameter("valoracion");
        String usuarioEmail=request.getParameter("usuarioEmail");
        if(usuarioEmail!=null){
            try {
                byte[] usuarioEmailSinCifrar = Base64.decodeBase64(usuarioEmail);
                String usuarioEmailDescifrado = cifrado.descifra(usuarioEmailSinCifrar, "clave");
                request.setAttribute("usuarioEmail", usuarioEmailDescifrado);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(usuarioPHPString!=null){
            String d=usuarioPHPString.replaceAll(" ","+");
            
            String usuarioDescifrado=cifrado.decrypt(d, "qwertyuiopasdfgh", "qwertyuiopasdfgh");
            
            Usuario usuarioObjecto = mapper.readValue(usuarioDescifrado, new TypeReference<Usuario>() {
                });
            request.setAttribute("usuarioPHP", usuarioObjecto);
        }
        if(emailPHPString!=null){
            String replaces=emailPHPString.replaceAll(" ", "+");
            String email=cifrado.decrypt(replaces,  "qwertyuiopasdfgh", "qwertyuiopasdfgh");
            String comillas=email.replaceAll("\"", "");
            request.setAttribute("emailPHP", comillas);
        }
        
        if(valoracion!=null){
            try {
                byte[] valoracionSinCifrar = Base64.decodeBase64(valoracion);
                String valoracionDescifrado = cifrado.descifra(valoracionSinCifrar, "clave");
                float f=Float.parseFloat(valoracionDescifrado);
                request.setAttribute("valoracion", (long)f);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (comentario != null) {
            try {
                byte[] comentarioSinCifrar = Base64.decodeBase64(comentario);
                String comentarioDescifrado = cifrado.descifra(comentarioSinCifrar, "clave");
                Comentario comentarioObjecto = mapper.readValue(comentarioDescifrado, new TypeReference<Comentario>() {
                });
                request.setAttribute("comentario", comentarioObjecto);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (usuarioString != null) {
            try {
                byte[] usaurioSinCifrar = Base64.decodeBase64(usuarioString);
                String usuarioDescifrado = cifrado.descifra(usaurioSinCifrar, "clave");
                Usuario usuarioObjecto = mapper.readValue(usuarioDescifrado, new TypeReference<Usuario>() {
                });
                request.setAttribute("usuario", usuarioObjecto);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (usuarioIdString != null) {
            try {
                String d=usuarioIdString.replaceAll(" ","+");
                byte[] usaurioSinCifrar = Base64.decodeBase64(d);
                String usuarioDescifrado = cifrado.descifra(usaurioSinCifrar, "clave");
                request.setAttribute("usuarioID", Integer.valueOf(usuarioDescifrado));
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (seguidoIdString != null) {
            try {
                byte[] seguidoSinCifrar = Base64.decodeBase64(seguidoIdString);
                String seguidoDescifrado = cifrado.descifra(seguidoSinCifrar, "clave");
                request.setAttribute("seguidoID", Integer.valueOf(seguidoDescifrado));
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (recetaID != null) {
            try {
                byte[] recetaSinCifrar = Base64.decodeBase64(recetaID);
                String recetaDescifrado = cifrado.descifra(recetaSinCifrar, "clave");
                request.setAttribute("recetaID", Integer.valueOf(recetaDescifrado));
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (comentarioID != null) {
            try {
                byte[] comentarioSinCifrar = Base64.decodeBase64(comentarioID);
                String comentarioDescifrado = cifrado.descifra(comentarioSinCifrar, "clave");
                request.setAttribute("comentarioID", Integer.valueOf(comentarioDescifrado));
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (receta != null) {
            try {
                byte[] recetaSinCifrar = Base64.decodeBase64(receta);
                String recetaDescifrada = cifrado.descifra(recetaSinCifrar, "clave");
                Receta recetaObject = mapper.readValue(recetaDescifrada, new TypeReference<Receta>() {
                });

                request.setAttribute("receta", recetaObject);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CifradoFilter:DoAfterProcessing");
        }
        DBConnection.closeConnection();
        PasswordHash cifrado = new PasswordHash();
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = (Usuario) request.getAttribute("usuarioDB");
        ArrayList<Integer> recetasNews = (ArrayList<Integer>) request.getAttribute("recetasNews");
        Receta receta = (Receta) request.getAttribute("recetaDB");
        Comentario comentario = (Comentario) request.getAttribute("comentarioDB");

        if (usuario != null) {
            try {
                String usuarioString = mapper.writeValueAsString(usuario);
                byte[] usuarioCifrada = cifrado.cifra(usuarioString, "clave");
                String usuarioBase64 = new String(Base64.encodeBase64(usuarioCifrada));
                mapper.writeValue(response.getOutputStream(), usuarioBase64);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (recetasNews != null) {
            try {
                String recetasString = mapper.writeValueAsString(recetasNews);
                byte[] recetasCifrado = cifrado.cifra(recetasString, "clave");

                String recetasBase64 = new String(Base64.encodeBase64(recetasCifrado));
                mapper.writeValue(response.getOutputStream(), recetasBase64);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (receta != null) {

            try {
                String recetaString = mapper.writeValueAsString(receta);
                byte[] recetasCifrado = cifrado.cifra(recetaString, "clave");
                String recetaBase64 = new String(Base64.encodeBase64(recetasCifrado));
                mapper.writeValue(response.getOutputStream(), recetaBase64);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (comentario != null) {
            try {
                String comentarioString = mapper.writeValueAsString(comentario);
                byte[] comentarioCifrado = cifrado.cifra(comentarioString, "clave");
                String comentarioBase64 = new String(Base64.encodeBase64(comentarioCifrado));
                mapper.writeValue(response.getOutputStream(), comentarioBase64);
            } catch (Exception ex) {
                Logger.getLogger(CifradoFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("CifradoFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     * @return 
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("CifradoFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     * @return 
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("CifradoFilter()");
        }
        StringBuffer sb = new StringBuffer("CifradoFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    /**
     *
     * @param msg
     */
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
