═══════════════════════════════════════════════════════════════════════════════
SISTEMA DE GESTIÓN MUNICIPAL - BPMN CAMUNDA SPRING BOOT
═══════════════════════════════════════════════════════════════════════════════

Proyecto integral desarrollado en Java con Spring Boot y Camunda BPM, que automatiza la gestión y seguimiento de procesos municipales tipo PQRS y trámites ciudadanos, con formularios inteligentes, lógica de negocio basada en reglas DMN, y orquestación de procesos mediante BPMN 2.0.

═══════════════════════════════════════════════════════════════════════════════
1. INSTALACIÓN Y CONFIGURACIÓN
═══════════════════════════════════════════════════════════════════════════════

PASO 1: DESCARGAR EL PROYECTO
------------------------------
  Descomprime el archivo .zip del proyecto en una carpeta de tu preferencia

PASO 2: COMPILAR EL PROYECTO
-----------------------------
Abre la terminal en la carpeta raíz del proyecto y ejecuta:

  mvn clean install

Este comando:
  ✓ Descarga todas las dependencias necesarias
  ✓ Compila el código Java
  ✓ Ejecuta las pruebas automáticas
  ✓ Genera el archivo .jar ejecutable

El archivo ejecutable se generará en:
  target/alcaldia-corocora-1.0.0-SNAPSHOT.jar

REQUISITOS
-----------
- Java 17+
- Maven 3.8+
- No requiere base de datos instalada: usa H2 en memoria

═══════════════════════════════════════════════════════════════════════════════
2. EJECUCIÓN DE LA APLICACIÓN
═══════════════════════════════════════════════════════════════════════════════

MÉTODO 1: CON MAVEN
----------------------
Desde la terminal en la carpeta raíz del proyecto:

  mvn spring-boot:run

MÉTODO 2: .JAR STANDALONE
--------------------------
  java -jar target/alcaldia-corocora-1.0.0-SNAPSHOT.jar

Ambas opciones lanzan la aplicación en: http://localhost:8080

═══════════════════════════════════════════════════════════════════════════════
3. ACCESO A LAS INTERFACES Y HERRAMIENTAS
═══════════════════════════════════════════════════════════════════════════════

CAMUNDA COCKPIT - Monitoreo de Procesos
----------------------------------------
URL: http://localhost:8080/camunda/app/cockpit/default/
Usuario: demo   |   Contraseña: demo

CAMUNDA TASKLIST - Gestión de Tareas para funcionarios
------------------------------------------------------
URL: http://localhost:8080/camunda/app/tasklist/default/
Usuario: demo   |   Contraseña: demo

CAMUNDA ADMIN - Administración avanzada
----------------------------------------
URL: http://localhost:8080/camunda/app/admin/default/
Usuario: demo   |   Contraseña: demo

CONSOLA H2 - Base de Datos en Memoria
--------------------------------------
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:camunda
Usuario: sa   |   Contraseña: sa

FORMULARIOS WEB - Ciudadano
----------------------------
URL: http://localhost:8080/completar-documentos.html?token=XXX&processId=YYY
(Este link se genera automáticamente por el sistema al solicitar documentos)

═══════════════════════════════════════════════════════════════════════════════
4. FUNCIONALIDADES DEL SISTEMA Y FLUJOS
═══════════════════════════════════════════════════════════════════════════════

Este sistema automatiza los trámites de forma modular:

- Registro de solicitudes PQRS o trámites de construcción/licencia
- Verificación automática de datos y documentos vía reglas DMN
- Solicitud inteligente de información/documentos faltantes mediante links únicos
- Gestión, análisis y asignación de casos por dependencias
- Evaluaciones técnicas y jurídicas por funcionarios
- Notificación automática al ciudadano y cierre automático por vencimientos
- Trazabilidad completa de cada tramitación en Camunda Cockpit

ETAPAS PRINCIPALES:
--------------------
1. REGISTRO CIUDADANO
   - El ciudadano ingresa su solicitud/trámite.
   - Recibe número de radicado único y/o correo.

2. REGLAS DE NEGOCIO (DMN)
   - Validación automática de datos y tipo de trámite.

3. DOCUMENTOS FALTANTES
   - Se notifica por correo con link web personalizado (token seguro).
   - El ciudadano complementa vía formulario web.

4. ESPERA Y REENTRADA
   - El sistema espera hasta 10 días. Si no hay respuesta, archiva automáticamente.

5. ASIGNACIÓN Y GESTIÓN FUNCIONARIO
   - El expediente pasa a la dependencia y empleado correspondiente.

6. EVALUACIÓN Y RESPUESTA
   - Empleado revisa, evalúa, desarrolla propuesta y responde por el sistema.

7. NOTIFICACIÓN Y CIERRE
   - El ciudadano es notificado automáticamente.
   - El trámite se cierra con comentarios.

═══════════════════════════════════════════════════════════════════════════════
5. PRUEBAS DEL SISTEMA Y FLUJOS
═══════════════════════════════════════════════════════════════════════════════

PRUEBA 1: FLUJO COMPLETO DE SOLICITUD Y COMPLETAR DOCUMENTOS
-------------------------------------------------------------
1. Simula una solicitud ciudadana desde el endpoint/web correspondiente.
2. Observa la generación del link para completar documentos en consola/backend.
3. Abre el link en el navegador y rellena el formulario.
4. Envía y verifica completitud en Camunda Cockpit.

PRUEBA 2: CONSULTA EN BASE DE DATOS H2
---------------------------------------
1. Abre: http://localhost:8080/h2-console
2. Conecta con:
   JDBC URL: jdbc:h2:mem:camunda
   Usuario: sa
   Contraseña: sa
3. Ejecuta: SELECT * FROM solicitud_pqrs;
4. Verifica datos grabados y cambios de estado.

═══════════════════════════════════════════════════════════════════════════════
6. ESTRUCTURA DEL PROYECTO
═══════════════════════════════════════════════════════════════════════════════

src/main/java/com/soft1/sistemaciudadano/
│
├── controllers/    # Controladores REST (Ciudadano, Funcionario)
├── service/        # Lógica de negocio y helpers
├── model/          # Entidades y clases de datos
├── process/        # BPMN y DMN
└── resources/
     ├── static/    # Formularios HTML ciudadanos y de funcionarios
     ├── application.properties  # Configuración Spring Boot
     └── procesos/  # Definiciones BPMN/DMN