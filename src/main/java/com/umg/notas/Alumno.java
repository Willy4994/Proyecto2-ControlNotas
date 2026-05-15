package com.umg.notas;

public class Alumno {
    private int id;
    private String carnet;
    private String nombres;
    private String apellidos;
    private String seccion;
    private Double nota;

    public Alumno(int id, String carnet, String nombres, String apellidos, String seccion, Double nota) {
        this.id = id;
        this.carnet = carnet;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.seccion = seccion;
        this.nota = nota;
    }

    public int getId() { return id; }
    public String getCarnet() { return carnet; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getSeccion() { return seccion; }
    public Double getNota() { return nota; }

    @Override
    public String toString() {
        String notaTexto = nota == null ? "Sin nota" : String.format("%.2f", nota);
        return String.format("ID: %d | Carnet: %s | Nombre: %s %s | Seccion: %s | Nota: %s",
                id, carnet, nombres, apellidos, seccion, notaTexto);
    }
}
