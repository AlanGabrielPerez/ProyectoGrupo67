/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogrupo67.ADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Statement;
import proyectogrupo67.entidades.Materia;

/**
 *
 * @author julian
 */
public class MateriaData {

    private Connection con = null;

    //Constructor
    public MateriaData() {

        con = Conexion.getConnection();

    }

    public void guardarMateria(Materia materia) {

        try {
            String sql = "INSERT INTO materia (nombre,año,activo)"
                    + "VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAñoMateria());
            ps.setBoolean(3, materia.getActivo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                materia.setIdMateria(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Materia agregada exitosamente!");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia");
        }

    }

    public void modificarMateria(Materia materia) {

        try {
            String sql = "UPDATE materia SET nombre=?,año=?,activo=? WHERE idMateria=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAñoMateria());
            ps.setBoolean(3, materia.getActivo());
            ps.setInt(4, materia.getIdMateria());
            int exito = ps.executeUpdate();

            if (exito == 1) {

                JOptionPane.showMessageDialog(null, "Materia modificada exitosamente!");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Update");

        }

    }

    public Materia buscarMateria(int idMateria) {
        
        Materia materia = new Materia();

        try {
            String sql = "SELECT nombre, año FROM materia WHERE idMateria = ? AND activo = 1";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);

            ResultSet rs = ps.executeQuery();
            
            

            if (rs.next()) {

                
                materia.setIdMateria(idMateria);
                materia.setNombre(rs.getString("nombre"));
                materia.setAñoMateria(rs.getInt("año"));
                materia.setActivo(Boolean.TRUE);

            } else {
                JOptionPane.showMessageDialog(null, "Materia no encontrada");

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Update");

        }

        return materia;

    }

}
