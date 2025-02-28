# iDoctorApp

## Descripción

**iDoctorApp** es una aplicación móvil desarrollada en **Java para Android**, diseñada para facilitar la gestión de citas médicas entre pacientes y doctores.


## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal del proyecto.
- **Firebase Authentication**: Sistema de autenticación para gestionar el acceso de usuarios.
- **Cloud Firestore**: Base de datos NoSQL para almacenar información de usuarios, doctores y citas.
- **Firebase Cloud Messaging (FCM)**: Implementación de notificaciones push.
- **Firebase Storage**: Almacenamiento de imágenes y documentos.

## Funcionalidades Principales

- **Autenticación de Usuarios**: Registro e inicio de sesión con Firebase Authentication.
- **Gestión de Citas**: Permite a los pacientes buscar especialistas y reservar citas médicas.
- **Notificaciones en Tiempo Real**: Envío de recordatorios y confirmaciones de citas mediante FCM.
- **Administración de Horarios**: Los doctores pueden gestionar sus horarios de consulta y disponibilidad.
- **Almacenamiento de Información Médica**: Subida y visualización de documentos médicos e imágenes relacionadas con las consultas.

## Estructura del Proyecto

```
iDoctorApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/idoctorapp/
│   │   │   │   ├── activities/
│   │   │   │   ├── adapters/
│   │   │   │   ├── models/
│   │   │   │   ├── services/
│   │   │   │   ├── utils/
│   │   │   │   └── views/
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
```

- **activities/**: Contiene las actividades principales de la aplicación.
- **adapters/**: Adaptadores para listas y elementos de la interfaz.
- **models/**: Define las clases de datos utilizadas en la aplicación.
- **services/**: Contiene la lógica para la comunicación con Firebase y otros servicios.
- **utils/**: Utilidades y funciones auxiliares.
- **views/**: Componentes personalizados de la UI.
- **layout/**: Archivos XML para la interfaz de usuario.
- **drawable/**: Recursos gráficos.
- **values/**: Archivos de recursos (colores, strings, estilos).
- **AndroidManifest.xml**: Configuración de permisos y componentes de la aplicación.

## Conclusión

