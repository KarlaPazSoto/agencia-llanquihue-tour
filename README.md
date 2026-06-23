# AgenciaLlanquihueTourApp

Aplicación de consola desarrollada en Java para la gestión de una agencia turística.

El sistema permite administrar clientes, guías turísticos y tours disponibles, utilizando programación orientada a objetos, manejo de archivos de texto y colecciones dinámicas mediante `ArrayList`.

---

## Objetivo

Gestionar información de una agencia turística permitiendo:

- Registrar clientes.
- Registrar guías turísticos.
- Registrar tours.
- Consultar personas registradas.
- Consultar tours disponibles.
- Buscar personas por RUT.
- Filtrar tours según distintos criterios.
- Persistir la información mediante archivos de texto.

---

## Tecnologías utilizadas

- Java
- IntelliJ IDEA
- Programación Orientada a Objetos (POO)
- Colecciones (`ArrayList`)
- Manejo de archivos (`BufferedReader`, `BufferedWriter`)
- Manejo de excepciones (`try-catch`)

---

## Estructura del proyecto

```text
src
│
├── app
│   └── Main.java
│
├── model
│   ├── Persona.java
│   ├── Cliente.java
│   ├── GuiaTuristico.java
│   ├── Direccion.java
│   └── Tour.java
│
├── service
│   ├── PersonaService.java
│   └── TourService.java
│
└── util
    ├── Validador.java
    └── ValidacionException.java

resources
├── personas.txt
└── tours.txt
```

---

## Conceptos de POO implementados

### Encapsulamiento

Todos los atributos fueron declarados como:

```java
private
```

y se accede a ellos mediante métodos `get` y `set`.

### Herencia

La clase `Persona` actúa como clase base.

```text
Persona
├── Cliente
└── GuiaTuristico
```

### Composición

La clase `Persona` contiene un objeto de tipo `Direccion`.

```java
private Direccion direccion;
```

Además, la clase `Cliente` contiene un objeto de tipo `Tour`.

```java
private Tour tourReservado;
```

### Polimorfismo

Se utiliza una colección de tipo:

```java
ArrayList<Persona>
```

capaz de almacenar tanto objetos `Cliente` como `GuiaTuristico`.

### Sobrescritura de métodos

Las clases implementan su propio método:

```java
@Override
public String toString()
```

para mostrar la información de manera legible.

---

## Persistencia de datos

La aplicación almacena información en archivos de texto ubicados en la carpeta:

```text
resources
```

### Tours

Archivo:

```text
tours.txt
```

Formato:

```text
Volcán Osorno;Puerto Varas;35000
Saltos del Petrohué;Petrohué;25000
```

### Personas

Archivo:

```text
personas.txt
```

Formato clientes:

```text
CLIENTE;Juan Pérez;11111111-1;Los Robles;123;Puerto Montt;987654321;Volcán Osorno
```

Formato guías:

```text
GUIA;Ana Soto;44444444-4;San Martín;321;Puerto Varas;934567890;Inglés
```

---

## Validaciones implementadas

El sistema valida:

- Campos vacíos.
- Números positivos.
- Formato de RUT.
- Dígito verificador del RUT.
- Teléfonos numéricos.
- Objetos nulos.
- Duplicidad de RUT.
- Duplicidad de tours.

Las validaciones se centralizan en la clase:

```java
Validador
```

utilizando excepciones personalizadas:

```java
ValidacionException
```

---

## Funcionalidades disponibles

### Menú principal

```text
1. Registrar cliente
2. Registrar guía turístico
3. Registrar tour
4. Ver todas las personas
5. Ver todos los tours
6. Buscar persona por RUT
7. Filtrar tours
8. Salir
```

### Filtros de tours

```text
1. Tours desde un precio
2. Tours hasta un precio
3. Tours por lugar
4. Volver
```

---

## Cómo ejecutar

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que existan los archivos:

```text
resources/personas.txt
resources/tours.txt
```

3. Ejecutar:

```java
Main.java
```

4. Utilizar el menú interactivo desde consola.

---

## Manejo de excepciones

La aplicación utiliza bloques:

```java
try-catch
```

para controlar errores de:

- Lectura de archivos.
- Escritura de archivos.
- Validaciones de datos.
- Conversión de números.

---

## Posibles mejoras futuras

- Eliminación de registros.
- Modificación de registros existentes.
- Interfaz gráfica.
- Persistencia mediante base de datos.
- Asociación entre tours y guías turísticos.
- Reportes y estadísticas de reservas.

---

## Autor

Proyecto desarrollado como parte del proceso de aprendizaje de Programación Orientada a Objetos en Java.# agencia-llanquihue-tour