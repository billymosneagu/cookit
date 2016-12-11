/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.auth0.jwt.internal.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author billy
 */
public class Comentario {
    private int id;
    private String texto;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Usuario origen;
    
    /**
     *
     * @param id
     * @param origen
     * @param texto
     */
    public Comentario(int id, Usuario origen, String texto) {
        this.id = id;
        this.origen = origen;
        this.texto = texto;
    }

    /**
     *
     */
    public Comentario() {
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
    public Usuario getOrigen() {
        return origen;
    }

    /**
     *
     * @param origen
     */
    public void setOrigen(Usuario origen) {
        this.origen = origen;
    }

    /**
     *
     * @return
     */
    public String getTexto() {
        return texto;
    }

    /**
     *
     * @param texto
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Comentario{" + "id=" + id + ", origen=" + origen + ", texto=" + texto + '}';
    }

    
    
    
    
}
