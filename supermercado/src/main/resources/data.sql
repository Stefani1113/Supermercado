-- CATEGORÍAS
INSERT IGNORE INTO categorias (id, nombre, descripcion) VALUES
(1, 'Lácteos',       'Leche, quesos, yogures y derivados'),
(2, 'Panadería',     'Pan, galletas, pasteles y productos horneados'),
(3, 'Bebidas',       'Jugos, gaseosas, agua y bebidas energéticas'),
(4, 'Aseo Personal', 'Jabones, champús, cremas y cuidado del cuerpo'),
(5, 'Carnes',        'Res, cerdo, pollo y embutidos');

-- PRODUCTOS
-- activo = 1 (true) para productos normales
-- activo = 0 (false) para simular un producto con Soft Delete
INSERT IGNORE INTO productos (id, nombre, descripcion, codigo_barras, precio, stock, activo, categoria_id) VALUES
-- Lácteos
(1,  'Leche Entera 1L',       'Leche entera pasteurizada',          '7702004000011', 3200.00,  50,  1, 1),
(2,  'Yogur Fresa 200g',      'Yogur de fresa con frutas',          '7702004000022', 1800.00,  30,  1, 1),
(3,  'Queso Campesino 500g',  'Queso fresco artesanal',             '7702004000033', 8500.00,  15,  1, 1),

-- Panadería
(4,  'Pan Tajado 500g',       'Pan blanco tajado de molde',         '7702004000044', 4200.00,  25,  1, 2),
(5,  'Galletas Integrales',   'Galletas de avena y miel 300g',      '7702004000055', 3500.00,  40,  1, 2),

-- Bebidas
(6,  'Agua Cristal 600ml',    'Agua purificada sin gas',            '7702004000066', 1500.00, 100,  1, 3),
(7,  'Jugo Hit Mango 330ml',  'Jugo de mango con pulpa',            '7702004000077', 2200.00,  60,  1, 3),
(8,  'Gaseosa Cola 2L',       'Bebida carbonatada sabor cola',      '7702004000088', 5500.00,  35,  1, 3),

-- Aseo Personal
(9,  'Jabón Dove 90g',        'Jabón en barra hidratante',          '7702004000099', 2800.00,  45,  1, 4),
(10, 'Champú Pantene 400ml',  'Champú para cabello normal',         '7702004000100', 15900.00, 20,  1, 4),

-- Carnes
(11, 'Pechuga Pollo 1kg',     'Pechuga de pollo fresca sin hueso',  '7702004000111', 12000.00, 10,  1, 5),
(12, 'Carne Molida 500g',     'Carne de res molida fresca',         '7702004000122', 9500.00,   8,  1, 5),

-- Producto con Soft Delete (activo = 0) para probar que NO aparece en listados
(13, 'Producto Descontinuado','Este producto fue dado de baja',     '7702004000133', 1000.00,   0,  0, 1);