# Prueba Técnica Capitole-Inditex
Proyecto desarrollado con Spring Boot y Maven. Implementa una arquitectura basada
en microservicios con arquitectura hexagonal y DDD, para el procesamiento de búsquedas
de productos similares, implementando pruebas unitarias, de integración y de sistema;
así como JaCoCo para la cobertura de código.

## Contenido
### 1. APIS
El microservicio dispone de una api "ProductApi" que contiene el endpoint requerido
para la prueba técnica.

- BasePath: http://localhost:5001
- GET: basePath + "/product/{productId}/similar"

### 2. Test realizados
Para esta prueba técnica se han realizado los siguientes tipos de test:
- Test unitarios.
- Test de integración.
- Test de sistema.

### 3. Ejecución de Test
Dado que estamos ante un proyecto dockerizado, el único requisito para poder ejecutar
los test del proyecto será tener instalado docker con docker-compose.

    docker-compose run --rm tests

#### 3.1. Resultados de los Test
Una vez ejecutados los test, se generará en la raíz del proyecto la carpeta /target,
que dispondrá en su interior de un conjunto de directorios, de los cuales nos interesan
los siguiente:
- /site/jacoco
  - Reporte de Jacoco de los test, indicando la cobertura de los mismos. Será necesario
    abrir el fichero index.html para ver el resultado.

### 4. Build y arranque de la app
Debemos ejecutar los siguientes comandos:

Compile:

    mvn clean package -DskipTests

Build:

    docker build -t challenge-inditex .

Start (con las dependencias simulado, influxdb y grafana):

    docker-compose up -d simulado influxdb grafana app

### 5. Ejecución K6
Debemos ejecutar el siguiente comando:

    docker-compose run --rm k6 run scripts/test.js    