# Proyecto 2 - Control de Notas de Alumnos

Aplicación de consola en Java con conexión a MySQL para controlar alumnos y notas de Programación 1 Secciones A y B.

## Requisitos
- Java JDK 17 o superior
- Maven
- MySQL Server

## Instalación de base de datos
1. Abrir MySQL Workbench o consola MySQL.
2. Ejecutar el archivo: `src/main/resources/database.sql`.
3. Si tu usuario de MySQL tiene contraseña, editar `DBConnection.java`.

## Ejecución
Desde la carpeta del proyecto:

```bash
mvn clean compile exec:java
```

## Menú implementado
1. Ingreso de Alumnos
2. Ingreso de Notas
3. Eliminar Alumnos
4. Actualizar datos y notas de alumnos
5. Buscar alumnos por Carnet o por Nombre
6. Obtener Promedios
7. Listar Alumnos
8. Salir

También incluye ordenamiento por carnet, nombre, apellidos o notas en la opción de listar alumnos.
