package procesaimagen;
//Librerias
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

//Creamos una clase que extienda de JFrame para crear la interfaz grafica
public class ProcesaImagen extends JFrame
{
//Declaracion de Variables
    private Button abrir = new Button("Abrir");
    private Button convertir = new Button("Convertir"); 
    JFileChooser chooser = new JFileChooser();
    JPanel panelBotones = new JPanel();
    JLabel etiqueta = new JLabel();
    BufferedImage bmp = null;
    
//Creamos el contructor de la clase
    public ProcesaImagen()
    {   
//Le damos el nombre a la venta 
        super("Procesamiento de Imagenes");
        //Agregamos los botones, Jpanel, Etiquetas que es con lo que trabajaremos en la ventana
        this.add(panelBotones,BorderLayout.SOUTH);
        this.add(etiqueta);
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(abrir);
        panelBotones.add(convertir);
        setVisible(true);
        setSize(1650,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Asignamos memoria para los 2 metodos LAMBDA
        cargarImagen();
        imagenBinaria();
    } 
//Metodo encargado de cargar la imagen
    public void cargarImagen()
    {
//Se agregó un ActionListener en el botón “abrir” para ejectuarse
        abrir.addActionListener(e ->{
          int respuesta;
          respuesta = chooser.showOpenDialog(null);
          if(respuesta == JFileChooser.APPROVE_OPTION)
          {
              try {
//Se crea una variable de tipo FILE donde  guardamos la ruta del archivo seleccionado
                  File archivo = chooser.getSelectedFile();
                  bmp = ImageIO.read(archivo);              
//la imagen seleccionada 
                  etiqueta.setIcon(new ImageIcon(bmp));
              } catch (IOException ex) {
                  Logger.getLogger(ProcesaImagen.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        });
    }
//este método se encarga de crear la imagen binaria
    public void imagenBinaria()
    {
//Agregamos el actionlistener al botón “convertir”
        convertir.addActionListener(e -> {
        try {
	//Obtenemos la imagen seleccionada
                  File archivo = chooser.getSelectedFile();
//Leemos el archivo
                  bmp = ImageIO.read(archivo);
                  int umbral = 100;
//Recorremos la matriz de la imagen pasando en 2 for los índices de la matriz
                  for(int i=0; i < bmp.getWidth(); i++)
                  {
                      for(int j=0; j < bmp.getHeight(); j++)
                      { 
                          int pixel = bmp.getRGB(i, j);
                          Color color = new Color(pixel);
                          int rojo = color.getRed();
                          int azul = color.getBlue();
                          int verde = color.getGreen();
                          int gris = (rojo + azul + verde)/3;

//Se obtienen los colore RGB y se cambian a una escala grises si el color es menor al umbral se vuelve 0 o si es mayo se vuelve 1
                          if(gris > umbral)
                          {
                              gris = 255;
                          }
                          Color pixelGris = new Color(gris,gris,gris);
                          bmp.setRGB(i, j, pixelGris.getRGB());
                      }
                  }
                  etiqueta.setIcon(new ImageIcon(bmp));
              } catch (IOException ex) {
                  Logger.getLogger(ProcesaImagen.class.getName()).log(Level.SEVERE, null, ex);
              }
        });
    }
    
    public static void main(String[] args) 
    {
        ProcesaImagen ventana = new ProcesaImagen();
    }
}
