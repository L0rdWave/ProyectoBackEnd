# ProyectoCRUD_Extendida

Pequeña aplicación Java de ejemplo para CRUD de Productos y Categorías.

Problema conocido:
- En Windows, javac puede usar una codificación por defecto que no coincide con la codificación UTF-8 de los archivos fuente. Esto produce errores del tipo "unmappable character for encoding windows-1252" cuando hay acentos o caracteres especiales.

Solución rápida:
- Usar la opción `-encoding UTF-8` al compilar con `javac`.

Scripts:
- `build.ps1` — Script PowerShell para compilar y ejecutar el proyecto usando UTF-8.

Uso (PowerShell):
```powershell
cd c:\Users\Admin\Documents\ProyectoCRUD_Extendida
.\build.ps1
```

Alternativas:
- Configurar tu editor (p. ej. VS Code) para guardar archivos `.java` en UTF-8.
- Usar un sistema de build (Maven/Gradle) y establecer la codificación en el archivo de configuración.

Si quieres, puedo añadir un `pom.xml` o `build.gradle` para manejar esto automáticamente.
