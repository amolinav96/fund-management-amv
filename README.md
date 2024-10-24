# Fund Management AMV

_Edición: 2024_

## Planteamiento
Se plantea crear un microservicio reactivo debido a que Fund Management AMV es un sistema que tendra altos niveles de concurrencia y escalabilidad, para esto decidimos utilizar springboot como framework principal y utilizar webflux como la libreria que nos permite crear flujos asincronos, de esta manera tendremos nuestra aplicacion reactiva, luego utilizamos una base de datos de Mongodb (ReactiveMongoRepository) al extender de esta clase garantizamos el flujo completo de nuestras transacciones asincronas permitiendo asi que desde las consultas a bases de datos recibamos flujos reactivos.

Tambien se plantea utilizar MVC como arquitectura base para crear (Controllers, models, resposity, services), implementar swagger para la documentacion de las apis y realizar pruebas unitarias utilizando JUnit 5  garantizando un 90% de la covertura de las pruebas, a nivel de servicios, objetos y servicios.

A nivel de seguridad se plantea utilizar JWT Spring security.


## Modelo de datos
![Modelo de datos](img/DataModel.png)

...

## Tecnologías Utilizadas
- **Lenguaje de Programación**: Java
- **Framework**: Spring Boot
- **Gestor de Dependencias**: Maven
- **Base de Datos**: MongoDB (local)
- **Pruebas Unitarias**: JUnit 5
- **Documentación API**: Swagger
- **Seguridad**: Spring security
- **Repositorio GitHub**: [Fund Management AMV](https://github.com/amv9621/fund-management-amv.git)

## Características
- **Gestión de Fondos**: Administración de suscripciones a fondos FPV y FIC.
- **Fondos Voluntarios de Pensión (FPV)**: Vehículo de inversión para obtener rendimientos óptimos y beneficios tributarios.
- **Fondos de Inversión Colectiva (FIC)**: Opciones de inversión gestionadas por expertos para retorno de capital y diversificación de riesgos.

## Requisitos Previos
- **Java 11 o superior**
- **Maven 3.6 o superior**
- **MongoDB instalado y configurado localmente**
- **Conexión a Internet** para descargar las dependencias de Maven

## Comenzando 🚀

_Descripción del **Sistema de Gestión de gestion de fondos nacionales** Fund Management AMV_

Fund Management AMV es un microservicio desarrollado en Java con Spring Boot para gestionar la suscripción de clientes a fondos FPV (Fondo Voluntario Pensional) y FIC (Fondo de Inversión Colectiva). El objetivo de esta plataforma es proporcionar una solución intuitiva y amigable para que los clientes puedan administrar sus fondos sin necesidad de contactar directamente con su asesor comercial.

### Pre-requisitos 📋

_Este proyecto requiere ciertos pre-requisitos para su ejecución:_

#### Java Development Kit (JDK)
Debes tener instalado Java Development Kit (JDK) en tu sistema. Este proyecto requiere JDK 21 o una versión superior.
Puedes descargar y configurar JDK desde el sitio oficial de Oracle o OpenJDK:

- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK](https://openjdk.java.net/)

#### Git
Git es necesario para clonar el repositorio del proyecto y gestionar versiones del código fuente. Puedes descargar Git
desde:

- [git-scm.com](https://git-scm.com/).

#### MongoDB
Se utiliza MongoDB como sistema de gestión de bases de datos. Asegúrate de tener MongoDB instalado y configurado
para poder ejecutar el proyecto.
- **MongoDB:** [mongodb.org](https://www.mongodb.org/).

#### Spring Boot
Spring Boot es utilizado como framework para la construcción de aplicaciones Java. Más información en:
- **Spring Boot:** [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot).

#### Swagger
Puedes consultar la documentacion de las apis desarrolladas en este microservicio :
- (http://localhost:8060/api/swagger-ui/index.html#/).


### Instalación 🔧

A continuación, se describen los pasos para configurar y ejecutar este proyecto Java en tu entorno de desarrollo.

#### Requisitos Previos
Antes de comenzar, asegúrate de tener los siguientes requisitos previos en tu sistema:

- **Java Development Kit (JDK):** Debes tener instalado Java Development Kit (JDK) en tu sistema.
  Para verificar si Java está instalado, puedes abrir una terminal y ejecutar el siguiente comando:

   ```shell
   java -version

#### Clonar el Repositorio

Para comenzar, clona este repositorio en tu máquina local usando Git:

```shell
git clone https://github.com/amv9621/fund-management-amv.git
```

**Nota:**  Asegúrate de que tu sistema tenga configuradas las variables de entorno JAVA_HOME y PATH para que apunten a tu instalación de JDK.


## Despliegue 📦

En esta sección, se proporcionan instrucciones y notas adicionales sobre cómo llevar tu proyecto a un entorno de producción o cómo desplegarlo para su uso.

### Despliegue Local 🏠

Si deseas ejecutar tu proyecto en tu propio entorno local para pruebas o desarrollo, sigue estos pasos generales:

1. **Requisitos Previos**: Asegúrate de que los requisitos previos del proyecto, como JDK y otras dependencias, estén instalados en tu máquina.

2. **Clonación del Repositorio**: Si aún no has clonado el repositorio, sigue las instrucciones en la sección "Clonar el Repositorio" de la [sección de instalación](#clonar-el-repositorio) para obtener una copia local del proyecto.

3. **Configuración de Variables de Entorno**: Asegúrate de que las variables de entorno necesarias, como `JAVA_HOME`, estén configuradas correctamente.

4. **Compilación y Ejecución**: Sigue las instrucciones de la [sección de instalación](#compilación-y-ejecución) para compilar y ejecutar el proyecto.


## Configuración de Variables de Entorno 🌍

Este proyecto utiliza variables de entorno para la configuración de la base de datos. Deberás configurar las siguientes variables de entorno en tu sistema:

- `spring.data.mongodb.host`: La URL de tu base de datos Mongodb (localhost).
- `spring.data.mongodb.port`: El puerto de la db local (27017).
- `spring.data.mongodb.database`: Nombre de la base de datos (amv_fund_management).

Puedes configurar estas variables de entorno en tu sistema operativo o en tu IDE si lo soporta. También puedes crear un archivo `.env` en la raíz de tu proyecto y definir las variables de entorno allí. Asegúrate de no subir este archivo a tu repositorio de código para proteger tus credenciales de base de datos.

## Ejecución de Comandos de Verificación de Código 🛠️

Este proyecto utiliza varias herramientas para verificar la calidad del código. Aquí te dejo cómo puedes ejecutar cada una de ellas:

- **Checkstyle:** Para ejecutar Checkstyle, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn checkstyle:check -Pci

- **Spotbugs:** Para ejecutar Spotbugs, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn -Pci spotbugs:check 

- **Pmd:** Para ejecutar Pmd, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn pmd:check

## Autor ✒️

Aldahir Molina Velasquez (Ingeniero informatico).

## Licencia 📄

Este proyecto se encuentra bajo la Licencia MIT. Consulta el archivo [LICENSE.md](LICENSE.md) para obtener más detalles.

---
## Créditos 📜

Este proyecto fue desarrollado con ❤️ por el equipo de desarrolladores de [Cidenet](https://Cidenet.com.co/) 😊.

Si tienes preguntas, comentarios o sugerencias, no dudes en ponerte en contacto con nosotros:

- GitHub: [Cidenet](https://github.com/saulolo) 🌐
- Correo Electrónico: comunicaciones@cidenet.com.co 📧
- LinkedIn: [Cidenet](https://www.linkedin.com/company/Cidenet-s-a-s/mycompany/) 👔
- Telefono: (+57) 3104533879 📞




