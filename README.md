# Mendel Challenge
Esta solución al desafío de Mendel se basa en una API "REST" que permite realizar algunas operaciones CRUD sobre transacciones.


## Ejecución
Para ejecutar el proyecto se deben seguir los siguientes pasos:

1. Correr el siguiente comandos desde la raíz del proyecto:

```bash
make build run
```

3. Para detener el contenedor y eliminar la imagen se puede utilizar el comando:

```bash
make clean
```

Se puede interactuar con la API a través de la URL `http://localhost:8080/`.

## Documentación

### Estructura

La API cuenta con una arquitectura de 3 capas:
- **<u>Persistencia</u>:** Donde se encuentra el repositorio de transacciones. A lo largo del desarrollo esta capa sufrió
varias modificaciones. Inicialmente se utilizó un repositorio con un mapa de transacciones, pero luego se optó por
hacer uso de JPA y Hibernate para persistir las transacciones en una base de datos H2. Sin embargo, se optó por volver
al uso del mapa para respetar el enunciado y **no usar SQL**, ya que internamente Hibernate utiliza SQL para realizar
las operaciones de persistencia.
- **<u>Servicio</u>:** Donde se encontraría la lógica de negocio. Esta capa es muy simple y solo se encarga de delegar las
operaciones al repositorio. Sin embargo, si se quisiera agregar más lógica como utilizar otro servicio para enviar
mails al momento de crear una transacción, esta sería la capa donde se debería implementar.
- **<u>Aplicación</u>:** Donde se encuentran el controlador y los endpoints de la API.

### Endpoints

- ```[GET] /transactions/$transaction_id```
- ```[GET] /transactions/type/$type```
- ```[GET] /transactions/sum/$transaction_id```
- ```[PUT] /transactions/$transaction_id```