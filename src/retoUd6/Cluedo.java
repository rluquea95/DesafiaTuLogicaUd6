package retoUd6;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cluedo {

	/* He declarado la variables fuera del método main como static, 
	 * para usarlas como variables globales y poder acceder desde
	 * todos los métodos.
	 */
	
	private static Scanner sc=new Scanner(System.in); 
	private static String entrada="";
	private static int respuesta=0;
	private static List <Jugador> datosJugador = new ArrayList<>();
	private static String personajesArray[] = {"Amapola", "Celeste", "Prado", "Mora", "Rubio", "Blanco"};
	private static String armasArray[] = {"Bate", "Pistola", "Candelabro", "Cuchillo", "Cuerda", "Hacha", "Pesa", "Veneno", "Trofeo"};
	private static String habitacionesArray[] = {"Casa de invitados", "Teatro", "Spa", "Observatorio", "Comedor", "Terraza", "Salón", "Cocina", "Vestíbulo"};
	private static File fichero=new File(System.getProperty("user.dir") + File.separator + "datosPartidaCluedo.txt");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		do {
			System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- \n" + 
								"+-+-                       BIENVENIDOS A CLUEDO                          -+-+ \n" +
								"-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- \n");

			System.out.println("Hay 6 sospechosos, diferentes escenas y armas con las que se ha cometido un\n" +
								"crimen, el jugador que acusa correctamente y descubre quien, donde y con qué\n" +
								"arma ocurrió, será el ganador.\n");
			
			System.out.println("************\n" + 
								"*PERSONAJES*\n" +
								"************");
			mostrarDatosArray(personajesArray); //Lista los datos almacenados en el array personaje.
			
			System.out.println("\n*******\n" + 
								"*ARMAS*\n" +
								"*******");
			mostrarDatosArray(armasArray); //Lista los datos almacenados en el array armas.
			
			System.out.println("\n**************\n" + 
								"*HABITACIONES*\n" +
								"**************");
			mostrarDatosArray(habitacionesArray); //Lista los datos almacenados en el array habitaciones.	
			
			/* Se agregarán los campos introducidos por el usuario al array correspondiente
			 * y se repetirá el proceso de agregar más, siempre que la función devuelva true.
			 */
			while(agregarMasOpciones());
			System.out.println("\n-.-.-.-.-.-.Barajando cartas.-.-.-.-.-.-\n");
			while(barajarCartas()==false); //Se repite la función siempre que devuelva false.
			System.out.println("\n**Baraja de cartas realizada con éxito**\n");
			mostrarCartas(); //Si aciertas la clave, muestra la información del jugador que se ha generado.
		}while(volverAJugar()); //Se vuelve a ejecutar el juego mientras la función devuelva true.
		guardarEnFichero(); //Guarda la información en el fichero datosPartidaCluedo.txt
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- \n" + 
							"+-+-+-+-                       FIN DEL JUEGO                          +-+-+-+- \n" +
							"-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- \n");
		sc.close();
	}

	public static boolean agregarMasOpciones() {
		/* Repite el código mientras el usuario introduzca valores distintos de 'si' o 'no',
		 * se controlan las posibles excepciones que puedan hacer que el programa se detenga
		 */
		do {
			System.out.println("\n¿Deseas agregar más opciones? (Sí/No)");
			try {
				entrada=sc.next();
				if(entrada.equalsIgnoreCase("si")==false && entrada.equalsIgnoreCase("sí")==false && entrada.equalsIgnoreCase("no")==false) {
					System.out.println("Sólo puedes introducir 'si' o 'no'");
				}
			}
			catch(Exception ex) {
				System.out.println("Ha sucedido un error.");
			}
		}while(entrada.equalsIgnoreCase("si")==false && entrada.equalsIgnoreCase("sí")==false && entrada.equalsIgnoreCase("no")==false);
		
		if(entrada.equalsIgnoreCase("si") || entrada.equalsIgnoreCase("sí")) {
			do {
				System.out.println("\nOpciones:\n" + 
									"1. Personaje\n" + 
									"2. Arma\n" +
									"3. Habitación\n" +
									"4. Salir");
				System.out.println("Introduce el número que corresponde a la opción a la que quieres agregar más contenido:");
				/* Repite el código mientras el usuario introduzca valores fuera del rango 1-4,
				 * se controlan las posibles excepciones que puedan hacer que el programa se detenga
				 */
				try {
					entrada=sc.next();
					respuesta=Integer.parseInt(entrada);
					if(respuesta<1 || respuesta>4) {
						System.out.println("Sólo puedes introducir los números que corresponden a cada opción (1-4).");
					}
				}
				catch(Exception ex) {
					System.out.println("Sólo puedes introducir números enteros positivos.");
				}
			}while(respuesta<1 || respuesta>4);
			
			/* Según el valor de respuesta (1-4) se ejecuta el caso correspondiente agregando los 
			 * campos que el usuario indique a cada array.
			 */	
			switch(respuesta) {
			case 1:
				do {
					System.out.println("\n¿Cuántos personajes quieres agregar?");
					try {
						entrada=sc.next();
						respuesta=Integer.parseInt(entrada);
					}
					catch (Exception ex) {
						System.out.println("Sólo puedes introducir números enteros positivos.");
						respuesta=0;
					}
				}while(respuesta<1);
				personajesArray=actualizarArray(personajesArray,respuesta); //Actualiza la longitud del array
				agregaOpcionesArray(respuesta,personajesArray); //Agrega los nuevos campos
				System.out.println("\nLista de personajes actualizada:" + Arrays.toString(personajesArray)); //Se muestra el array actualizado
				return true;
				
			case 2:
				do {
					System.out.println("\n¿Cuántas armas quieres agregar?");
					try {
						entrada=sc.next();
						respuesta=Integer.parseInt(entrada);
					}
					catch (Exception ex) {
						System.out.println("Sólo puedes introducir números enteros positivos.");
						respuesta=0;
					}
				}while(respuesta<1);
				armasArray=actualizarArray(armasArray,respuesta);
				agregaOpcionesArray(respuesta,armasArray);
				System.out.println("\nLista de armas actualizada:" + Arrays.toString(armasArray));
				return true;
				
			case 3:
				do {
					System.out.println("\n¿Cuántas habitaciones quieres agregar?");
					try {
						entrada=sc.next();
						respuesta=Integer.parseInt(entrada);
					}
					catch (Exception ex) {
						System.out.println("Sólo puedes introducir números enteros positivos.");
						respuesta=0;
					}
				}while(respuesta<1);
				habitacionesArray=actualizarArray(habitacionesArray,respuesta);
				agregaOpcionesArray(respuesta,habitacionesArray);
				System.out.println("\nLista de personajes actualizada:" + Arrays.toString(habitacionesArray));
				return true;
				
			default:
				System.out.println("\nGenial, sigamos!");
				return false;
			}
		}
		else{
			System.out.println("\nGenial, sigamos!");
			return false;
		}	
	}
	
	public static boolean barajarCartas() {
		/* Se crean tres variables, en las cuales, cada una de ellas almacenará un nº aleatorio.
		 * Con esas variables asignamos la posición del array para poder crear una instancia de
		 * la clase Jugador.
		 * Se controlan las posibles excepciones.
		 */	
		int numPersonaje=(int)(Math.random() * (personajesArray.length));
		int numArmas=(int)(Math.random() * (armasArray.length));
		int numHabitaciones=(int)(Math.random() * (habitacionesArray.length));
		
		System.out.println("Carta asignada para escoger personaje: " + numPersonaje);
		System.out.println("Carta asignada para escoger arma: " + numArmas);
		System.out.println("Carta asignada para escoger habitación: " + numHabitaciones);
		
		try {
			datosJugador.add(new Jugador(personajesArray[numPersonaje], armasArray[numArmas], habitacionesArray[numHabitaciones]));
			return true;
		}
		catch(Exception ex) {
			System.out.println("Ha sucedido un error.");
			return false;
		}
	}
	
	public static void mostrarCartas() {
		/* Repite el código mientras el usuario no introduzca 3 cifras.
		 * Si el usuario acierta la clave, se muestra la instancia de Jugador generada.
		 */
		System.out.println("Si introduces la clave correcta, te puedo mostrar quién ha sido el culpable,\n" +
							"cómo y dónde...(La combinación ganadora contiene 3 cifras): ");
		do {
			try {
				entrada=sc.next();
				respuesta=Integer.parseInt(entrada);
				if(entrada.length()<3) {
					System.out.println("\nDebes introducir una combinación de tres cifras (000-999)");
				}
			}
			catch (Exception ex) {
				System.out.println("\nSólo puedes introducir números enteros positivos: ");
				respuesta=0;
			}
		}while(entrada.length()<3);
		
		if(respuesta==456) {
			System.out.println("****¡Has acertado la clave!****");
			System.out.println("\n" + datosJugador.get(datosJugador.size()-1));
		}
		else {
			System.out.println("Clave incorrecta.");
		}
	}
	
	public static boolean volverAJugar() {
		/* Repite el código mientras el usuario introduzca valores distintos de 'si' o 'no',
		 * Devuelve true si se introduce 'si' o devuelve falso si el usuario introduce 'no'.
		 */
		System.out.println("\n¿Quieres volver a crear una nueva combinación para el juego?(Sí/No)");
		do {
			try {
				entrada=sc.next();
				if(entrada.equalsIgnoreCase("si")==false && entrada.equalsIgnoreCase("sí")==false && entrada.equalsIgnoreCase("no")==false) {
					System.out.println("Sólo puedes introducir 'Si' o 'No'");
				}
			}
			catch(Exception ex) {
				System.out.println("Ha sucedido un error.");
			}
		}while(entrada.equalsIgnoreCase("si")==false && entrada.equalsIgnoreCase("sí")==false && entrada.equalsIgnoreCase("no")==false);
		if(entrada.equalsIgnoreCase("si") || entrada.equalsIgnoreCase("sí")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void guardarEnFichero() {
		//Almacena en el fichero la fecha y la combinación de jugador generada en la partida.
		try {
			BufferedWriter escribir= new BufferedWriter(new FileWriter(fichero, true));
			escribir.write("Fecha de la partida: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n"); //Obtenemos la fecha formateada.
			for(Jugador jugador:datosJugador){
				escribir.write(jugador.toString());
				escribir.newLine();
			}
			escribir.newLine();
			escribir.close();
		}
		catch(Exception ex) {
			System.out.println("Error a la hora de guardar los datos en el fichero.");
		}
		System.out.println("\n-.-.-.-.-.-.Guardando datos en fichero.-.-.-.-.-.-");
		System.out.println("\n**Datos guardados con éxito**\n");
	}

	public static void agregaOpcionesArray(int respuesta, String[] antiguoArray) {
		//Agrega al array correspondiente el campo que el usuario introduce.
		try{
			sc.nextLine();
			for(int i=(antiguoArray.length-respuesta); i<antiguoArray.length; i++) {
				System.out.println("Introduce el nombre a agregar:");
				entrada=sc.nextLine();
				antiguoArray[i]=entrada;
			}
		}
		catch(Exception ex) {
			System.out.println("Ha surgido un error.");
		}
	}

	public static String[] actualizarArray(String[] antiguoArray, int respuesta) {
		String[] nuevoArray=new String[antiguoArray.length+respuesta];
		System.arraycopy(antiguoArray, 0, nuevoArray, 0, antiguoArray.length);
		return nuevoArray;
	}

	public static void mostrarDatosArray(String[] Array) {
		for(int i=0; i<Array.length; i++) {
			System.out.println("- " + Array[i]);
		}
	}
}
