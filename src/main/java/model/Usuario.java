/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 *
 * @author billy
 */
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contrasenna;
    private long valoracionMedia;
    private String imagenPerfil;
    private ArrayList<Integer> recetas;
    private ArrayList<Integer> seguidores;
    private ArrayList<Integer> seguidos;

    /**
     *
     * @param id
     * @param nombre
     * @param email
     * @param contrasenna
     * @param valoracionMedia
     * @param imagenPerfil
     * @param recetas
     * @param seguidores
     * @param seguidos
     */
    public Usuario(int id, String nombre, String email,String contrasenna, long valoracionMedia, String imagenPerfil, ArrayList<Integer> recetas, ArrayList<Integer> seguidores, ArrayList<Integer> seguidos) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenna=contrasenna;
        this.email = email;
        this.valoracionMedia = valoracionMedia;
        this.imagenPerfil = imagenPerfil;
        this.recetas = recetas;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
    }

    /**
     *
     */
    public Usuario() {
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getContrasenna() {
        return contrasenna;
    }

    /**
     *
     * @param contrasenna
     */
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    
    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public long getValoracionMedia() {
        return valoracionMedia;
    }

    /**
     *
     * @param valoracionMedia
     */
    public void setValoracionMedia(long valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    /**
     *
     * @return
     */
    public String getImagenPerfil() {
        return imagenPerfil;
    }

    /**
     *
     * @param imagenPerfil
     */
    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getRecetas() {
        return recetas;
    }

    /**
     *
     * @param recetas
     */
    public void setRecetas(ArrayList<Integer> recetas) {
        this.recetas = recetas;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getSeguidores() {
        return seguidores;
    }

    /**
     *
     * @param seguidores
     */
    public void setSeguidores(ArrayList<Integer> seguidores) {
        this.seguidores = seguidores;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getSeguidos() {
        return seguidos;
    }

    /**
     *
     * @param seguidos
     */
    public void setSeguidos(ArrayList<Integer> seguidos) {
        this.seguidos = seguidos;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre +", contraseña=" + contrasenna + ", email=" + email + ", valoracionMedia=" + valoracionMedia + ", imagenPerfil=" + imagenPerfil + ", recetas=" + recetas + ", seguidores=" + seguidores + ", seguidos=" + seguidos + '}';
    }

}
