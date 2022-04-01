import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class Controle{

    public static void main (String [] args)  throws IOException{
        Caracterizacao c = new Caracterizacao();
        MontaEstruturaCache(32,2,4,2,"LFU");
    }

    //Numero Bytes da cache,Numero de palavras por bloco,tamanho da palavra e as vias
    //4 bytes, 2 palavras por bloco, 8 bytes de dados por linha e que tenha 32 bytes de cache a partir de 2 vias 

    public static void MontaEstruturaCache (int tamCache,int qtdVias,int tamPalavraBytes,int numPalvraBlocos,String metodo) throws IOException{
        ArrayList<String> enderecos = ConverteBinario32Bits();
        
        ArrayList<String> tagArray = new ArrayList<>();
        ArrayList<String> palavraArray = new ArrayList<>();
        ArrayList<String> seletorConjArray = new ArrayList<>();



        int qntdBytesDadosLinhas = tamPalavraBytes * numPalvraBlocos; //Numero de bytes de dados por linha
        int tamPalavras = tamCache / (tamPalavraBytes * numPalvraBlocos);  //qtd de linhas
        int qntdLinhasCache = tamCache / qntdBytesDadosLinhas;
        int tamConj = qntdLinhasCache / qtdVias;
        numPalvraBlocos = numPalvraBlocos / 2;
        tamConj = tamConj / 2;
        int tamTag = 32 - numPalvraBlocos - tamConj;
        
        for(int i = 0; i < enderecos.size(); i++){
            StringBuilder sb  = new StringBuilder();    

            sb.append(enderecos.get(i).toString());
            String tag  = ""; String seletorConj = ""; String palavra = "";


        
            tag = sb.subSequence(0,tamTag).toString();
            seletorConj = sb.subSequence(tamTag,tamTag + tamConj).toString();
            palavra = sb.subSequence(tamTag + tamConj,tamTag + tamConj + numPalvraBlocos).toString();

            tagArray.add(tag);
            palavraArray.add(palavra);
            seletorConjArray.add(seletorConj);
            // System.out.println("TAG:" + tag +"   " + tag.length());
            //System.out.println("bitConjuntoAssociativo:"+ seletorConj +"   " + seletorConj.length());
            // System.out.println("Palavra:" + palavra +"   " + palavra.length());
            //System.out.println(tagArray.get(i) + "   "  + tag.length());
            //System.out.println(palavraArray.get(i) + "  " + palavra.length());   
            //System.out.println(seletorConjArray.get(i) + "  " + seletorConj.length());     
  
        }

        tamConj = tamConj * 2;
        System.out.println("qntdBytesDadosLinhas:" + qntdBytesDadosLinhas );
        System.out.println("Tamanho da palavras em bytes:" + tamPalavras );
        System.out.println("Qtd linhas cache:" + qntdLinhasCache );
        System.out.println("Numero de Conjuntos associativos: " + tamConj);
        System.out.println("Tag:"  + tagArray.get(3) +"   " +  "Bit Cj Associativo:" + seletorConjArray.get(3) + "   Palavra:"  
                            + palavraArray.get(3));
        // conjuntos associativos criados
                
        ArrayList<LinhaCache> conj = new ArrayList<>();

        for(int i = 0; i < qntdLinhasCache; i ++){
            LinhaCache lc = new LinhaCache(numPalvraBlocos,0);
            conj.add(lc);
        }

        int miss = 0;
        int hit = 0;
        int temptotal = 0;
        int cache1 = 0;
        int cache2 = 0;

        for(int i = 0; i < tagArray.size(); i++){
            for(int j = 0; j < conj.size(); j++){      
                if(containsConj(conj,tagArray.get(i)) == false){  
                      if(conj.get(j).getmemAssociativa() == -1){
                          conj.get(j).setmemAssociativa(Integer.parseInt(tagArray.get(i),2));
                          conj.get(j).somaCountAccess();
                          miss++;
                          break;
                      }
                                 
                }
                else if(containsConj(conj,tagArray.get(i)) == true){
                    conj.get(j).somaCountAccess();
                    hit++;
                    break;
                }                 
            } 
            
            
            boolean cheio = true; 

            for(int j = 0; j < conj.size(); j ++){
                if(conj.get(j).getmemAssociativa() == -1){
                    cheio = false;
                }
            }
            
            
            if(cheio){
                if(containsConj(conj,tagArray.get(i)) == true){
                    

                }
                else{
                    if(metodo.equals("LFU")){
                        int indice = LFU(conj);
                        conj.get(indice).setmemAssociativa(Integer.parseInt(tagArray.get(i),2));
                        miss++;
                    }
                    else{
                        int indice = randomMetod(conj);
                        conj.get(indice).setmemAssociativa(Integer.parseInt(tagArray.get(i),2));
                        miss++;
                    }
                }
            }
          


        }

        System.out.println(hit);
        System.out.println(miss);
    }
 

    public static boolean containsConj(ArrayList<LinhaCache> conj,String tag){

        boolean teste = false;
            for(int x=0; x < conj.size();x++){
                if(conj.get(x).getmemAssociativa() == Integer.parseInt(tag,2)){
                    teste= true;
            } 
        }

        return teste;
    } 


    public static int randomMetod(ArrayList<LinhaCache> conj){
        Random gerador = new Random();   
        int indiceLinha = gerador.nextInt(conj.size());    
        return indiceLinha;
    }

    public static int LFU(ArrayList<LinhaCache> conj){

       int minValue = conj.get(0).getCountAccess();
       int indiceLinha = 0;

       for(int i = 0 ; i < conj.size(); i++ ){

          if(conj.get(i).getCountAccess() <minValue){
              minValue = conj.get(i).getCountAccess();
              indiceLinha = i;
          }
       }

      return indiceLinha;
     
    }

    public static ArrayList<String> ConverteBinario32Bits() throws IOException{
        
        StringBuilder sb  = new StringBuilder();    

        ArrayList<String> enderecos = readCaracterizacao();
    
        for(int i  =0; i < enderecos.size(); i++){
            sb.append(Integer.toBinaryString(Integer.parseInt(enderecos.get(i))));
            enderecos.set(i,sb.toString());
            sb.delete(0, sb.length());
        }

        sb.delete(0, sb.length());
        for(int i =0; i < enderecos.size(); i++){
            sb.append(enderecos.get(i));
            while(sb.length() < 32){
                
                sb.insert(0,"0");
            }
            enderecos.set(i,sb.toString());
            sb.delete(0, sb.length());
        }

       return enderecos;
    }


    public static ArrayList<String> readCaracterizacao() throws IOException{

        FileReader arq = new FileReader("InstrucoesCache.txt");
        BufferedReader lerArq = new BufferedReader(arq);
        ArrayList<String> enderecos = new ArrayList<>();

        String linha = "";

        int count = 0;
        while (linha != null) {
                linha = lerArq.readLine(); 
                enderecos.add(linha);
                count++;

            }

        arq.close();
        enderecos.remove(enderecos.size()-1);
        count--;
        System.out.println(count);
        return enderecos;
    }

    
        
        
        // int maxvalueenderecos = 0;
        // for(int i = 0; i < enderecos.size(); i++){
        //     if(maxvalueenderecos < Integer.parseInt(enderecos.get(i))){
        //         maxvalueenderecos = Integer.parseInt(enderecos.get(i));
        //     }
        // }

        // enderecos.clear();

        // for(int i = 0; i < maxvalueenderecos; i++){
        //     enderecos.add(Integer.toString(i));
        // }

        // System.out.println("MemoriaAssociativa" + conj20[j].getmemAssociativa());
        //  System.out.println("Teste: " + Integer.parseInt(tagArray.get(i),2));
        
        // System.out.println("Miss");
        // System.out.println("TagArray" + Integer.parseInt(tagArray.get(i),2));

        //         List<Integer> checaConjsub = checaConj.subList(k+1, 4);
        //         //System.out.println(checaConjsub.contains(checaConj.get(k)));
}