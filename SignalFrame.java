import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class SignalFrame extends JFrame{
    JLabel l1 = new JLabel("Code : "), l2 = new JLabel("Chaine binaire : ");
    JComboBox<String> c = new JComboBox<>();
    TextField tf = new TextField();
    JButton signal = new JButton("Signal");
    JButton quitter = new JButton("Quitter");

    JPanel p1 = new JPanel(), p2;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)size.getWidth();

    //Classe interne pour dessiner le signal
    class SignalPanel extends JPanel{
        private String chaine;
        private String methode;
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            this.chaine = tf.getText();
            this.methode = c.getSelectedItem().toString();

            setBackground(Color.WHITE);
            setLayout(null);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.LIGHT_GRAY);

            if(!this.chaine.isEmpty()){
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                g2.setColor(Color.BLACK);

                if(methode == "NRZ"){
                    String  poly;
                    char currentChar[] = new char[1];

                    poly = tf.getText(); // poly prend les valeurs de TextField

                    g.setColor(Color.gray); // tracer la grille avec une couleur grise
                    g.drawLine(0, 85, poly.length()*30, 85);
                    g.drawLine(0, 158, poly.length()*30, 158);
                    g.drawLine(0, 230, poly.length()*30, 230);
                    g.setColor(Color.BLACK);
                    g.drawString("nV",0,85);
                    g.drawString("-nV",0,230);
                    g.drawString("0V",0,158);

                    for (int i = 0; i < poly.length(); i++) {

                        ((Graphics2D) g).setStroke(new BasicStroke(3));
                        g.setColor(Color.red);

                        if (poly.charAt(i) == '0') { // si c un zero alor trace en bas
                            g.drawLine(i*30, 230, (i*30) + 30, 230);

                        } else if (poly.charAt(i) == '1') { // si c 1 alors tracer en haut
                            g.drawLine(i*30, 85, (i*30) + 30, 85);
                        }

                        g.setColor(Color.gray);

                        if(i > 0) {
                            if(poly.charAt(i) != poly.charAt(i-1))
                                g.setColor(Color.red);
                            g.drawLine(i*30, 85, i*30, 230);
                        }

                        //tracer les bits correspendant en dessus de la grille
                        currentChar[0] = poly.charAt(i);
                        g.setColor(Color.black);
                        g.drawChars(currentChar, 0, 1, (i*30)+20, 70);
                    }
                }
                if(methode == "Manchester"){
                    String  poly;
                    char currentChar[] = new char[1];

                    poly = tf.getText();
                    g.setColor(Color.gray);
                    g.drawLine(0, 85, poly.length()*30*2, 85);
                    g.drawLine(0, 158, poly.length()*30*2, 158);
                    g.drawLine(0, 230, poly.length()*30*2, 230);
                    g.drawString("nV",poly.length(),85);
                    g.drawString("-nV",poly.length(),230);
                    g.drawString("0V",poly.length(),158);

                    for (int i = 0; i < poly.length(); i++) {

                        g.setColor(Color.red);
                        ((Graphics2D) g).setStroke(new BasicStroke(3));
                        if (poly.charAt(i) == '0') { // si c un zero alor trace de bas en haut
                            g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                            g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                            g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);
                            if(i>0 && poly.charAt(i-1) == '0'){
                                g.drawLine((i*2*30), 85, (i*2*30), 230);

                            }
                        } else if (poly.charAt(i) == '1') { // si c 1 alors tracer de haut en bas
                            g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                            g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                            g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);
                            if(i>0 && poly.charAt(i-1) == '1'){
                                g.drawLine((i*2*30), 85, (i*2*30), 230);

                            }


                        }
                        //tracer les bits correspendant en dessus de la grille
                        currentChar[0] = poly.charAt(i);
                        g.setColor(Color.black);
                        g.drawChars(currentChar, 0, 1, (i*30*2)+20, 70);
                    }
                }
            }
            if(methode == "Manchester différentiel"){
                String  poly;
                char currentChar[] = new char[1];
                poly = tf.getText();
                char var;

                g.setColor(Color.gray);
                g.drawLine(0, 85, poly.length()*30*2, 85);
                g.drawLine(0, 158, poly.length()*30*2, 158);
                g.drawLine(0, 230, poly.length()*30*2, 230);
                g.drawString("nV",poly.length(),85);
                g.drawString("-nV",poly.length(),230);
                g.drawString("0V",poly.length(),158);


                var = poly.charAt(0); //var prend le 1er bits
                boolean tag=true; //true on monte , false on descend
                int etat_prec=0;
                g.setColor(Color.red);
                ((Graphics2D) g).setStroke(new BasicStroke(3));

                if(poly.charAt(0) == '1'){

                    g.drawLine(0*2*30, 230, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+30, 85, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+30, 85, (0*2*30)+60, 85);

                    tag=true; // bit 1 montant

                }
                else {// si c'est un zero alors trace en bas

                    g.drawLine((0*2*30), 85, (0*2*30)+30, 85);
                    g.drawLine((0*2*30)+30, 85, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+60, 230, (0*2*30)+30, 230);

                    tag = false; //bit 0 descendant

                }
                currentChar[0] = poly.charAt(0);
                g.setColor(Color.black);
                g.drawChars(currentChar, 0, 1, (0*30*2)+20, 70);

                for (int i=1; i < poly.length(); i++) {

                    etat_prec=(int) var;
                    var = poly.charAt(i);
                    g.setColor(Color.red);

                    if(etat_prec == var){ //si le bit actuel est égal au bit precedent
                        if (poly.charAt(i) == '1') {
                            if(tag == true){ //si le front precedent est montant alors ce front est descendant
                                g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;
                            }
                            else  { //ce front est montant
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);
                                tag=true;

                            }
                        }

                        else { //le bit est égal à zero
                            if(tag==true){//si le front precedent est montant alors ce front est montant
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);
                                tag=true;
                            }
                            else{  //si le front precedent est descendant ce font est descendant
                                g.drawLine(i*2*30, 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;

                            }
                        }
                    }
                    else{ // si les deux bits sont differents
                        if (poly.charAt(i) == '0') {
                            if(tag==true){ //si le front precedent est montant alors ce front est montant
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine(i*2*30, 230, i*2*30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);

                                tag=true;

                            }
                            else if (tag==false){//si le front precedent est descendant alors ce front est descendant
                                g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                                g.drawLine(i*2*30, 85, i*2*30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);
                                tag=false;
                            }

                        }

                        else {//l'etat present est 1 et l'etat precedent est 0 donc renversement
                            if(tag==true){//si le front precedent est montant alors ce front est descendant
                                g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;

                            }
                            else{//si le front precedent est descendant alors ce front est montant
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);

                                tag=true;

                            }}

                    }
                    //tracer les bits correspendant en dessus de la grille
                    currentChar[0] = poly.charAt(i);
                    g.setColor(Color.black);
                    g.drawChars(currentChar, 0, 1, (i*30*2)+20, 70);
                }


            }
            if(methode == "Miller"){
                char currentChar[] = new char[1];
                String poly;
                poly = tf.getText();

                g.setColor(Color.gray);
                g.drawLine(0, 85, poly.length()*30*2, 85);
                g.drawLine(0, 158, poly.length()*30*2, 158);
                g.drawLine(0, 230, poly.length()*30*2, 230);
                g.drawString("nV",poly.length(),85);
                g.drawString("-nV",poly.length(),230);
                g.drawString("0V",poly.length(),158);


                char var = poly.charAt(0);
                boolean tag=true; //true transition, false absence de transition
                int etat_prec=0;
                g.setColor(Color.red);
                ((Graphics2D) g).setStroke(new BasicStroke(3));
                if(poly.charAt(0) == '1'){

                    g.drawLine(0*2*30, 230, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+30, 85, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+30, 85, (0*2*30)+60, 85);

                    tag=true; // bit 1 une transition

                }
                else {// si c un zero alors absence de trasition

                    g.drawLine((0*2*30), 230, (0*2*30)+30, 230);
                    g.drawLine((0*2*30)+60, 230, (0*2*30)+30, 230);

                    tag = false; //bit 0

                }
                currentChar[0] = poly.charAt(0);
                g.setColor(Color.black);
                g.drawChars(currentChar, 0, 1, (0*30*2)+20, 245);

                for (int i=1; i < poly.length(); i++) {

                    etat_prec=(int) var;
                    var = poly.charAt(i);
                    g.setColor(Color.red);

                    if(etat_prec == var){
                        if (poly.charAt(i) == '1') {
                            if(tag == true){ //si y a une transition
                                g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;
                            }
                            else  {
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);
                                tag=true;

                            }
                        }


                        else { //bit égal a 0
                            if(tag==true){//si le front precedent est en transition alors tracer en haut
                                g.drawLine(i*2*30, 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);
                                tag=true;
                            }
                            else if (tag==false){  //si le front precedent est decendant ce font est descendant
                                g.drawLine((i*2*30), 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;

                            }
                        }
                    }
                    else{
                        if (poly.charAt(i) == '0') {
                            if(tag==true){ //si le front precedent est montant alors absence de trasition en haut
                                g.drawLine(i*2*30, 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);

                                tag=true;

                            }
                            else if (tag==false){//si le front precedent est descendant alors absence de trasition vers en bas
                                g.drawLine((i*2*30), 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);
                                tag=false;
                            }

                        }

                        else {//l'etat present est 1 et l'etat precedent est 0 renversement
                            if(tag==true){//tracer de haut vers le bas
                                g.drawLine((i*2*30), 85, (i*2*30)+30, 85);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+60, 230, (i*2*30)+30, 230);

                                tag=false;

                            }
                            else{//tracer de bas vers le haut
                                g.drawLine(i*2*30, 230, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+30, 230);
                                g.drawLine((i*2*30)+30, 85, (i*2*30)+60, 85);

                                tag=true;

                            }}


                    }
                    //tracer les bits correspendant en dessus de la grille
                    currentChar[0] = poly.charAt(i);
                    g.setColor(Color.black);
                    g.drawChars(currentChar, 0, 1, (i*30*2)+20, 70);
                }

            }

        }

    }
    public SignalFrame() {
        super("Signal ");
        setBounds(0, 200, width, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        c.addItem("NRZ");
        c.addItem("Manchester");
        c.addItem("Manchester différentiel");
        c.addItem("Miller");
        tf.setColumns(30);
        p1.add(l2);
        p1.add(tf);
        p1.add(l1);
        p1.add(c);
        p1.add(signal);
        p1.add(quitter);
        p2 = new SignalPanel();

        quitter.addActionListener(ae -> System.exit(0));

        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                tf.setEditable(e.getKeyChar() == '1' || e.getKeyChar() == '0' || e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE);
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        signal.addActionListener(e -> {
            if(!tf.getText().isEmpty()){
                p2.revalidate();
                p2.repaint();
            }
        });

        //changer la couleur de boutons
        JButton[] boutons = {signal, quitter};
        for (JButton bouton : boutons) {
            bouton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    bouton.setBackground(Color.CYAN);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bouton.setBackground(UIManager.getColor("Button.background"));
                }
            });
        }
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);

        setVisible(true);

    }
    public static void main(String args[]){
        SwingUtilities.invokeLater(SignalFrame::new);


    }
}