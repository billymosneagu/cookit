/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import conection.DBConnection;


import model.Usuario;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comentario;
import model.Receta;
//import org.neo4j.driver.v1.exceptions.ClientException;

/**
 *Metodo encargado de gestionar las distintas operaciones que tengan que ver con comentarios
 * @author billy
 */
public class ComentarioDAO {
    /**
     * Nos devuelve un comentario a partir del identificador
     * @param idComentario
     * @return 
     */
    public Comentario getComentario(int idComentario){
        Comentario comentario=new Comentario();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("match (c:Comentario) where id(c)=? return c.texto as texto");
            ps.setInt(1, idComentario);
            rs=ps.executeQuery();
            int id=0;
            while(rs.next()){
                comentario.setTexto(rs.getString("texto"));
            }
            ps=conn.prepareStatement("MATCH (u)-[r:COMENT]->(c) where id(c)= ? RETURN id(u) as id, u.nombre as nombre, u.email as email,u.contrasenna as contrasenna, u.imagenDePerfil as imagenDePerfil, u.valoracionMedia as valoracionMedia");
            ps.setInt(1, idComentario);
             rs=ps.executeQuery();
            while(rs.next()){
                id=rs.getInt("id");
                String nombre=rs.getString("nombre");
                String contrasenna=rs.getString("contrasenna");
                String email=rs.getString("email");
                long valoracionMedia=rs.getLong("valoracionMedia");
                String imagenDePerfil=rs.getString("imagenDePerfil");
                Usuario usuario=new Usuario();
                usuario.setId(id);
                usuario.setNombre(nombre);
                usuario.setContrasenna(contrasenna);
                usuario.setEmail(email);
                usuario.setValoracionMedia(valoracionMedia);
                usuario.setImagenPerfil(imagenDePerfil);
                comentario.setOrigen(usuario);
            }
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ComentarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if(rs!=null){
                    rs.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return comentario;
    }
    /**
     * Añade un comentario vinculandolo con una receta
     * @param idReceta
     * @param comentario 
     */
    public void addComentario(int idReceta, Comentario comentario){
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("CREATE (c:Comentario{ texto: ?, idOrigen: ?}) return id(c)");
            ps.setString(1, comentario.getTexto());
            ps.setInt(2, comentario.getOrigen().getId());
             rs=ps.executeQuery();
            int id=0;
            while(rs.next()){
                id=rs.getInt("id(c)");
            }
            ps=conn.prepareStatement("MATCH (r:Receta), (c:Comentario) where id(r)=? and id(c)=? create (r)-[:HAS]->(c)");
            ps.setInt(1, idReceta);
            ps.setInt(2, id);
            ps.execute();
            ps=conn.prepareStatement("MATCH (r:Comentario), (u:Usuario) where id(r)=? and id(u)=? create (u)-[:COMENT]->(r)");
            ps.setInt(1, id);
            ps.setInt(2, comentario.getOrigen().getId());
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ComentarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if(rs!=null){
                    rs.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Borra un comentario a partir del identificador
     * @param idComentario 
     */
    public void deleteCometnario(int idComentario){
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement("MATCH (c:Comentario) where id(c)=? DETACH delete c");
            ps.setInt(1, idComentario);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ComentarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
