import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
 
public class interfaz extends JFrame {
 
    private final Calculadora calculadora = new Calculadora();
 
    private final JLabel pantallaOperacion = new JLabel(" ", SwingConstants.RIGHT);
    private final JLabel pantalla = new JLabel("0", SwingConstants.RIGHT);
 
    private double primerNumero = 0;
    private String operador = null;
    private boolean nuevaEntrada = true;
    private boolean enError = false;
 
    private static final Color FONDO = new Color(30, 30, 36);
    private static final Color BTN_NUMERO = new Color(58, 58, 68);
    private static final Color BTN_OPERADOR = new Color(255, 149, 0);
    private static final Color BTN_ESPECIAL = new Color(100, 100, 115);
    private static final Color BTN_IGUAL = new Color(52, 199, 89);
 
    public interfaz() {
        super("Calculadora Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(FONDO);
 
        add(crearPantalla(), BorderLayout.NORTH);
        add(crearTeclado(), BorderLayout.CENTER);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
 
        configurarTeclado();
 
        setSize(340, 500);
        setMinimumSize(new Dimension(300, 440));
        setLocationRelativeTo(null);
    }
 
    private JPanel crearPantalla() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
 
        pantallaOperacion.setForeground(new Color(160, 160, 170));
        pantallaOperacion.setFont(new Font("SansSerif", Font.PLAIN, 16));
 
        pantalla.setForeground(Color.WHITE);
        pantalla.setFont(new Font("SansSerif", Font.BOLD, 40));
 
        panel.add(pantallaOperacion);
        panel.add(pantalla);
        panel.setPreferredSize(new Dimension(0, 100));
        return panel;
    }
 
    private JPanel crearTeclado() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBackground(FONDO);
 
        String[] botones = {
            "C", "←", "±", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };
 
        for (String texto : botones) {
            if (texto.isEmpty()) {
                panel.add(new JLabel());
                continue;
            }
            panel.add(crearBoton(texto));
        }
        return panel;
    }
 
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 22));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setFocusable(false);
 
        switch (texto) {
            case "+": case "-": case "×": case "÷":
                boton.setBackground(BTN_OPERADOR); break;
            case "C": case "←": case "±":
                boton.setBackground(BTN_ESPECIAL); break;
            case "=":
                boton.setBackground(BTN_IGUAL); break;
            default:
                boton.setBackground(BTN_NUMERO);
        }
 
        boton.addActionListener(e -> procesar(texto));
        return boton;
    }
 
    private void procesar(String texto) {
        if (enError && !texto.equals("C")) {
            return;
        }
        switch (texto) {
            case "C": limpiar(); break;
            case "←": borrarUltimo(); break;
            case "±": cambiarSigno(); break;
            case "=": calcular(); break;
            case "+": case "-": case "×": case "÷":
                elegirOperador(texto); break;
            case ".": agregarPunto(); break;
            default: agregarDigito(texto);
        }
    }
 
    private void agregarDigito(String d) {
        if (nuevaEntrada || pantalla.getText().equals("0")) {
            pantalla.setText(d);
        } else {
            pantalla.setText(pantalla.getText() + d);
        }
        nuevaEntrada = false;
    }
 
    private void agregarPunto() {
        if (nuevaEntrada) {
            pantalla.setText("0.");
            nuevaEntrada = false;
        } else if (!pantalla.getText().contains(".")) {
            pantalla.setText(pantalla.getText() + ".");
        }
    }
 
    private void borrarUltimo() {
        if (nuevaEntrada) return;
        String t = pantalla.getText();
        if (t.length() <= 1 || (t.length() == 2 && t.startsWith("-"))) {
            pantalla.setText("0");
            nuevaEntrada = true;
        } else {
            pantalla.setText(t.substring(0, t.length() - 1));
        }
    }
 
    private void cambiarSigno() {
        String t = pantalla.getText();
        if (t.equals("0")) return;
        pantalla.setText(t.startsWith("-") ? t.substring(1) : "-" + t);
    }
 
    private void elegirOperador(String op) {
        if (operador != null && !nuevaEntrada) {
            calcular();
            if (enError) return;
        }
        primerNumero = Double.parseDouble(pantalla.getText());
        operador = op;
        nuevaEntrada = true;
        pantallaOperacion.setText(formatear(primerNumero) + " " + op);
    }
 
    private void calcular() {
        if (operador == null) return;
 
        double segundo = Double.parseDouble(pantalla.getText());
        String expresion = formatear(primerNumero) + " " + operador + " " + formatear(segundo) + " =";
 
        try {
            double resultado;
            switch (operador) {
                case "+": resultado = calculadora.sumar(primerNumero, segundo); break;
                case "-": resultado = calculadora.restar(primerNumero, segundo); break;
                case "×": resultado = calculadora.multiplicar(primerNumero, segundo); break;
                default:  resultado = calculadora.dividir(primerNumero, segundo);
            }
            pantalla.setText(formatear(resultado));
            pantallaOperacion.setText(expresion);
        } catch (ArithmeticException ex) {
            pantalla.setText("Error");
            pantallaOperacion.setText(ex.getMessage());
            enError = true;
        }
 
        operador = null;
        nuevaEntrada = true;
    }
 
    private void limpiar() {
        pantalla.setText("0");
        pantallaOperacion.setText(" ");
        primerNumero = 0;
        operador = null;
        nuevaEntrada = true;
        enError = false;
    }
 
    private String formatear(double valor) {
        if (valor == Math.rint(valor) && Math.abs(valor) < 1e15) {
            return String.valueOf((long) valor);
        }
        String s = String.valueOf(valor);
        return s;
    }
 
    private void configurarTeclado() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) procesar(String.valueOf(c));
                else if (c == '.' || c == ',') procesar(".");
                else if (c == '+') procesar("+");
                else if (c == '-') procesar("-");
                else if (c == '*') procesar("×");
                else if (c == '/') procesar("÷");
                else if (c == '=' || c == '\n') procesar("=");
                else if (c == '\b') procesar("←");
                else if (c == 27) procesar("C");
            }
        });
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new interfaz().setVisible(true));
    }
}