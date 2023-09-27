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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Alumno buscarAlumnoId(int id){
    String sql = "SELECT dni, apellido, nombre, fechaN, estado FROM alumno WHERE idAlumno = ?";
    Alumno alumno = null;
    
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
            alumno = new Alumno();
            alumno.setIdAlumno(id);
            alumno.setDni(rs.getInt("dni"));
            alumno.setApellido(rs.getString("apellido"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setFechaNacimiento(rs.getDate("fechaN").toLocalDate());
            alumno.setActivo(rs.getBoolean("estado"));
             
            } else {
            JOptionPane.showMessageDialog(null, "Alumno no encontrado");
            }
            ps.close();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error de busqueda");
        }
    return alumno;
    }
    
    public Alumno buscarAlumnoDni(int dni){
        String sql = "SELECT `idAlumno`, `dni`, `apellido`, `nombre`, `fechaN`, `estado` FROM `alumno` WHERE dni = ?";
        Alumno alumno = null;
        
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, dni);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaN").toLocalDate());
                alumno.setActivo(rs.getBoolean("estado"));
                 
                } else {
                JOptionPane.showMessageDialog(null, "Alumno no encontrado");
                }
                ps.close();
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Error de busqueda");
            }
        return alumno;
        }
    
    public List <Alumno> listarAlumnos(){
    String sql = "SELECT `idAlumno`, `dni`, `apellido`, `nombre`, `fechaN` FROM `alumno`WHERE estado = 1";   
    ArrayList <Alumno> alumnos = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
            Alumno alum = new Alumno();
                alum.setIdAlumno(rs.getInt("idAlumno"));
                alum.setDni(rs.getInt("dni"));
                alum.setApellido(rs.getString("apellido"));
                alum.setNombre(rs.getString("nombre"));
                alum.setFechaNacimiento(rs.getDate("fechaN").toLocalDate());
                alum.setActivo(true);
                alumnos.add(alum);
                
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de mensaje sql");
        }
        
    return alumnos; 
    }
    
    public void eliminarAlumno(int id){
    
    String sql = "UPDATE alumno SET estado= 0 WHERE idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int exito = ps.executeUpdate();
            if (exito == 1){
            JOptionPane.showMessageDialog(null, "Alumno dado de baja");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de mensaje sql(borrado)");
        }
        
    }
    
     public List <Alumno> listarAlumnosInactivos(){
    String sql = "SELECT `idAlumno`, `dni`, `apellido`, `nombre`, `fechaN` FROM `alumno`WHERE estado = 0";   
    ArrayList <Alumno> alumnos = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
            Alumno alum = new Alumno();
                alum.setIdAlumno(rs.getInt("idAlumno"));
                alum.setDni(rs.getInt("dni"));
                alum.setApellido(rs.getString("apellido"));
                alum.setNombre(rs.getString("nombre"));
                alum.setFechaNacimiento(rs.getDate("fechaN").toLocalDate());
                alum.setActivo(false);
                alumnos.add(alum);
                
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de mensaje sql");
        }
        
    return alumnos; 
    }
    
}
