// Lizbeth Espinoza Rodríguez
// 35161819
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Catcher {
    private static final String nombreExchange = "mensajes";
    private static ConnectionFactory factory;
    private static Connection conexion;
    private static Channel canal;
    private static String nombreQueue;
    public  static String mensaje;

    Catcher() {
        mensaje="";
    }
    // Configurar la conexion con el servidor RabbitMQ
    private static void configurarConexion() {
        factory = new ConnectionFactory();
        factory.setHost("localhost"); //nombre del host
        try{
            factory.setPort(5671); // Puerto para el TLS
            factory.useSslProtocol();
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    private static void configurarCanal() {
        try {
            conexion = factory.newConnection();
            canal = conexion.createChannel();
            canal.exchangeDeclare(nombreExchange, "direct"); // Configurar Exchange
            nombreQueue = canal.queueDeclare().getQueue(); // declarar queue
        } catch (IOException e) {
            System.out.println(e);
        } catch (TimeoutException t) {
            System.out.println(t);
        }
    }

    public static void recibirMensajes() {
        try {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
               String msj = new String(delivery.getBody(), "UTF-8"); // Se recibira el mensaje en UTF-8
               System.out.println(">Recibi mensajes de " + delivery.getEnvelope().getRoutingKey() + "':'" + msj + "'");
               mensaje = msj;
            };
            canal.basicConsume(nombreQueue, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void operacionRecibirMensaje(String[] tipos) throws Exception{
        System.out.println("Catcher directo de Lizbeth Espinoza Rodriguez "+ tipos);
        configurarConexion(); //Configura la conexión con el servidor RabbitMQ
        configurarCanal(); //Configura el canal de conexión
        //String[] tipos = {"gerencia"}; //En caso de que no se haya indicado argumentos por default tomara 'normal'
        /* if(argv.length>0){
            tipos = argv;
        } */
        for (String severity : tipos) {
            canal.queueBind(nombreQueue, nombreExchange, severity); // Se crea enlaces para cada argumento de entrada
        }
        System.out.println("Esperando mensajes");
        //this.mensaje = "";
        recibirMensajes();
    }

   /*  public static void main(String[] argv) throws Exception {
        System.out.println("Catcher directo de Lizbeth Espinoza Rodriguez ");
        configurarConexion();
        configurarCanal();
        String[] tipos = {"gerencia"}; //En caso de que no se haya indicado argumentos por default tomara 'normal'
        /* if(argv.length>0){
            tipos = argv;
        } *
        for (String severity : tipos) {
            canal.queueBind(nombreQueue, nombreExchange, severity); // Se crea enlaces para cada argumento de entrada
        }
        System.out.println("Esperando mensajes");
        recibirMensaje();
    } */
}