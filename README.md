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
- [GET] /transactions/type/$type
- [GET] /transactions/sum/$transaction_id
- [PUT] /transactions/$transaction_id

### Aplicante
- [Ezequiel Rodriguez](https://github.com/erodriguezzz)