public class Converte {

    
  
    public static String converteDecimalParaBinario(int valor) {
        int resto = -1;
        StringBuilder sb = new StringBuilder();
      
        if (valor == 0) {
           return "0";
        }
      
        // enquanto o resultado da divisão por 2 for maior que 0 adiciona o resto ao início da String de retorno
        while (valor > 0) {
           resto = valor % 2;
           valor = valor / 2;
           sb.insert(0, resto);
        }
      
        return sb.toString();
    }
    
    public static String converteDecimalParaHexadecimal(int valor) {
        char[] hexa = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int resto = -1;
        StringBuilder sb = new StringBuilder();
      
        if (valor == 0) {
           return "0";
        }
      
        // enquanto o resultado da divisão por 16 for maior que 0 adiciona o resto ao início da String de retorno
        while (valor > 0) {
           resto = valor % 16;
           valor = valor / 16;
           sb.insert(0, hexa[resto]);
        }
      
        return sb.toString();
    }

    public static int converteBinarioParaDecimal(String valorBinario) {
        int valor = 0;
      
        // soma ao valor final o dígito binário da posição * 2 elevado ao contador da posição (começa em 0)
        for (int i=valorBinario.length(); i>0; i--) {
           valor += Integer.parseInt(valorBinario.charAt(i-1)+"")*Math.pow(2, (valorBinario.length()-i));
        }
      
        return valor;
    }

    public static String converteBinarioParaHexadecimal(String binario) {
        char[] hexa = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder();
        int posicaoInicial = 0;
        int posicaoFinal = 0;
        char caractereEncontrado = '-';
      
        // começa a pegar grupos de 4 dígitos da direita para a esquerda, por isso posicaoInicial e posicaoFinal ficam na posição de fim da String
        posicaoInicial = binario.length();
        posicaoFinal = posicaoInicial;
      
        while (posicaoInicial > 0) {
           // pega de 4 em 4 caracteres da direita para a esquerda. Se o último grupo à esquerda tiver menos de 4 caracteres, pega os restantes (1, 2 ou 3)
           posicaoInicial = ((posicaoInicial - 4) >= 0) ? posicaoInicial - 4 : 0;
      
           /*
           *  Transforma o grupo de 4 caracteres em um dígito hexadecimal. Primeiro converte o grupo de 4 (ou menos) caracteres em decimal e depois pega
           *  o caractere correspondente no array de hexadecimais. Utilize o método converteBinarioParaDecimal(String) mais acima.
           */
           caractereEncontrado = hexa[converteBinarioParaDecimal(binario.substring(posicaoInicial, posicaoFinal))];
           // insere no início da String de retorno
           sb.insert(0, caractereEncontrado);
      
           posicaoFinal = posicaoInicial;
        }
      
        return sb.toString();
    }

    public static String converteHexadecimalParaBinario(String valorHexa) {
        int posicao = 0;
        int posicaoArrayHexa = -1;
        StringBuilder sb = new StringBuilder();
        String valorConvertidoParaBinario = null;
        char[] hexa = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      
        // enquanto tem caracteres em hexa para converter
        while (posicao < valorHexa.length()) {
           // pega a posição do caractere no array de caracteres hexadecimais
           posicaoArrayHexa = ARRAYS2.binarySearch(hexa, valorHexa.charAt(posicao));
           // pega o valor em decimal (correspondente à posição do caractere no array de hexadecimais) e converte para binário
           valorConvertidoParaBinario = converteDecimalParaBinario(posicaoArrayHexa);
           /*
           *  Se o valor convertido para binário tem menos de 4 dígitos, complementa o valor convertido com '0' à esquerda até
           *  ficar com 4 dígitos, se não for o caractere mais à esquerda do valor em hexadecimal
           */
           if (valorConvertidoParaBinario.length() != 4 && posicao > 0) {
              for (int i=0; i < (4-valorConvertidoParaBinario.length()); i++) {
                 sb.append("0");
              }
           }
      
           sb.append(valorConvertidoParaBinario);
           posicao++;
        }
        return sb.toString();
    }

    public static int converteHexadecimalParaDecimal(String valorHexa) {
        int valor = 0;
        int posicaoCaractere = -1;
        char[] hexa = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      
        // soma ao valor final o dígito binário da posição * 16 elevado ao contador da posição (começa em 0)
        for (int i=valorHexa.length(); i>0; i--) {
           posicaoCaractere = ARRAYS2.binarySearch(hexa, valorHexa.charAt(i-1));
           valor += posicaoCaractere*Math.pow(16, (valorHexa.length()-i));
        }
      
        return valor;
    }
}
