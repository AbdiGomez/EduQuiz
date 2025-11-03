# EduQuiz — Juego Educativo Multinivel con Registro de Base de Datos status

**EduQuiz** es un videojuego educativo desarrollado en **Java (JavaFX + MariaDB)** cuyo objetivo es fomentar el aprendizaje de la informática de manera **lúdica e interactiva**, promoviendo la alfabetización digital y la comprensión de conceptos tecnológicos a través de la gamificación.

---

## Propósito del Proyecto
Acercar el conocimiento informático a personas de todas las edades y reducir la brecha digital, mediante una experiencia de aprendizaje accesible, divertida y basada en niveles progresivos de dificultad.

---

## Equipo de Desarrollo

| Rol | Integrante | Responsabilidades |
|------|-------------|-------------------|
| Líder de Proyecto | Abdiel Miguel Gómez Alemán | Coordinación del equipo, control del repositorio GitHub, conexión MariaDB, lógica del juego y revisión de entregas. |
| Backend (Base de Datos) | Yahir Campos Martínez | Diseño del modelo E-R, creación de tablas (`users`, `levels`, `questions`, `scores`), conexión JDBC y operaciones CRUD. |
| Frontend (UI) | Abisai Blas Díaz | Diseño e implementación de interfaces con **JavaFX + Scene Builder**, flujo visual y controladores FXML. |
| QA y Documentación | Luis Alexis Lucho Hernández | Generación de **Javadoc**, pruebas funcionales, control de calidad y documentación técnica. |

---

## Especificaciones Técnicas

| Categoría | Tecnología / Herramienta |
|------------|--------------------------|
| **Lenguaje** | Java (JDK 25.0.1) |
| **Entorno de desarrollo** | IntelliJ IDEA 2025.2.4 |
| **Base de datos** | MariaDB 12.0.2 + JDBC 3.5.6 |
| **Framework UI** | JavaFX 25.0.1 + Scene Builder 25.0 |
| **Gestor de dependencias** | Maven |
| **Seguridad** | BCrypt para cifrado de contraseñas |
| **Validaciones** | JetBrains Annotations (`@NotNull`, `@Nullable`) |
| **Control de versiones** | Git 2.51.1.1 + GitHub |

---

## Funcionalidades Principales

- Registro e inicio de sesión de usuarios.  
- Gestión de preguntas por nivel (básico, intermedio, avanzado).  
- Cálculo automático de puntaje según respuestas.  
- Avance progresivo entre niveles.  
- Guardado de progreso, estadísticas y ranking general.  
- Módulo de administración para pruebas y validación de BD.  
- Seguridad con **BCrypt** y arquitectura **MVC** modular.  





