Atomic Labs
Luis Enrique Canales Oliveros

¿QUÉ HICE PARA LLEGAR A LA SOLUCIÓN?

Para lograr llegar a la solución del problema dado por Atomic Labs, se utilizaron diferentes
estrategias, que se explicarán a continuación:

1) Programación Orientada a Objetos: usar este tipo de programación dio mayor facilidad a desarrollar
una solución práctica y factible, tanto en GIU como la lógica que está detrás. Esto es gracias a que
la programación orientada objetos cuenta con la combinación de datos y métodos que se relacionan con este.
Al igual que este paradigma se pueden crear objetos que tienen atributos.Entonces un ejemplo de su uso en
el programa fue crear una linkedList de objetos de la clase zombie y colaboradores, donde a sus 
atributos se le puede modificar a través del tiempo.

Las ventajas que tuvo este paradigma sobre el problema fueron:
	a)Se proporciona un buen marco que facilita la creación de una interfaz gráfica de usuario aplicaciones (GUI).
	b)Facilidad de mantenimiento y modificación de los objetos existentes.
	c)Los componentes se pueden reutilizar.
	d)Una estructura modular clara se puede obtener, la cual no revelará el mecanismo detrás del diseño.

CLASE ZOMBIES

La clase Zombie cuenta con un constructor, donde se inicializan los valores de su posición en X y Y, así como
su posición anterior cuando se mueve por el plano. La intención de usar su posición actual y su posición pasada 
es evitar que el zombie regrese a su casilla anterior. No obstante, la clase zombie como la clase
colaborador proporciona los métodos de movimiento, con la finalidad de trasladarse en el tablero virtual.


CLASE COLABORADORES

La clase Colaborador es parecida a la clase Zombie. Sin embargo, esta clase cuenta con nuevos atributos, tal como:
	a)color, permite ir cambiando de azul - naranja - rojo, dependiendo de la transición que tenga
	durante la simulación al ser tocado por un zombie.
	b)infectado, es uno de los estados que tiene el colaborador, antes de ser zombie.
	c) convertido, es el último paso después de ser infectado por un zombie.

CLASE TABLERO

En esta clase se crean dos linkedList de objetos. La decisión de usar una LinkedList y no usar una ArrayList, se 
muestran a continuación:
	a)Permite inserción o eliminación en tiempo constante, ya sea usando iteraciones.
	b)cuando reutiliza iteradores existentes para insertar y eliminar elementos. Estas operaciones se pueden realizar en O (1)
	c)cuando se agrega o quita del encabezado de la lista, ya que esas operaciones son O (1) , mientras que son O (n) para ArrayList. 
	d)Una arrayLists toma tanta memoria asignada para la capacidad, independientemente de si los elementos se han agregado realmente.

De igual manera, la clase Tablero cuenta con una serie de métodos para poder llevar a cabo las operaciones del programa, ya sea generar los
zombies o los colaboradores y dibujarlos en pantalla, checar cuando un espacio está vacío para que un colaborador pueda avanzar sin chocar con
una pared u otro objeto, analizar cuando un colaborador está infectado, cuando se ha transformado en un zombie, entre otros.

LÓGICA DEL PROGRAMA

Para que un colaborador logre llegar a las casillas de salida, el tablero se divide en 4 secciones. Cada uno de los colaboradores que estén en las
diferentes secciones (excepto la que se encuentra en la parte inferior derecha) buscarán recorrer el tablero con un máximo de tres operaciones.
La primera operación es llegar a la mitad del tablero, para posteriormente al cumplir esta condición, bajar hasta estar entre los rangos de coordenadas "Y"
de las casillas de salida. Finalmente al cumplir las dos condiciones pasadas, los colaboradores recorrerán la última parte del tablero dirigiéndose a su
objetivo.

En el trayecto se encuentran obstáculos para cada uno de los colaboradores, como por ejemplo zombies, paredes o hasta sus propios colegas. Es por esta razón, 
que se implementó un método para checar si existe un objeto (colaborador, pared, zombie) delante de él. En caso de encontrar uno, el colaborador checara las 
casillas que tiene a su alrededor buscando una que está vacía.


CHECAR UN CASILLA VACÍA

La clase Map Generator, es la encargada de generar la matriz por donde los colaboradores y zombies caminan. Al crearse cada casilla se le asigna dentro de la matriz un número.
El número que tenga será lo que valide que tipo de objeto es, como por ejemplo, si una pared tiene el valor de 1 y no puede cruzar dicha casilla.
Así mismo, otra manera que se checa si una casilla está vacía es por comparación de posicionamiento entre los objetos, si es diferente puede avanzar y si es igual, se puede avanzar.


