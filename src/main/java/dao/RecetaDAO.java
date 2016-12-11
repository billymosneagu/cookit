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
import model.Receta;
//import org.neo4j.driver.v1.exceptions.ClientException;

/**
 *Metodo encargado de gestionar las distintas operaciones que tengan que ver con recetas
 * @author billy
 */
public class RecetaDAO {
    /**
     * Devuelve receta a partir de su identificador
     * @param idReceta
     * @return 
     */
    public Receta getReceta(int idReceta){
        Receta receta=new Receta();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try {
             ps=conn.prepareStatement("match(r:Receta) where id(r)=? return r.nombre as nombre, r.ingredientes as ingredientes, r.imagen as imagen, r.pasos as pasos, r.valoracionMedia as valoracionMedia;");
            ps.setInt(1, idReceta);
             rs=ps.executeQuery();
            while(rs.next()){
                String nombre=rs.getString("nombre");
                String ingredientes=rs.getString("ingredientes");
                String imagen=rs.getString("imagen");
                String pasos=rs.getString("pasos");
                long valoracionMedia=rs.getLong("valoracionMedia");
                receta.setId(idReceta);
                receta.setNombre(nombre);
                receta.setImagen(imagen);
                receta.setIngredientes(ingredientes);
                receta.setPasos(pasos);
                receta.setValoracionMedia(valoracionMedia);
                receta.setComentarios(getComentarios(idReceta));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return receta ;
        
    }
    /**
     * Añade una receta a la base de datos
     * @param idUsuario
     * @param receta 
     */
    public void addReceta(int idUsuario,Receta receta){
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("CREATE (r:Receta{nombre: ?, imagen: ?, ingredientes: ?, pasos: ?, valoracionMedia: ?}) return id(r)");
            ps.setString(1, receta.getNombre());
            ps.setString(2, receta.getImagen());
            ps.setString(3, receta.getIngredientes());
            ps.setString(4, receta.getPasos());
            ps.setLong(5, receta.getValoracionMedia());
             rs=ps.executeQuery();
            int id=0;
            while(rs.next()){
                id=rs.getInt("id(r)");
            }
            ps=conn.prepareStatement("MATCH (a:Usuario), (r:Receta) WHERE id(a)=? and id(r)=? CREATE (a)-[:POST]->(r)");
            ps.setInt(1, idUsuario);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
     * Aplica los cambios realizados sobre una receta
     * @param idReceta
     * @param receta 
     */
    public void updateReceta(int idReceta,Receta receta){
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement("MATCH (r:Receta) where id(r)= ? set r.nombre= ?, r.imagen= ?, r.ingredientes= ?, r.pasos= ?, r.valoracionMedia= ?");
            ps.setInt(1, idReceta);
            ps.setString(2, receta.getNombre());
            ps.setString(3, receta.getImagen());
            ps.setString(4, receta.getIngredientes());
            ps.setString(5, receta.getPasos());
            ps.setLong(6, receta.getValoracionMedia());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
               
                if (ps != null) {
                    ps.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * ELimina una receta de la base de datos
     * @param idReceta 
     */
    public void deleteReceta(int idReceta){
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement("MATCH (r:Receta) where id(r)= ? DETACH delete r");
            ps.setInt(1, idReceta);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    }/**
     * Devuelve una lista de identificadores de los comentarios que se han hecho a una receta 
     * @param id
     * @return 
     */
    public ArrayList<Integer> getComentarios(int id){
        ArrayList<Integer> comentarios=new ArrayList<>();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("MATCH (a)-[r:HAS]->(b) where id(a)= ? RETURN id(b) ");
            ps.setInt(1, id);
             rs=ps.executeQuery();
            while(rs.next()){
                int idComentario=rs.getInt("id(b)");
                comentarios.add(idComentario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
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
        return comentarios;
    }
    /**
     * Devuelve una lista de identificadores de las recetas publicadas por usuarioas a los que se esta siguiendo
     * @param id
     * @return 
     */
    public ArrayList<Integer> getNewsRecetas(int id){
        ArrayList<Integer> recetas=new ArrayList<>();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("MATCH (a)-[:FOLLOW]->(b), (b)-[:POST]->(c) where id(a)=? RETURN id(c);");
            ps.setInt(1, id);
             rs=ps.executeQuery();
            while(rs.next()){
                int idReceta=rs.getInt("id(c)");
                recetas.add(idReceta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return recetas;
        
    }
    public ArrayList<Integer> getTop(){
        ArrayList<Integer> recetas=new ArrayList<>();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("match (u:Receta) where u.valoracionMedia>0  return id(u) as id  ORDER BY id(u) DESC");
            
             rs=ps.executeQuery();
            while(rs.next()){
                int idReceta=rs.getInt("id");
                recetas.add(idReceta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return recetas;
        
    }
    public ArrayList<Integer> getBusqueda(Receta receta){
        ArrayList<Integer> recetas=new ArrayList<>();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("MATCH (r:Receta) WHERE r.nombre =~ '(?i).*p.*' RETURN id(r) as id");
             Statement stmt=conn.createStatement();
             rs=stmt.executeQuery("MATCH (r:Receta) WHERE r.nombre=~'.*(?i)"+receta.getNombre()+".*' or r.ingredientes=~'.*(?i)"+receta.getIngredientes()+".*' or r.pasos=~'.*(?i)"+receta.getPasos()+".*' RETURN id(r) as id");
            while(rs.next()){
                int idReceta=rs.getInt("id");
                recetas.add(idReceta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecetaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return recetas;
        
    }
    /**
     * Recoge la valoracion enviada, calcula la media y finalmente la añade a la base de datos
     * @param idReceta
     * @param valoracion 
     */
    public void addValoracion(int idReceta, long valoracion){
        Receta receta=new Receta();
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             ps=conn.prepareStatement("match(r:Receta) where id(r)=? return r.nombre as nombre, r.ingredientes as ingredientes, r.imagen as imagen, r.pasos as pasos, r.valoracionMedia as valoracionMedia;");
            ps.setInt(1, idReceta);
             rs=ps.executeQuery();
            while(rs.next()){
                String nombre=rs.getString("nombre");
                String ingredientes=rs.getString("ingredientes");
                String imagen=rs.getString("imagen");
                String pasos=rs.getString("pasos");
                long valoracionMedia=rs.getLong("valoracionMedia");
                receta.setId(idReceta);
                receta.setValoracionMedia(valoracionMedia);
            }
            long media=0;
            if(receta.getValoracionMedia()!=0){
               media=(receta.getValoracionMedia()+valoracion)/2;
            }else{
                media=valoracion;
            }
            
            ps=conn.prepareStatement("MATCH (r:Receta) where id(r)= ? set r.valoracionMedia= ?");
            ps.setInt(1, idReceta);
            ps.setLong(2, media);
            ps.execute();
            UsuarioDAO controlousuario=new UsuarioDAO();
            Usuario usuario=controlousuario.getUsuarioByIdReceta(idReceta);
            if(usuario.getValoracionMedia()==0){
                controlousuario.addValoacion(usuario.getId(), valoracion);
            }else{
                long mediausuario=(usuario.getValoracionMedia()+valoracion)/2;
                controlousuario.addValoacion(usuario.getId(), mediausuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
}
