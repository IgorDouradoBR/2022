import java.util.TreeMap;

public class Iterativo {
    static TreeMap<String, Long> arvore;

    public static void main(String args[]) {
        String entrada = "111001111111111111001111011111110111001110110111";
        boolean maiorPulo = false;
        long resultado = 1;
        long resultadoAux=0;
        long entradaA = 0;
        long entradaB = 0;
        long saida = 0;
        String entradaaux= "";
        long saida2 = 0;
        long resultadoFinal=0;
        int entradaLen = entrada.length();
        arvore = new TreeMap<>();
        arvore.put(0 + ("" + maiorPulo), resultado);
        for (int i = 0; i < entradaLen; i++) {
            entradaA = 0;
            entradaB = 0;
            saida = 0;
            saida2 = 0;
            if (!(i <= 3)) {
                if (entrada.charAt(i - 3) == '1') {
                    arvore.put(i + "" + (!maiorPulo), arvore.get(i - 3 + "" + (maiorPulo)));
                }
            }
            if (i < entradaLen) {
                if (entrada.charAt(i) == '1') {
                    if (arvore.containsKey(i - 1 + ""+(!maiorPulo))&& entrada.charAt(i - 1) == '1') {saida = arvore.get(i - 1 + ""+(!maiorPulo))+saida;}
                    if (arvore.containsKey((i - 2) + ""+(!maiorPulo))&& entrada.charAt(i - 2) == '1') {saida2 += arvore.get((i - 2) + ""+(!maiorPulo))+saida2;}
                    if (arvore.containsKey(i - 2 + ""+(maiorPulo)) && entrada.charAt(i - 2) == '1') {entradaB= arvore.get(i - 2 + ""+(maiorPulo))+entradaB;}
                    if (arvore.containsKey(i - 1 + ("" + maiorPulo)) && entrada.charAt(i - 1) == '1') {    entradaA = arvore.get(i - 1 + "" + (maiorPulo))+entradaA;}
                    resultado = saida + saida2 + entradaA + entradaB;
                    if (i != 0) {
                        if(entrada.charAt(i) == '1'){arvore.put(i + ""+(maiorPulo), resultado);}
                    }
                    if (i == entradaLen - 1 && arvore.containsKey(i - 3 + ""+(maiorPulo))) {
                        resultado = arvore.get((i - 3) + ""+(maiorPulo))+resultado;
                    }
                }
            }
        }
        System.out.println("Saída: "+ resultado);
    }
}
