package sudoku;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JOptionPane;
import javafx.util.Pair;
import java.util.Random;

public class Sudoku extends JFrame implements ActionListener {

    private final JPanel jpanel = (JPanel) this.getContentPane();
    private final JTextField[][] tablero;
    private final JButton resolver, reiniciar, setEjemplo;
    private final JLabel creditos;

    public Sudoku() {
        this.tablero = new JTextField[9][9];
        jpanel.setLayout(null);
        jpanel.setBackground(new Color(249, 233, 187));

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = new JTextField();
                if (i >= 0 && i <= 2) {
                    tablero[i][j].setBackground(new Color(233, 176, 242));
                    if (j >= 3 && j <= 5) {
                        tablero[i][j].setBackground(new Color(176, 242, 194));
                    }
                } else if (i >= 3 && i <= 5) {
                    tablero[i][j].setBackground(new Color(156, 200, 255));
                    if (j >= 3 && j <= 5) {
                        tablero[i][j].setBackground(new Color(255, 182, 174));
                    }
                } else if (i >= 6 && i <= 8) {
                    tablero[i][j].setBackground(new Color(255, 255, 174));
                    if (j >= 3 && j <= 5) {
                        tablero[i][j].setBackground(new Color(176, 242, 194));
                    }
                }
                tablero[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                tablero[i][j].setBounds((j + 1) * 40, (i + 1) * 40, 30, 30);
                jpanel.add(tablero[i][j], null);
            }
        }
        reiniciar = new JButton("Reinciar");
        reiniciar.addActionListener(this);
        reiniciar.setBounds(450, 50, 120, 40);
        reiniciar.setBackground(new Color(176, 242, 194));

        setEjemplo = new JButton("Ejemplo");
        setEjemplo.addActionListener(this);
        setEjemplo.setBounds(450, 100, 120, 40);
        setEjemplo.setBackground(new Color(176, 242, 194));

        resolver = new JButton("Resolver");
        resolver.addActionListener(this);
        resolver.setBounds(450, 150, 120, 40);
        resolver.setBackground(new Color(176, 242, 194));

        creditos = new JLabel("Proyecto Final: IA ICI 2021");
        creditos.setFont(new Font("Garamond", Font.PLAIN, 22));
        creditos.setBounds(200, 400, 400, 50);

        jpanel.add(creditos, null);
        jpanel.add(reiniciar, null);
        jpanel.add(setEjemplo, null);
        jpanel.add(resolver, null);

        setSize(650, 500);
        setTitle("Sudoku");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean repetidoFila(int matrizTablero[][], int fila, int num) {
        for (int col = 0; col < matrizTablero.length; col++) {
            if (matrizTablero[fila][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean repetidoColum(int matrizTablero[][], int columna, int num) {
        for (int fila = 0; fila < matrizTablero.length; fila++) {
            if (matrizTablero[fila][columna] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean repetidoCuadro(int matrizTablero[][], int cuadroFil, int cuadroCol, int num) {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (matrizTablero[fila + cuadroFil][columna + cuadroCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean asignacionValida(int matrizTablero[][], int fila, int columna, int num) {
        return !repetidoFila(matrizTablero, fila, num) && !repetidoColum(matrizTablero, columna, num) && !repetidoCuadro(matrizTablero, fila - fila % 3, columna - columna % 3, num);
    }

    private Pair<Integer, Integer> casillasVacias(int matrizTablero[][]) {
        for (int fila = 0; fila < matrizTablero.length; fila++) {
            for (int columna = 0; columna < matrizTablero.length; columna++) {
                if (matrizTablero[fila][columna] == 0) {
                    return new Pair<>(fila, columna);
                }
            }
        }
        return new Pair<>(9, 9);
    }

    private boolean resolverSudoku(int matrizTablero[][]) {
        Pair<Integer, Integer> ans = new Pair<>(9, 9);

        if (ans.getKey().equals(casillasVacias(matrizTablero).getKey())
                && ans.getValue().equals(casillasVacias(matrizTablero).getValue())) {
            return true;
        }
        Pair<Integer, Integer> ubicacion = casillasVacias(matrizTablero);
        int fila = ubicacion.getKey();
        int columna = ubicacion.getValue();
        for (int num = 1; num <= 9; num++) {
            if (asignacionValida(matrizTablero, fila, columna, num)) {
                matrizTablero[fila][columna] = num;
                if (resolverSudoku(matrizTablero)) {
                    return true;
                }
                matrizTablero[fila][columna] = 0;
            }
        }

        return false;
    }

    private boolean tableroMatriz() {
        int[][] matrizTablero = new int[9][9];
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero.length; columna++) {
                if (tablero[fila][columna].getText().equals("")
                        || tablero[fila][columna].getText().equals(0)
                        || tablero[fila][columna].getText().equals(" ") || tablero[fila][columna].getText().equals("0")) {
                    matrizTablero[fila][columna] = 0;
                } else {
                    int num = Integer.parseInt(tablero[fila][columna].getText());
                    if (num > 9 || num < 0) {
                        JOptionPane.showMessageDialog(null, "Valores en el tablero incorrectos");
                        return false;
                    } else {
                        matrizTablero[fila][columna] = num;
                    }
                }

            }

        }
        if (resolverSudoku(matrizTablero)) {
            respuesta(matrizTablero);
        } else {
            JOptionPane.showMessageDialog(null, "Sin Solución");
        }
        return true;
    }

    private void respuesta(int matrizTablero[][]) {
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero.length; columna++) {
                if (matrizTablero[fila][columna] != 0) {
                    tablero[fila][columna].setText(String.valueOf(matrizTablero[fila][columna]));
                } else {
                    tablero[fila][columna].setText(null);
                }

            }
        }
    }

    private void reiniciarTablero() {
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero.length; columna++) {
                tablero[fila][columna].setText(null);
            }
        }
    }

    private void ponerEjemplo() {
        int conta = 0;
        int aleatorioNum;
        int fila;
        int columna;
        int[][] matrizEjemplo = new int[9][9];
        Random rand = new Random();
        do {
            aleatorioNum = 1 + rand.nextInt(9);
            fila = rand.nextInt(9);
            columna = rand.nextInt(9);
            if (asignacionValida(matrizEjemplo, fila, columna, aleatorioNum)) {
                matrizEjemplo[fila][columna] = aleatorioNum;
                conta++;
            } else {
                matrizEjemplo[fila][columna] = 0;
            }
        } while (conta < 18);
        reiniciarTablero();
        respuesta(matrizEjemplo);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object seleccion = evento.getSource();
        if (seleccion == reiniciar) {
            reiniciarTablero();
        } else if (seleccion == resolver) {
            tableroMatriz();
        } else if (seleccion == setEjemplo) {
            ponerEjemplo();
        }
    }

    public static void main(String arg[]) {
        Sudoku sudoku = new Sudoku();
    }

}
