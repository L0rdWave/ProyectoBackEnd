# Script de compilación y ejecución para ProyectoCRUD_Extendida
# Uso: Ejecuta en PowerShell desde la raíz del proyecto
# .\build.ps1

# Crear carpeta de salida si no existe
if (-not (Test-Path -Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}

# Compilar todos los .java en src usando UTF-8
Write-Host "Compilando con encoding UTF-8..."
javac -encoding UTF-8 -d out src\*.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error de compilación." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "Compilación exitosa. Ejecutando Main..." -ForegroundColor Green
java -cp out Main
