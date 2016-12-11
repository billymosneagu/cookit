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
public class Receta {
    private int id;
    private String nombre;
    private String imagen;
    private String pasos;
    private String ingredientes;
    private long valoracionMedia;
    private ArrayList<Integer> comentarios;

    /**
     *
     */
    public Receta() {
    }

    /**
     *
     * @param id
     * @param imagen
     * @param pasos
     * @param ingredientes
     * @param valoracionMedia
     * @param comentarios
     */
    public Receta(int id, String imagen, String pasos, String ingredientes, long valoracionMedia, ArrayList<Integer> comentarios) {
        this.id = id;
        this.imagen = imagen;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
        this.valoracionMedia = valoracionMedia;
        this.comentarios = comentarios;
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
    public String getImagen() {
        return imagen;
    }

    /**
     *
     * @param imagen
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     *
     * @return
     */
    public String getPasos() {
        return pasos;
    }

    /**
     *
     * @param pasos
     */
    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    /**
     *
     * @return
     */
    public String getIngredientes() {
        return ingredientes;
    }

    /**
     *
     * @param ingredientes
     */
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
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
    public ArrayList<Integer> getComentarios() {
        return comentarios;
    }

    /**
     *
     * @param comentarios
     */
    public void setComentarios(ArrayList<Integer> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Receta{" + "id=" + id + ", imagen=" + imagen + ", pasos=" + pasos + ", ingredientes=" + ingredientes + ", valoracionMedia=" + valoracionMedia + ", comentarios=" + comentarios + '}';
    }
    
    
    
}
