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

}
