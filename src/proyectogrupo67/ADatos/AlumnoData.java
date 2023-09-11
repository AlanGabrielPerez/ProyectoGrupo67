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
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Statement;
import proyectogrupo67.entidades.Alumno;


/**
 *
 * @author Pc
 */
public class AlumnoData {
    
    private Connection con;

    public AlumnoData() {
        con = Conexion.getConnection();
        
    }
    
    public void guardarAlumno(Alumno alumno){
        String sql = "INSERT INTO alumno(dni, apellido, nombre, fechaN, estado) VALUES (?,?,?,?,?)";
      
        try {
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setBoolean(5, alumno.isActivo());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
            alumno.setIdAlumno(rs.getInt(1));
            JOptionPane.showMessageDialog(null, "Alumno guardado");
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println("error en el preparedStatement" + ex.getMessage());
        }
    }
    
    public void modificarAlumno(Alumno alumno){
        
        String sql="UPDATE `alumno` SET `dni`= ? ,apellido=?,nombre=?,fechaN=?,estado=? WHERE idAlumno = ?" ;
        
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setBoolean(5, true);
            ps.setInt(6, alumno.getIdAlumno());
            int exito = ps.executeUpdate();
            
            if (exito == 1 ){
                
              JOptionPane.showMessageDialog(null, "Alumno modificado");
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Update");
        }
        
        
        
    }
    
}
