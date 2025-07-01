[README.md](https://github.com/user-attachments/files/20449042/README.md)# Perfulandia

# 🧾 Proyecto: Transformación Digital - Perfulandia SPA

Este repositorio contiene el desarrollo técnico del sistema basado en microservicios para la empresa Perfulandia SPA, como parte de la Evaluación Parcial 3 de la asignatura **Desarrollo Full Stack I**.

## 📦 Descripción General del Proyecto

> Nuestro sistema busca facilitar y modernizar el sistema monolítico de perfulandia. Implementando 4 microservicios en total (usuarios, productos, carrito, pedidos) los cuales permitirá obtener una mayor escalabilidad, facilitar el mantenimiento, disminuir la latencia, etc. Lo que significará un mejor rendimiento general del sistema.
> 

## 🧩 Arquitectura de Microservicios

> 📝 Describir cómo está estructurado el sistema en microservicios. Pueden incluir un diagrama y explicar brevemente la función de cada servicio.
> 

### Microservicios Desarrollados

- `usuarioservice`: > Permite buscar, guardar, eliminar y listar usuarios.
- `productoservice`: > Permite buscar, guardar, eliminar y listar productos.
- `carritoservice`: > Permite agregar y eliminar productos, además de eliminar, buscar, listar y vaciar carritos.
- `pedidoservice`: > Permite crear, actualizar, listar, buscar y eliminar y pedidos.

## 🛠️ Tecnologías Utilizadas

> Para la creación de nuestra Base de datos usamos Laragon 🐘
 Para toda la creación de codigo utilizamos IntelliJ IDEA 🧑‍💻
 Para almacenar todos los archivos y tener acceso a ellos utilizamos GitHub 🐈‍⬛
> 

## 🗄️ Configuración de Bases de Datos

> En este caso nos decidimos por usar Laragon, ya que teníamos conocimiento previo con esta.
> 

Tabla Usuario:

![BD_Usuario.png](BD_Usuario.png)

Tabla Producto:

![BD_Producto.png](BD_Producto.png)

Tablas Carrito:

![BD_Carrito_1.png](BD_Carrito_1.png)

![BD_Carrito_2.png](BD_Carrito_2.png)

Tablas Pedidos:

![BD_Pedidos_1.png](BD_Pedidos_1.png)

![BD_Pedidos2.png](BD_Pedidos2.png)

## 📮 Endpoints y Pruebas

> Prueba en Postman en el caso de la creación de un usuario.
> 

![Usuario_Creacion.png](0aacb603-2ade-4276-9e71-460d92550dea.png)

Prueba en Postman en el caso de obtener un producto.

![Producto_Lectura.png](0fc3ecaf-52de-4c43-9543-d3770b37ee9a.png)

Prueba en Postman en el caso de la visualización de un pedido.

![Pedido_Lectura.png](f5922300-5f87-4f50-8503-df311f261b90.png)

Prueba en Postman en  el caso de la creación de un carrito de compras.

![Carrito_Creacion.png](50a76912-b142-42d0-bc97-636517466918.png)

## 🧑‍💻 Integrantes del Equipo

> 📝 Indicar nombre completo y rol de cada integrante del equipo.
> 

| Nombre | Rol en el proyecto |
| --- | --- |
| Matias Loyola | HateOAS |
| Constanza Cárdenas | Pruebas Unitarias |
| Claudio Aro | Swagger |

## 📂 Estructura del Repositorio

```
📦 perfulandia-backend
├── carritoservice
├── pedidoservice
├── usuarioservice
├── productoservice
└── README.md

```

## 👥 Colaboración en GitHub

> En este caso decidimos que cada uno se encargaría de una tarea diferente como ya lo señalamos en el apartado de Integrantes del Equipo.
> 

## 📈 Lecciones Aprendidas

> Gracias a este proyecto logramos desarrollar de mejor manera las habilidades de cada integrante del equipo, logrando así entender mucho mas sobre como es desarrollar un proyecto de este tipo.
> 

---
