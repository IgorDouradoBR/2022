
//Igor Pereira Dourado
//Professor, dei prosseguimento a ideia que estava lá no repl.it da disciplina, só que em java
import java.util.ArrayList;

public class AliceBackTrad {
    static int encontrou =0;
    public static int subfazsoma(ArrayList<Integer> vetor, int index, int k, int soma, ArrayList<Integer> vetorAux) {
        for (int i = index; i > -1; i--) {
            soma += vetor.get(i);
            vetorAux.add(vetor.get(i));
            if (k == soma) {
                encontrou=1;
                System.out.println("Resultado esperado:" + k);
                System.out.println("Somas:" + soma);
                System.out.print("Lista com os valores somados: ");
                for (int j = 0; j < vetorAux.size(); j++) {
                    System.out.print(vetorAux.get(j) + " , ");
                }
                int somatorio = 0;
                for (int j = 0; j < vetorAux.size(); j++) {
                    somatorio += vetorAux.get(j);
                }
                System.out.println("\nNumero de valores da soma:" + vetorAux.size()
                        + " e o somatório dos valores da lista: " + somatorio);
                System.exit(0);
            } else if (i < 1) {
                if (soma != k) {
                    vetorAux.remove(vetorAux.size() - 1);
                }
            } else if (soma > k) {
                vetorAux.remove(vetorAux.size() - 1);
                soma -= vetor.get(i);
            } else if (soma < k) {
                subfazsoma(vetor, i - 1, k, soma, vetorAux);
                soma -= vetor.get(i);
                vetorAux.remove(vetorAux.size() - 1);
                subfazsoma(vetor, i - 1, k, soma, vetorAux);
            } else {
                vetorAux.remove(vetorAux.size() - 1);
            }

        }
        return 0;
    }

    public static void main(String[] args) {
        ArrayList<Integer> vetor = new ArrayList<Integer>();
        int alvo = Integer.parseInt(args[0]);

        for (int i = 1; i < args.length; i++) {
            vetor.add(Integer.parseInt(args[i]));
        }

        ArrayList<Integer> vazio = new ArrayList<Integer>();
        subfazsoma(vetor, vetor.size() - 1, alvo, 0, vazio);
        if(encontrou==0){
            System.out.println("Não foi achado solução");
        }
    }
}
