/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogrupo67;

import java.time.LocalDate;
import proyectogrupo67.ADatos.AlumnoData;
import proyectogrupo67.ADatos.MateriaData;
import proyectogrupo67.entidades.Alumno;
import proyectogrupo67.entidades.Materia;

/**
 *
 * @author Asus
 */
public class ProyectoGrupo67 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Alumno alu = new Alumno ("NetBeans", "F", LocalDate.of(1898, 03, 20), true, 23435345);
        //AlumnoData enviar = new AlumnoData();
        //enviar.guardarAlumno(alu);
        //enviar.modificarAlumno(alu);
        //Alumno alum = enviar.buscarAlumnoDni(23435345);
        //System.out.println(alum.getApellido());

        Materia Historia=new Materia(5, "Historia4",1,true);
        MateriaData mat=new MateriaData();
        //mat.guardarMateria(Historia);
        
        //mat.modificarMateria(Historia);
     
        System.out.println(mat.buscarMateria(2));
        
    }
    
}
