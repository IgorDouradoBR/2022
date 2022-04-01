import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintWriter;

public class App {

    public static void main(String[] args) {
        Map<String, Integer> registradores = inicioRegistradores();// valores que ficarão armazenados nos registradores
                                                                   // no decorrer do programa

        String[] instrucaoEdata = new String[30]; // array de 30 posições
        boolean datas = false;
        boolean instrs = false;
        int contdatas = 0;
        int continstrs = 0;
        String currDir = Paths.get("").toAbsolutePath().toString();// ler arquivo
        String nameComplete = currDir + "\\" + "paraCodificar.asm";
        Path path = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            while (sc.hasNext()) { // analisa enquanto houver conteúdo
                String linha = sc.nextLine();
                if (linha.contains(".data")) { // quando acha a palavra .data
                    datas = true;
                    instrs = false;
                } else if (linha.contains(".text")) { // quando acha a palavra .text
                    datas = false;
                    instrs = true;
                } else if (datas == true) { // lê o que está escrito em .data e inclui na posição 20 do array
                    instrucaoEdata[20 + contdatas] = linha;
                    contdatas++; // conta o nº de datas
                } else if (instrs == true) { // lê o que está escrito em .text e pões na 1ª pos do array
                    instrucaoEdata[continstrs] = linha;
                    continstrs++; // conta o nº de instruç
                }

            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        System.out.println("campo de dados: "); // imprime o que foi lido no .data
        for (int i = 0; i < contdatas; i++) {
            System.out.println(instrucaoEdata[20 + i]);
        }
        System.out.println("campo de instruções: "); // imprime o que foi lido no .text
        for (int i = 0; i < continstrs; i++) {
            System.out.println(instrucaoEdata[i]);
        }
        System.out.println("\nPasso a passo com as instruções: ");
        for (int clock = 0; clock < continstrs; clock++) { // vai passar pelas instruções
            String[] splitar = instrucaoEdata[clock].split(" ");
            String[] operadores = splitar[1].split(",");
            System.out.println((clock + 1) + "ª instrução: " + instrucaoEdata[clock]); // instruç na pos do clock
            if (splitar[0].equals("lui")) { // instrução lui
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + " = "
                        + registradores.get(operadores[0])); // mostra o valor original do registrador a ser alterado
                registradores.replace(operadores[0], 20); // valor inicial do .data no vetor
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n"); // valor
                                                                                                                      // depois
                                                                                                                      // da
                                                                                                                      // op
                instrucaoEdata[clock] = instrucaoEdata[clock].replace(operadores[1], "20"); // operação
                System.out.println("Mudança na memória para readequação do código: " + instrucaoEdata[clock]);
                registradores.replace("pc", clock + 2); // arruma os valores do registradores
                printaSinais(1, 0, 1, 0, 0, 0, 0, 0, 00); // imprime sinais de controle
                printaReg(registradores); // imprime os registradores atualizados
            }
            if (splitar[0].equals("ori")) { // instrç ori
                System.out.print("\nRegistrador (memória) que foi mudado =  Antes: " + operadores[0] + " = "
                        + registradores.get(operadores[0])); // mostra o valor original do registrador a ser alterado
                registradores.replace(operadores[0],
                        registradores.get(operadores[1]) + ((Integer.parseInt(operadores[2]) / 4)));
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n"); // valor
                                                                                                                      // depois
                                                                                                                      // da
                                                                                                                      // op
                String[] variav = instrucaoEdata[registradores.get(operadores[1])
                        + (Integer.parseInt(operadores[2]) / 4)].split("\t");
                System.out.println("variavel sendo buscada = " + variav[0] + " " + variav[2]); // imprime a variavel da
                                                                                               // op
                registradores.replace("pc", clock + 2); // arruma os valores do registradores
                printaSinais(1, 0, 1, 0, 0, 0, 0, 0, 00); // imprime sinais de controle
                printaReg(registradores); // imprime os registradores atualizados
            }
            if (splitar[0].equals("lw")) { // instruç lw
                String[] aux1 = operadores[1].split("\\(");
                int deslocam = Integer.parseInt(aux1[0]) / 4;
                String[] aux2 = aux1[1].split("\\)");
                String[] variav = instrucaoEdata[registradores.get(aux2[0]) + deslocam].split("\t");
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + " = "
                        + registradores.get(operadores[0])); // mostra o valor original do registrador a ser alterado
                registradores.replace(operadores[0], Integer.parseInt(variav[2]));
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n"); // valor
                                                                                                                      // depois
                                                                                                                      // da
                                                                                                                      // op
                System.out.println("variavel sendo lida = " + variav[0] + " " + variav[2]); // imprime a variavel da op
                registradores.replace("pc", clock + 2); // arruma os valores do registradores
                printaSinais(1, 0, 1, 0, 0, 1, 0, 0, 00); // imprime sinais de controle
                printaReg(registradores); // imprime os registradores atualizados
            }
            if (splitar[0].equals("addu")) { // instruç addu
                int result = ulaR(0010, operadores[1], operadores[2], registradores);
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + " = "
                        + registradores.get(operadores[0])); // mostra o valor original do registrador a ser alterado
                registradores.replace(operadores[0], result);
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n"); // valor
                                                                                                                      // depois
                                                                                                                      // da
                                                                                                                      // op
                registradores.replace("pc", clock + 2); // arruma os valores do registradores
                printaSinais(1, 1, 0, 0, 0, 0, 0, 0, 10); // imprime sinais de controle
                printaReg(registradores); // imprime os registradores atualizados
            }
            if (splitar[0].equals("sw")) {
                String[] aux1 = operadores[1].split("\\(");
                int deslocam = Integer.parseInt(aux1[0]) / 4;
                String[] aux2 = aux1[1].split("\\)");
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + aux2[0] + " = "
                        + registradores.get(aux2[0]));
                registradores.replace(aux2[0], registradores.get(operadores[0]) + deslocam);
                System.out.print("      Depois: " + aux2[0] + " = " + registradores.get(aux2[0]) + "\n");
                registradores.replace("pc", clock + 2);
                printaSinais(0, -1, 1, 0, 1, -1, 0, 0, 00);
                printaReg(registradores);
            }
            if (splitar[0].equals("beq")) {
                int result = ulaR(0110, operadores[0], operadores[1], registradores);
                if (result == 1) {
                    clock = clock + (Integer.parseInt(operadores[2]));
                    printaSinais(0, -1, 0, 1, 0, -1, 1, 0, 01);
                } else {
                    printaSinais(0, -1, 0, 1, 0, -1, 0, 0, 01);
                }
                registradores.replace("pc", clock + 2);
                printaReg(registradores);
            }
            if (splitar[0].equals("sll")) {
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + "= "
                        + registradores.get(operadores[0]));
                registradores.replace(operadores[0],
                        registradores.get(operadores[1]) << Integer.parseInt(operadores[2]));// valor inicial do .data
                                                                                             // no nosso vetor
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n");
                registradores.replace("pc", clock + 2);
                printaSinais(1, 1, 0, 0, 0, 0, 0, 0, 10);
                printaReg(registradores);
            }
            if (splitar[0].equals("j")) {
                if (operadores[0].contains("0x")) {
                    String auxil = operadores[0].substring(6);
                    int x = Integer.parseInt(auxil, 16);
                    instrucaoEdata[clock] = instrucaoEdata[clock].replace(operadores[0], "" + x);
                    System.out.println("Mudança na memória para readequação do código: " + instrucaoEdata[clock]);
                    clock = (x - 4) / 4;

                } else {
                    int x = Integer.parseInt(operadores[0]);
                    clock = (x - 4) / 4;
                }
                registradores.replace("pc", clock + 2);
                printaSinais(0, -1, -1, -1, 0, -1, 1, 1, -1);
                printaReg(registradores);
            }
            if (splitar[0].equals("slt")) {
                int result = ulaR(0111, operadores[1], operadores[2], registradores);
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + "= "
                        + registradores.get(operadores[0]));
                registradores.replace(operadores[0], result);
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n");
                registradores.replace("pc", clock + 2);
                printaSinais(1, 1, 0, 0, 0, 0, 0, 0, 10);
                printaReg(registradores);
            }
            if (splitar[0].equals("and")) {
                int result = ulaR(0000, operadores[1], operadores[2], registradores);
                System.out.print("\nRegistrador (memória) que foi mudado = Antes: " + operadores[0] + "= "
                        + registradores.get(operadores[0]));
                registradores.replace(operadores[0], result);
                System.out.print("      Depois: " + operadores[0] + " = " + registradores.get(operadores[0]) + "\n");
                registradores.replace("pc", clock + 2);
                printaSinais(1, 1, 0, 0, 0, 0, 0, 0, 10);
                printaReg(registradores);

            }

        }
        System.out.println("campo de dados APÓS a execução das instruções com as readequações: ");
        for (int i = 0; i < contdatas; i++) {
            System.out.println(instrucaoEdata[20 + i]);
        }
        System.out.println("campo de instruções APÓS a execução das instruções com as readequações: ");
        for (int i = 0; i < continstrs; i++) {
            System.out.println(instrucaoEdata[i]);
        }

    }

    public static void printaReg(Map<String, Integer> registradores) {
        System.out.println("\nTodos os registradores até o momento dessa instrução: ");
        for (int i = 0; i < 32; i++) {
            String aux = "$".concat("" + i);
            System.out.print("(" + aux + " | " + registradores.get(aux) + ") ");
        }
        System.out.print("(pc | " + registradores.get("pc") + ") ");
        System.out.print("(hi | " + registradores.get("hi") + ") ");
        System.out.print("(lo | " + registradores.get("lo") + ") ");
        System.out.println("\n");

    }

    public static void printaSinais(int regWrt, int regDst, int aluSrc, int branch, int memWrt, int memToReg, int zer,
            int jump, int aluOp) {
        System.out.println("Sinais de controle para a instrução: ");
        System.out.println("regWrite | regDst | aluSrc | branch | memWrite | memToReg | pcSrc | jump | aluOp");
        System.out.println("   " + regWrt + "     |   " + regDst + "    |   " + aluSrc + "    |   " + branch
                + "    |    " + memWrt + "     |    " + memToReg + "     |   " + (zer & branch) + "   |  " + jump
                + "   |  " + aluOp + "   ");
    }

    public static int ulaR(int oper, String reg1, String reg2, Map<String, Integer> registradores) {
        if (oper == 0010) {// addu
            return registradores.get(reg1) + registradores.get(reg2);
        }
        if (oper == 0111) {// slt
            if (registradores.get(reg1) < registradores.get(reg2)) {
                return 1;
            } else {
                return 0;
            }
        }
        if (oper == 0000) {// and
            return registradores.get(reg1) & registradores.get(reg2);
        }
        if (oper == 0110) {// beq
            if (registradores.get(reg1) == registradores.get(reg2)) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    // incia os registradores
    public static Map<String, Integer> inicioRegistradores() {
        Map<String, Integer> registradores = new HashMap<String, Integer>();
        registradores.put("$0", 0);
        registradores.put("$1", 0);
        registradores.put("$2", 0);
        registradores.put("$3", 0);
        registradores.put("$4", 0);
        registradores.put("$5", 0);
        registradores.put("$6", 0);
        registradores.put("$7", 0);
        registradores.put("$8", 0);
        registradores.put("$9", 0);
        registradores.put("$10", 0);
        registradores.put("$11", 0);
        registradores.put("$12", 0);
        registradores.put("$13", 0);
        registradores.put("$14", 0);
        registradores.put("$15", 0);
        registradores.put("$16", 0);
        registradores.put("$17", 0);
        registradores.put("$18", 0);
        registradores.put("$19", 0);
        registradores.put("$20", 0);
        registradores.put("$21", 0);
        registradores.put("$22", 0);
        registradores.put("$23", 0);
        registradores.put("$24", 0);
        registradores.put("$25", 0);
        registradores.put("$26", 0);
        registradores.put("$27", 0);
        registradores.put("$28", 268468224);
        registradores.put("$29", 2147479548);
        registradores.put("$30", 0);
        registradores.put("$31", 0);
        registradores.put("pc", 0x00400000);
        registradores.put("hi", 0);
        registradores.put("lo", 0);
        return registradores;

    }
}
