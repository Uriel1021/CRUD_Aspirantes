/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import conection.DbConnection;
import dao.AspiranteDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.Aspirante;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.swing.JOptionPane;

/**
 *
 * @author Ariake Merino
 */
public class AspiranteController extends HttpServlet {

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
        String action = request.getParameter("action");

        switch (action) {
            case "list":
                listarAspirantes(request, response);
                break;
            case "details":
                detalle(request, response);
                break;
            case "delete":
                eliminar(request, response);
                break;
        }
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

        String action = request.getParameter("action");

        switch (action) {
            case "store":
                nuevo(request, response);
                break;
            case "update":
                actualizar(request, response);
                break;

        }
    }

    protected void listarAspirantes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DbConnection cx = new DbConnection();
        AspiranteDao aspirantesDao = new AspiranteDao(cx);
        List<Aspirante> lista = aspirantesDao.getAll();
        cx.disconnect();
        String json = new Gson().toJson(lista);
        response.getWriter().write(json);
    }

    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Establecer conexion con la base de datos
        DbConnection cx = new DbConnection();
        AspiranteDao aspiranteDao = new AspiranteDao(cx);
        //Recuperar los datos 
        String nombre = request.getParameter("nombre");
        String apPaterno = request.getParameter("appaterno");
        String apMaterno = request.getParameter("apmaterno");
        String procedencia = request.getParameter("procedencia");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String especialidad = request.getParameter("especialidad");
        String direccion = request.getParameter("direccion");
        
        //Crear un objeto y agregar los datos
        Aspirante aspirante = new Aspirante();
        aspirante.setNombre(nombre);
        aspirante.setAp_paterno(apPaterno);
        aspirante.setAp_materno(apMaterno);
        aspirante.setProcedencia(procedencia);
        aspirante.setCorreo(correo);
        aspirante.setTelefono(telefono);
        aspirante.setEspecialidad(especialidad);
        aspirante.setDireccion(direccion);
        //insertar los datos
        boolean respuesta = aspiranteDao.insert(aspirante);
        //desconectar
        cx.disconnect();
        JsonObject json = new JsonObject();
        if (respuesta == true) {
            json.addProperty("respuesta", Boolean.TRUE);
        } else {
            json.addProperty("respuesta", Boolean.FALSE);
        }
        response.getWriter().print(json);
    }

    protected void detalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recuperar el id del aspirante
        int id_aspirante = Integer.parseInt(request.getParameter("id"));
        //Establecer conexion con la base de datos
        DbConnection cx = new DbConnection();
        AspiranteDao aspiranteDao = new AspiranteDao(cx);
        //Recuperar los datos 
        Aspirante aspirante = new Aspirante();
        aspirante = aspiranteDao.details(id_aspirante);
        cx.disconnect();
        String json = new Gson().toJson(aspirante);
        response.getWriter().write(json);
    }

    protected void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id_aspirante = Integer.parseInt(request.getParameter("id"));
        DbConnection cx = new DbConnection();
        AspiranteDao aspiranteDao = new AspiranteDao(cx);
        boolean respuesta = aspiranteDao.delete(id_aspirante);
        //desconectar
        cx.disconnect();
        JsonObject json = new JsonObject();
        if (respuesta == true) {
            json.addProperty("respuesta", Boolean.TRUE);
        } else {
            json.addProperty("respuesta", Boolean.FALSE);
        }
        response.getWriter().print(json);

    }
    
    
    
    
    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Establecer conexion con la base de datos
        DbConnection cx = new DbConnection();
        AspiranteDao aspiranteDao = new AspiranteDao(cx);
        //Recuperar los datos 
        int id_aspirante = Integer.parseInt(request.getParameter("id_aspirante"));
        String nombre = request.getParameter("nombre");
        String apPaterno = request.getParameter("appaterno");
        String apMaterno = request.getParameter("apmaterno");
        String procedencia = request.getParameter("procedencia");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String especialidad = request.getParameter("especialidad");
        String direccion = request.getParameter("direccion");
        //Crear un objeto y agregar los datos
        Aspirante aspirante = new Aspirante();
        aspirante.setNombre(nombre);
        aspirante.setAp_paterno(apPaterno);
        aspirante.setAp_materno(apMaterno);
        aspirante.setProcedencia(procedencia);
        aspirante.setCorreo(correo);
        aspirante.setTelefono(telefono);
        aspirante.setEspecialidad(especialidad);
        aspirante.setDireccion(direccion);
        //insertar los datos
        boolean respuesta = aspiranteDao.update(aspirante,id_aspirante);
        //desconectar
        cx.disconnect();
        JsonObject json = new JsonObject();
        if (respuesta == true) {
            json.addProperty("respuesta", Boolean.TRUE);
        } else {
            json.addProperty("respuesta", Boolean.FALSE);
        }
        response.getWriter().print(json);
    }

}
