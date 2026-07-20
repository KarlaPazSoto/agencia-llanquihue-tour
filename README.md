# AgenciaLlanquihueTourApp

Aplicación con interfaz gráfica (GUI) desarrollada en Java para la gestión integral de una agencia turística.

El sistema permite administrar clientes, guías turísticos, tours disponibles y servicios turísticos especializados, implementando una arquitectura orientada a objetos con interfaces polimórficas, colecciones genéricas y persistencia de datos mediante archivos de texto.

---

## Objetivo

Gestionar información integral de una agencia turística permitiendo:

- Registrar clientes asociados a tours disponibles.
- Registrar guías turísticos con información de idiomas.
- Registrar tours con lugares, precios y servicios turísticos asociados.
- Registrar servicios turísticos especializados:
    - Paseos Lacustres.
    - Excursiones Culturales.
    - Rutas Gastronómicas.
- Visualizar la información registrada mediante tablas gráficas.
- Modificar y eliminar registros existentes utilizando selección mediante listas desplegables.
- Mostrar un resumen general de todas las entidades mediante procesamiento polimórfico.
- Mantener persistencia de datos mediante archivos de texto.
---

## Tecnologías utilizadas

- Java 11+
- IntelliJ IDEA
- Programación Orientada a Objetos (POO)
- Interfaces y Polimorfismo
- Colecciones Genéricas (`ArrayList<Registrable>`)
- Swing (GUI - JFrame, JPanel, JButton, JComboBox, etc.)
- Manejo de archivos (`BufferedReader`, `BufferedWriter`)
- Manejo de excepciones (`try-catch`)
- Operador `instanceof` para tipo-comprobación

---

## Arquitectura del sistema

La aplicación está organizada siguiendo una separación por responsabilidades:

### Model

Contiene las clases que representan las entidades principales del sistema:

- Personas.
- Tours.
- Servicios turísticos.

### Service

Contiene la lógica de negocio:

- Validaciones.
- Registro.
- Modificación.
- Eliminación.
- Persistencia de información.

### Paneles

Contiene la interfaz gráfica independiente para cada módulo:

- PanelTours.
- PanelPersonas.
- PanelServicios.
- PanelResumen.

### Data

Administra la colección general de entidades mediante:

- GestorEntidades.

### Util

Contiene herramientas auxiliares:

- Validaciones.
- Manejo de excepciones personalizadas.

---

## Estructura del proyecto

```text
src
│
├── app
│   ├── Main.java
│   └── VentanaPrincipal.java
│
├── paneles
│   ├── PanelTours.java
│   ├── PanelPersonas.java
│   ├── PanelServicios.java
│   └── PanelResumen.java
│ 
├── model
│   ├── Registrable.java (Interfaz)
│   ├── Persona.java
│   ├── Cliente.java
│   ├── GuiaTuristico.java
│   ├── Direccion.java
│   ├── Tour.java
│   ├── ServicioTuristico.java (Clase abstracta)
│   ├── PaseoLacustre.java
│   ├── ExcursionCultural.java
│   └── RutaGastronomica.java
│
├── service
│   ├── PersonaService.java
│   ├── TourService.java
│   └── ServicioService.java
│
├── data
│   └── GestorEntidades.java
│
└── util
    ├── Validador.java
    └── ValidacionException.java

resources
├── personas.txt
├── tours.txt
└── servicios.txt
```

---

## Conceptos de POO implementados

### Interfaz Registrable

Interfaz que define un contrato común para todas las entidades del sistema:

```java
public interface Registrable {
    String mostrarResumen();
}
```

Implementada por: `Tour`, `Persona`, `Cliente`, `GuiaTuristico`, `ServicioTuristico` (y sus subclases).

### Encapsulamiento

Todos los atributos fueron declarados como:

```java
private
```

y se accede a ellos mediante métodos `get` y `set`.

### Herencia

**Jerarquía 1: Personas**

```text
Persona (clase base)
├── Cliente
└── GuiaTuristico
```

**Jerarquía 2: Servicios Turísticos**

```text
ServicioTuristico (clase abstracta)
├── PaseoLacustre
├── ExcursionCultural
└── RutaGastronomica
```

### Composición

- La clase `Persona` contiene un objeto de tipo `Direccion`.
- La clase `Cliente` contiene un objeto de tipo `Tour`.
- La clase `Tour` contiene un objeto opcional de tipo `ServicioTuristico`.

### Polimorfismo con Genéricos

Se utiliza una colección genérica que permite almacenar objetos de distintos tipos:

```java
ArrayList<Registrable> entidades
```

capaz de almacenar `Tour`, `Cliente`, `GuiaTuristico`, `PaseoLacustre`, `ExcursionCultural` y `RutaGastronomica`.

### Operador instanceof

Utilizado en `GestorEntidades` para identificar el tipo específico de cada entidad y aplicar lógica diferenciada:

```java
if (registrable instanceof Cliente cliente) {
    // Procesar cliente específicamente
} else if (registrable instanceof Tour tour) {
    // Procesar tour específicamente
}
```

### Sobrescritura de métodos

