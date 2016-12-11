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
//import org.neo4j.driver.v1.exceptions.ClientException;

/**
 *
 * @author billy
 */
public class UsuarioDAO {

    /**
     * Nos devuelve un usario a partir del identificador de una de sus recetas
     *
     * @param idReceta
     * @return
     */
    public Usuario getUsuarioByIdReceta(int idReceta) {
        Usuario usuario = new Usuario();
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("MATCH (a)-[r:POST]->(b) where id(b)= ? RETURN id(a) as id, a.nombre as nombre, a.email as email,a.contrasenna as contrasenna, a.imagenDePerfil as imagenDePerfil, a.valoracionMedia as valoracionMedia");
            ps.setInt(1, idReceta);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String contrasenna = rs.getString("contrasenna");
                String email = rs.getString("email");
                long valoracionMedia = rs.getLong("valoracionMedia");
                String imagenDePerfil = rs.getString("imagenDePerfil");
                usuario.setId(id);
                usuario.setNombre(nombre);
                usuario.setContrasenna(contrasenna);
                usuario.setEmail(email);
                usuario.setValoracionMedia(valoracionMedia);
                usuario.setImagenPerfil(imagenDePerfil);
                usuario.setRecetas(getRecetas(id));
                usuario.setSeguidos(getSegidos(id));
                usuario.setSeguidores(getSegidores(id));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuario;
    }
    public boolean activeUser(int id){
        boolean active=false;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where id(u)=? return u.activo");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                active=rs.getBoolean("u.activo");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return active;
    }

    /**
     * Recoge la valoracion enviada, calcula la media y finalmente la añade a la
     * base de datos
     *
     * @param idUsuario
     * @param valoracion
     */
    public void addValoacion(int idUsuario, long valoracion) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("MATCH (u:Usuario) where id(u)= ? set u.valoracionMedia= ?");
            ps.setInt(1, idUsuario);
            ps.setLong(2, valoracion);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * DEvuelve un usuario a partir del email con el que se ha registrado
     *
     * @param mail
     * @return
     */
    public Usuario getUsuario(String mail) {
        Usuario usuario = new Usuario();
        Connection conn = DBConnection.getConnection();
        int id = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where u.email=? return u.nombre as nombre,u.contrasenna as contrasenna,u.email as email,u.valoracionMedia as valoracionMedia, u.imagenDePerfil as imagenDePerfil, id(u) as id");
            ps.setString(1, mail);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String contrasenna = rs.getString("contrasenna");
                String email = rs.getString("email");
                long valoracionMedia = rs.getLong("valoracionMedia");
                String imagenDePerfil = rs.getString("imagenDePerfil");
                usuario.setId(id);
                usuario.setNombre(nombre);
                usuario.setContrasenna(contrasenna);
                usuario.setEmail(email);
                usuario.setValoracionMedia(valoracionMedia);
                usuario.setImagenPerfil(imagenDePerfil);
                usuario.setRecetas(getRecetas(id));
                usuario.setSeguidos(getSegidos(id));
                usuario.setSeguidores(getSegidores(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuario;

    }

    /**
     * Nos devuelve el usuario a partir de su identificador
     *
     * @param id
     * @return
     */
    public Usuario getUsuarioById(int id) {
        Usuario usuario = new Usuario();
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //int id=0;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where id(u)=? return u.nombre as nombre,u.contrasenna as contrasenna,u.email as email,u.valoracionMedia as valoracionMedia, u.imagenDePerfil as imagenDePerfil, id(u) as id");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String contrasenna = rs.getString("contrasenna");
                String email = rs.getString("email");
                long valoracionMedia = rs.getLong("valoracionMedia");
                String imagenDePerfil = rs.getString("imagenDePerfil");
                usuario.setId(id);
                usuario.setNombre(nombre);
                usuario.setContrasenna(contrasenna);
                usuario.setEmail(email);
                usuario.setValoracionMedia(valoracionMedia);
                usuario.setImagenPerfil(imagenDePerfil);
                usuario.setRecetas(getRecetas(id));
                usuario.setSeguidos(getSegidos(id));
                usuario.setSeguidores(getSegidores(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuario;
    }

    /**
     * Añade un usuario a la base de datos
     *
     * @param usuario
     */
    public void addUsuario(Usuario usuario) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("CREATE (u:Usuario {nombre: ? , email: ? , contrasenna: ? , valoracionMedia: 0 , imagenDePerfil: '',activo: false}) RETURN u");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getContrasenna());
            ps.execute();
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void activate(int idUsuario) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where id(u)=? set u.activo=true");
            ps.setInt(1, idUsuario);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cambiarContrasenna(String email, String pass) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where u.email=? set u.contrasenna= ?");
            ps.setString(1, email);
            ps.setString(2, pass);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * Elimina un usario de la bae de datos a prtir de su identificador
     *
     * @param id
     */
    public void deleteUsuario(int id) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where id(u)=? DETACH DELETE u");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * Modifica los cambios que se han realizado al usuario
     *
     * @param id
     * @param usuario
     */
    public void updateUsuario(int id, Usuario usuario) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("match (u:Usuario) where id(u)=? set u.nombre=?, u.email= ?, u.imagenDePerfil= ?");
            ps.setInt(1, id);
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getImagenPerfil());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * A partir de identificador del usuario conectado y el identificador del
     * que quiere segir se crea un vinculo entre los dos usuarios
     *
     * @param idUsuario
     * @param idSeguir
     */
    public void seguirUsuario(int idUsuario, int idSeguir) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("MATCH (a:Usuario), (b:Usuario) WHERE id(a)=? AND id(b)=? CREATE (a)-[:FOLLOW]->(b)");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idSeguir);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * Nos devuelve un listado de identificadores de los usuarios a los que se
     * esta siguiendo
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getSegidos(int id) {
        ArrayList<Integer> seguidos = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("MATCH (a)-[r:FOLLOW]->(b) where id(a)= ? RETURN id(b) ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                int idSeguido = rs.getInt("id(b)");

                seguidos.add(idSeguido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seguidos;
    }

    /**
     * Nos devuelve un listado de identificadores de los usuarios a los que nos
     * estan siguiendo
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getSegidores(int id) {
        ArrayList<Integer> seguidos = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("MATCH (b)-[r:FOLLOW]->(a) where id(a)= ? RETURN id(b) ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                int idSeguido = rs.getInt("id(b)");

                seguidos.add(idSeguido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seguidos;
    }

    /**
     * A partir de identificador del usuario conectado y el identificador del
     * que quiere dejar de seguir se rompe el vinculo entre los dos usuarios
     *
     * @param idUsuario
     * @param idSeguido
     */
    public void dejarDeSeguir(int idUsuario, int idSeguido) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("MATCH (a)-[r:FOLLOW]->(b) where id(a)= ? and id(b)= ? DETACH DELETE r");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idSeguido);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
     * Nos devuelve un listado de identificadores de las recetas que se han
     * publicado
     *
     * @param id
     * @return
     */
    public ArrayList<Integer> getRecetas(int id) {
        ArrayList<Integer> recetas = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("MATCH (a)-[r:POST]->(b) where id(a)= ? RETURN id(b) ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                int idReceta = rs.getInt("id(b)");

                recetas.add(idReceta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return recetas;
    }

}
