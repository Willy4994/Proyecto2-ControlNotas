package com.umg.notas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public void ingresarAlumno(String carnet, String nombres, String apellidos, String seccion) {
        String sql = "INSERT INTO alumnos (carnet, nombres, apellidos, seccion) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carnet);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, seccion);
            ps.executeUpdate();
            System.out.println("Alumno ingresado correctamente.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: ya existe un alumno con ese carnet.");
        } catch (SQLException e) {
            System.out.println("Error al ingresar alumno: " + e.getMessage());
        }
    }

    public void ingresarNota(String carnet, double nota) {
        String sql = "UPDATE alumnos SET nota = ? WHERE carnet = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, nota);
            ps.setString(2, carnet);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Nota ingresada correctamente." : "No se encontro alumno con ese carnet.");
        } catch (SQLException e) {
            System.out.println("Error al ingresar nota: " + e.getMessage());
        }
    }

    public void eliminarAlumno(String dato) {
        List<Alumno> encontrados = buscar(dato);
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron alumnos.");
            return;
        }
        for (Alumno a : encontrados) System.out.println(a);
        System.out.print("Ingrese el ID exacto del alumno a eliminar: ");
        int id = Main.leerEntero();
        System.out.print("Esta seguro de eliminarlo? (S/N): ");
        String respuesta = Main.SC.nextLine().trim();
        if (!respuesta.equalsIgnoreCase("S")) {
            System.out.println("Eliminacion cancelada.");
            return;
        }
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Alumno eliminado correctamente." : "No se elimino ningun registro.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
        }
    }

    public void actualizarAlumno(int id, String nombres, String apellidos, String seccion, Double nota) {
        String sql = "UPDATE alumnos SET nombres=?, apellidos=?, seccion=?, nota=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, seccion);
            if (nota == null) ps.setNull(4, Types.DECIMAL); else ps.setDouble(4, nota);
            ps.setInt(5, id);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Datos actualizados correctamente." : "No se encontro el alumno.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public List<Alumno> buscar(String dato) {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE carnet = ? OR nombres LIKE ? OR apellidos LIKE ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dato);
            ps.setString(2, "%" + dato + "%");
            ps.setString(3, "%" + dato + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return lista;
    }

    public void mostrarPromedios() {
        String sql = "SELECT seccion, AVG(nota) AS promedio FROM alumnos WHERE nota IS NOT NULL GROUP BY seccion";
        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Promedios por seccion:");
            boolean hay = false;
            while (rs.next()) {
                hay = true;
                System.out.printf("Seccion %s: %.2f%n", rs.getString("seccion"), rs.getDouble("promedio"));
            }
            if (!hay) System.out.println("No hay notas registradas.");
        } catch (SQLException e) {
            System.out.println("Error al obtener promedios: " + e.getMessage());
        }
    }

    public List<Alumno> listarPorSeccion(String seccion, String orden) {
        List<Alumno> lista = new ArrayList<>();
        String campoOrden = switch (orden) {
            case "1" -> "carnet";
            case "2" -> "nombres";
            case "3" -> "apellidos";
            case "4" -> "nota";
            default -> "id";
        };
        String sql = "SELECT * FROM alumnos WHERE seccion = ? ORDER BY " + campoOrden;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, seccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }
        return lista;
    }

    private Alumno mapear(ResultSet rs) throws SQLException {
        Double nota = rs.getObject("nota") == null ? null : rs.getDouble("nota");
        return new Alumno(rs.getInt("id"), rs.getString("carnet"), rs.getString("nombres"),
                rs.getString("apellidos"), rs.getString("seccion"), nota);
    }
}
