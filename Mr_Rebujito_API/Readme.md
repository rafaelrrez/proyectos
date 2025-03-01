# Mr\_Rebujito\_API

## Descripción

**Mr\_Rebujito\_API** es una aplicación desarrollada con **Spring Boot** que proporciona una API RESTful para la gestión de casetas de feria.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal del proyecto.
- **Spring Boot**: Framework para el desarrollo de aplicaciones empresariales en Java.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **MySQL**: Base de datos en memoria utilizada para pruebas y desarrollo.

## Funcionalidades Principales

- **Gestión de Casetas**: Permite crear, leer, actualizar y eliminar casetas de feria.
- **Gestión de Casetas**: Maneja la información relacionada con las casetas así como sus socios, miembros, productos que se venden, la licencia de dicha caseta.
- **Autenticación y Autorización**: Implementa seguridad básica para proteger los endpoints de la API.

## Estructura del Proyecto

La estructura del proyecto sigue las convenciones estándar de una aplicación Spring Boot:

```
src/
├── main/
│   ├── java/
│   │   └── com.example.mrrebujitoapi/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/
        └── com.example.mrrebujitoapi/
```

- **controller/**: Contiene las clases que manejan las solicitudes HTTP entrantes.
- **model/**: Define las entidades JPA que representan las tablas de la base de datos.
- **repository/**: Interfaces que extienden `JpaRepository` para interactuar con la base de datos.
- **service/**: Contiene la lógica de negocio de la aplicación.
- **application.properties**: Archivo de configuración de la aplicación.
- **data.sql**: Script SQL para inicializar la base de datos con datos predeterminados.

## Conclusión

**Mr\_Rebujito\_API** es una aplicación desarrollada con Spring Boot que proporciona una API RESTful para la gestión de casetas de feria. Su estructura modular y el uso de tecnologías estándar facilitan su mantenimiento y escalabilidad.

