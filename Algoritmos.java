
import java.util.Arrays;

public class Algoritmos {    
   
    private static int cont=0;
    
    // Metodo main  
    public static void main(String args[]) {
        for (int i=10; i<400; i=i+10) {
            // Cria e inicializa o vetor com os valores do maior para o menor
            int vet[] = new int[i];
            for(int j=0; j<vet.length; j++)
                vet[vet.length-1-j] = j;
            
            cont=0;            
            // Alterne entre as chamadas dos algoritmos de classificacao
            // bubblesort(vet);
            // quickSort(vet); 
            // insertionsort(vet);
             mergeSort(vet,0,vet.length-1);
            
           // Imprime a contagem de operacoes 
           System.out.println(i+";"+cont);
        }
    }      
    
    ///////////////////////////////////////////////////////////
    public static void bubblesort(int vet[]) {
        boolean troca;
        for (int i = 0; i < vet.length; i++) {
            troca = false;
            for (int j = 0; j < vet.length - 1; j++) {
                if (vet[j] > vet[j + 1]) {
                    troca = true;
                    int aux = vet[j];
                    vet[j] = vet[j + 1];
                    vet[j + 1] = aux;
                }
                cont++;
            }
            if (!troca)break;
        }                
    }  
    
    ///////////////////////////////////////////////////////////
    public static void insertionsort(int vet[]) {               
        int n = vet.length;
        for (int k = 1; k < n; k++) { // começa com o segundo elemento
            int cur = vet[k]; // inserir cur=vet[k]
            int j = k; // para achar índice correto para cur
            while ((j > 0) && (vet[j - 1] > cur)) { // data[j-1] deve vir depois de cur
                vet[j] = vet[j - 1];
                j--;
                cont++;
            }
            vet[j] = cur; // lugar correto de cur
        }               
    }   
       
    ///////////////////////////////////////////////////////////
    public static void mergeSort(int[] array, int inicio, int fim) {
        if (fim <= inicio)
            return;
        int meio = (inicio + fim) / 2;
        mergeSort(array, inicio, meio);
        mergeSort(array, meio + 1, fim);
        int[] A = new int[meio - inicio + 1];
        int[] B = new int[fim - meio];
        for (int i = 0; i <= meio - inicio; i++) {
            A[i] = array[inicio + i];  cont++;
        }
        for (int i = 0; i <= fim - meio - 1; i++) {
            B[i] = array[meio + 1 + i];  cont++;
        }
        int i = 0, j = 0;
        for (int k = inicio; k <= fim; k++) {
            cont++;
            if (i < A.length && j < B.length) {
                if (A[i] < B[j]) 
                    array[k] = A[i++];
                else 
                    array[k] = B[j++];
            } else if (i < A.length) {
                array[k] = A[i++];
            } else if (j < B.length) {
                array[k] = B[j++];
            }
        }
    }
    
    public static void mergeSort2(int[] vet) {
        int n = vet.length;
        if (n < 2) {
            return; // vetor já ordenado (apenas um elemento)
        }
        // Divide em dois (S1 e S2)
        int mid = n / 2;
        int[] S1 = Arrays.copyOfRange(vet, 0, mid); // copia primeira metade
        int[] S2 = Arrays.copyOfRange(vet, mid, n); // copia segunda metade
        cont++;
        // Conquista(com recursão)
        mergeSort2(S1); // ordena a cópia da primeira metade
        mergeSort2(S2); // ordena a cópia da segunda metade

        // Junta os resultados
        merge(S1, S2, vet);
    }
    
    private static void merge(int[] S1, int[] S2, int[] vet) {
        int i = 0, j = 0;
        while ((i + j) < vet.length) {
            cont++;
            if (j == S2.length || (i < S1.length && S1[i] < S2[j])) {
                vet[i + j] = S1[i++]; // copia i-esimo elemento de S1 e incrementa i
            } else {
                vet[i + j] = S2[j++]; // copia j-esimo element of S2 e incrementa j
            }
        }
    }           
    
    ///////////////////////////////////////////////////////////
    public static void quickSort(int[] vet) {
        quickSort(vet, 0, vet.length - 1);
    }
    private static void quickSort(int vet[], int low, int high) {
        int i, j, pivot, aux;
        i = low;
        j = high;
        pivot = vet[(low + high) / 2]; cont++;

        while (i <= j) {
            while (vet[i] < pivot && i < high) {
                i++; cont++;
            }
            while (vet[j] > pivot && j > low) {
                j--; cont++;
            }
            if (i <= j) {
                aux = vet[i];
                vet[i] = vet[j];
                vet[j] = aux;
                i++;
                j--;
            }
        }
        if (j > low) {
            quickSort(vet, low, j);
        }
        if (i < high) {
            quickSort(vet, i, high);
        }
    }            
        
}