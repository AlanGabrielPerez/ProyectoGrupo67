/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogrupo67.entidades;

/**
 *
 * @author julian
 */
public class Materia {
    
    private int idMateria;
    private String nombre;
    private int añoMateria;
    private Boolean activo;

    public Materia() {
        
    }

    public Materia(int idMateria, String nombre, int añoMateria, Boolean activo) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.añoMateria = añoMateria;
        this.activo = activo;
    }

    public Materia(String nombre, int añoMateria, Boolean activo) {
        this.nombre = nombre;
        this.añoMateria = añoMateria;
        this.activo = activo;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAñoMateria() {
        return añoMateria;
    }

    public void setAñoMateria(int añoMateria) {
        this.añoMateria = añoMateria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Materia" + "idMateria= " + idMateria + ", Nombre= " + nombre + ", a\u00f1oMateria= " + añoMateria + ", Activo= " + activo + '}';
    }
    
    
}
