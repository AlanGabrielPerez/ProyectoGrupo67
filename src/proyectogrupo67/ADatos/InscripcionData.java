/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogrupo67.ADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Statement;
import proyectogrupo67.entidades.*;

/**
 *
 * @author Pc
 */
public class InscripcionData {
   private Connection con;
   private MateriaData matData = new MateriaData();
   private AlumnoData aluData = new AlumnoData();

    public InscripcionData() {
        con = Conexion.getConnection();
         aluData = new AlumnoData(); 
         matData = new MateriaData(); 
        
    }
    
    
   
   public void guardarInscripcion(Inscripcion ins){
   String sql = "INSERT INTO `inscripcion`(`nota`, `idAlumno`, `idMateria`) VALUES (?,?,?)";
   
       try {
          PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           ps.setDouble(1, ins.getNota());
           ps.setInt(2,ins.getAlumno().getIdAlumno());
           ps.setInt(3, ins.getMateria().getIdMateria());
           ps.executeUpdate();
           ResultSet rs = ps.getGeneratedKeys();
           if (rs.next()){
               ins.setIdInscripcion(rs.getInt(1));
               JOptionPane.showMessageDialog(null, "Inscripcion guardada");
           }
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error en el sql");
       }
   

   }
   
   public List <Materia> obtenerMateriasCursadas(int id){
   String sql = "SELECT i.iDmateria, nombre, año, activo FROM inscripcion i, materia m WHERE i.idMateria = m.idMateria AND i.idAlumno = ?";
   ArrayList <Materia> cursadas = new ArrayList();
   
       try {
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           while (rs.next()){
          Materia materia = new Materia ();
          materia.setIdMateria(rs.getInt("idMateria"));
          materia.setAñoMateria(rs.getInt("año"));
          materia.setNombre(rs.getString("nombre"));
          materia.setActivo(rs.getBoolean("activo"));
          cursadas.add(materia);
           }
           
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "error en el sql");
       }
     return cursadas;
   }
   
   public List <Inscripcion> obtenerInscripciones(){
    String sql = "SELECT * FROM `inscripcion`";
    ArrayList <Inscripcion> inscripciones = new ArrayList();
       try {
           PreparedStatement ps = con.prepareStatement(sql);
           
           ResultSet rs = ps.executeQuery();
           while (rs.next()){
               
               Inscripcion ins = new Inscripcion();
               
               ins.setIdInscripcion(rs.getInt("idInscripcion"));
               ins.setAlumno(aluData.buscarAlumnoId(rs.getInt("idAlumno")));
               ins.setMateria(matData.buscarMateria(rs.getInt("idMateria")));
               ins.setNota(rs.getInt("nota"));
           }
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "error en el sql" + ex.getMessage());
       }
       
       return inscripciones;
   } 
   
   
   
}
