/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conection.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Aspirante;

/**
 *
 * @author Ariake Merino
 */
public class AspiranteDao {

    private DbConnection conn;

    public AspiranteDao(DbConnection conn) {
        this.conn = conn;
    }

    /**
     * Metodo que retorna todos los registros de la tabla usuario ordenados en
     * forma descendente
     *
     * @return una lista que contiene objetos de tipo alumno si la consulta se
     * ejecuta exitosamente, caso contrario retorna null
     * @Exception SQLException
     */
    public List<Aspirante> getAll() {
        try {
            String sql = "SELECT * FROM aspirantes_data ORDER BY id_aspirante desc;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            //Crear una instancia
            List<Aspirante> list = new LinkedList<Aspirante>();
            Aspirante aspirante;

            while (rs.next()) {
                aspirante = new Aspirante();
                aspirante.setId_aspirante(rs.getInt("id_aspirante"));
                aspirante.setNombre(rs.getString("nombre"));
                aspirante.setAp_paterno(rs.getString("ap_paterno"));
                aspirante.setAp_materno(rs.getString("ap_materno"));
                aspirante.setProcedencia(rs.getString("procedencia"));
                aspirante.setCorreo(rs.getString("correo"));
                aspirante.setTelefono(rs.getString("telefono"));
                aspirante.setEspecialidad(rs.getString("especialidad"));
                aspirante.setDireccion(rs.getString("direccion"));
                //Agregamos a la lista el objeto user
                list.add(aspirante);
            }
            return list;
            //Retorna la lista con todos los objetos

        } catch (SQLException e) {
            System.out.println("Error en UserDao.getAll:" + e.getMessage());
            return null;
        }
    }

    public boolean insert(Aspirante aspirante) {
        try {
            String sql = "INSERT INTO aspirantes_data(nombre,ap_paterno,ap_materno,procedencia,correo,telefono,especialidad,direccion) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            if (aspirante.getNombre().length() == 0 || aspirante.getAp_paterno().length() == 0 || aspirante.getAp_materno().length() == 0 || aspirante.getProcedencia().length() == 0 || aspirante.getCorreo().length() == 0 || aspirante.getTelefono().length() == 0 || aspirante.getEspecialidad().length() == 0 || aspirante.getDireccion().length() == 0) {
                return false;
            } else {
                preparedStatement.setString(1, aspirante.getNombre());
                preparedStatement.setString(2, aspirante.getAp_paterno());
                preparedStatement.setString(3, aspirante.getAp_materno());
                preparedStatement.setString(4, aspirante.getProcedencia());
                preparedStatement.setString(5, aspirante.getCorreo());
                preparedStatement.setString(6, aspirante.getTelefono());
                preparedStatement.setString(7, aspirante.getEspecialidad());
                preparedStatement.setString(8, aspirante.getDireccion());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public Aspirante details(int id) {
        try {
            String sql = "SELECT * FROM aspirantes_data WHERE id_aspirante=? LIMIT 1;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            Aspirante aspirante = new Aspirante();

            while (rs.next()) {
                aspirante = new Aspirante();
                aspirante.setId_aspirante(rs.getInt("id_aspirante"));
                aspirante.setNombre(rs.getString("nombre"));
                aspirante.setAp_paterno(rs.getString("ap_paterno"));
                aspirante.setAp_materno(rs.getString("ap_materno"));
                aspirante.setProcedencia(rs.getString("procedencia"));
                aspirante.setCorreo(rs.getString("correo"));
                aspirante.setTelefono(rs.getString("telefono"));
                aspirante.setEspecialidad(rs.getString("especialidad"));
                aspirante.setDireccion(rs.getString("direccion"));
            }
            return aspirante;
            //Retorna la lista con todos los objetos

        } catch (SQLException e) {
            return null;
        }
    }

    public boolean delete(int id) {
        try {
            String sql = "DELETE FROM aspirantes_data WHERE id_aspirante=?;";//se esta armando la cadena de la consulta
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql); //para ejecutar consultas parametrzadas para evtar que inyecten codigo sql a las consultas
            preparedStatement.setInt(1, id);// el valor del identificacdor que recibimos como parametro
            int rows = preparedStatement.executeUpdate();//Ejecuta la consulta lo que traiga de resultado se asigna a rows
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Aspirante aspirante, int id) {
        try {
            String sql = "UPDATE aspirantes_data SET nombre=?,ap_paterno=?,ap_materno=?,procedencia=?,correo=?,telefono=?,especialidad=?,direccion=? WHERE id_aspirante=" + id + "";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, aspirante.getNombre());
            preparedStatement.setString(2, aspirante.getAp_paterno());
            preparedStatement.setString(3, aspirante.getAp_materno());
            preparedStatement.setString(4, aspirante.getProcedencia());
            preparedStatement.setString(5, aspirante.getCorreo());
            preparedStatement.setString(6, aspirante.getTelefono());
            preparedStatement.setString(7, aspirante.getEspecialidad());
            preparedStatement.setString(8, aspirante.getDireccion());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
