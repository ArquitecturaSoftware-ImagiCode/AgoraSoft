# ImagiCode

## Tabla de contenidos
* [Descripción](#descripción)
* [Tecnologias](#tecnologias)
* [Configuración](#configuración)

## Descripción
Este proyecto fue creado con el propósito de ofrecer una solución digital integral para la administración de plazas de mercado. Surge de la necesidad de modernizar y optimizar procesos que tradicionalmente se han llevado de manera manual, como la gestión de contratos de arrendamiento, el control de morosidad, la supervisión de ventas, la logística de distribución y el manejo de parqueaderos.

El sistema está pensado principalmente para administradores de plazas de mercado, dueños de locales, distribuidores logísticos, trabajadores de parqueadero, comerciantes y clientes, permitiendo a cada actor interactuar con las funciones que le corresponden a través de un rol diferenciado.

En esencia, el objetivo es facilitar la toma de decisiones, mejorar la transparencia y eficiencia en la gestión de los locales y servicios asociados a la plaza, y brindar una experiencia más organizada tanto a comerciantes como a compradores.

## Tecnologias
Este proyecto esta creado con:
* Java 17
* .NET
* springboot  3.x
* Maven
* Node
* postgresql
* chocolatey

## Configuración

> [!NOTE]  
>  Es necesario que se verifiquen todos los pasos para no tener problemas con la colaboracion.

# Instalación de Git
Antes de comenzar, asegúrate de tener *Git* instalado.  

## En MacOS
`bash
# Con Homebrew
brew install git

# Verificar instalación
git --version

`
## En Windows

1. Descarga el instalador desde la página oficial: https://git-scm.com/download/win
2. Ejecuta el instalador y sigue los pasos del asistente (puedes dejar las opciones por defecto).
3. Una vez instalado, abre Git Bash o PowerShell y verifica con:
`bash
git --version
`
después de ejecutado el comando debe salir lo siguiente en la consola: 

* git version 2.45.1.windows.1

# Instalación Node
Antes de comenzar, asegúrate de tener *Node* instalado.  
puedes comprobarlos de la siguiente manera:

`bash
node -v
`
`bash
npm -v
`
## MacOS
bash
node -v

bash
npm -v

## En Windows

1. Descarga el instalador oficial desde: https://nodejs.org
2. Ejecuta el instalador y sigue los pasos (deja opciones por defecto).
3. Una vez instalado, abre PowerShell o CMD y verifica:
`bash
node -v
`
`bash
npm -v
`

# Instalación chocolatey
## 🔹 En Windows
Chocolatey se instala desde *PowerShell* con permisos de administrador:
#### En Windows
Acá hay dos casos:
### Caso 1
Es posible que ya tengas esta herramienta en tu computador entonces para verificar toca hacer lo siguiente:

Abrir PowerShell como Administrador y ejecutar:

powershell
choco -v
`
Si devuelve la versión entonces significa que ya la tienes y no hay lío, la más reciente hasta la fecha es 2.5.1 si te sale en una versión menor la puedes actualizar sin borrar con el sigueinte comando
`powershell
choco upgrade chocolatey -y
`
### Caso 2
No la tienes y toca instalarla, para hacer esto sigue los siguntes pasos:
1. en la consola de comandos powershell toca ejecutarla como administrador
2. Con powershell toca asefurarnos que tenga una de las siguientes polizas de ejecución y no sea *Restricted*, sugerimos usar Bypass o AllSigned, corre el siguiente comando: 
`powershell
Get-ExecutionPolicy
`
Si retorna *Restricted* entonces puede ejecutar cualquiera de los dos comandos para más seguridad y evitar scripts malicionsos
`powershell
Set-ExecutionPolicy AllSigned
`
`powershell
Set-ExecutionPolicy Bypass -Scope Process
`
Ahora ejecuta el comando de instalación

`powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
`
3. Pegalo en el powershell como administrador 
4. Espera unos segundo y listo, ya tienes chocolatey instalado

> [!NOTE]  
> 1. Si te sale una advertencia de este estilo es porque ya la tienes instalada, revisa la versión y de ser necesario actualiza:
> `powershell
>  ADVERTENCIA: 'choco' was found at 'C:\ProgramData\chocolatey\bin\choco.exe'.
>  ADVERTENCIA: An existing Chocolatey installation was detected. Installation will not continue. This script will not
>  overwrite existing installations.
>  If there is no Chocolatey installation at 'C:\ProgramData\chocolatey', delete the folder and attempt the installation
>  again.
>  Please use choco upgrade chocolatey to handle upgrades of Chocolatey itself.
>  If the existing installation is not functional or a prior installation did not complete, follow these steps:
> - Backup the files at the path listed above so you can restore your previous installation if needed.
> - Remove the existing installation manually.
> - Rerun this installation script.
> - Reinstall any packages previously installed, if needed (refer to the lib folder in the backup).
>  Once installation is completed, the backup folder is no longer needed and can be deleted.
> `
> 2. Si quieres saber comandos o empezar puedes ejecutar choco o choco -?

## MacOS
Para macos como tal no hay una versión de chocolatey ya que es una herramienta propia de windows

# Instalación de Java
1. entrar a la página web https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. Descargar para windows x64 si tu equipo cumple esa especificación ( aunque ya los equipos modernos generalmente vienen con x64) debe tener el nombre Windows x64 *MSI Installer*
3. seguir los pasos que dice el instalador
4. En configuración busca variables de entorno 
5. variables de entorno → en la secición variables del sistema → path → Editar y pega la ruta donde está el java con su versión.

Añade la ruta donde está bin, por ejemplo: 
C:\Program Files\Java\jdk-17\bin.

### IDE seleccionado
Para facilidad en el desarrollo recomendamos usar IntelliJ IDEA Community Edition o Ultimate Edition.


### Descargar el Repsitorio
Abre PowerShell o CMD y donde mantendras el repositorio y ejecuta.
`bash
git clone "URL-Del-Repositorio"
`
Despues de esto abre el repositorio desde el IDE y espera a que las depenncias e indexaciones terminen para empezar a desarrollar.

#### Ejemplo de colaboracion


> [!NOTE]  
>  En menos de 10 seg se debe ejecutar el sensor.

`bash
./sensor -s 2 -t 3 -f datos.txt -p pipe1

# Instalación de PostgreSQL

## En Windows
1. Descarga el instalador desde: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads  
2. Ejecuta el instalador y sigue los pasos (puedes dejar la configuración por defecto).  
3. Define una contraseña para el usuario `postgres` y guárdala.  
4. Para verificar la instalación, abre **SQL Shell (psql)** y ejecuta:  
`
sql
`
## En MacOS
`
brew install postgresql
brew services start postgresql
`
## Para verificar:
`
psql postgres
`
### Iniciar el proyecto
## Backend (Spring Boot)
`
cd agoraSoft
mvn spring-boot:run
`
## Frontend (Angular)
`
cd frontend
ng serve
`
## El backend estará en http://localhost:8080
## El frontend en http://localhost:4200
