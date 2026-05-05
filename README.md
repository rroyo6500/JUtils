### ⚠️ Aviso de Propósito (Disclaimer)

**JUtils** es un proyecto personal nacido con el objetivo de centralizar y organizar utilidades de uso frecuente en proyectos de aprendizaje o prototipado rápido.

Es importante tener en cuenta que:
*   **Enfoque en Simplicidad**: Las herramientas aquí incluidas están diseñadas para ser "simples" y fáciles de implementar, priorizando la legibilidad y la rapidez sobre arquitecturas complejas de nivel empresarial[cite: 8, 14].
*   **Entorno no Profesional**: Esta librería **no está pensada** para ser utilizada en entornos de producción crítica o sistemas altamente profesionales que requieran niveles extremos de concurrencia, seguridad avanzada o auditorías de rendimiento[cite: 9, 14].
*   **Uso Educativo**: Su propósito principal es servir como base de utilidades para el autor y otros desarrolladores que busquen soluciones rápidas y directas para sus propios proyectos personales o académicos[cite: 8, 14].

# JUtils - Java Utility Library

**JUtils** es una biblioteca de utilidades para Java diseñada para simplificar tareas comunes como la manipulación de archivos, gestión de interfaces gráficas (Swing), validación de datos, logging y conexión a bases de datos.

## 🚀 Estructura del Proyecto

La biblioteca se divide en varios paquetes especializados:

### 🛠️ Core & Console (Validación y Estilo)
*   **`Validator.java`**: Actúa como un sistema de "guard clauses". Permite validar que los objetos no sean nulos, las cadenas no estén vacías o que los números se encuentren dentro de un rango específico, lanzando excepciones con mensajes formateados si las condiciones no se cumplen.
*   **`TStyle.java`**: Proporciona constantes y métodos para aplicar colores ANSI (rojo, verde, azul, etc.) y estilos (negrita, cursiva, subrayado) al texto de la consola, facilitando la creación de interfaces de comandos legibles.

### 📁 IO (Entrada/Salida y Archivos)
*   **`FileUtilHandler.java`**: Centraliza operaciones de lectura y escritura de archivos de texto. Maneja automáticamente el buffering y ofrece opciones para sobreescribir o añadir contenido (`append`) a archivos existentes.
*   **`DataFileUtil.java`**: Implementa un motor de parsing para un formato de datos personalizado (usando delimitadores como `¡`, `:`, `^` y `~`). Permite guardar y cargar mapas (`Map<String, String>`) de forma estructurada.
*   **`ScannerAux.java`**: Un envoltorio (*wrapper*) para la clase `Scanner` estándar que permite solicitar datos al usuario por consola (String, int, double, etc.) mostrando un mensaje de guía de forma limpia.

### 🖼️ GUI (Interfaz Gráfica)
*   **`CustomFonts.java`**: Un gestor de fuentes TrueType (.ttf). Permite cargar archivos de fuentes externas, almacenarlas en un caché interno por nombre y recuperarlas con diferentes tamaños para su uso en la interfaz.
*   **`Mouse.java`**: Facilita el rastreo de la posición del ratón y el estado de los clics sobre cualquier componente de Swing, simplificando la lógica de interacción en aplicaciones gráficas.

### 📊 Data Structures & Objects
*   **`Board.java`**: Una estructura genérica de tablero bidimensional ($T[][]$). Es ideal para juegos o aplicaciones que requieran una rejilla de tamaño fijo. Incluye métodos para obtener/establecer posiciones con validación de límites y copias profundas del tablero.

### 📝 Logging
*   **`LoggerAux.java`**: Un sistema de registro de eventos que imprime mensajes formateados en la consola (con fecha, hora y nivel de severidad) y, opcionalmente, los guarda en archivos `.log` diarios de forma automática.

### 🗄️ BBDD (Bases de Datos)
*   **`BBDDConnection.java`**: Una abstracción de JDBC para gestionar conexiones a bases de datos. Facilita la ejecución de consultas (`SELECT`) y actualizaciones (`INSERT`, `UPDATE`, `DELETE`) mediante *PreparedStatements* para evitar inyecciones SQL.
*   **`DSLContextGenerator.java`**: Utilidad específica para proyectos que usen **jOOQ**. Simplifica la creación del `DSLContext` necesario para realizar consultas fluidas, permitiendo configurar el dialecto SQL (MySQL, PostgreSQL, etc.) fácilmente.
