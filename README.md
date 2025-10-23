# 🛒 ProyectoCRUD_Extendida (Backend)

## Descripción del Proyecto

Este proyecto es una aplicación **Backend en Java** desarrollada como **Preentrega** para el curso de **[Nombre del Curso, ej: Desarrollo Backend con JavaScript]** de **[Nombre de la Institución, ej: Visual Code Studio]**.

El objetivo principal es demostrar la implementación de las operaciones fundamentales **CRUD** (Crear, Leer, Actualizar, Eliminar) para la gestión de un sistema de inventario simple que incluye:

1.  **Artículos:** El elemento principal de inventario.
2.  **Productos:** [Si es diferente de Artículo, puedes detallar su rol, ej: Versiones del Artículo].
3.  **Categorías:** Para clasificar y organizar los artículos/productos.

Actualmente, la aplicación gestiona los datos en memoria, lo que permite una revisión rápida de la lógica de negocio y la estructura de las clases.

---

## ⚙️ Estructura del Código

| Carpeta/Clase | Rol en el Proyecto |
| :--- | :--- |
| `src/` | Contiene el código fuente de Java. |
| `src/Articulo.java` | Clase que representa los artículos/ítems. |
| `src/Categoria.java` | Clase para la clasificación de los artículos. |
| `src/Producto.java` | Clase para la gestión específica de productos. |
| `src/CRUD.java` | Contiene la lógica central de las operaciones (Create, Read, Update, Delete). |
| `src/Main.java` & `Menu.java` | Lógica de arranque y la interfaz de consola para interactuar. |
| `build.ps1` | Script de automatización de compilación/ejecución (Windows/PowerShell). |

---

## ▶️ Cómo Ejecutar (Para Revisión)

La forma recomendada de ejecutar y revisar el proyecto es usando el *script* de automatización, que también soluciona problemas de codificación comunes en Windows.

**Requisitos:** JDK (Java Development Kit) 17+ instalado.

1.  Abre la terminal **PowerShell** en el directorio raíz del proyecto.
2.  Ejecuta el script:

    ```powershell
    .\build.ps1
    ```

El script compilará el código y ejecutará el menú de la aplicación.

---

## ❗ Nota Importante sobre Encoding

*Si se compila manualmente*, puede ser necesario usar el *flag* `-encoding UTF-8` en `javac` para evitar errores con caracteres especiales/acentos en el código fuente. El *script* `build.ps1` ya lo incluye.
