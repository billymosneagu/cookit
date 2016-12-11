/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Cookit.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import control.PasswordHash;
import dao.FactoriaDAO;
import dao.RecetaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Receta;

/**
 *
 * @author billy
 */
@WebServlet(name = "RecetaServlet", urlPatterns = {"/RecetaServlet"})
public class RecetaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String op = request.getParameter("op");
        FactoriaDAO control = new FactoriaDAO();
        ObjectMapper mapper = new ObjectMapper();
        PasswordHash cifrado = new PasswordHash();
        RecetaDAO d=new RecetaDAO();
        
        if (op != null) {
            switch (op) {
                case "GetRecetasNews": {
                    int usuarioID = (int) request.getAttribute("usuarioID");
                    ArrayList<Integer> recetas = control.getNewsRecetas(usuarioID);
                    request.setAttribute("recetasNews", recetas);
                    break;
                }
                case "getReceta": {
                    int usuarioID = (int) request.getAttribute("recetaID");
                    Receta receta = control.getReceta(usuarioID);
                    request.setAttribute("recetaDB", receta);
                    break;
                }
                case "addReceta":{
                    int usuarioID = (int) request.getAttribute("usuarioID");
                    Receta receta=(Receta)request.getAttribute("receta");
                    control.addReceta(usuarioID, receta);
                    break;
                }
                case "addValoracion":{
                    int recetaId=(int)request.getAttribute("recetaID");
                    long valoracion=(long)request.getAttribute("valoracion");
                    control.addValoracion(recetaId, valoracion);
                    break;
                }
                case "UpdateReceta":{
                    Receta receta=(Receta)request.getAttribute("receta");
                    control.updateReceta(receta.getId(), receta);
                    break;
                }
                case "top":{
                    request.setAttribute("recetasNews", control.getTop());
                    break;
                }
                case "busqueda":{
                    Receta receta=(Receta)request.getAttribute("receta");
                    request.setAttribute("recetasNews", control.getBusqueda(receta));
                    break;
                }
            }
        }
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
