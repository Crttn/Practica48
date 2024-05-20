import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
        try {
            // Deserializar el XML a objetos Java
            File file = new File("catalogo.xml"); // Se crea un objeto File para el archivo XML
            JAXBContext jaxbContext = JAXBContext.newInstance(Autos.class); // Se inicializa el contexto de JAXB para la clase Autos
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); // Se crea un Unmarshaller para deserializar el XML
            Autos autos = (Autos) jaxbUnmarshaller.unmarshal(file); // Se deserializa el archivo XML en un objeto Autos

            Scanner scanner = new Scanner(System.in);
            int option = 0;

            // Menú principal
            while (option != 3) {
                System.out.println("Menu:");
                System.out.println("1. Mostrar todo el contenido del fichero");
                System.out.println("2. Buscar un auto y modificar sus valores");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        mostrarContenido(autos);
                        break;
                    case 2:
                        buscarYModificarAuto(autos, scanner);
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }

            // Serializar los objetos Java modificados de vuelta a XML
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); // Crear un Marshaller para serializar
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Configurar la salida XML formateada
            jaxbMarshaller.marshal(autos, file); // Serializar el objeto Autos y escribir en el archivo XML

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar todo el contenido del fichero
    private static void mostrarContenido(Autos autos) {
        for (Auto auto : autos.getAutos()) {
            System.out.println("ID: " + auto.getId());
            System.out.println("Marca: " + auto.getMarca());
            System.out.println("Modelo: " + auto.getModelo());
            System.out.println("Año: " + auto.getAño());
            System.out.println("Color: " + auto.getColor());
            System.out.println("Tipo Motor: " + auto.getTipoMotor());
            System.out.println("Cilindrada: " + auto.getCilindrada());
            System.out.println("Potencia: " + auto.getPotencia());
            System.out.println("Transmisión Tipo: " + auto.getTransmision().getTipo());
            System.out.println("Transmisión Velocidades: " + auto.getTransmision().getVelocidades());
            System.out.println();
        }
    }

    // Método para buscar y modificar un auto
    private static void buscarYModificarAuto(Autos autos, Scanner scanner) {
        System.out.print("Introduce el ID del auto a buscar: ");
        String id = scanner.nextLine();
        Auto auto = buscarAutoPorId(autos.getAutos(), id); // Buscar el auto por ID en la lista de autos

        if (auto != null) { // Si se encuentra el auto
            System.out.println("Auto encontrado: " + auto.getModelo());

            System.out.print("Introduce el nuevo color: ");
            String nuevoColor = scanner.nextLine();
            auto.setColor(nuevoColor);

            System.out.print("Introduce el nuevo tipo de motor: ");
            String nuevoTipoMotor = scanner.nextLine();
            auto.setTipoMotor(nuevoTipoMotor);

            System.out.print("Introduce la nueva cilindrada: ");
            double nuevaCilindrada = scanner.nextDouble();
            auto.setCilindrada(nuevaCilindrada);

            System.out.print("Introduce la nueva potencia: ");
            int nuevaPotencia = scanner.nextInt();
            auto.setPotencia(nuevaPotencia);

            scanner.nextLine();

            System.out.print("Introduce el nuevo tipo de transmisión: ");
            String nuevoTipoTransmision = scanner.nextLine();
            auto.getTransmision().setTipo(nuevoTipoTransmision);

            System.out.print("Introduce las nuevas velocidades de la transmisión: ");
            int nuevasVelocidades = scanner.nextInt();
            auto.getTransmision().setVelocidades(nuevasVelocidades);

            System.out.println("Datos del auto modificados correctamente.");
        } else {
            System.out.println("Auto no encontrado.");
        }
    }

    // Método para buscar un auto por ID en una lista de autos
    private static Auto buscarAutoPorId(List<Auto> autos, String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return auto;
            }
        }
        return null;
    }
}
