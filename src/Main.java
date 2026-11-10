import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();
        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("       CALCULADORA JAVA");
            System.out.println("==============================");
            System.out.println("1. Sumar");
            System.out.println("2. Restar");
            System.out.println("3. Multiplicar");
            System.out.println("4. Dividir");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            while (!teclado.hasNextInt()) {
                System.out.print("Ingresa una opción válida: ");
                teclado.next();
            }
            opcion = teclado.nextInt();

            if (opcion >= 1 && opcion <= 4) {
                System.out.print("Ingresa el primer número: ");
                while (!teclado.hasNextDouble()) {
                    System.out.print("Dato inválido. Ingresa un número: ");
                    teclado.next();
                }
                double numero1 = teclado.nextDouble();

                System.out.print("Ingresa el segundo número: ");
                while (!teclado.hasNextDouble()) {
                    System.out.print("Dato inválido. Ingresa un número: ");
                    teclado.next();
                }
                double numero2 = teclado.nextDouble();

                switch (opcion) {
                    case 1:
                        System.out.println("Resultado: " + calculadora.sumar(numero1, numero2));
                        break;
                    case 2:
                        System.out.println("Resultado: " + calculadora.restar(numero1, numero2));
                        break;
                    case 3:
                        System.out.println("Resultado: " + calculadora.multiplicar(numero1, numero2));
                        break;
                    case 4:
                        if (numero2 == 0) {
                            System.out.println("Error: no se puede dividir entre cero.");
                        } else {
                            System.out.println("Resultado: " + calculadora.dividir(numero1, numero2));
                        }
                        break;
                }
            } else if (opcion != 5) {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 5);

        System.out.println("Gracias por utilizar la calculadora.");
        teclado.close();
    }
}
