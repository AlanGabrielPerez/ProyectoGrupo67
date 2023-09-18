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
   
   
     public ArrayList<Inscripcion> obtenerInscripcionesPorAlumno (int id) {
       ArrayList <Inscripcion> listaInscrip = new ArrayList<>();
       
       String sql = "SELECT * FROM inscripcion WHERE idAlumno = ?";
       
       try {
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           while (rs.next()){
           Inscripcion ins= new Inscripcion ();
           ins.setIdInscripcion(rs.getInt("idInscripcion"));
           ins.setAlumno(aluData.buscarAlumnoId(id));
           ins.setMateria(matData.buscarMateria(rs.getInt("idMateria")));
           ins.setNota(rs.getInt("nota"));
           
           listaInscrip.add(ins);    
           
           }
           ps.close();
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error sql");
       }
       
       return listaInscrip;

     }  

    public List<Alumno> alumnosXMateria (int idMateria) {
        ArrayList <Alumno> aluXmat= new ArrayList<>();
        String sql = "SELECT idAlumno FROM inscripcion WHERE idMateria = ?";
       
       try {
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alu = new Alumno ();
                alu = aluData.buscarAlumnoDni(rs.getInt("idAlumno"));
                aluXmat.add(alu);            
            }
            ps.close();
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error sql");
       }
            
        
        return aluXmat;
    }

    public void borrarInscripcion (int idAlumno, int idMateria){
            
        String sql = "DELETE FROM inscripcion WHERE idAlumno=? AND idMateria=?";
        PreparedStatement ps;
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, idAlumno);
           ps.setInt(2,idMateria);  
           int exito = ps.executeUpdate();
            
            if (exito == 1 ){
                
              JOptionPane.showMessageDialog(null, "Alumno dado de baja ("+matData.buscarMateria(idMateria).getNombre()+").");
               }
           
           ps.close();
           
       } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql");
       }    
    
    }
    
     public List <Materia> materiasNoCursadas(int id){
        String sql = "SELECT idMateria FROM Materia WHERE idMateria NOT IN (SELECT idMateria FROM inscripcion WHERE idAlumno = ?)";
         ArrayList <Materia> Nocursadas = new ArrayList();
   
       try {
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           while (rs.next()){
          Materia mat = matData.buscarMateria(rs.getInt("idMateria"));
          Nocursadas.add(mat);
           }
           ps.close();
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "error en el sql");
       }
     return Nocursadas;
   }
 
     public void actualizarNota (int idAlumno, int idMateria, int nota){
            
        String sql = "UPDATE inscripcion SET nota=? WHERE idAlumno=? and idMateria=?";
        PreparedStatement ps;
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, nota);
           ps.setInt(2, idAlumno);
           ps.setInt(3,idMateria);  
           int exito = ps.executeUpdate();
            
            if (exito == 1 ){
                
              JOptionPane.showMessageDialog(null, "Nota actulizada.");
               }
           
           ps.close();
           
       } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql");
       }    
    
    }
   
}
