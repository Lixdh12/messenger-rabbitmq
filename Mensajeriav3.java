import javax.swing.*;
import javax.swing.border.Border;

import com.rabbitmq.client.Return;

import javax.swing.BorderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.scene.*;
import sun.font.*;
import java.awt.*;
import java.awt.event.*;

public class Mensajeriav3 extends JFrame  implements ActionListener, Runnable{
  private JPanel panelSeleccion, panelChat;
  private JLabel titulo, instruccion;
  private JCheckBox ger,desa1,desa2,adm1,adm2,mar1,mar2, todos;
  private JComboBox <String> listaDeAreas;
  private JTextArea chats;
  private JTextField mensaje;
  private JButton enviar;
  private Thread hilo;
  private boolean mensaje_enviado,inicioHilo;
  private String seleccionDeArea, itemValor, tmp;
  Catcher catcher = new Catcher();


   public Mensajeriav3(){
     seleccionDeArea = "";
     itemValor = "";
     tmp = "";
     hilo=new Thread(this);
     this.IniciaRecibimientoDeMensajes("");
     hilo.start(); // Inicio de hilo
     inicioHilo = false;
     enviar = new JButton("Enviar");
      /**Configuracion del panel de seleccion */
         panelSeleccion = new JPanel();
         /* panelSeleccion.setBackground(Color.lightGray); */
         Border borderPanel = BorderFactory.createLoweredBevelBorder();
         panelSeleccion.setBorder(borderPanel);
         panelSeleccion.setLocation(0, 0);
         panelSeleccion.setBounds(15, 55,270,300);
         panelSeleccion.setLayout(null);
      /**Configuracion del panel de chat */
         panelChat = new JPanel();
         panelChat.setBackground(Color.lightGray);
         panelChat.setLocation(250, 30);
         panelChat.setBounds(250,0,250,400);
         panelChat.setLayout(null);
         
      /** Configurar etiquetas */
         titulo = new JLabel();
         titulo.setFont(new Font("Arial",Font.BOLD,17));
         titulo.setText("Mensajeria empresarial");
         titulo.setBounds(50, 10, 250, 20);
         add(titulo);
         instruccion = new JLabel();
         instruccion.setFont(new Font("Arial",Font.PLAIN,14));
         instruccion.setText("Seleccione el personal");
         instruccion.setBounds(30, 6, 250, 20);
         panelSeleccion.add(instruccion);
      /** Configurar checkboxs */
         todos = new JCheckBox("Todos");
         todos.setSelected(true);
         todos.setBounds(10, 30, 250, 20);
         todos.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  desa1.setEnabled(false);
                  desa2.setEnabled(false);
                  adm1.setEnabled(false);
                  adm2.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  seleccionDeArea = "todos";
                  enviar.setEnabled(true); // boton "Enviar"
               }else{
                  ger.setEnabled(true);
                  desa1.setEnabled(true);
                  desa2.setEnabled(true);
                  adm1.setEnabled(true);
                  adm2.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(todos);
         ger = new JCheckBox("Gerencia");
         ger.setBounds(10, 60, 250, 20);
         ger.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  todos.setEnabled(false);
                  desa1.setEnabled(false);
                  desa2.setEnabled(false);
                  adm1.setEnabled(false);
                  adm2.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "gerencia"; // nomenclatura representa el area GERENCIA
               }else{
                  todos.setEnabled(true);
                  desa1.setEnabled(true);
                  desa2.setEnabled(true);
                  adm1.setEnabled(true);
                  adm2.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(ger);
         desa1 = new JCheckBox("Area de desarrollo(Lider)");
         desa1.setBounds(10, 90, 250, 20);
         desa1.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa2.setEnabled(false);
                  adm1.setEnabled(false);
                  adm2.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "desaLider"; // nomenclatura representa el area DESARROLLO (LIDER)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa2.setEnabled(true);
                  adm1.setEnabled(true);
                  adm2.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(desa1);
         desa2 = new JCheckBox("Area de desarrollo(Empleados)");
         desa2.setBounds(10, 120, 250, 20);
         desa2.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa1.setEnabled(false);
                  adm1.setEnabled(false);
                  adm2.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "desaEmp"; // nomenclatura representa el area DESARROLLO (EMPLEADOS)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa1.setEnabled(true);
                  adm1.setEnabled(true);
                  adm2.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(desa2);
         adm1 = new JCheckBox("Administracion y finanzas(Lider)");
         adm1.setBounds(10, 150, 250, 20);
         adm1.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa2.setEnabled(false);
                  desa1.setEnabled(false);
                  adm2.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "admLider"; // nomenclatura representa el area ADM (LIDER)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa2.setEnabled(true);
                  desa1.setEnabled(true);
                  adm2.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(adm1);         
         adm2 = new JCheckBox("Administracion y finanzas(Empleados)");
         adm2.setBounds(10, 180, 250, 20);
         adm2.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa2.setEnabled(false);
                  desa1.setEnabled(false);
                  adm1.setEnabled(false);
                  mar1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "admEmp"; // nomenclatura representa el area ADMIN (EMPLEADOS)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa2.setEnabled(true);
                  desa1.setEnabled(true);
                  adm1.setEnabled(true);
                  mar1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(adm2);         
         mar1 = new JCheckBox("Marketing y comunicacion(Lider)");
         mar1.setBounds(10, 210, 252, 20);
         mar1.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa2.setEnabled(false);
                  desa1.setEnabled(false);
                  adm2.setEnabled(false);
                  adm1.setEnabled(false);
                  mar2.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "markLider"; // nomenclatura representa el area MARKE (LIDER)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa2.setEnabled(true);
                  desa1.setEnabled(true);
                  adm2.setEnabled(true);
                  adm1.setEnabled(true);
                  mar2.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         panelSeleccion.add(mar1);
         mar2 = new JCheckBox("Marketing y comunicacion(Empleados)");
         mar2.setBounds(10, 240, 260, 20);
         mar2.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               if(e.getStateChange()==ItemEvent.SELECTED){
                  ger.setEnabled(false);
                  todos.setEnabled(false);
                  desa2.setEnabled(false);
                  desa1.setEnabled(false);
                  adm2.setEnabled(false);
                  adm1.setEnabled(false);
                  mar1.setEnabled(false);
                  enviar.setEnabled(true); // boton "Enviar"
                  seleccionDeArea = "markEmp"; // nomenclatura representa el area MARKE (EMPLEADO)
               }else{
                  ger.setEnabled(true);
                  todos.setEnabled(true);
                  desa2.setEnabled(true);
                  desa1.setEnabled(true);
                  adm2.setEnabled(true);
                  adm1.setEnabled(true);
                  mar1.setEnabled(true);
                  enviar.setEnabled(false); // boton "Enviar"
                  seleccionDeArea = "";
               }
            }
         });
         System.out.println("***"+seleccionDeArea);
         panelSeleccion.add(mar2);
         /** Panel derecha */
         String[] lista = { "Selecciona tu area", "Gerencia", "Area de desarrollo(Lider)", "Area de desarrollo(Empleados)", "Administracion y finanzas(Lider)",
             "Administracion y finanzas(Empleados)", "Marketing y comunicacion(Lider)", "Marketing y comunicacion(Empleados)" };
         listaDeAreas = new JComboBox<>(lista);
         listaDeAreas.setBounds(320, 50, 220, 20);
         listaDeAreas.setSelectedIndex(0);
         listaDeAreas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                int indexA = listaDeAreas.getSelectedIndex();
                switch(indexA){
                    case 0: itemValor = ""; break;
                    case 1: itemValor = "gerencia"; break;
                    case 2: itemValor = "desaLider"; break;
                    case 3: itemValor = "desaEmp"; break;
                    case 4: itemValor = "admLider"; break;
                    case 5: itemValor = "admEmp"; break;
                    case 6: itemValor = "markLider"; break;
                    case 7: itemValor = "markEmp"; break;
                }
                chats.setText("");
                if(itemValor == ""){
                  inicioHilo = false;
                  System.out.println("estoy apagado :C");
                }else{
                  IniciaRecibimientoDeMensajes(itemValor);
                  inicioHilo = true;
                  System.out.println("estoy encendido >:D con el area: "+ itemValor);
                 }                
                 tmp = itemValor;

			}
        });
        panelChat.add(listaDeAreas);

         /** Configurar textArea */
         chats = new JTextArea();
         chats.setBounds(320,85, 220, 200);
         chats.setEditable(false);
         chats.setLineWrap(true);
         Border borderA = BorderFactory.createMatteBorder(1,2,1,1,Color.black);
         chats.setBorder(borderA);
         panelChat.add(chats);
         /** Configurar campo de texto */
         mensaje = new JTextField();
         mensaje.setBounds(320,290,150,25);
         Border borderF = BorderFactory.createLineBorder(Color.black,1);
         mensaje.setBorder(borderF);
         panelChat.add(mensaje);
         /** Configurar botón */
         enviar.setEnabled(false); // boton "Enviar"
         enviar.setBounds(470,290,70,25);
         enviar.setBorder(borderF);
         enviar.setBackground(Color.LIGHT_GRAY);
         
         panelChat.add(enviar);

         this.add(panelSeleccion);
         this.add(panelChat);
         enviar.addActionListener(this);
         /**Configuracion de la ventana */
         this.setSize(600,450);
         this.setLocation(600, 250);
         this.setVisible(true);
         this.setTitle("Mensajeria empresarial");
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.mensaje_enviado = false;
        }

        private void IniciaRecibimientoDeMensajes(String area){
            String[] areas = {"todos",area};
            try{
                catcher.operacionRecibirMensaje(areas); // Configuracion y esperar mensajes
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
        
        private void enviarMensaje(){
            Process p;
            try{
             String cadena = "python generador.py"+" "+seleccionDeArea+" "+mensaje.getText();
             System.out.println(cadena);
             p = Runtime.getRuntime().exec(cadena);
             BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
             String s = br.readLine(); 
             // cmpcadenas = s;
             System.out.println(s);
             p.waitFor();
             p.destroy();
             chats.append(" << " + s + "\n");
            }catch(Exception e){
               System.out.println(e);
            }        
        }

        private void recibirMensaje(){
            try{
               chats.append(" >> " + catcher.mensaje+ "\n"); // Agregar el mensaje en el JTextArea
            }catch(Exception e){
               System.out.println(e);
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==this.enviar) {
               this.enviarMensaje();
               this.mensaje_enviado = true;
               this.mensaje.setText("");
            }
         }
        public void run(){
            while(true){
                 System.out.println(catcher.mensaje);  
                if(this.mensaje_enviado){
                     catcher.mensaje="";
                     this.mensaje_enviado = false;
                   }else{
                         if(catcher.mensaje != "" && !this.mensaje_enviado){
                             this.recibirMensaje();
                             catcher.mensaje="";
                         }
                     }
               }
               
         }
      
     public static void main(String[] arg){
        Mensajeriav3 mensajeria = new Mensajeriav3();

        }
     }