/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Comentario;
import model.Receta;
import model.Usuario;

/**
 * Clase creada como capa entre las clases UsuarioDAO,RecetaDAO y ComentarioDAO en el que se reunen todos sus metodos
 * @author billy
 */
public class FactoriaDAO {

    /**
     *
     * @param idReceta
     * @return
     */
    public Usuario getUsuarioByIdReceta(int idReceta){
        UsuarioDAO dao=new UsuarioDAO();
        return dao.getUsuarioByIdReceta(idReceta);
    }
    public boolean activeUser(int id){
        UsuarioDAO dao=new UsuarioDAO();
        return dao.activeUser(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public Usuario getUsuarioById(int id){
         UsuarioDAO dao=new UsuarioDAO();
         return dao.getUsuarioById(id);
     }

    /**
     *
     * @param mail
     * @return
     */
    public Usuario getUsuario(String mail){
         UsuarioDAO dao=new UsuarioDAO();
         return dao.getUsuario(mail);
     }

    /**
     *
     * @param usuario
     */
    public void addUsuario(Usuario usuario) throws SQLException{
        UsuarioDAO dao=new UsuarioDAO();
        dao.addUsuario(usuario);
    }
    public void activate(int idUsuario){
        UsuarioDAO dao=new UsuarioDAO();
        dao.activate(idUsuario);
    }
    public void cambiarContrasenna(String email,String pass){
        UsuarioDAO dao=new UsuarioDAO();
        dao.cambiarContrasenna(email, pass);
    }

    /**
     *
     * @param id
     */
    public void deleteUsuario(int id){
        UsuarioDAO dao=new UsuarioDAO();
        dao.deleteUsuario(id);
    }

    /**
     *
     * @param id
     * @param usuario
     */
    public void updateUsuario(int id, Usuario usuario){
        UsuarioDAO dao=new UsuarioDAO();
        dao.updateUsuario(id, usuario);
    }

    /**
     *
     * @param idUsuario
     * @param idSeguir
     */
    public void seguirUsuario(int idUsuario, int idSeguir){
        UsuarioDAO dao=new UsuarioDAO();
        dao.seguirUsuario(idUsuario, idSeguir);
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getSegidos(int id){
        UsuarioDAO dao=new UsuarioDAO();
        return dao.getSegidos(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getSegidores(int id){
        UsuarioDAO dao=new UsuarioDAO();
        return dao.getSegidores(id);
    }

    /**
     *
     * @param idUsuario
     * @param idSeguido
     */
    public void dejarDeSeguir(int idUsuario, int idSeguido){
        UsuarioDAO dao=new UsuarioDAO();
        dao.dejarDeSeguir(idUsuario, idSeguido);
    }

    /**
     *
     * @param idUsuario
     * @param receta
     */
    public void addReceta(int idUsuario,Receta receta){
        RecetaDAO dao=new RecetaDAO();
        dao.addReceta(idUsuario, receta);
    }

    /**
     *
     * @param idReceta
     * @param receta
     */
    public void updateReceta(int idReceta,Receta receta){
        RecetaDAO dao=new RecetaDAO();
        dao.updateReceta(idReceta, receta);
    }

    /**
     *
     * @param idReceta
     */
    public void deleteReceta(int idReceta){
        RecetaDAO dao=new RecetaDAO();
        dao.deleteReceta(idReceta);
    }

    /**
     *
     * @param idComentario
     * @return
     */
    public Comentario getComentario(int idComentario){
        ComentarioDAO dao=new ComentarioDAO();
        return dao.getComentario(idComentario);
    }

    /**
     *
     * @param idReceta
     * @param comentario
     */
    public void addComentario(int idReceta, Comentario comentario){
         ComentarioDAO dao=new ComentarioDAO();
         dao.addComentario(idReceta, comentario);
     }

    /**
     *
     * @param idComentario
     */
    public void deleteCometnario(int idComentario){
         ComentarioDAO dao=new ComentarioDAO();
         dao.deleteCometnario(idComentario);
     }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getComentarios(int id){
         RecetaDAO dao=new RecetaDAO();
         return dao.getComentarios(id);
     }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getNewsRecetas(int id){
          RecetaDAO dao=new RecetaDAO();
          return dao.getNewsRecetas(id);
      }
    public ArrayList<Integer> getTop(){
        RecetaDAO dao=new RecetaDAO();
        return dao.getTop();
    }
    public ArrayList<Integer> getBusqueda(Receta receta){
        RecetaDAO dao=new RecetaDAO();
        return dao.getBusqueda(receta);
    }

    /**
     *
     * @param idReceta
     * @return
     */
    public Receta getReceta(int idReceta){
          RecetaDAO dao=new RecetaDAO();
          return dao.getReceta(idReceta);
      }

    /**
     *
     * @param idReceta
     * @param valoracion
     */
    public void addValoracion(int idReceta, long valoracion){
           RecetaDAO control=new RecetaDAO();
           control.addValoracion(idReceta, valoracion);
       }
}
    
