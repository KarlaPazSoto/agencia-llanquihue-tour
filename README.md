# AgenciaLlanquihueTourApp

Aplicación con interfaz gráfica (GUI) desarrollada en Java para la gestión integral de una agencia turística.

El sistema permite administrar clientes, guías turísticos, tours disponibles y servicios turísticos especializados, implementando una arquitectura orientada a objetos con interfaces polimórficas, colecciones genéricas y persistencia de datos mediante archivos de texto.

---

## Objetivo

Gestionar información integral de una agencia turística permitiendo:

- Registrar clientes con asignación a tours específicos.
- Registrar guías turísticos con idiomas especializados.
- Registrar tours con servicios turísticos asociados.
- Registrar servicios turísticos (Paseos Lacustres, Excursiones Culturales, Rutas Gastronómicas).
- Consultar y visualizar todas las entidades registradas.
- Buscar personas por RUT.
- Filtrar tours según criterios.
- Mostrar resumen polimórfico de todas las entidades.
- Persistir información mediante archivos de texto (personas.txt, tours.txt, servicios.txt).

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

## Estructura del proyecto

```text
src
│
├── app
│   ├── Main.java
│   └── VentanaPrincipal.java
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

La aplicación cuenta con una interfaz visual amigable desarrollada con **Swing** (`JFrame`, `JPanel`, `JButton`, `JComboBox`, etc.).

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

- Eliminación y modificación de registros desde GUI.
- Persistencia mediante base de datos.
- Asociación entre guías turísticos y tours.
- Reportes y estadísticas avanzadas de reservas.
- Búsqueda y filtrado avanzado desde GUI.
- Autenticación de usuarios.

---

## Autor

Karla Paz Soto
