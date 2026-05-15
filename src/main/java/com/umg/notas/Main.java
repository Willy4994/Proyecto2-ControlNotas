package com.umg.notas;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static final Scanner SC = new Scanner(System.in);
    private static final AlumnoDAO dao = new AlumnoDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> ingresoAlumnos();
                case 2 -> ingresoNotas();
                case 3 -> eliminarAlumnos();
                case 4 -> actualizarDatosNotas();
                case 5 -> buscarAlumnos();
                case 6 -> dao.mostrarPromedios();
                case 7 -> listarAlumnos();
                case 8 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 8);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Ingreso de Alumnos");
        System.out.println("2. Ingreso de Notas");
        System.out.println("3. Eliminar Alumnos");
        System.out.println("4. Actualizar datos y notas de alumnos");
        System.out.println("5. Buscar alumnos por Carnet o por Nombre");
        System.out.println("6. Obtener Promedios");
        System.out.println("7. Listar Alumnos");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void ingresoAlumnos() {
        System.out.print("Carnet: ");
        String carnet = SC.nextLine().trim();
        System.out.print("Nombres: ");
        String nombres = SC.nextLine().trim();
        System.out.print("Apellidos: ");
        String apellidos = SC.nextLine().trim();
        String seccion = leerSeccion();
        if (carnet.isBlank() || nombres.isBlank() || apellidos.isBlank()) {
            System.out.println("Carnet, nombres y apellidos son obligatorios.");
            return;
        }
        dao.ingresarAlumno(carnet, nombres, apellidos, seccion);
    }

    private static void ingresoNotas() {
        System.out.print("Carnet del alumno: ");
        String carnet = SC.nextLine().trim();
        System.out.print("Nota de 0 a 100: ");
        double nota = leerDouble();
        if (nota < 0 || nota > 100) {
            System.out.println("La nota debe estar entre 0 y 100.");
            return;
        }
        dao.ingresarNota(carnet, nota);
    }

    private static void eliminarAlumnos() {
        System.out.print("Ingrese carnet o nombre a buscar: ");
        dao.eliminarAlumno(SC.nextLine().trim());
    }

    private static void actualizarDatosNotas() {
        System.out.print("Ingrese carnet o nombre a buscar: ");
        String dato = SC.nextLine().trim();
        List<Alumno> encontrados = dao.buscar(dato);
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron alumnos.");
            return;
        }
        for (Alumno a : encontrados) System.out.println(a);
        System.out.print("Ingrese el ID exacto del alumno a actualizar: ");
        int id = leerEntero();
        System.out.print("Nuevos nombres: ");
        String nombres = SC.nextLine().trim();
        System.out.print("Nuevos apellidos: ");
        String apellidos = SC.nextLine().trim();
        String seccion = leerSeccion();
        System.out.print("Nueva nota, o escriba -1 para dejar sin nota: ");
        double notaIngresada = leerDouble();
        Double nota = notaIngresada < 0 ? null : notaIngresada;
        dao.actualizarAlumno(id, nombres, apellidos, seccion, nota);
    }

    private static void buscarAlumnos() {
        System.out.print("Ingrese carnet, nombre o apellido: ");
        List<Alumno> lista = dao.buscar(SC.nextLine().trim());
        if (lista.isEmpty()) System.out.println("No se encontraron alumnos.");
        else lista.forEach(System.out::println);
    }

    private static void listarAlumnos() {
        String seccion = leerSeccion();
        System.out.println("Ordenar por: 1.Carnet 2.Nombre 3.Apellidos 4.Notas 5.Sin orden especial");
        System.out.print("Seleccione orden: ");
        String orden = SC.nextLine().trim();
        List<Alumno> lista = dao.listarPorSeccion(seccion, orden);
        if (lista.isEmpty()) System.out.println("No hay alumnos en la seccion indicada.");
        else lista.forEach(System.out::println);
    }

    private static String leerSeccion() {
        while (true) {
            System.out.print("Seccion A o B: ");
            String seccion = SC.nextLine().trim().toUpperCase();
            if (seccion.equals("A") || seccion.equals("B")) return seccion;
            System.out.println("Seccion no valida. Debe ser A o B.");
        }
    }

    public static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero entero valido: ");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
