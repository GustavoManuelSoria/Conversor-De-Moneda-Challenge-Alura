package conversorDeMonedas.controller;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {

    private static Map<String, Double> tasasDeCambio;
    private static Scanner scanner;

    public MenuPrincipal(Map<String, Double> tasas, Scanner sc) {
        tasasDeCambio = tasas;
        scanner = sc;
    }

    public void ejecutarMenu() {
        int opcion = 0;

        do {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        realizarConversion("USD", "ARS");
                        break;
                    case 2:
                        realizarConversion("ARS", "USD");
                        break;
                    case 3:
                        realizarConversion("USD", "BRL");
                        break;
                    case 4:
                        realizarConversion("BRL", "USD");
                        break;
                    case 5:
                        realizarConversion("USD", "COP");
                        break;
                    case 6:
                        realizarConversion("COP", "USD");
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("\nOpción no válida. Por favor, elija un número del 1 al 7.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.err.println("\nERROR: Entrada inválida. Por favor, ingrese un número del menú.");
                scanner.next();
                opcion = 0;
            } catch (Exception e) {
                System.err.println("\nERROR inesperado en la ejecución: " + e.getMessage());
                opcion = 0;
            }

        } while (opcion != 7);

        System.out.println("\n¡Gracias por usar el conversor!");
    }

    private static void mostrarMenu() {
        String separador = "***********************************************";

        System.out.println("\n" + separador);
        System.out.println("Sea bienvenido/a al Conversor de Moneda =>]");
        System.out.println("\n1) Dólar (USD) =>> Peso argentino (ARS)");
        System.out.println("2) Peso argentino (ARS) =>> Dólar (USD)");
        System.out.println("3) Dólar (USD) =>> Real brasileño (BRL)");
        System.out.println("4) Real brasileño (BRL) =>> Dólar (USD)");
        System.out.println("5) Dólar (USD) =>> Peso colombiano (COP)");
        System.out.println("6) Peso colombiano (COP) =>> Dólar (USD)");
        System.out.println("7) Salir");
        System.out.print("\nElija una opción válida:\n");
        System.out.println(separador);
        System.out.print("=> ");
    }

    private static void realizarConversion(String origen, String destino) {
        System.out.print("Ingrese el valor que desea convertir: ");

        try {
            double monto = scanner.nextDouble();

            Double tasaOrigen = tasasDeCambio.get(origen);
            Double tasaDestino = tasasDeCambio.get(destino);

            if (tasaOrigen == null || tasaDestino == null) {
                System.err.println("\nERROR: Una de las tasas requeridas no se encontró en los datos de la API.");
                return;
            }

            double montoConvertido;

            if (origen.equals("USD")) {
                montoConvertido = monto * tasaDestino;
            } else if (destino.equals("USD")) {
                montoConvertido = monto / tasaOrigen;
            } else {
                double montoEnBase = monto / tasaOrigen;
                montoConvertido = montoEnBase * tasaDestino;
            }


            System.out.printf("\nEl valor de %.2f [%s] corresponde al valor final de %.2f [%s]\n",
                    monto,
                    origen,
                    montoConvertido,
                    destino);

        } catch (InputMismatchException e) {
            System.err.println("\nERROR: Monto inválido. Por favor, ingrese un número (ej. 10.50).");
            scanner.next();
        }
    }
}
