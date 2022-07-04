# READ ME:
Guía para comprender y ejecutar mi proyecto.   ¡¡¡Me han salido todos los TESTS!!!
  
### RELACIONES Y HERENCIAS:  
Para comprender las relaciones con las clases tenemos que observar la imagen adjunta como un conjunto de herencias y relaciones. Las **herencias** vienen determinadas por las líneas continuas, las cuales emergen de la clase “account”. Así, “student_checking”, “saving”, “checking”, y “credit_card” comparten atributos con esta. 
Las líneas discontinues significan **relaciones**. La clase “account” se relaciona con “account_holder” ya que cada cuenta tiene su titular. A su vez, "account_holder" es un tipo de “User”, de ahí que herede de este. Sin embargo, no es el único user existente ya que también tenemos el rol de “admin”. 

Por último se encuentra la clase “third-party” que no es un tipo de usuario ya que no tiene contraseña. Esta clase no tiene adjunta ningún tipo de petición de seguridad para acceder a sus peticiones. Solo tendrá que ofrecer la información necesaria.

**Nota:** Credit_card no incluía fecha de creación en el enunciado pero se la he indicado ya que es otro tipo de cuenta con fecha de creación y agiliza las operaciones. 

----------------------------------------------------------------------------------------------------------------------------------------
### ATRIBUTOS  
La mayoría de los atributos de las cuentas tienen valores entre un mínimo y un máximo. Por ejemplo, el mínimo balance para la cuenta Saving debe estar comprendido entre 100 y 1000. Para ello recurrimos a spring-boot-starter-validation y así aseguramos que el usuario introduzca valores permitidos. En el caso en que no se requiera ninguno de esos parámetros, se les asigna un número concreto en un constructor (que se podrá cambiar igualmente con el setter). De igual manera ocurre con el secondaryOwner, agregamos un segundo constructor sin esta atributo en cada cuenta de manera que sea opcional introducirlo al instanciar una cuenta. 

Penaltyfee y balance son de tipo Money, cuya moneda es el euro.

Las cuentas Saving y CreditCard tienen intereses. Esto se lleva a cabo con un método en el modelo de cada cuenta. Ahí se crea el atributo “last update date” el cual indica la fecha (tipo DATE) de la última actualización de intereses. La primera vez que se añaden intereses a una cuenta, esa fecha cambia a ese día (el día de hoy). Así, cada vez que se añadan intereses, la fecha se va actualizando al día de "hoy". Por último, ese balance modificado que se obtiene al pasar por el método se introduce en el getter de manera que cada vez que accedamos al balance de la cuenta lo tendremos actualizado con sus intereses correspondientes. 

Otra peculiaridad de la cuenta de ahorro es que cuando el balance cae por debajo del balance mínimo establecido, a este valor se le debe descontar la penalización penaltyfee. Se realiza con una condición if y se actualiza el get cada vez que esto ocurre.

------------------------------------------------------------------------------------------------------------------------------------------

Vayamos a la chicha del asunto :D  
### ¡¡PETICIONES HTTP!!
Como ya sabemos, existen 4 tipos diferentes de cuentas. Con todas ellas se pueden realizar casi las mismas operaciones únicamente modificando el endpoint de la url de cada petición. A continuación, voy a explicar el caso del tipo de cuenta “Checking”.   
**Nota:** Para el resto solo hay que cambiar “/checking/…” por el tipo de cuenta que se quiera.

Otro dato importante es que existen dos tipos de usuarios en el programa. Cada uno puede realizar peticiones diferentes.   
  Estos son:
  
	 **ACCOUNT HOLDERS** 
   
    Son los titulares de la cuenta. Los cuales pueden acceder solo y únicamente a sus cuentas. Es decir, a los registros cuyo id coincide con los suyos. Esto se realiza a través de una petición tipo GET.

"/checking/{secretKey}"

- Se introduce este endpoint en el postman y el programa busca la cuenta Checking cuyo secretKey coindice con el parámetro introducido, validando que exista en la base de datos. Así, te devuelve solo el balance, ya que es lo que se quiere pedir. 

La búsqueda de balance a través del secretKey se lleva a cabo con una @Query en el respotirorio CheckingRepository.

