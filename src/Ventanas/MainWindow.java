package Ventanas;

import Grafico.Clase;
import Structures_Interface.ClassList;
import Structures_Logic.Graph;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.commons.io.FileUtils;

/**
 * Ventana principal de la aplicacion.
 * @author Daniel Camacho
 */
public class MainWindow extends JFrame
{
    private final String title;
    private final Image icon;
    private final Color backColor;
    private final Color borderColor;
    private final Image returt;
    private final JFileChooser fileChooser;
    private final JLayeredPane mainPanel;
    private final JPanel statusBar;
    private final JLabel fileName;
    private final JLabel Status;
    private final JLabel Completeness;
    private final JButton Complete;
    private final JButton classTab;
    private final JButton jarTab;
    private final JButton allTab;
    private final JButton missingTab;
    private File lastFile;
    private final Gestor gestor;
    private DefaultListModel classList;
    private DefaultListModel jarList;
    private DefaultListModel allFileList;
    private DefaultListModel missingList;
    private JList mainList;
    private JScrollPane scrollPane;
    private Graph grafo;
    private Graph Subgrafo;
    private JButton Generate;
    private ClassList lista;
    private JButton Stats;

    public MainWindow(String title,Image icono,Gestor gestor)
    {
        this.title=title;
        this.icon=icono;
        this.backColor= new Color(31,31,31);
        this.borderColor= new Color(57,61,63);
        this.returt=Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Resources/Icons/beet.png"));
        this.gestor= gestor;
        this.fileChooser= new JFileChooser();
        FileNameExtensionFilter JARfilter = new FileNameExtensionFilter("Jar Files","jar");
        FileNameExtensionFilter JAVAfilter = new FileNameExtensionFilter("Java Files","java");
        fileChooser.setFileFilter(JARfilter);
        fileChooser.addChoosableFileFilter(JAVAfilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        statusBar= new JPanel();
        fileName= new JLabel();
        Status= new JLabel();
        classTab= new JButton();
        jarTab= new JButton();
        allTab= new JButton();
        missingTab= new JButton();
        Completeness= new JLabel();
        this.mainList= new JList();
        this.scrollPane = new JScrollPane(mainList);
        this.mainList.setBackground(backColor);
        this.classList= new DefaultListModel();
        this.jarList= new DefaultListModel();
        this.allFileList= new DefaultListModel();
        this.missingList= new DefaultListModel();
        this.Complete= new JButton("Complete using Maven™");
        this.mainPanel= new JLayeredPane();
        this.Generate= new JButton("Generate!");
        this.Stats= new JButton("JAR Statistics");
        this.Subgrafo=new Graph();
        try {
            FileUtils.cleanDirectory(new File("src/Resources/JAR/"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Init();   
    }

    /**
     * Metodo encargado de inicializar los componentes de la ventana.
     */
    public void Init()
    {
        //Caracteristicas de la ventana principal.
        this.setTitle(this.title);
        this.setSize(1300,700);
        this.setIconImage(icon);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(backColor);
        this.setLayout(null);
        
        
        mainPanel.setBackground(backColor);
        mainPanel.setBounds(300,53,1000,646);
        mainPanel.setOpaque(true);
        mainPanel.setLayout(null);
        mainList.setLayoutOrientation(JList.VERTICAL);
        this.scrollPane.setBounds(0,0,1000,646);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setBorder(null);
        mainPanel.add(scrollPane,1,0);
        this.add(mainPanel);
        
        
        JPanel Bar= new JPanel();
        Bar.setSize(new Dimension(1300,25));
        Bar.setBackground(borderColor);
        Bar.setLocation(0,0);
        Bar.setLayout(null);
        this.add(Bar);
        
        JButton exit= new JButton();
        exit.setBackground(Color.RED);
        exit.setForeground(Color.WHITE);
        exit.setBorder(null);
        exit.setText("X");
        exit.setBounds(1270,0,30,25);
        exit.setFocusPainted(false);
        exit.addActionListener(e->{
                System.exit(0);
            
        });
        Bar.add(exit);
        
        JButton minimize= new JButton();
        minimize.setBackground(Color.GRAY);
        minimize.setForeground(Color.BLACK);
        minimize.setBorder(null);
        minimize.setText("---");
        minimize.setBounds(1240,0,30,25);
        minimize.setFocusPainted(false);
        minimize.addActionListener(e -> {
            setState(Frame.ICONIFIED);
        });
        Bar.add(minimize);
        
        JPanel Panel1= new JPanel();
        Panel1.setSize(new Dimension(300,700));
        Panel1.setBackground(Color.DARK_GRAY);
        Panel1.setLayout(null);
        this.add(Panel1,BorderLayout.WEST);
        
        JButton Add= new JButton("Add JAR file");
        Add.setBackground(new Color(0,184,0));
        Add.setBorder(BorderFactory.createMatteBorder(4,4,4,0,backColor));
        Add.setFont(Add.getFont().deriveFont(Font.BOLD,28));
        Add.setForeground(Color.BLACK);
        Add.setBounds(0,100,300,65);
        Add.setFocusPainted(false);
        Add.addActionListener(e->{
        
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
            {
                lastFile = fileChooser.getSelectedFile();
                File []array= lastFile.listFiles();
                this.grafo= new Graph();
                try 
                {
                    grafo.init(lastFile.toString(),lastFile.getName());
                    lista= grafo.getListClass().ConvertToClassList();
                    gestor.generateDisplay();
                } catch (IOException ex) 
                {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassFormatException ex) 
                {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) 
                {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.refresh();
                
                System.out.println(lastFile.getName());
                System.out.println(lastFile);
               Generate.setBackground(new Color(235,60,1));
               Generate.setEnabled(true);
               Stats.setEnabled(true);
               Stats.setBackground(new Color(0,184,0));

            }
        });
        Panel1.add(Add);
        
        
        Stats.setBackground(Color.DARK_GRAY);
        Stats.setFont(Stats.getFont().deriveFont(Font.BOLD,28));
        Stats.setForeground(Color.BLACK);
        Stats.setBounds(0,165+65,300,65);
        Stats.setBorder(BorderFactory.createMatteBorder(4,4,4,0,backColor));
        Stats.setFocusPainted(false);
        Stats.setEnabled(false);
        Stats.addActionListener(e->{
            dispose();
            gestor.showStatics(lista,true);
        });
        Panel1.add(Stats);
        
        
        Generate.setBackground(Color.DARK_GRAY);
        Generate.setFont(Generate.getFont().deriveFont(Font.BOLD,28));
        Generate.setForeground(Color.BLACK);
        Generate.setBounds(0,360,300,65);
        Generate.setBorder(BorderFactory.createMatteBorder(4,4,4,0,backColor));
        Generate.setFocusPainted(false);
        Generate.setEnabled(false);
        Generate.addActionListener(e->{
                Generate.setBackground(new Color(235,60,1));
                Generate.setForeground(Color.BLACK);
                Generate.setText("Generating...");
                Generate.setEnabled(false);
                
                lista.printDepsCoords();
                assignCoords(lista);
                lista.printDepsCoords();
                gestor.Generate(lista);
                
                
            
            
        });
        Panel1.add(Generate);
        
        
        Complete.setBackground(Color.DARK_GRAY);
        Complete.setFont(Complete.getFont().deriveFont(Font.BOLD,20));
        Complete.setForeground(Color.BLACK);
        Complete.setBounds(0,490,300,65);
        Complete.setBorder(BorderFactory.createMatteBorder(4,4,4,0,backColor));
        Complete.setFocusPainted(false);
        Complete.setEnabled(false);
        Complete.addActionListener(e->{
                
                
            
        });
        Panel1.add(Complete);
        
        JLabel returtL= new JLabel();
        returtL.setIcon(new ImageIcon(this.returt));
        returtL.setBounds(10,610,100,100);
        Panel1.add(returtL);
        
        JLabel Copyright= new JLabel("® 2018 Powered by Returt Technologies");
        Copyright.setFont(Copyright.getFont().deriveFont(Font.ITALIC,12));
        Copyright.setForeground(Color.BLACK);
        Copyright.setBounds(60,620,1000,100);
        Panel1.add(Copyright);
        
        
        statusBar.setBounds(300,25,1000,28);
        statusBar.setBackground(new Color(0,184,0));
        this.add(statusBar);
        
        
        fileName.setText("NO FILE SELECTED");
        fileName.setBounds(0,0,800,28);
        fileName.setFont(fileName.getFont().deriveFont(Font.BOLD,15));
        statusBar.add(fileName);
        
        Status.setFont(Status.getFont().deriveFont(Font.BOLD,15));
       
        Completeness.setFont(Completeness.getFont().deriveFont(Font.BOLD,15));
        
        classTab.addActionListener(e->{
                classTab.setBackground(new Color(235,60,1));
                classTab.setForeground(Color.BLACK);
                classTab.setEnabled(false);
                
                jarTab.setEnabled(true);
                jarTab.setBackground(new Color(0,184,0));
                
                allTab.setEnabled(true);
                allTab.setBackground(new Color(0,184,0));
                
                missingTab.setEnabled(true);
                missingTab.setBackground(new Color(0,184,0));
                
                fileName.setText(lastFile.getName()+"/Classes");
                mainList.setModel(classList);
        });
        
        jarTab.addActionListener(e->{
                jarTab.setBackground(new Color(235,60,1));
                jarTab.setForeground(Color.BLACK);
                jarTab.setEnabled(false);
                
                classTab.setEnabled(true);
                classTab.setBackground(new Color(0,184,0));
                
                allTab.setEnabled(true);
                allTab.setBackground(new Color(0,184,0));
                
                missingTab.setEnabled(true);
                missingTab.setBackground(new Color(0,184,0));
                
                fileName.setText(lastFile.getName()+"/Jars");
                mainList.setModel(jarList);
        });
        allTab.addActionListener(e->{
                allTab.setBackground(new Color(235,60,1));
                allTab.setForeground(Color.BLACK);
                allTab.setEnabled(false);
                
                jarTab.setEnabled(true);
                jarTab.setBackground(new Color(0,184,0));
                
                classTab.setEnabled(true);
                classTab.setBackground(new Color(0,184,0));
                
                missingTab.setEnabled(true);
                missingTab.setBackground(new Color(0,184,0));
                
                fileName.setText(lastFile.getName()+"/All Files");
                mainList.setModel(allFileList);
            
        });
        
        missingTab.addActionListener(e->{
                missingTab.setBackground(new Color(235,60,1));
                missingTab.setForeground(Color.BLACK);
                missingTab.setEnabled(false);
                
                jarTab.setEnabled(true);
                jarTab.setBackground(new Color(0,184,0));
                
                classTab.setEnabled(true);
                classTab.setBackground(new Color(0,184,0));
                
                allTab.setEnabled(true);
                allTab.setBackground(new Color(0,184,0));
                
                fileName.setText(lastFile.getName()+"/Missing dependencies");
                mainList.setModel(missingList);
                
            
        });  
    }
    
    public void goingBack()
    {
        this.Generate.setEnabled(true);
        this.Generate.setText("Generate!");
    }

    /**
     * Metodo para actualizar la pantalla.
     */
    public void refresh()
    {
        clean();
        statusBar.setLayout(null);
        statusBar.add(Status);
        statusBar.add(Completeness);
        
        fileName.setText(lastFile.getName()+"/Classes");
        fileName.setBounds(0,0,800,28);
        
        Completeness.setBounds(880,0,147,28);
        Completeness.setText("UNKNOWN");
        
        Status.setBounds(800,0,53,28);
        Status.setText("Status: ");
        
        this.mainPanel.add(classTab,2,0);
        classTab.setBounds(755,620,85,26);
        classTab.setBackground(new Color(235,60,1));
        classTab.setBorder(BorderFactory.createMatteBorder(2,2,2,2,borderColor));
        classTab.setText("Classes");
        classTab.setFocusPainted(false);
        classTab.setForeground(Color.BLACK);
        
        this.mainPanel.add(jarTab,2,0);
        jarTab.setBounds(835,620,85,26);
        jarTab.setBackground(new Color(0,184,0));
        jarTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        jarTab.setText("Jars");
        jarTab.setFocusPainted(false);
        jarTab.setForeground(Color.BLACK);
        jarTab.setEnabled(true);
       
        this.mainPanel.add(allTab,2,0);
        allTab.setBounds(915,620,85,26);
        allTab.setBackground(new Color(0,184,0));
        allTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        allTab.setText("All Files");
        allTab.setFocusPainted(false);
        allTab.setForeground(Color.BLACK);
        allTab.setEnabled(true);
        
        this.mainPanel.add(missingTab,2,0);
        missingTab.setBounds(625,620,135,26);
        missingTab.setBackground(new Color(0,184,0));
        missingTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        missingTab.setText("Missing Dependencies");
        missingTab.setFocusPainted(false);
        
        missingTab.setForeground(Color.BLACK);
        
        mainList.setBackground(backColor);
        mainList.setFont(mainList.getFont().deriveFont(Font.PLAIN,20));
        mainList.setForeground(Color.WHITE);
        mainList.setBounds(0,0,1000,646);
        
        mainList.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent evt) 
            {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) 
                {
                    int index = list.locationToIndex(evt.getPoint());
                    String s= list.getModel().getElementAt(index).toString();
                    if(s.endsWith(".jar"))
                    {
                        try {
                            Subgrafo=grafo.makeSubGraph(s);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassFormatException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lastFile=Subgrafo.getLastFile();
                        setSubgraph();
                        
                    }
                } 
            }
        });
        
        classList.addElement(" -----------------------------------------------------------------Classes----------------------------------------------------------------");
        setList(grafo.getALLclases(),classList,"classes");
        
        jarList.addElement(" -----------------------------------------------------------------Jars--------------------------------------------------------------------");
        setList(grafo.getAllJars(),jarList,"jar");
        
        allFileList.addElement(" ----------------------------------------------------------------All Files-----------------------------------------------------------------");
        allFileList.addElement(" >>>CLASSES");
        setList(grafo.getALLclases(),allFileList,"all");
        allFileList.addElement(" >>>JARS");
        setList(grafo.getAllJars(),allFileList,"all");

        
        missingList.addElement(" --------------------------------------------------------Missing Dependencies-------------------------------------------------------");
        setList("",missingList,"miss");
        
        mainList.setModel(classList);
        
    }

    /**
     * Metodo para inicializar las listas 
     * @param lista Lista de elementos
     * @param model Lista recorrible
     */
    public void setList(String lista,DefaultListModel model,String id)
    {
        if(lista!="")
        {
            String[] clases= lista.split("@");
            for(int i=0;i<clases.length;i++)
            {
                model.addElement(clases[i]);
            }
        }
        else
        {
            if(id.equals("jar"))
            {
                model.addElement(">>NO JARS FOUND");
            }
            else if(id.equals("classes"))
            {
                 model.addElement(">>NO CLASSES FOUND");
            }
            else if(id.equals("all"))
            {
                 model.addElement(">>NO FILES FOUND");
            }
            else if(id.equals("miss"))
            {
                model.addElement(">>ONLY MY WILL TO LIVE IS MISSING :)");
            }
        }
    }

    /**
     * Metodo para dejar todas las listas en blanco.
     */
    public void clean()
    {
        classList.clear();
        jarList.clear();
        missingList.clear();
        allFileList.clear();
    }

    /**
     * Metodo para asignar las coordenadas en el canvas de cada una de las clases.
     * @param listaP ClassList de clases
     */
    public void assignCoords(ClassList listaP)
    {
        int x=10;
        int y=350;
        int x0=10;
        int y0=50;
        int ind=0;
        int bigstx=0;
        Boolean inicial=true;
        while(listaP.Get(ind)!=null)
        {
            Clase actual=listaP.Get(ind);
            actual.assignCoords(x, y);
            if(inicial)
            {
                bigstx=actual.getXRcoord();
                x=50+bigstx;
                y=y0;
                inicial=!inicial;
                
            }
            else
            {
               if(actual.getY()+30>595)
               {
                   if(x+actual.getXRcoord()>1195)
                   {
                       x=x0;
                       y=y0;
                       actual.assignCoords(x, y);
                      
                   }
                   else
                   {
                        y=y0;
                        x=x+bigstx+50;
                        actual.assignCoords(x, y);
                        bigstx=actual.getXRcoord();
                   }
               }
               if(actual.getXRcoord()>bigstx)
                {
                    bigstx=actual.getXRcoord();
                }
               y+=100;
            }
            ind++;
            
        }
        ind=0;
        while(listaP.Get(ind)!=null)
        {
            Clase actual=listaP.Get(ind);
            int ind2=0;
            while(actual.getDeps().Get(ind2)!=null)
            {
                Clase depActual=actual.getDeps().Get(ind2);
                if(listaP.inList(depActual.getName()))
                {
                    depActual.assignCoords(listaP.getCoordByName(depActual.getName(),"x"),listaP.getCoordByName(depActual.getName(),"y"));
                }
                ind2++;
            }
            ind++;
        }
        ind=0;
        while(listaP.Get(ind)!=null)
        {
            Clase actual=listaP.Get(ind);
            int ind2=0;
            while(actual.getRefs().Get(ind2)!=null)
            {
                Clase refActual=actual.getRefs().Get(ind2);
                if(listaP.inList(refActual.getName()))
                {
                    refActual.assignCoords(listaP.getCoordByName(refActual.getName(),"x"),listaP.getCoordByName(refActual.getName(),"y"));
                }
                ind2++;
            }
            ind++;
        }
    }
    public void setSubgraph()
    {
        this.clean();
        clean();
        statusBar.setLayout(null);
        statusBar.add(Status);
        statusBar.add(Completeness);
        
        fileName.setText(lastFile.getName()+"/Classes");
        fileName.setBounds(0,0,800,28);
        
        Completeness.setBounds(880,0,147,28);
        Completeness.setText("UNKNOWN");
        
        Status.setBounds(800,0,53,28);
        Status.setText("Status: ");
        
        this.mainPanel.add(classTab,2,0);
        classTab.setBounds(755,620,85,26);
        classTab.setBackground(new Color(235,60,1));
        classTab.setBorder(BorderFactory.createMatteBorder(2,2,2,2,borderColor));
        classTab.setText("Classes");
        classTab.setFocusPainted(false);
        classTab.setForeground(Color.BLACK);
        
        this.mainPanel.add(jarTab,2,0);
        jarTab.setBounds(835,620,85,26);
        jarTab.setBackground(new Color(0,184,0));
        jarTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        jarTab.setText("Jars");
        jarTab.setFocusPainted(false);
        jarTab.setForeground(Color.BLACK);
        jarTab.setEnabled(true);
       
        this.mainPanel.add(allTab,2,0);
        allTab.setBounds(915,620,85,26);
        allTab.setBackground(new Color(0,184,0));
        allTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        allTab.setText("All Files");
        allTab.setFocusPainted(false);
        allTab.setForeground(Color.BLACK);
        allTab.setEnabled(true);
        
        this.mainPanel.add(missingTab,2,0);
        missingTab.setBounds(625,620,135,26);
        missingTab.setBackground(new Color(0,184,0));
        missingTab.setBorder(BorderFactory.createMatteBorder(2,2,0,2,borderColor));
        missingTab.setText("Missing Dependencies");
        missingTab.setFocusPainted(false);
        
        missingTab.setForeground(Color.BLACK);
        
        mainList.setBackground(backColor);
        mainList.setFont(mainList.getFont().deriveFont(Font.PLAIN,20));
        mainList.setForeground(Color.WHITE);
        mainList.setBounds(0,0,1000,646);
        
        mainList.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent evt) 
            {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) 
                {
                    int index = list.locationToIndex(evt.getPoint());
                    String s= list.getModel().getElementAt(index).toString();
                    if(s.endsWith(".jar"))
                    {
                        try {
                            Subgrafo=grafo.makeSubGraph(s);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassFormatException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lastFile=Subgrafo.getLastFile();
                        setSubgraph();
                        
                    }
                } 
            }
        });
        classList.addElement(" -----------------------------------------------------------------Classes----------------------------------------------------------------");
        setList(Subgrafo.getALLclases(),classList,"classes");
        
        jarList.addElement(" -----------------------------------------------------------------Jars--------------------------------------------------------------------");
        setList(Subgrafo.getAllJars(),jarList,"jar");
        
        allFileList.addElement(" ----------------------------------------------------------------All Files-----------------------------------------------------------------");
        allFileList.addElement(" >>>CLASSES");
        setList(Subgrafo.getALLclases(),allFileList,"all");
        allFileList.addElement(" >>>JARS");
        setList(Subgrafo.getAllJars(),allFileList,"all");

        
        missingList.addElement(" --------------------------------------------------------Missing Dependencies-------------------------------------------------------");
        setList("",missingList,"miss");
        
        mainList.setModel(classList);
        
        jarTab.setEnabled(true);
        allTab.setEnabled(true);
        
        lista= Subgrafo.getListClass().ConvertToClassList();
    }

}
