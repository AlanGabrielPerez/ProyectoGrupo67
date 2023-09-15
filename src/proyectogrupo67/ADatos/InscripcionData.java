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
}