- También pueden ingresar dinero a otras cuentas. Esto se realza con una petición del verbo patch  "/checking/{id}/{primaryOwner}"
Donde por parámetros se debe introducir el id y l nombre del titular de la cuenta a la que se transfiere el dinero y el dinero que se desea ingresar en el body.  

Lo que ocurre al realizar dicha operación es que al balance de la cuenta cuyo id sea el introducido se le restará el dinero.  Por consiguiente, a la cuenta cuyo secreteKey sea el introducido se le restará la misma cantidad. Así, se transfiere dinero de una cuenta a otra. Estas operaciones se encuentran en la capa de servicio.


**Dato:** Todas las cuentas pueden realizar transferencias unas a otras sin importar el tipo. Por ejemplo, una checking puede hacer una transferencia a studentChecking para ingresar dinero de padre/madre a hijo.


**IMPORTANTE:** Aunque el enunciado no lo pidiera, también se necesita introducir por parámetro el secretKey de la cuenta que está realizando la transferencia ya que así es como se hace en la realidad y me parecía más completo. 


- El accountHolder también puede eliminar su cuenta proporcionando su id en el endpoint "/checking/{id}"  
   También esta:
  
	  **ADMINS** 
      
    
-	Son los únicos que pueden ver todas las cuentas que existen en la base de datos. (Es obvio que un usuario accountholder no puede acceder a esa información privada)

Esto se lleva a cabo con una petición del verbo GET con endpoint “/checkings” donde no hace falta especificar id concreto, desea ver todoas las existentes. 

-	Los administradores son los responsables de crear nuevas cuentas. Esto se lleva a cabo con una petición post “checking/” introduciendo en el body toda la información que se requiere de una cuenta Checking.  


- También pueden acceder a un balance en concreto y actualizarlo.
Petición del tipo get "/checking/{id}/balance"
 Donde en el endpoint se introduce el id de la cuenta que se desea modificar el balance y en el body el el nuevo valor del balance de tipo money. Este caso es diferente a las transferencias que realiza el accountHolder ya que antes el transferido gana dinero y el transferidor pierde. Aquí simplemente se le cambia el balance a una cuenta, sin que el admin tenga repercusión ya que este no es propietario de ninguna cuenta. 



Por último, existe un tipo de transferencias peculiares. Son las:   

    **THIRD PARTY**  
   
Se trata de una manera de ingresar o retirar dinero de una cuenta de cualquier tipo sin ser un propietario de otra cuenta o un administrador. Es el caso de una compra o una devolución en una tienda (la tienda no tiene cuenta como tal)

Se pueden realizar por tanto dos operaciones: 
-	Retirada de dinero de una cuenta (una compra):
La cuenta que quiere realizarla introduce en el endpoint "thirdParty/decrease/{id}"
. La palabra “decrease” hace referencia a que es una retirada de dinero y el id es de la cuenta que realiza la compra. Además, como parámetro debe introducir el pin secreto de dicha cuenta. En el body introduce el dinero que se retira. Y además, en el header se debe introducir el hashKey del thirdParty.  
Lo que ocurre al realizar dicha operación es que al balance de la cuenta cuyo id sea el introducido se le restará el dinero.  Sin embargo, al thirdParty no se le ingresa nada ya que no tiene cuenta.

-	Ingreso de dinero a una cuenta (una devolución):
Se realiza del mismo modo pero introduciendo la petición “thirdParty/increase/{id} y los mismos parámetros requeridos anteriormente. 

**NOTA:** Todas las cuentas pueden hacer transferencias a cualquier tipo de cuenta. Sin embargo, el ThirdParty solo puede a las cuentas de tipo que tangan secretKEy (CHECKING Y STUDENT CHECKING)


**NOTA 2:**  Cada vez que se ingresa o se retira dinero de una cuenta se comprueba si el dinero introducido en el body de la petición es mayor que 0. Si no es así se lanza una excepción indicando que la cantidad es errónea. 

**NOTA ÚLTIMA, lo prometo:** Los tests de Studentcheckings son muy similares a los de Checking y los de creditCard a los de saving por los que los he omitido. 
