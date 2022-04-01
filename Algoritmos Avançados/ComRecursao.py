encontrou = 0
def subfazsoma( L, k, index, soma, vetor ) :

    for i in range(index, -1, -1):
      vetor.append(L[i])
      soma += L[i]
      if soma == k:
        encontrou=1
        print ("Quantia possuída e soma: ", k)
        print ("Valores que vão ser somados: ", vetor)
        print ("número de elementos na vetor:", len(vetor)," e a soma deles: ", sum(vetor))
        exit()

      elif  i < 1:
          if soma !=k:
            vetor.pop(len(vetor)-1)
      elif soma > k:
        vetor.pop(len(vetor)-1)
        soma -= L[i]
      elif soma < k:
          maper = {}
          maper[(i,soma)] = soma
          if soma in maper:
            return soma
          else:
            subfazsoma( L, k, i-1, soma, vetor )
            soma -= L[i]
            vetor.pop(len(vetor)-1)
            subfazsoma( L, k, i-1, soma, vetor )
      else:
          vetor.pop(len(vetor)-1)




vetor=[145, 35, 67, 8, 3, 58, 101, 54, 32, 217, 88, 22]
#vetor = [47, 3, 3, 5, 11, 11, 11, 11, 11, 18, 18, 23]
soma = vetor[0]
vetor.pop(0)
vetoraux=[]
subfazsoma(sorted(vetor), soma, len(vetor)-1, 0,vetoraux)
if encontrou==0:
    print("Não foi achado solução")
