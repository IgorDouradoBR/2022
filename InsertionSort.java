import java.util.Scanner;
public class InsertionSort {
	

	//Notação O:O(n² ou n^2)
    public static void insertionSort(int[] vetor) {
		int index=0;
		int pos=0;
		int aux= 0;		
		for (pos= 1; pos<vetor.length; pos++)//começa do 1 pq o 0 ja é tratado como ordenado
		{
		  aux = vetor[pos];//para guardar o valor
		  for (index = pos - 1; (vetor[index] > aux) && (index >= 0) ; index--)
		  {
			 vetor[index + 1] = vetor[index];
		   }
			vetor[index + 1] = aux;
		}
	}

	public static void imprime(int[] vetor)
    {
        for(int i=0; i<vetor.length; i++) {
            System.out.print(vetor[i] + " | ");
        }
	}
	
    public static void main(String args[]) {
		Scanner teclado = new Scanner(System.in);
		System.out.println("**Algoritmo ordenador de números de um vetor**");
		System.out.println("Quantos números deseja acrescentar ao seu vetor: ");
		int n= teclado.nextInt();
		int []vetor = new int[n];
		for (int i=0; i<n; i++){
			System.out.println("qual o "+ (i+1)+ "º número desejas adicionar a lista?");
			int num= teclado.nextInt();
			vetor[i]= num;
		}
		insertionSort(vetor);
		System.out.println("Números do vetor ordenador em ordem crescente pelo insertion-sort: ");
		imprime(vetor);
	 
	}
}