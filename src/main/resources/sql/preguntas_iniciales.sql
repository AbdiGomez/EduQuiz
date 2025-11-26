-- ==========================================================
-- 🎮 PREGUNTAS INICIALES PARA EL JUEGO EDUQUIZ MULTINIVEL
-- ==========================================================
USE juego_db;

-- ----------------------------------------------------------
-- 🔹 LIMPIAR TABLAS (opcional si estás recargando datos)
-- ----------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE questions;
TRUNCATE TABLE levels;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------------------------------------
-- 🔹 CREAR NIVELES
-- ----------------------------------------------------------
INSERT INTO levels (id, name, description, difficulty) VALUES
(1, 'Nivel 1 - Básico', 'Preguntas introductorias de cultura digital y computación.', 1),
(2, 'Nivel 2 - Intermedio', 'Preguntas de programación, redes y software.', 2),
(3, 'Nivel 3 - Avanzado', 'Preguntas avanzadas sobre bases de datos, seguridad y algoritmos.', 3);

-- ----------------------------------------------------------
-- 🔹 NIVEL 1 - BÁSICO
-- ----------------------------------------------------------
INSERT INTO questions (level_id, text, correct_answer, option_a, option_b, option_c, option_d) VALUES
(1, '¿Qué significa la abreviatura CPU?', 'Unidad Central de Procesamiento', 'Unidad Central de Procesamiento', 'Centro de Procesamiento Único', 'Unidad Computacional Principal', 'Controlador de Procesos Universales'),
(1, '¿Cuál de los siguientes dispositivos es una entrada?', 'Teclado', 'Monitor', 'Impresora', 'Teclado', 'Altavoz'),
(1, '¿Qué es un archivo con extensión .txt?', 'Un archivo de texto plano', 'Un archivo comprimido', 'Un archivo ejecutable', 'Un archivo de texto plano', 'Un archivo de imagen'),
(1, '¿Qué tecla se usa para copiar en Windows?', 'Ctrl + C', 'Ctrl + C', 'Ctrl + V', 'Ctrl + X', 'Ctrl + Z'),
(1, '¿Cuál es el sistema operativo de código abierto?', 'Linux', 'Windows', 'macOS', 'Linux', 'Android'),
(1, '¿Qué parte del computador muestra la información al usuario?', 'Monitor', 'Monitor', 'CPU', 'Ratón', 'Teclado'),
(1, '¿Cuál es el navegador de Internet?', 'Google Chrome', 'Word', 'Excel', 'Google Chrome', 'PowerPoint'),
(1, '¿Qué dispositivo almacena datos permanentemente?', 'Disco duro', 'RAM', 'Procesador', 'Disco duro', 'Teclado'),
(1, '¿Qué extensión tienen los archivos ejecutables en Windows?', '.exe', '.exe', '.doc', '.pdf', '.txt'),
(1, '¿Cuál de los siguientes es un lenguaje de programación?', 'Python', 'HTML', 'Python', 'Word', 'Excel');

-- ----------------------------------------------------------
-- 🔹 NIVEL 2 - INTERMEDIO
-- ----------------------------------------------------------
INSERT INTO questions (level_id, text, correct_answer, option_a, option_b, option_c, option_d) VALUES
(2, '¿Qué significa HTML?', 'HyperText Markup Language', 'HyperText Markup Language', 'HighText Machine Language', 'Hyper Transfer Markup Language', 'Home Tool Markup Language'),
(2, '¿Qué lenguaje se usa para diseñar la estructura de una página web?', 'HTML', 'Python', 'HTML', 'C++', 'SQL'),
(2, '¿Qué protocolo se usa para transferir archivos en Internet?', 'FTP', 'SMTP', 'FTP', 'HTTP', 'SSH'),
(2, '¿Qué comando de Git se usa para clonar un repositorio?', 'git clone', 'git copy', 'git clone', 'git push', 'git download'),
(2, '¿Cuál es el lenguaje usado para bases de datos relacionales?', 'SQL', 'C++', 'Python', 'SQL', 'HTML'),
(2, '¿Qué tipo de software es Windows?', 'Sistema operativo', 'Sistema operativo', 'Aplicación', 'Antivirus', 'Navegador'),
(2, '¿Qué unidad mide la velocidad de un procesador?', 'Hertz (Hz)', 'Byte', 'Megabyte', 'Hertz (Hz)', 'Píxeles'),
(2, '¿Qué significa IoT?', 'Internet of Things', 'Internet of Tools', 'Interface of Technology', 'Internet of Things', 'Input of Transmission'),
(2, '¿Qué elemento del modelo OSI corresponde a la dirección IP?', 'Capa de red', 'Capa de transporte', 'Capa de red', 'Capa física', 'Capa de sesión'),
(2, '¿Qué extensión tienen los archivos Java compilados?', '.class', '.java', '.exe', '.class', '.jar');

-- ----------------------------------------------------------
-- 🔹 NIVEL 3 - AVANZADO
-- ----------------------------------------------------------
INSERT INTO questions (level_id, text, correct_answer, option_a, option_b, option_c, option_d) VALUES
(3, '¿Cuál es la complejidad temporal del algoritmo de búsqueda binaria?', 'O(log n)', 'O(log n)', 'O(n)', 'O(n²)', 'O(1)'),
(3, '¿Qué capa del modelo OSI se encarga del enrutamiento de paquetes?', 'Capa de red', 'Capa de enlace de datos', 'Capa física', 'Capa de transporte', 'Capa de red'),
(3, '¿Qué es una llave primaria en una base de datos?', 'Un identificador único para cada registro', 'Un campo repetido', 'Un campo temporal', 'Un identificador único para cada registro', 'Una restricción de texto'),
(3, '¿Qué comando SQL elimina una tabla?', 'DROP TABLE', 'DELETE TABLE', 'REMOVE TABLE', 'DROP TABLE', 'TRUNCATE'),
(3, '¿Qué es una red VLAN?', 'Una red virtual dentro de una red física', 'Una red inalámbrica local', 'Un tipo de dirección IP', 'Una red virtual dentro de una red física', 'Un protocolo de cifrado'),
(3, '¿Qué protocolo cifra la comunicación en la web?', 'HTTPS', 'HTTP', 'FTP', 'HTTPS', 'SMTP'),
(3, '¿Qué es un ataque de fuerza bruta?', 'Intentar contraseñas repetidamente hasta acertar', 'Robar información mediante phishing', 'Infectar con virus', 'Interceptar tráfico', 'Intentar contraseñas repetidamente hasta acertar'),
(3, '¿Qué significa CRUD?', 'Create, Read, Update, Delete', 'Copy, Read, Upload, Delete', 'Create, Run, Undo, Drop', 'Create, Read, Update, Delete', 'Compile, Run, Update, Debug'),
(3, '¿Qué motor de base de datos usa MariaDB?', 'InnoDB', 'PostgreSQL', 'InnoDB', 'OracleDB', 'MyISAM'),
(3, '¿Qué algoritmo se usa comúnmente para almacenar contraseñas de forma segura?', 'BCrypt', 'SHA1', 'MD5', 'Base64', 'BCrypt');

