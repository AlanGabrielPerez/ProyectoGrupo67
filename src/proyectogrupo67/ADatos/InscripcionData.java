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
import proyectogrupo67.entidades.Inscripcion;

/**
 *
 * @author Pc
 */
public class InscripcionData {
   private Connection con;
   private MateriaData matData;
   private AlumnoData aluData;

    public InscripcionData() {
    }
   
   public void guardarMateria (Inscripcion ins){
   String sql = "INSERT INTO inscripcion(nota, idAlumno, idMateria) VALUES (?,?,?)";
   
       try {
           PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           AlumnoData ad = new AlumnoData();
           MateriaData md = new MateriaData();
           ps.setDouble(1, ins.getNota());
           ps.setInt(2, ad.buscarAlumnoDni(ins.getAlumno().getDni()).getIdAlumno());
           ps.setInt(3, md.buscarMateria(ins.getMateria().getIdMateria()).getIdMateria());
           ps.executeUpdate();
           
           ResultSet rs = ps.getGeneratedKeys();
           if (rs.next()){
            ins.setIdInscripcion(rs.getInt(1));
            JOptionPane.showMessageDialog(null, "Inscripcion guardada");
           }
           ps.close();
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error en el sql");
       }
   
   
   }
}
