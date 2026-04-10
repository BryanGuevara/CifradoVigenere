package cifradovigenere;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CifradoVigenere {

    static char[] alfabeto = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray();
    static char[] numeros = "0123456789".toCharArray();
    static char[][][] letras = new char[2][][];
    static int num = 0;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        letras = new char[2][alfabeto.length][alfabeto.length];

        for (int i = 0; i < letras[0].length; i++) {
            for (int j = 0; j < letras[0][i].length; j++) {
                letras[0][i][j] = alfabeto[(j + i) % alfabeto.length];
            }
        }

        for (int i = 0; i < letras[1].length; i++) {
            for (int j = 0; j < letras[1][i].length; j++) {
                letras[1][i][j] = numeros[(j + i) % numeros.length];
            }
        }

        String respuesta = "";
        System.out.println("========= <[Codificador Vigenere]> =============================================");
        while (!respuesta.equals("0")) {
            System.out.print("(1) Encriptar\n(2) Desencriptar\n(0) Terminar\n¿Que desea hacer?: ");
            respuesta = scan.nextLine();
            if (respuesta.equals("1")) {
                System.out.println("========= <[Encriptador Vigenere]> =============================================");
                encriptador();
                System.out.println("================================================================================");
            } else if (respuesta.equals("2")) {
                System.out.println("========= <[Desencriptador Vigenere]> ==========================================");
                desEncriptador();
                System.out.println("================================================================================");
            } else if (!respuesta.equals("0")) {
                System.out.println("========= <[Error]> ============================================================");
                System.err.println("Opción no válida, inténtelo de nuevo.");
                System.out.println("================================================================================");
            }
        }
        scan.close();
        System.out.println("================================================================================");
    }

    public static void encriptador() {
        num = 0;
        System.out.print("Ingrese la palabra/frase a Encriptar: ");
        char[] palabra = scan.nextLine().toCharArray();
        System.out.print("Ingrese la clave de Encriptar: ");
        String claveInput = scan.nextLine().toUpperCase();

        claveInput = claveInput.replaceAll("[^A-ZÑ ]", "");

        if (!claveInput.matches("[A-ZÑ ]+")) {
            System.out.println("========= <[Error]> ============================================================");
            System.err.println("La clave solo puede contener letras.");
            return;
        }

        char[] clave = claveInput.toCharArray();

        int[][] mapPalabra = new int[palabra.length][2];
        int[] mapClave = new int[clave.length];

        for (int i = 0; i < palabra.length; i++) {
            if (Character.isLetter(palabra[i])) {
                for (int j = 0; j < alfabeto.length; j++) {
                    if (Character.toUpperCase(palabra[i]) == alfabeto[j]) {
                        mapPalabra[i][0] = j;
                        mapPalabra[i][1] = Character.isUpperCase(palabra[i]) ? 0 : 1;
                    }
                }
            } else if (Character.isDigit(palabra[i])) {
                for (int j = 0; j < numeros.length; j++) {
                    if (palabra[i] == numeros[j]) {
                        mapPalabra[i][0] = j;
                        mapPalabra[i][1] = 2;
                    }
                }
            } else if (palabra[i] == ' ') {
                for (int j = 0; j < alfabeto.length; j++) {
                    if (palabra[i] == alfabeto[j]) {
                        mapPalabra[i][0] = j;
                        mapPalabra[i][1] = 3;
                    }
                }
            }
        }

        for (int i = 0; i < clave.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                if (clave[i] == alfabeto[j]) {
                    mapClave[i] = j;
                }
            }
        }

        num = 0;
        StringBuilder palabraCifrada = new StringBuilder();
        for (int i = 0; i < palabra.length; i++) {
            char letra;
            if (mapPalabra[i][1] == 2) {
                letra = letras[1][mapClave[num]][mapPalabra[i][0]];
            } else {
                letra = letras[0][mapClave[num]][mapPalabra[i][0]];
                if (mapPalabra[i][1] == 1) {
                    letra = Character.toLowerCase(letra);
                }
            }
            palabraCifrada.append(letra);
            num = (num + 1) % clave.length;
        }

        System.out.println("La palabra cifrada es: " + palabraCifrada);
    }

    public static void desEncriptador() {
        num = 0;
        System.out.print("Ingrese la palabra/frase a Desencriptar: ");
        char[] palabra = scan.nextLine().toCharArray();
        System.out.print("Ingrese la clave de Encriptar: ");
        String claveInput = scan.nextLine().toUpperCase();

        claveInput = claveInput.replaceAll("[^A-ZÑ ]", "");

        if (!claveInput.matches("[A-ZÑ ]+")) {
            System.out.println("========= <[Error]> ============================================================");
            System.err.println("La clave solo puede contener letras.");
            return;
        }

        char[] clave = claveInput.toCharArray();

        int[] mapClave = new int[clave.length];
        for (int i = 0; i < clave.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                if (clave[i] == alfabeto[j]) {
                    mapClave[i] = j;
                }
            }
        }

        num = 0;
        StringBuilder palabraDescifrada = new StringBuilder();
        for (int i = 0; i < palabra.length; i++) {
            boolean esMinuscula = Character.isLowerCase(palabra[i]);
            char letraMayus = Character.toUpperCase(palabra[i]);
            int originalIndex = -1;

            if (Character.isLetter(palabra[i])) {
                for (int j = 0; j < alfabeto.length; j++) {
                    if (letraMayus == letras[0][mapClave[num]][j]) {
                        originalIndex = j;
                        break;
                    }
                }
                char letraOriginal = alfabeto[originalIndex];
                if (esMinuscula) {
                    letraOriginal = Character.toLowerCase(letraOriginal);
                }
                palabraDescifrada.append(letraOriginal);
            } else if (Character.isDigit(palabra[i])) {
                for (int j = 0; j < numeros.length; j++) {
                    if (palabra[i] == letras[1][mapClave[num]][j]) {
                        originalIndex = j;
                        break;
                    }
                }
                palabraDescifrada.append(numeros[originalIndex]);
            }
            num = (num + 1) % clave.length;
        }

        System.out.println("La palabra descifrada es: " + palabraDescifrada);
    }
}
