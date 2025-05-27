# 📚 Primer Parcial — Gestión de Biblioteca (Java + JDBC + H2)

Este proyecto es una aplicación de consola desarrollada en Java que permite gestionar autores y libros con persistencia en base de datos H2.

---

## ✅ Funcionalidades

- ABM (CRUD) completo para Autores y Libros
- Listar libros por autor
- Eliminar autor y sus libros (transacción)
- Validación de entradas (números y texto)
- Logs de operaciones con Log4j2
- Uso de clase genérica `GenericDAO<T>`

---

## 🛠️ Tecnologías utilizadas

- Java
- JDBC
- Base de datos H2 (modo archivo)
- Gradle
- Log4j2
- Arquitectura en capas (`model`, `dao`, `dao.impl`, `util`, `main`)

---

## ⚙️ ¿Cómo ejecutar el proyecto?

1. Clonar o descomprimir el proyecto
2. Abrir en IntelliJ IDEA o cualquier IDE compatible con Gradle
3. Asegurarse de tener configurado:
   - Java 17 o superior
   - Gradle sincronizado

4. Ejecutar desde la clase principal:

```
org.example.Main
```

✅ La base de datos se generará automáticamente en la carpeta:
```
./data/biblioteca.mv.db
```

---

## 🔍 Acceder a la base de datos desde la consola web de H2

1. Ejecutá H2 Console (consola web)
   
2. En el campo **JDBC URL**, pegá la ruta completa del archivo generado.  
   Para obtenerla:
   - Navegá hasta la carpeta `/data` del proyecto
   - Hacé clic derecho en `biblioteca.mv.db` → "Copiar como ruta"
   - Pegala en la consola con el formato (no debe tener las extensiones .mv.db):

```
jdbc:h2:RUTA/AL/PROYECTO/data/biblioteca
```

(Ejemplo: `jdbc:h2:C:/Users/Lucas/Desktop/ParcialNaveda/data/biblioteca`)

3. Usuario: `sa`  
   Contraseña: *(vacía)*  
   Luego presioná **Connect**

4. Ejecutá consultas como:

```sql
SELECT * FROM autor;
SELECT * FROM libro;
```

---

## 👨‍💻 Autor

**Lucas Pujada**  
UTN FRM – Comisión 3

---

## 📂 Estructura de carpetas

```
src/
├── main/
│   ├── java/
│   │   └── org.example/
│   │       ├── model/
│   │       ├── dao/
│   │       ├── dao.impl/
│   │       ├── util/
│   │       └── Main.java
│   └── resources/
│       └── log4j2.xml
```

---
