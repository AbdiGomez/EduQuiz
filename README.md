# EDUQUIZ: “JUEGO EDUCATIVO MULTINIVEL CON REGISTRO DE BASE DE DATOS Y ESTATUS”

## 1. Especificación General del Proyecto

**EduQuiz** es un videojuego educativo desarrollado en **Java (JavaFX + MariaDB)**. Su proposito es fomentar el aprendizaje de la informática de manera lúdica e interactiva, utilizando una plataforma multinivel de preguntas y respuestas. El sistema registra el progreso, los puntajes y la información de los usuarios en una base de datos MariaDB.

### 1.1 Objetivos Principales

* Desarrollar un videojuego educativo en Java que fomente el aprendizaje de informática mediante preguntas gamificadas y registro de progreso.
* Implementar la arquitectura **Modelo-Vista-Controlador (MVC)** para estructurar el sistema.
* Integrar **MariaDB** para la gestión de usuarios, puntajes y progreso (operaciones CRUD).
* Incorporar autenticación y **cifrado con BCrypt** para asegurar las credenciales de los usuarios.
* Generar **documentación con Javadoc** para el código fuente.

### 1.2 Repositorio y Flujo de Git

El proyecto utiliza **Git** para el control de versiones, gestionado a través de **GitHub**.

* **Repositorio Principal:** [https://github.com/AbdiGomez/EduQuiz](https://github.com/AbdiGomez/EduQuiz)

## 2. Requerimientos Identificados

### 2.1 Requisitos Técnicos (Generales)

| Componente | Versión / Tecnología | Uso Específico |
| :--- | :--- | :--- |
| **Lenguaje** | Java (JDK v25.0.1) | Lógica de la aplicación. |
| **IDE** | IntelliJ IDEA v2025.2.4 | Entorno de desarrollo. |
| **Base de Datos** | MariaDB v12.0.2, JDBC v3.5.6 | Almacenamiento persistente (datos de usuarios, puntajes, preguntas). |
| **Interfaz (UI)** | JavaFX v25.0.1 y Scene Builder v25.0.0 | Diseño de la experiencia de usuario (UI/UX). |
| **Dependencias** | Maven | Gestión del proyecto y librerías. |
| **Seguridad** | BCrypt | Hashing y cifrado de contraseñas. |
| **Versiones** | Git v2.51.1.1 y GitHub | Control de versiones colaborativo. |

### 2.2 Requisitos Funcionales (Características)

1.  **Registro y Login:** Autenticación de usuarios con BCrypt.
2.  **Gestión de Preguntas:** Carga de preguntas por nivel temático.
3.  **Cálculo de Puntaje:** Sistema de cálculo de puntaje en tiempo real.
4.  **Avance Progresivo:** Bloqueo y desbloqueo de niveles (Básico, Intermedio, Avanzado).
5.  **Estadísticas:** Guardado y visualización del progreso y puntajes.
6.  **Ranking:** Visualización del puntaje más alto (Ranking).

### 2.3 Requisitos No Funcionales

* **Usabilidad:** Interfaz de usuario intuitiva, diseñada con JavaFX.
* **Rendimiento:** Tiempos de respuesta inferiores a 2 segundos en la carga de niveles y preguntas.
* **Seguridad:** Uso de cifrado BCrypt para todas las contraseñas.
* **Mantenibilidad:** Código estructurado bajo el patrón MVC y documentado con Javadoc.
* **Compatibilidad:** Ejecución compatible con sistemas operativos modernos (Windows, macOS, Linux) que soporten JRE 17+.

## 3. Estructura y Funcionamiento del Juego

### 3.1 Arquitectura del Juego

El juego sigue el patrón **Modelo-Vista-Controlador (MVC)**:

* **Modelo:** Contiene la lógica del negocio, manejo de datos y la conexión a MariaDB.
* **Vista:** Contiene los archivos FXML (JavaFX) que definen la interfaz gráfica.
* **Controlador:** Actúa como intermediario, manejando la interacción del usuario y actualizando el Modelo y la Vista.

### 3.2 Niveles y Temas

El juego se divide en 3 niveles de dificultad progresiva, evaluando diferentes áreas:

* **Nivel 1 (Básico):** Conceptos generales de computación (Sistemas operativos Open Source, Navegadores Web, Componentes básicos).
* **Nivel 2 (Intermedio):** Programación en Java (Variables, Tipos de datos, Extensiones de archivos: `.java` vs `.class`).
* **Nivel 3 (Avanzado):** Redes y telecomunicaciones (Capas del Modelo OSI, Protocolos de red).

### 3.3 Mecánica del Juego

* **Temporizador:** Cada pregunta tiene un tiempo límite (aprox. 10 a 12 segundos).
* **Selección:** Se presentan 4 opciones de respuesta para seleccionar la correcta.
* **Finalización:** Al terminar las 5 preguntas de un nivel, se muestra un resumen con estadísticas, puntaje porcentual y calificación con estrellas (de 1 a 3).

## 4. Guía de Instalación Rápida

La aplicación requiere el **Java Runtime Environment (JRE)** para su ejecución.

1.  **Requisito:** Tener instalado **JRE 17+**.
2.  **Ejecución:** Descargue el archivo ejecutable (`.jar`) y ábralo desde la terminal:

    ```bash
    java -jar EduQuiz-1.0-SNAPSHOT.jar 
    ```

---


***
**Aviso Legal y Derechos de Autor**
All Rights Reserved <br>
Copyright (c) 2025 Abdiel Miguel Gomez Aleman, Yahir Campos Martinez, Abisai Blas Diaz, y Luis Alexis Lucho Hernandez <br>
This project and its source code may not be copied, reproduced, modified, distributed, or used in any form without explicit written permission from the authors.
