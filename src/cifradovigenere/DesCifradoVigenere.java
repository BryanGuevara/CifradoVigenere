/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cifradovigenere;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author bryan
 */
public class DesCifradoVigenere {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scan = new Scanner(System.in);

        String[] alfabeto = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", " ",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
        };

        String[][] letras = new String[alfabeto.length][alfabeto.length];
        int num = 0;
        for (int i = 0; i < letras.length; i++) {
            for (int j = 0; j < letras[i].length; j++) {
                int numLetra = j + num;
                if (numLetra >= alfabeto.length) {
                    numLetra = numLetra - alfabeto.length;
                }
                letras[i][j] = alfabeto[numLetra];
            }
            num++;
        }

        System.out.print("Ingrese la palabra/frase a Desencriptar: ");
        String[] palabra = scan.nextLine().toUpperCase().split("");

        System.out.print("Ingrese la clave de Encriptar: ");
        String[] clave = scan.nextLine().toUpperCase().split("");

        int[] mapClave = new int[clave.length];
        for (int i = 0; i < clave.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                if (clave[i].equals(alfabeto[j])) {
                    mapClave[i] = j;
                }
            }
        }

        num = 0;
        String palabraDescifrada = "";
        for (int i = 0; i < palabra.length; i++) {
            int originalIndex = -1;
            for (int j = 0; j < alfabeto.length; j++) {
                if (palabra[i].equals(letras[mapClave[num]][j])) {
                    originalIndex = j;
                    break;
                }
            }
            palabraDescifrada += alfabeto[originalIndex];
            num = (num + 1) % clave.length;
        }

        System.out.println("La palabra descifrada es: " + palabraDescifrada);
    }
}
