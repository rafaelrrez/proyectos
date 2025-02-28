# Mr\_Rebujito\_API

## Descripción

**Mr\_Rebujito\_API** es una aplicación desarrollada con **Spring Boot** que proporciona una API RESTful para la gestión de recetas de cócteles, con énfasis en el "Rebujito".

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal del proyecto.
- **Spring Boot**: Framework para el desarrollo de aplicaciones empresariales en Java.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **H2 Database**: Base de datos en memoria utilizada para pruebas y desarrollo.

## Funcionalidades Principales

- **Gestión de Recetas**: Permite crear, leer, actualizar y eliminar recetas de cócteles.
- **Gestión de Ingredientes**: Maneja la información relacionada con los ingredientes de las recetas.
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

**Mr\_Rebujito\_API** es una aplicación desarrollada con Spring Boot que proporciona una API RESTful para la gestión de recetas de cócteles. Su estructura modular y el uso de tecnologías estándar facilitan su mantenimiento y escalabilidad.