Todas las clases implementan `mostrarResumen()` con personalizaciones según su tipo de entidad.

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
Volcán Osorno;Puerto Varas;35000;Navegación Lago Llanquihue
Saltos del Petrohué;Petrohué;25000;Ninguno
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

### Servicios Turísticos

Archivo:

```text
servicios.txt
```

Formato:

```text
PASEO_LACUSTRE;Navegación Lago Llanquihue;2;Bote
EXCURSION_CULTURAL;Ruta Arqueológica;4;Pucará de Antuco
RUTA_GASTRONOMICA;Tour Gastronómico Chilote;3;5
```

Se almacenan 4 campos separados por punto y coma:
- Tipo de servicio
- Nombre
- Duración en horas
- Dato especial (varía según tipo)

---

## Validaciones implementadas

El sistema valida:

- Campos vacíos en todos los formularios.
- Números positivos (precios, duración, teléfono).
- Formato y dígito verificador del RUT.
- Teléfonos numéricos.
- Objetos nulos.
- Duplicidad de RUT en personas.
- Duplicidad de tours por nombre.
- Duplicidad de servicios por nombre.
- Formato de dirección (calle, número, comuna).
- Conversión de tipos de datos.

Las validaciones se centralizan en la clase:

```java
Validador
```

utilizando excepciones personalizadas:

```java
ValidacionException
```

Las excepciones se capturan en la GUI y se muestran al usuario mediante `JOptionPane`.

---

## Interfaz Gráfica (GUI)

La aplicación cuenta con una interfaz gráfica desarrollada utilizando Java Swing.

La ventana principal funciona como contenedor de los diferentes módulos del sistema mediante pestañas independientes.

### VentanaPrincipal.java

La ventana principal se encarga de:

- Inicializar los servicios de negocio.
- Cargar información almacenada desde archivos.
- Crear y administrar los paneles gráficos.
- Coordinar la comunicación entre los módulos.

La interfaz está dividida en cuatro pestañas:

### 1. Panel Tours

Permite gestionar los tours disponibles.

Funciones:

- Registrar nuevos tours.
- Asociar opcionalmente un servicio turístico.
- Visualizar tours registrados mediante tablas.
- Modificar tours existentes seleccionándolos desde una lista.
- Eliminar tours considerando restricciones de asociación con clientes.

Campos gestionados:

- Nombre del tour.
- Lugar.
- Precio.
- Servicio asociado.

---

### 2. Panel Personas

Permite gestionar clientes y guías turísticos.

Funciones:

- Registrar clientes asociados a tours.
- Registrar guías turísticos con idiomas.
- Visualizar personas registradas.
- Modificar información existente.
- Eliminar personas mediante selección del registro.

Campos gestionados:

Datos generales:
- Nombre.
- RUT.
- Teléfono.
- Dirección.

Datos específicos:
- Cliente:
  - Tour reservado.

- Guía turístico:
  - Idioma.

### 3. Panel Servicios

Permite administrar los servicios turísticos disponibles.

Funciones:

- Registrar servicios especializados.
- Visualizar catálogo de servicios.
- Modificar servicios existentes.
- Eliminar servicios considerando asociaciones con tours.

Tipos disponibles:

- Paseo Lacustre.
- Excursión Cultural.
- Ruta Gastronómica.

Campos gestionados:

- Tipo de servicio.
- Nombre.
- Duración.
- Información específica según el tipo:
  - Tipo de embarcación.
  - Lugar histórico.
  - Cantidad de paradas.

### VentanaPrincipal.java

Proporciona 4 pestañas (tabs) para gestionar:

1. **Tours** - Agregar tours con servicios asociados y visualizar todos los tours registrados.
2. **Personas** - Agregar clientes o guías turísticos según tipo seleccionado, visualizar todas las personas.
3. **Servicios** - Agregar servicios turísticos (Paseos, Excursiones, Rutas) y visualizar catálogo.
4. **Resumen General** - Mostrar resumen polimórfico de todas las entidades del sistema.

### Características de la GUI

- **Campos de entrada dinámicos** que se adaptan según el tipo de entidad seleccionada.
- **Validación en tiempo real** con mensajes de error usando `JOptionPane`.
- **Tablas visuales** (`JTable`) para mostrar entidades de forma estructurada.
- **Combos desplegables** para seleccionar tours, servicios e idiomas.
- **Botones contextuales** que ejecutan operaciones específicas.
- **Área de texto** para mostrar resúmenes y estadísticas.
- - Selección de registros mediante componentes JComboBox para evitar errores de escritura.
- Formularios reutilizables separados en paneles independientes.
- Ventanas emergentes de confirmación y validación mediante JOptionPane.

---

## Cómo ejecutar

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que existan los archivos en la carpeta `resources/`:

```text
resources/personas.txt
resources/tours.txt
resources/servicios.txt
```

3. Abrir la clase:

```java
app.Main
```

4. Ejecutar el método:

```java
public static void main(String[] args)
```

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

- Persistencia mediante base de datos.
- Reportes y estadísticas avanzadas de reservas.
- Búsqueda y filtrado avanzado desde GUI.
- Autenticación de usuarios.

---

## Autor

Karla Paz Soto
