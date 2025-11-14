import com.google.gson.Gson;
import conversorDeMonedas.controller.MenuPrincipal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConversordeMonedas {
    private static Map<String, Double> tasas;


    static void main(String[] args) {
        System.out.println("Cargando tasas de cambio...");

        if (!obtenerTasas()) {
            System.err.println("ERROR: No se pudieron cargar las tasas de cambio. Revisa tu API Key, la conexión, o si las monedas requeridas están disponibles.");
            return;
        }
        System.out.println("Tasas cargadas con éxito.");

        Scanner scanner = new Scanner(System.in);
        MenuPrincipal controlador = new MenuPrincipal(tasas, scanner);
        controlador.ejecutarMenu();
        scanner.close();
    }


    private static boolean obtenerTasas() {
       String direccion = "https://v6.exchangerate-api.com/v6/a66fc4343353d228e7b0056f/latest/USD";

        try {
           HttpClient client = HttpClient.newHttpClient();
           HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

           HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
           String jsonResponse = response.body();

           Gson gson = new Gson();
           TasaDeCambio data = gson.fromJson(jsonResponse, TasaDeCambio.class);

           tasas = data.getTasasDeConversion();

           return tasas != null && tasas.containsKey("ARS") && tasas.containsKey("BRL") && tasas.containsKey("COP");
       } catch (IOException e) {
           System.err.println("Error de I/O o conexión al obtener tasas: " + e.getMessage());
           return false;
       } catch (InterruptedException e) {
           System.err.println("La operación HTTP fue interrumpida: " + e.getMessage());
           return false;
       } catch (Exception e) {
           System.err.println("Error inesperado al procesar las tasas: " + e.getMessage());
           return false;
       }

   }
}
