import random

lista2 = []
lista2.append(503894)
for x in range(10000):
  lista2.append(random.randint(15000, 20000))
print("oi")
lista = [123,  45, 5, 6,  78, 8, 8, 8, 7, 5, 43, 21]
listabin = [2000, 8, 256, 4, 32, 1024, 16, 128, 1, 64, 2, 512]
li = [10, 2, 8,4,5,2,52,1,2,4]

def GetValues(list):
    tail = list[1:]
    head = list[0]
    return (head, tail)

mem = {}
def CheckValues(head, tail):
    if sum(tail) == head: return tail
    for x in range(0,len(tail)):
      aux = []
      soma = 0
      for y in range(x, len(tail)):
        if y != x:
          aux.append(tail[y])
        soma = sum(aux)
        if soma >= head:
          result = CheckValues(head, aux)
          if result != None:
            return result

list = GetValues((lista2))
head = list[0]
tail = list[1]

print(CheckValues(head, tail))