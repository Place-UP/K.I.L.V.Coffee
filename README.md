
# K.I.L.V.Coffee - API

This is a complete API for online commerce plataform.


## Features

- Client/Seller register
- Products register
- Image upload
- Products reservation
- Search filter by product
- Search filter by seller accessibility


## API Reference

#### Get all sellers

```http
  GET /api/sellers
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `NULL` | `NULL` | **Don't have**. |

#### Get seller

```http
  GET /api/sellers/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of seller |

#### Post seller

```http
  POST /api/sellers
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of seller |
| `email`      | `string` | **Required**. Email of seller |
| `cnpj`      | `string` | **Required**. Cnpj of seller |
| `phone`      | `string` | **Required**. Phone of seller |
| `password`      | `string` | **Required**. password of seller |
| `mute`      | `boolean` | Seller have support for mute person |
| `blind`      | `boolean` | Seller have support for blind person |
| `weelchair`      | `boolean` | Seller have support for weelchair person |
| `deaf`      | `boolean` | Seller have support for deaf person |
| `state`      | `string` |  **Required**. State of seller |
| `city`      | `string` |  **Required**. City of seller |
| `district`      | `string` |  **Required**. District of seller|
| `street`      | `string` |  **Required**. Street of seller|
| `houseNumber`      | `string` |  **Required**. House number of seller|

#### Put seller

```http
  PUT /api/sellers/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of seller |


#### Delete seller

```http
  DELETE /api/sellers/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of seller |

#### Post seller attachment image

```http
  POST /api/attachments/seller/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `file`      | `multipart` | **Required**. File of seller image |


#### Get all clients

```http
  GET /api/clients
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `NULL` | `NULL` | **Don't have**. |


#### Get client

```http
  GET /api/clients/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of client |

#### Post client

```http
  POST /api/clients
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of client |
| `email`      | `string` | **Required**. Email of client |
| `password`      | `string` | **Required**. password of client |

#### Put client

```http
  PUT /api/clients/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of client |

#### Delete client

```http
  DELETE /api/clients/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of client |

#### Get all products

```http
  GET /api/products
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `order` | `string` | **Required**. The order of products list |


#### Get product

```http
  GET /api/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of item to fetch |

#### Post product

```http
  POST /api/products
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `description`      | `string` | **Required**. Description of product |
| `price`      | `big decimal` | **Required**. Price of product |
| `seller`      | `integer` | **Required**. Id of product's seller |

#### Put product

```http
  PUT /api/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of product |

#### Delete product

```http
  DELETE /api/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of product |

#### Post product attachment image

```http
  POST /api/attachments/product/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of item to fetch |

#### Get reservation

```http
  GET /api/reservations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of reservation |

#### Post reservation

```http
  POST /api/reservations
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `client`      | `integer` | **Required**. Id of client |
| `seller`      | `integer` | **Required**. Id of seller |
| `items`      | `array` | **Required**. items array |
| `items [product]`      | `integer` | **Required**. Id of product |
| `items [quantity]`      | `integer` | **Required**. Quantity of product |


#### Patch reservation status

```http
  PATCH /api/reservations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `newStatus`      | `string` | **Required**. Status of reservation |









## Authors

- [@kaykyFreitas](https://github.com/kaykyFreitas)
- [@TorHugo](https://github.com/TorHugo)
- [@Larissa-Sousa ](https://github.com/Larissa-Sousa)
- [@Igor-santos1](https://github.com/Igor-santos1)


#### By nightCode