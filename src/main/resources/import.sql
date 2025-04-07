/* SEXO */
SET IDENTITY_INSERT sexos ON;
INSERT sexos (id, sexo) VALUES (1, N'MUJER');
INSERT sexos (id, sexo) VALUES (2, N'HOMBRE');
INSERT sexos (id, sexo) VALUES (3, N'INTERSEX (INDETERMINADO)');
INSERT sexos (id, sexo) VALUES (4, N'DESCONOCIDO');
SET IDENTITY_INSERT sexos OFF;

/* NACIONALIDAD */
SET IDENTITY_INSERT nacionalidades ON;
INSERT nacionalidades (id, nacionalidad) VALUES (1, UPPER('Chile'));
INSERT nacionalidades (id, nacionalidad) VALUES (2, UPPER('Alemania'));
INSERT nacionalidades (id, nacionalidad) VALUES (3, UPPER('Argentina'));
INSERT nacionalidades (id, nacionalidad) VALUES (4, UPPER('Bolivia'));
INSERT nacionalidades (id, nacionalidad) VALUES (5, UPPER('Brasil'));
INSERT nacionalidades (id, nacionalidad) VALUES (6, UPPER('China'));
INSERT nacionalidades (id, nacionalidad) VALUES (7, UPPER('Colombia'));
INSERT nacionalidades (id, nacionalidad) VALUES (8, UPPER('Cuba'));
INSERT nacionalidades (id, nacionalidad) VALUES (9, UPPER('Ecuador'));
INSERT nacionalidades (id, nacionalidad) VALUES (10, UPPER('España'));
INSERT nacionalidades (id, nacionalidad) VALUES (11, UPPER('Estados Unidos de América'));
INSERT nacionalidades (id, nacionalidad) VALUES (12, UPPER('Haití'));
INSERT nacionalidades (id, nacionalidad) VALUES (13, UPPER('México'));
INSERT nacionalidades (id, nacionalidad) VALUES (14, UPPER('Perú'));
INSERT nacionalidades (id, nacionalidad) VALUES (15, UPPER('República Dominicana'));
INSERT nacionalidades (id, nacionalidad) VALUES (16, UPPER('Venezuela'));
INSERT nacionalidades (id, nacionalidad) VALUES (17, UPPER('Otro País'));
SET IDENTITY_INSERT nacionalidades OFF;

/* Region */
INSERT INTO regiones (region) VALUES(UPPER('De Tarapacá'));
INSERT INTO regiones (region) VALUES(UPPER('De Antofagasta'));
INSERT INTO regiones (region) VALUES(UPPER('De Atacama'));
INSERT INTO regiones (region) VALUES(UPPER('De Coquimbo'));
INSERT INTO regiones (region) VALUES(UPPER('De Valparaiso'));
INSERT INTO regiones (region) VALUES(UPPER('Del Libertador B. O´Higgins'));
INSERT INTO regiones (region) VALUES(UPPER('Del Maule'));
INSERT INTO regiones (region) VALUES(UPPER('Del Biobío'));
INSERT INTO regiones (region) VALUES(UPPER('De La Araucanía'));
INSERT INTO regiones (region) VALUES(UPPER('De Los Lagos'));
INSERT INTO regiones (region) VALUES(UPPER('De Aisén del Gral.C.Ibañez del Campo'));
INSERT INTO regiones (region) VALUES(UPPER('De Magallanes y de La Antártica chilena'));
INSERT INTO regiones (region) VALUES(UPPER('Metropolitana De Santiago'));
INSERT INTO regiones (region) VALUES(UPPER('De Los Ríos'));
INSERT INTO regiones (region) VALUES(UPPER('De Arica y Parinacota'));
INSERT INTO regiones (region) VALUES(UPPER('De Ñuble'));

/* SERVICIO DE SALUD */
SET IDENTITY_INSERT servicios_salud ON;
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(1, UPPER('Arica y Parinacota'), '15');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(2, UPPER('Tarapacá'), '1');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(3, UPPER('Antofagasta'), '2');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(4, UPPER('Atacama'), '3');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(5, UPPER('Coquimbo'), '4');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(6, UPPER('Valparaíso San Antonio'), '5');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(7, UPPER('Viña del Mar Quillota'), '5');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(8, UPPER('Aconcagua'), '5');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(9, UPPER('Norte'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(10, UPPER('Occidente'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(11, UPPER('Central'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(12, UPPER('Oriente'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(13, UPPER('Sur'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(14, UPPER('Sur-Oriente'), '13');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(15, UPPER('O´Higgins'), '6');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(16, UPPER('Maule'), '7');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(17, UPPER('Ñuble'), '16');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(18, UPPER('Concepción'), '8');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(28, UPPER('Arauco'), '8');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(19, UPPER('Talcahuano'), '8');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(20, UPPER('Biobío'), '8');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(29, UPPER('Araucanía Norte'), '9');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(21, UPPER('Araucanía Sur'), '9');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(22, UPPER('Los Ríos'), '14');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(23, UPPER('Osorno'), '10');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(24, UPPER('Reloncaví'), '10');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(33, UPPER('Chiloé'), '10');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(25, UPPER('Aisén'), '11');
INSERT INTO servicios_salud (id, servicio_salud, region_id) VALUES(26, UPPER('Magallanes'), '12');
SET IDENTITY_INSERT servicios_salud OFF;

/* COMUNA */
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1101', UPPER('Iquique'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1107', UPPER('Alto Hospicio'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1401', UPPER('Pozo Almonte'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1402', UPPER('Camiña'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1403', UPPER('Colchane'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1404', UPPER('Huara'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('1405', UPPER('Pica'), '1', '2');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2101', UPPER('Antofagasta'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2102', UPPER('Mejillones'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2103', UPPER('Sierra Gorda'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2104', UPPER('Taltal'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2201', UPPER('Calama'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2202', UPPER('Ollagüe'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2203', UPPER('San Pedro de Atacama'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2301', UPPER('Tocopilla'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('2302', UPPER('Maria Elena'), '2', '3');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3101', UPPER('Copiapo'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3102', UPPER('Caldera'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3103', UPPER('Tierra Amarilla'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3201', UPPER('Chañaral'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3202', UPPER('Diego de Almagro'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3301', UPPER('Vallenar'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3302', UPPER('Alto del Carmen'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3303', UPPER('Freirina'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('3304', UPPER('Huasco'), '3', '4');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4101', UPPER('La Serena'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4102', UPPER('Coquimbo'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4103', UPPER('Andacollo'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4104', UPPER('La Higuera'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4105', UPPER('Paiguano'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4106', UPPER('Vicuña'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4201', UPPER('Illapel'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4202', UPPER('Canela '), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4203', UPPER('Los Vilos'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4204', UPPER('Salamanca'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4301', UPPER('Ovalle'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4302', UPPER('Combarbalá'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4303', UPPER('Monte Patria'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4304', UPPER('Punitaqui'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('4305', UPPER('Río Hurtado'), '4', '5');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5101', UPPER('Valparaíso'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5102', UPPER('Casablanca'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5103', UPPER('Con Con'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5104', UPPER('Juan Fernández'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5105', UPPER('Puchuncaví'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5801', UPPER('Quilpue'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5107', UPPER('Quintero'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5804', UPPER('Villa Alemana'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5109', UPPER('Viña del Mar'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5201', UPPER('Isla de Pascua'), '5', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5301', UPPER('Los Andes'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5302', UPPER('Calle Larga'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5303', UPPER('Rinconada'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5304', UPPER('San Esteban'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5401', UPPER('La Ligua'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5402', UPPER('Cabildo'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5403', UPPER('Papudo'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5404', UPPER('Petorca'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5405', UPPER('Zapallar'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5501', UPPER('Quillota'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5502', UPPER('Calera'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5503', UPPER('Hijuelas'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5504', UPPER('La Cruz'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5802', UPPER('Limache'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5506', UPPER('Nogales'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5803', UPPER('Olmué'), '5', '7');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5601', UPPER('San Antonio'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5602', UPPER('Algarrobo'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5603', UPPER('Cartagena'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5604', UPPER('El Quisco'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5605', UPPER('El Tabo'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5606', UPPER('Santo Domingo'), '5', '6');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5701', UPPER('San Felipe'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5702', UPPER('Catemu'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5703', UPPER('Llaillay'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5704', UPPER('Panquehue'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5705', UPPER('Putaendo'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('5706', UPPER('Santa Maria'), '5', '8');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6101', UPPER('Rancagua'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6102', UPPER('Codegua'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6103', UPPER('Coinco'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6104', UPPER('Coltauco'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6105', UPPER('Doñihue'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6106', UPPER('Graneros'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6107', UPPER('Las Cabras'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6108', UPPER('Machalí'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6109', UPPER('Malloa'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6110', UPPER('Mostazal'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6111', UPPER('Olivar'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6112', UPPER('Peumo'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6113', UPPER('Pichidegua'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6114', UPPER('Quinta de Tilcoco'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6115', UPPER('Rengo'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6116', UPPER('Requínoa'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6117', UPPER('San Vicente'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6201', UPPER('Pichilemu'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6202', UPPER('La Estrella'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6203', UPPER('Litueche'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6204', UPPER('Marchihue'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6205', UPPER('Navidad'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6206', UPPER('Paredones'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6301', UPPER('San Fernando'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6302', UPPER('Chepica'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6303', UPPER('Chimbarongo'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6304', UPPER('Lolol'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6305', UPPER('Nancagua'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6306', UPPER('Palmilla'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6307', UPPER('Peralillo'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6308', UPPER('Placilla'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6309', UPPER('Pumanque'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('6310', UPPER('Santa Cruz'), '6', '15');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7101', UPPER('Talca'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7102', UPPER('Constitución'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7103', UPPER('Curepto'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7104', UPPER('Empedrado'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7105', UPPER('Maule'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7106', UPPER('Pelarco'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7107', UPPER('Pencahue'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7108', UPPER('Río Claro'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7109', UPPER('San Clemente'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7110', UPPER('San Rafael'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7201', UPPER('Cauquenes'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7202', UPPER('Chanco'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7203', UPPER('Pelluhue'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7301', UPPER('Curicó'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7302', UPPER('Hualañé'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7303', UPPER('Licantén'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7304', UPPER('Molina'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7305', UPPER('Rauco'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7306', UPPER('Romeral'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7307', UPPER('Sagrada Familia'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7308', UPPER('Teno'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7309', UPPER('Vichuquén'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7401', UPPER('Linares'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7402', UPPER('Colbún'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7403', UPPER('Longaví'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7404', UPPER('Parral'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7405', UPPER('Retiro'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7406', UPPER('San Javier'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7407', UPPER('Villa Alegre'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('7408', UPPER('Yerbas Buenas'), '7', '16');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8101', UPPER('Concepción'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8102', UPPER('Coronel'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8103', UPPER('Chiguayante'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8104', UPPER('Florida'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8105', UPPER('Hualqui'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8106', UPPER('Lota'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8107', UPPER('Penco'), '8', '19');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8108', UPPER('San Pedro de la Paz'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8109', UPPER('Santa Juana'), '8', '18');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8110', UPPER('Talcahuano'), '8', '19');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8111', UPPER('Tome'), '8', '19');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8112', UPPER('Hualpén'), '8', '19');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8201', UPPER('Lebu'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8202', UPPER('Arauco'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8203', UPPER('Cañete'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8204', UPPER('Contulmo'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8205', UPPER('Curanilahue'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8206', UPPER('Los Álamos'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8207', UPPER('Tirúa'), '8', '28');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8301', UPPER('Los Ángeles'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8302', UPPER('Antuco'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8303', UPPER('Cabrero'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8304', UPPER('Laja'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8305', UPPER('Mulchén'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8306', UPPER('Nacimiento'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8307', UPPER('Negrete'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8308', UPPER('Quilaco'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8309', UPPER('Quilleco'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8310', UPPER('San Rosendo'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8311', UPPER('Santa Bárbara'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8312', UPPER('Tucapel'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8313', UPPER('Yumbel'), '8', '20');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('8314', UPPER('Alto Biobío'), '8', '20');
/* salto */
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16101', UPPER('Chillán'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16102', UPPER('Bulnes'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16202', UPPER('Cobquecura'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16203', UPPER('Coelemu'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16302', UPPER('Coihueco'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16103', UPPER('Chillán Viejo'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16104', UPPER('El Carmen'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16204', UPPER('Ninhue'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16303', UPPER('Ñiquén'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16105', UPPER('Pemuco'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16106', UPPER('Pinto'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16205', UPPER('Portezuelo'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16107', UPPER('Quillón'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16201', UPPER('Quirihue'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16206', UPPER('Ránquil'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16301', UPPER('San Carlos'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16304', UPPER('San Fabián'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16108', UPPER('San Ignacio'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16305', UPPER('San Nicolás'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16207', UPPER('Treguaco'), '16', '17');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16109', UPPER('Yungay'), '16', '17');
/* fin salto */
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9101', UPPER('Temuco'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9102', UPPER('Carahue'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9103', UPPER('Cunco'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9104', UPPER('Curarrehue'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9105', UPPER('Freire'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9106', UPPER('Galvarino'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9107', UPPER('Gorbea'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9108', UPPER('Lautaro'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9109', UPPER('Loncoche'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9110', UPPER('Melipeuco'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9111', UPPER('Nueva Imperial'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9112', UPPER('Padre Las Casas'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9113', UPPER('Perquenco'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9114', UPPER('Pitrufquén'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9115', UPPER('Pucón'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9116', UPPER('Saavedra'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9117', UPPER('Teodoro Schmidt'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9118', UPPER('Toltén'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9119', UPPER('Vilcún'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9120', UPPER('Villarrica'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9121', UPPER('Cholchol'), '9', '21');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9201', UPPER('Angol'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9202', UPPER('Collipulli'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9203', UPPER('Curacautín'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9204', UPPER('Ercilla'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9205', UPPER('Lonquimay'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9206', UPPER('Los Sauces'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9207', UPPER('Lumaco'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9208', UPPER('Purén'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9209', UPPER('Renaico'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9210', UPPER('Traiguén'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('9211', UPPER('Victoria'), '9', '29');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10101', UPPER('Puerto Montt'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10102', UPPER('Calbuco'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10103', UPPER('Cochamó'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10104', UPPER('Fresia'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10105', UPPER('Frutillar'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10106', UPPER('Los Muermos'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10107', UPPER('Llanquihue'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10108', UPPER('Maullín'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10109', UPPER('Puerto Varas'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10201', UPPER('Castro'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10202', UPPER('Ancud'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10203', UPPER('Chonchi'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10204', UPPER('Curaco de Vélez'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10205', UPPER('Dalcahue'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10206', UPPER('Puqueldón'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10207', UPPER('Queilén'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10208', UPPER('Quellón'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10209', UPPER('Quemchi'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10210', UPPER('Quinchao'), '10', '33');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10301', UPPER('Osorno'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10302', UPPER('Puerto Octay'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10303', UPPER('Purranque'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10304', UPPER('Puyehue'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10305', UPPER('Río Negro'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10306', UPPER('San Juan de la Costa'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10307', UPPER('San Pablo'), '10', '23');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10401', UPPER('Chaitén'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10402', UPPER('Futaleufú'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10403', UPPER('Hualaihué'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('10404', UPPER('Palena'), '10', '24');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11101', UPPER('Coihaique'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11102', UPPER('Lago Verde'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11201', UPPER('Aisén'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11202', UPPER('Cisnes'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11203', UPPER('Guaitecas'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11301', UPPER('Cochrane'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11302', UPPER('O´Higgins'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11303', UPPER('Tortel'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11401', UPPER('Chile Chico'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('11402', UPPER('Río Ibáñez'), '11', '25');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12101', UPPER('Punta Arenas'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12102', UPPER('Laguna Blanca'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12103', UPPER('Río Verde'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12104', UPPER('San Gregorio'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12201', UPPER('Cabo de Hornos'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12202', UPPER('Antártica'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12301', UPPER('Porvenir'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12302', UPPER('Primavera'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12303', UPPER('Timaukel'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12401', UPPER('Natales'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('12402', UPPER('Torres del Paine'), '12', '26');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13101', UPPER('Santiago'), '13', '11');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13102', UPPER('Cerrillos'), '13', '11');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13103', UPPER('Cerro Navia'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13104', UPPER('Conchalí'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13105', UPPER('El Bosque'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13106', UPPER('Estación Central'), '13', '11');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13107', UPPER('Huechuraba'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13108', UPPER('Independencia'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13109', UPPER('La Cisterna'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13110', UPPER('La Florida'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13111', UPPER('La Granja'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('16307', UPPER('La Granja MS'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13112', UPPER('La Pintana'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13113', UPPER('La Reina'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13114', UPPER('Las Condes'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13115', UPPER('Lo Barnechea'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13116', UPPER('Lo Espejo'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13117', UPPER('Lo Prado'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13118', UPPER('Macul'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13119', UPPER('Maipu'), '13', '11');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13120', UPPER('Ñuñoa'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13121', UPPER('Pedro Aguirre Cerda'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13122', UPPER('Peñalolén'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13123', UPPER('Providencia'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13124', UPPER('Pudahuel'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13125', UPPER('Quilicura'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13126', UPPER('Quinta Normal'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13127', UPPER('Recoleta'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13128', UPPER('Renca'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13129', UPPER('San Joaquín'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13130', UPPER('San Miguel'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13131', UPPER('San Ramón'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13132', UPPER('Vitacura'), '13', '12');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13201', UPPER('Puente Alto'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13202', UPPER('Pirque'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13203', UPPER('San José de Maipo'), '13', '14');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13301', UPPER('Colina'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13302', UPPER('Lampa'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13303', UPPER('Tiltil'), '13', '9');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13401', UPPER('San Bernardo'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13402', UPPER('Buin'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13403', UPPER('Calera de Tango'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13404', UPPER('Paine'), '13', '13');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13501', UPPER('Melipilla'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13502', UPPER('Alhué'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13503', UPPER('Curacaví'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13504', UPPER('Maria Pinto'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13505', UPPER('San Pedro'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13601', UPPER('Talagante'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13602', UPPER('El Monte'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13603', UPPER('Isla de Maipo'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13604', UPPER('Padre Hurtado'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('13605', UPPER('Peñaflor'), '13', '10');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14101', UPPER('Valdivia'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14102', UPPER('Corral'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14103', UPPER('Lanco'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14104', UPPER('Los Lagos'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14105', UPPER('Máfil'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14106', UPPER('Mariquina'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14107', UPPER('Paillaco'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14108', UPPER('Panguipulli'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14201', UPPER('La Unión'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14202', UPPER('Futrono'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14203', UPPER('Lago Ranco'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('14204', UPPER('Río Bueno'), '14', '22');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('15101', UPPER('Arica'), '15', '1');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('15102', UPPER('Camarones'), '15', '1');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('15201', UPPER('Putre'), '15', '1');
INSERT INTO comunas (codigo_comuna, comuna, region_id, servicio_salud_id) VALUES('15202', UPPER('General Lagos'), '15', '1');

/* Establecimientos */

/* ######################################################################################################################*/

/* Administracion Salud */
SET IDENTITY_INSERT administracion_salud ON;
INSERT administracion_salud (id, admin_salud) VALUES (1, N'DSMU')
INSERT administracion_salud (id, admin_salud) VALUES (2, N'DSMR')
SET IDENTITY_INSERT administracion_salud OFF;

/* Ley */
SET IDENTITY_INSERT ley ON;
INSERT ley (id, ley) VALUES (1, N'Ley 19.378')
INSERT ley (id, ley) VALUES (2, N'Honorario')
INSERT ley (id, ley) VALUES (3, N'Código del Trabajo')
INSERT ley (id, ley) VALUES (4, N'Ley 19.664')
SET IDENTITY_INSERT ley OFF;

/* Tipo Contratos */
SET IDENTITY_INSERT tipo_contrato ON;
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (1, N'INDEFINIDO', 1)
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (2, N'PLAZO FIJO', 1)
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (3, N'REEMPLAZO', 1)
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (4, N'N/A', 2)
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (5, N'N/A', 3)
INSERT tipo_contrato (id, tipo_contrato, ley_id) VALUES (6, N'CONTRATA', 4)
SET IDENTITY_INSERT tipo_contrato OFF;

/* Categorias de Profesiones */
SET IDENTITY_INSERT categoria_profesion ON;
INSERT categoria_profesion (id, categoria_profesion) VALUES (1, N'A')
INSERT categoria_profesion (id, categoria_profesion) VALUES (2, N'B')
INSERT categoria_profesion (id, categoria_profesion) VALUES (3, N'C')
INSERT categoria_profesion (id, categoria_profesion) VALUES (4, N'D')
INSERT categoria_profesion (id, categoria_profesion) VALUES (5, N'E')
INSERT categoria_profesion (id, categoria_profesion) VALUES (6, N'F')
INSERT categoria_profesion (id, categoria_profesion) VALUES (7, N'N/A')
SET IDENTITY_INSERT categoria_profesion OFF;

/* Nivel de Carrera */
SET IDENTITY_INSERT nivel_carrera ON;
INSERT nivel_carrera (id, nivel_carrera) VALUES (1, N'15')
INSERT nivel_carrera (id, nivel_carrera) VALUES (2, N'14')
INSERT nivel_carrera (id, nivel_carrera) VALUES (3, N'13')
INSERT nivel_carrera (id, nivel_carrera) VALUES (4, N'12')
INSERT nivel_carrera (id, nivel_carrera) VALUES (5, N'11')
INSERT nivel_carrera (id, nivel_carrera) VALUES (6, N'10')
INSERT nivel_carrera (id, nivel_carrera) VALUES (7, N'9')
INSERT nivel_carrera (id, nivel_carrera) VALUES (8, N'8')
INSERT nivel_carrera (id, nivel_carrera) VALUES (9, N'7')
INSERT nivel_carrera (id, nivel_carrera) VALUES (10, N'6')
INSERT nivel_carrera (id, nivel_carrera) VALUES (11, N'5')
INSERT nivel_carrera (id, nivel_carrera) VALUES (12, N'4')
INSERT nivel_carrera (id, nivel_carrera) VALUES (13, N'3')
INSERT nivel_carrera (id, nivel_carrera) VALUES (14, N'2')
INSERT nivel_carrera (id, nivel_carrera) VALUES (15, N'1')
INSERT nivel_carrera (id, nivel_carrera) VALUES (16, N'0')
SET IDENTITY_INSERT nivel_carrera OFF;

/* Profesion relacionada a su categoria*/
SET IDENTITY_INSERT profesion ON;
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (1, N'BIOQUIMICO', 1)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (2, N'MEDICO', 1)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (3, N'ODONTOLOGO(A)', 1)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (4, N'QUIMICO FARMACEUTICO', 1)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (5, N'ASISTENTE SOCIAL', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (6, N'ENFERMERA(O)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (7, N'FONOAUDIOLOGO(A)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (8, N'INGENIERO(A)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (9, N'KINESIOLOGO(A)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (10, N'NUTRICIONISTA', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (11, N'PROFESOR(A)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (12, N'PSICOLOGO(A)', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (13, N'TECNOLOGO(A) MEDICO', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (14, N'TERAPEUTA OCUPACIONAL', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (15, N'OTRO', 2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (16, N'TENS', 3)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (17, N'TANS', 3)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (18, N'TECNICO DE SALUD', 4)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (19, N'ADMINISTRATIVO', 5)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (20, N'AUXILIAR DE SERVICIO', 6)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (21, N'MATRON(A)', 2)
-- INSERT profesion (id, profesion, categoria_profesion_id) VALUES (22, N'PROFESION', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (23, N'BIOQUIMICO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (24, N'MEDICO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (25, N'ODONTOLOGO(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (26, N'QUIMICO FARMACEUTICO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (27, N'ASISTENTE SOCIAL', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (28, N'ENFERMERA(O)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (29, N'FONOAUDIOLOGO(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (30, N'INGENIERO(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (31, N'KINESIOLOGO(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (32, N'NUTRICIONISTA', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (33, N'PROFESOR(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (34, N'PSICOLOGO(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (35, N'TECNOLOGO(A) MEDICO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (36, N'TERAPEUTA OCUPACIONAL', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (37, N'OTRO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (38, N'TENS', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (39, N'TANS', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (40, N'TECNICO DE SALUD', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (41, N'ADMINISTRATIVO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (42, N'AUXILIAR DE SERVICIO', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (43, N'MATRON(A)', 7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (44, N'TONS',3)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (45, N'TONS',7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (46, N'ADMINISTRADOR (A) PUBLICO(A)',2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (47, N'CONTADOR (A)',2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (48, N'PSICOPEDAGOGA',2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (49, N'ADMINISTRADOR (A) PUBLICO(A)',7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (50, N'CONTADOR (A)',7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (51, N'PSICOPEDAGOGA',7)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (52, N'EDUCADOR(A) DE PARVULOS',2)
INSERT profesion (id, profesion, categoria_profesion_id) VALUES (53, N'EDUCADOR(A) DE PARVULOS',7)
SET IDENTITY_INSERT profesion OFF;

/* Especialidades (Medicos)*/
SET IDENTITY_INSERT especialidad ON;
INSERT especialidad (id, especialidad, profesion_id) VALUES (1, N'N/A', 1)
INSERT especialidad (id, especialidad, profesion_id) VALUES (2, N'MEDICINA FAMILIAR', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (3, N'PEDIATRIA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (4, N'MEDICINA INTERNA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (5, N'GINECO-OBSTETRICIA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (6, N'PSIQUIATRIA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (7, N'SALUD PUBLICA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (8, N'OTRAS', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (9, N'N/A', 3)
INSERT especialidad (id, especialidad, profesion_id) VALUES (10, N'N/A', 4)
INSERT especialidad (id, especialidad, profesion_id) VALUES (11, N'N/A', 5)
INSERT especialidad (id, especialidad, profesion_id) VALUES (12, N'N/A', 6)
INSERT especialidad (id, especialidad, profesion_id) VALUES (13, N'N/A', 7)
INSERT especialidad (id, especialidad, profesion_id) VALUES (14, N'N/A', 8)
INSERT especialidad (id, especialidad, profesion_id) VALUES (15, N'N/A', 9)
INSERT especialidad (id, especialidad, profesion_id) VALUES (16, N'N/A', 10)
INSERT especialidad (id, especialidad, profesion_id) VALUES (17, N'N/A', 11)
INSERT especialidad (id, especialidad, profesion_id) VALUES (18, N'N/A', 12)
INSERT especialidad (id, especialidad, profesion_id) VALUES (19, N'N/A', 13)
INSERT especialidad (id, especialidad, profesion_id) VALUES (20, N'N/A', 14)
INSERT especialidad (id, especialidad, profesion_id) VALUES (21, N'N/A', 15)
INSERT especialidad (id, especialidad, profesion_id) VALUES (22, N'N/A', 16)
INSERT especialidad (id, especialidad, profesion_id) VALUES (23, N'N/A', 17)
INSERT especialidad (id, especialidad, profesion_id) VALUES (24, N'N/A', 18)
INSERT especialidad (id, especialidad, profesion_id) VALUES (25, N'N/A', 19)
INSERT especialidad (id, especialidad, profesion_id) VALUES (26, N'N/A', 20)
INSERT especialidad (id, especialidad, profesion_id) VALUES (27, N'N/A', 21)
INSERT especialidad (id, especialidad, profesion_id) VALUES (28, N'N/A', 22)
INSERT especialidad (id, especialidad, profesion_id) VALUES (29, N'N/A', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (30, N'N/A', 23)
INSERT especialidad (id, especialidad, profesion_id) VALUES (31, N'MEDICINA FAMILIAR', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (32, N'PEDIATRIA', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (33, N'MEDICINA INTERNA', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (34, N'GINECO-OBSTETRICIA', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (35, N'PSIQUIATRIA', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (36, N'SALUD PUBLICA', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (37, N'OTRAS', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (38, N'N/A', 24)
INSERT especialidad (id, especialidad, profesion_id) VALUES (39, N'N/A', 25)
INSERT especialidad (id, especialidad, profesion_id) VALUES (40, N'N/A', 26)
INSERT especialidad (id, especialidad, profesion_id) VALUES (41, N'N/A', 27)
INSERT especialidad (id, especialidad, profesion_id) VALUES (42, N'N/A', 28)
INSERT especialidad (id, especialidad, profesion_id) VALUES (43, N'N/A', 29)
INSERT especialidad (id, especialidad, profesion_id) VALUES (44, N'N/A', 30)
INSERT especialidad (id, especialidad, profesion_id) VALUES (45, N'N/A', 31)
INSERT especialidad (id, especialidad, profesion_id) VALUES (46, N'N/A', 32)
INSERT especialidad (id, especialidad, profesion_id) VALUES (47, N'N/A', 33)
INSERT especialidad (id, especialidad, profesion_id) VALUES (48, N'N/A', 34)
INSERT especialidad (id, especialidad, profesion_id) VALUES (49, N'N/A', 35)
INSERT especialidad (id, especialidad, profesion_id) VALUES (50, N'N/A', 36)
INSERT especialidad (id, especialidad, profesion_id) VALUES (51, N'N/A', 37)
INSERT especialidad (id, especialidad, profesion_id) VALUES (52, N'N/A', 38)
INSERT especialidad (id, especialidad, profesion_id) VALUES (53, N'N/A', 39)
INSERT especialidad (id, especialidad, profesion_id) VALUES (54, N'N/A', 40)
INSERT especialidad (id, especialidad, profesion_id) VALUES (55, N'N/A', 41)
INSERT especialidad (id, especialidad, profesion_id) VALUES (56, N'N/A', 42)
INSERT especialidad (id, especialidad, profesion_id) VALUES (57, N'N/A', 43)
INSERT especialidad (id, especialidad, profesion_id) VALUES (58, N'N/A', 44)
INSERT especialidad (id, especialidad, profesion_id) VALUES (59, N'N/A', 46)
INSERT especialidad (id, especialidad, profesion_id) VALUES (60, N'N/A', 47)
INSERT especialidad (id, especialidad, profesion_id) VALUES (61, N'N/A', 48)
INSERT especialidad (id, especialidad, profesion_id) VALUES (62, N'N/A', 52)
INSERT especialidad (id, especialidad, profesion_id) VALUES (63, N'MEDICINA DE URGENCIA', 2)
INSERT especialidad (id, especialidad, profesion_id) VALUES (64, N'MEDICINA DE URGENCIA', 24)
SET IDENTITY_INSERT especialidad OFF;

/* Cargos de trabajp o puestos laborales */
SET IDENTITY_INSERT cargo ON;
INSERT cargo (id, cargo) VALUES (1, N'ADMINISTRATIVO')
INSERT cargo (id, cargo) VALUES (2, N'ATENCIÓN CLÍNICA')
INSERT cargo (id, cargo) VALUES (3, N'ATENCIÓN ODONTOLÓGICA')
INSERT cargo (id, cargo) VALUES (4, N'AUXILIAR DE SERVICIO')
INSERT cargo (id, cargo) VALUES (5, N'CHOFER')
INSERT cargo (id, cargo) VALUES (6, N'DIRECTOR(A)')
INSERT cargo (id, cargo) VALUES (7, N'ENCARGADO PROGRAMA')
INSERT cargo (id, cargo) VALUES (8, N'ESTADÍSTICA')
INSERT cargo (id, cargo) VALUES (9, N'GUARDIA')
INSERT cargo (id, cargo) VALUES (10, N'JEFE(A) DE SECTOR')
INSERT cargo (id, cargo) VALUES (11, N'JEFE(A) SOME')
INSERT cargo (id, cargo) VALUES (12, N'MANTENIMIENTO')
INSERT cargo (id, cargo) VALUES (13, N'SECRETARIA')
INSERT cargo (id, cargo) VALUES (14, N'SUBDIRECTOR(A)')
INSERT cargo (id, cargo) VALUES (15, N'OTRO')
INSERT cargo (id, cargo) VALUES (16, N'SUBDIRECCION ADMINISTRATIVA')
INSERT cargo (id, cargo) VALUES (17, N'AREA TRANVERSAL')
INSERT cargo (id, cargo) VALUES (18, N'ENCARGADO (A) CECOSF')
INSERT cargo (id, cargo) VALUES (19, N'INFORMATICA')
INSERT cargo (id, cargo) VALUES (20, N'ABASTECIMIENTO')
INSERT cargo (id, cargo) VALUES (21, N'REMUNERACIONES')
INSERT cargo (id, cargo) VALUES (22, N'LICENCIAS MEDICAS')
INSERT cargo (id, cargo) VALUES (23, N'CONVENIOS')
INSERT cargo (id, cargo) VALUES (24, N'PROYECTOS')
SET IDENTITY_INSERT cargo OFF;

/* Asignacion Chofer */
SET IDENTITY_INSERT asignacion_chofer ON;
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (1, N'N/A', 1)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (2, N'N/A', 2)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (3, N'N/A', 3)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (4, N'N/A', 4)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (5, N'SI', 5)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (6, N'NO', 5)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (7, N'N/A', 6)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (8, N'N/A', 7)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (9, N'N/A', 8)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (10, N'N/A', 9)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (11, N'N/A', 10)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (12, N'N/A', 11)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (13, N'N/A', 12)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (14, N'N/A', 13)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (15, N'N/A', 14)
INSERT asignacion_chofer (id, asignacion_chofer, cargo_id) VALUES (16, N'N/A', 15)
SET IDENTITY_INSERT asignacion_chofer OFF;

/* Bienios */
SET IDENTITY_INSERT bienios ON;
INSERT bienios (id, bienios) VALUES (1, N'0')
INSERT bienios (id, bienios) VALUES (2, N'1')
INSERT bienios (id, bienios) VALUES (3, N'2')
INSERT bienios (id, bienios) VALUES (4, N'3')
INSERT bienios (id, bienios) VALUES (5, N'4')
INSERT bienios (id, bienios) VALUES (6, N'5')
INSERT bienios (id, bienios) VALUES (7, N'6')
INSERT bienios (id, bienios) VALUES (8, N'7')
INSERT bienios (id, bienios) VALUES (9, N'8')
INSERT bienios (id, bienios) VALUES (10, N'9')
INSERT bienios (id, bienios) VALUES (11, N'10')
INSERT bienios (id, bienios) VALUES (12, N'11')
INSERT bienios (id, bienios) VALUES (13, N'12')
INSERT bienios (id, bienios) VALUES (14, N'13')
INSERT bienios (id, bienios) VALUES (15, N'14')
INSERT bienios (id, bienios) VALUES (16, N'15')
SET IDENTITY_INSERT bienios OFF;

/* Sistema o Tipo de Prevision */
SET IDENTITY_INSERT prevision ON;
INSERT prevision (id, prevision) VALUES (1, N'AFP')
INSERT prevision (id, prevision) VALUES (2, N'IPS')
INSERT prevision (id, prevision) VALUES (3, N'NO COTIZA')
SET IDENTITY_INSERT prevision OFF;

/* Tipo de Isapre */
SET IDENTITY_INSERT isapre ON;
INSERT isapre (id, isapre) VALUES (1, N'FONASA')
INSERT isapre (id, isapre) VALUES (2, N'ISAPRE')
INSERT isapre (id, isapre) VALUES (3, N'CAPREDENA')
INSERT isapre (id, isapre) VALUES (4, N'DIPRECA')
INSERT isapre (id, isapre) VALUES (5, N'SISA')
INSERT isapre (id, isapre) VALUES (6, N'NINGUNA')
INSERT isapre (id, isapre) VALUES (7, N'DESCONOCIDO')
SET IDENTITY_INSERT isapre OFF;

/*Servicio-Comuna: Es la comuna donde se encuentra el servicio de salud */
SET IDENTITY_INSERT servicio_comuna ON;
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (1, 8, N'Aconcagua', 5701, N'San Felipe', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (2, 3, N'Antofagasta', 2101, N'Antofagasta', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (3, 29, N'Araucanía Norte', 9201, N'Angol', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (4, 21, N'Araucanía Sur', 9101, N'Temuco', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (5, 28, N'Arauco', 8201, N'Lebu', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (6, 1, N'Arica y Parinacota', 15101, N'Arica', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (7, 4, N'Atacama', 3101, N'Copiapo', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (8, 20, N'Biobío', 8301, N'Los Ángeles', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (9, 33, N'Chiloé', 10201, N'Castro', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (10, 18, N'Concepción', 8101, N'Concepción', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (11, 5, N'Coquimbo', 4101, N'La Serena', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (12, 15, N'O?Higgins', 6101, N'Rancagua', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (13, 16, N'Maule', 7101, N'Talca', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (14, 24, N'Reloncaví', 10101, N'Puerto Montt', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (15, 2, N'Tarapacá', 1101, N'Iquique', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (16, 26, N'Magallanes', 12101, N'Punta Arenas', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (17, 11, N'Central', 13101, N'Santiago', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (18, 9, N'Norte', 13108, N'Independencia', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (19, 10, N'Occidente', 13126, N'Quinta Normal', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (20, 12, N'Oriente', 13123, N'Providencia', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (21, 13, N'Sur', 13130, N'San Miguel', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (22, 14, N'Sur-Oriente', 13201, N'Puente Alto', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (23, 17, N'Ñuble', 16101, N'Chillán', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (24, 23, N'Osorno', 10301, N'Osorno', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (25, 19, N'Talcahuano', 8110, N'Talcahuano', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (26, 22, N'Los Ríos', 14101, N'Valdivia', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (27, 6, N'Valparaíso San Antonio', 5101, N'Valparaíso', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (28, 7, N'Viña del Mar Quillota', 5109, N'Viña del Mar', 1, N'Servicio de Salud')
INSERT servicio_comuna (id, servicio_salud_id, servicio_salud, codigo_comuna, comuna_nombre, perfil_id, perfil_nombre) VALUES (29, 25, N'Aisén', 11101, N'Coihaique', 1, N'Servicio de Salud')
SET IDENTITY_INSERT servicio_comuna OFF;

/* Funcionarios Datos de Pruebas */
INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Exequiel Antonio', 'Guzmán', 'Fontecilla', 2, 1, '13433025-2', '1978-02-01', '2021-08-04', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Maria', 'Soto', 'Jara', 1, 1, '10970239-0', '1974-06-21', '2021-08-11', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Yennifer Edith', 'Rojas', 'Balboa', 1, 1, '16468009-6', '1986-11-13', '2021-08-05', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Javier', 'Carisaya', 'Leyton', 2, 1, '13639725-7', '1979-11-16', '2021-08-05', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Abigail', 'Yavi', 'Gutierrez', 1, 1, '25120537-K', '2015-09-11', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Luis Eduardo', 'Anacona', 'Jelvez', 2, 1, '7083130-9', '1958-02-14', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Jose Antonio', 'Triviño', 'Santibañez', 2, 2, '17511809-8', '1989-07-27', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Carolina Victoria', 'Romero', 'Paez', 1, 7, '13639694-3', '1979-11-07', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Clemencia Marisol', 'Rios', 'Aguilar', 1, 1, '24312189-2', '1974-01-07', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Elaine', 'Catalan', 'Gutierrez', 1, 10, '14561236-5', '1973-09-17', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('Veronica', 'Gonzalez', 'Rodríguez', 1, 11, '13005046-8', '1972-11-21', '2021-08-18', 1);
-- INSERT INTO funcionarios (nombres, apellido_paterno, apellido_materno, sexo_id, nacionalidad_id, run, fecha_nacimiento, create_at, enabled) VALUES('José', 'Cortez', 'Hernandez', 2, 15, '23173879-7', '2009-11-16', '2021-08-18', 1);

/* Contratos - 24 campos */
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(11, 13101, 111310199, 1, 1, 1, 2, 14, 8, 14, 10, 11, 44, 4, '2017-01-04', 2, 1, 2, 4540990, 5480790, 1, 1, '2021-08-20', 2, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(7, 5103, 107600, 1, 1, 2, 1, 7, 2, 4, 6, 7, 44, 8, '2013-08-20', 4, 2, 2, 5200000, 5857990, 0, 0, '2021-08-20', 3, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(10, 13124, 110350, 2, 2, 4, 2, 3, 10, 16, 2, 2, 22, 2, '2019-08-20', 10, 1, 1, 2400000, 2800000, 0, 0, '2021-08-20', 4, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(12, 13120, 112310, 1, 3, 5, 3, 9, 16, 22, 2, 2, 11, 12, '2010-08-20', 7, 1, 4, 1700500, 2205000, 0, 0, '2021-08-20', 5, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(12, 13114, 112304, 2, 1, 2, 3, 15, 16, 22, 2, 2, 33, 15, '2006-08-20', 5, 1, 4, 2300000, 2890000, 0, 1, '2021-08-20', 5, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(11, 13101, 111310199, 1, 1, 1, 2, 14, 8, 14, 10, 11, 22, 9, '2012-06-27', 2, 1, 2, 4540990, 5480790, 1, 1, '2021-08-20', 1, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(11, 13101, 111310199, 1, 1, 1, 2, 14, 8, 14, 10, 11, 11, 9, '2012-06-27', 2, 1, 2, 4540990, 5480790, 1, 1, '2021-08-20', 1, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(11, 13101, 111310199, 1, 1, 1, 2, 14, 8, 14, 10, 11, 4, 9, '2012-06-27', 2, 1, 2, 4540990, 5480790, 1, 1, '2021-08-20', 1, 1, null, null, null, null, null, null, null, null, null, null);
-- INSERT INTO contratos (servicio_salud_id, codigo_comuna, codigo_nuevo_establecimiento, admin_salud_id, ley_id, tipo_contrato_id, categoria_profesion_id, nivel_carrera_id, profesion_id, especialidad_id, cargo_id, asignacion_chofer_id, jornada_laboral, anios_servicio, fecha_ingreso, bienios_id, prevision_id, isapre_id, sueldo_base, total_haberes, validado, revisado, create_at, funcionario_id, enabled, fecha_disabled, fecha_edicion, usuario_creador, usuario_editor, fecha_carga, tipo_ingreso_registro, fecha_validacion, usuario_validador, fecha_revision, usuario_revisor) VALUES(11, 13101, 111310199, 1, 2, 4, 7, 16, 30, 44, 10, 11, 33, 9, '2012-06-27', 2, 1, 2, 4540990, 5480790, 1, 1, '2021-08-20', 1, 1, null, null, null, null, null, null, null, null, null, null);

/* Roles Perfil */
-- INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_ADMIN');
INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_MINSAL');
INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_SERVICIO');
INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_COMUNA');
INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_LA_GRANJA');
INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_LECTURA');
-- INSERT INTO roles_perfiles (role_perfil) VALUES('ROLE_USUARIO');

/* Usuarios */
INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Exequiel Antonio', 'Guzmán', 'Fontecilla', 2, '13433025-2', 'guzman.exe@gmail.com', '981714236', '13433025-2', '$2a$10$sXK.bGjops74Ao.hEaoOxevMvFqZNKi44fYIeHMm.Ws.w.Vfu4Eh2', 1, 0, '2021-05-24 23:47:54', '2021-08-12 11:07:48', '1978-02-01', 0, 13120, 1, 12, null, 1);	-- 1	ROLE_MINSAL
INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Macarena Ignacia', 'Sandoval', 'Sanhueza', 1, '16360051-k', 'macarena.sandoval@minsal.cl', '973766977', '16360051-k', '$2a$10$kY34LDOPm5lTH.dsE9wACexbx8lTuyOGkwj12XMQLQHG/SMfw0ccG', 1, 0, null, '2021-08-15 13:48:34', '1986-10-20', 0, 13101, 1, 11, '2021-09-04', 1);		-- 2	ROLE_MINSAL
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Maria', 'Soto', 'Jara', 1, '10970239-0', 'msoto@hotmail.com', '942342574', '10970239-0', '$2a$10$YW9LMcxWMAIca0SjHrjrB.6Z//fhm3T2EZVPT55y0QmvkACOjM5LW', 1, 0, null, '2021-08-12 11:08:55', '1974-06-21', 0, 13114, 1, 12, '2021-08-27', 1);									-- 3 	ROLE_MINSAL
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Javier', 'Carisaya', 'Leyton', 2, '13639725-7', 'jcarisayal@gmail.com', '999113556', '13639725-7', '$2a$10$sGbOPefz4/7jXmB6.WGEVeotRXyY/XvTELoz/GshU4eh5yb/DD11.', 1, 0, null, '2021-09-06 11:49:58', '1979-11-16', 0, 13202, 1, 14, null, 2);								-- 4	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Yennifer Edith', 'Rojas', 'Balboa', 1, '16468009-6', 'yrojasb@gmail.com', '997788084', '16468009-6', '$2a$10$kOgO2cRk0nI7fsiznS/54OGy7lOMWQ/NnyHCHS8Jbmz0otXw9OanC', 1, 0, null, '2021-09-06 11:54:42', '1986-11-13', 0, 8301, 1, 20, null, 4);								-- 5	ROLE_LA_GRANJA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Carolina Victoria', 'Romero', 'Paez', 1, '13639694-3', 'cromerop@hotmail.com', '948545485', '13639694-3', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);							-- 6	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Wilson', 'Neira', 'Flores', 2, '17013386-2', 'mail1@hotmail.com', '911223344', '17013386-2', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 3);										-- 7	ROLE_COMUNA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Corinne', 'Rodriguez', 'Perez', 1, '9894579-2', 'mail2@hotmail.com', '911223344', '9894579-2', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 4);									-- 8	ROLE_LA_GRANJA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Lucas', 'Cuevas', 'Aguilera', 2, '22076167-3', 'mail3@hotmail.com', '911223344', '22076167-3', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);									-- 9	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Giselle', 'Espinoza', 'Aramay', 1, '19046317-6', 'mail4@gmail.com', '911223344', '19046317-6', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 3);									-- 10	ROLE_COMUNA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Sara', 'Rivera', 'Vargas', 1, '12436683-6', 'mail5@gmail.com', '911223344', '12436683-6', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 4);											-- 11	ROLE_LA_GRANJA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Clemencia Marisol', 'Rios', 'Aguilar', 1, '24312189-2', 'mail6@gmail.com', '911223344', '24312189-2', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);								-- 12	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Marcela', 'Navas', 'Rodriguez', 1, '15422039-9', 'mail7@gmail.com', '911223344', '15422039-9', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 3);									-- 13	ROLE_COMUNA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Maribel', 'Lovera', 'Cruz', 1, '14741018-2', 'mail8@gmail.com', '911223344', '14741018-2', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 4);										-- 14	ROLE_LA_GRANJA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Danitza', 'Gomez', 'Viza', 1, '19010463-K', 'mail9@gmail.com', '911223344', '19010463-K', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 1);											-- 15	ROLE_MINSAL
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Maritza', 'Rivera', 'Vargas', 1, '15000503-5', 'mail10@gmail.com', '911223344', '15000503-5', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);										-- 16	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Maximiliano', 'Luengo', 'Rivera', 2, '24362410-K', 'mail11@gmail.com', '911223344', '24362410-K', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 3);									-- 17	ROLE_COMUNA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Eylin', 'Mallea', 'Fernadez', 1, '22227896-1', 'mail12@gmail.com', '911223344', '22227896-1', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 4);										-- 18	ROLE_LA_GRANJA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Ingrid', 'Tancara', 'Haylla', 1, '20547749-7', 'mail13@gmail.com', '911223344', '20547749-7', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);										-- 19	ROLE_SERVICIO
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Marcos', 'lequepi', 'Chambe', 2, '10604332-9', 'mail14@gmail.com', '911223344', '10604332-9', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 3);										-- 20	ROLE_COMUNA
-- INSERT INTO users (nombre, apellido_paterno, apellido_materno, sexo_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at, fecha_nacimiento, is_online, codigo_comuna, nacionalidad_id, servicio_salud_id, last_visit_date, role_perfil_id) VALUES('Nancy', 'Prieto', 'Cortes', 1, '11813486-9', 'mail15@gmail.com', '911223344', '11813486-9', '$2a$10$vbxkUDa.ITC7dukU/CZeSeBQIqvHQ0g6gWZ2DHWoz.9LNk6VlmMaq', 1, 0, null, '2021-09-15 11:54:42', '1979-11-07', 0, 11101, 1, 25, null, 2);										-- 21	ROLE_SERVICIO

-- INSERT INTO users (username, password, enabled) VALUES('eguzman', '$2a$10$M.wKkNXKUR1QcFrdrihvg.0MwT7Ef1Y21v4JCVOmnKLJHOJK/PEHK', 1);
-- INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$jsIuQDksEw7ZGUyw28uEe.B0TiGrIy0DmzZr12IOdq099tkSIK2AO', 1);

/* Roles */
-- INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USUARIO');
-- INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');
-- INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USUARIO');
INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_MINSAL');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_MINSAL');
-- INSERT INTO authorities (user_id, authority) VALUES(3, 'ROLE_MINSAL');
-- INSERT INTO authorities (user_id, authority) VALUES(4, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(5, 'ROLE_LA_GRANJA');
-- INSERT INTO authorities (user_id, authority) VALUES(6, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(7, 'ROLE_COMUNA');
-- INSERT INTO authorities (user_id, authority) VALUES(8, 'ROLE_LA_GRANJA');
-- INSERT INTO authorities (user_id, authority) VALUES(9, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(10, 'ROLE_COMUNA');
-- INSERT INTO authorities (user_id, authority) VALUES(11, 'ROLE_LA_GRANJA');
-- INSERT INTO authorities (user_id, authority) VALUES(12, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(13, 'ROLE_COMUNA');
-- INSERT INTO authorities (user_id, authority) VALUES(14, 'ROLE_LA_GRANJA');
-- INSERT INTO authorities (user_id, authority) VALUES(15, 'ROLE_MINSAL');
-- INSERT INTO authorities (user_id, authority) VALUES(16, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(17, 'ROLE_COMUNA');
-- INSERT INTO authorities (user_id, authority) VALUES(18, 'ROLE_LA_GRANJA');
-- INSERT INTO authorities (user_id, authority) VALUES(19, 'ROLE_SERVICIO');
-- INSERT INTO authorities (user_id, authority) VALUES(20, 'ROLE_COMUNA');
-- INSERT INTO authorities (user_id, authority) VALUES(21, 'ROLE_SERVICIO');

