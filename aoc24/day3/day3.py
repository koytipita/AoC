file = open('input.txt', 'r')
#file = open('example.txt', 'r')
Lines = file.readlines()
total_sum = 0

signList = []
dotList = []
digits = []
numbers = set()
y=0

def checkNeighborDigits(sign, dotList):
    neighborDigitList = []
    if (sign[0]-1,sign[1]-1) not in dotList:
        neighborDigitList.append((sign[0]-1,sign[1]-1))
    if (sign[0] , sign[1] - 1) not in dotList:
        neighborDigitList.append((sign[0], sign[1] - 1))
    if (sign[0] + 1, sign[1] - 1) not in dotList:
        neighborDigitList.append((sign[0] + 1, sign[1] - 1))
    if (sign[0] - 1, sign[1]) not in dotList:
        neighborDigitList.append((sign[0] - 1, sign[1]))
    if (sign[0] + 1, sign[1]) not in dotList:
        neighborDigitList.append((sign[0] + 1, sign[1]))
    if (sign[0] - 1, sign[1] + 1) not in dotList:
        neighborDigitList.append((sign[0] - 1, sign[1] + 1))
    if (sign[0] , sign[1] + 1) not in dotList:
        neighborDigitList.append((sign[0], sign[1] + 1))
    if (sign[0] + 1, sign[1] + 1) not in dotList:
        neighborDigitList.append((sign[0] + 1, sign[1] + 1))
    return neighborDigitList

'''
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
'''
def parseNumbers(neihborDigitList):
    numbers = set()
    y=0
    for line in Lines:
        x = 0
        for indice in range(len(line)-1):
            if (x,y) in neighborDigitList:
                number = 0
                digits = 0
                if line[x-2].isdigit():
                    number += int(line[x-2]) * 100
                    digits += 1
                if line[x-1].isdigit():
                    number += int(line[x-1]) * 10
                    digits += 1
                else:
                    number = 0
                    digits = 0
                if line[x].isdigit():
                    number += int(line[x])
                    digits+=1
                if line[x+1].isdigit() and digits < 3:
                    number *= 10
                    number += int(line[x+1])
                    digits += 1
                if line[x+2].isdigit() and digits < 3:
                    number *= 10
                    number += int(line[x+2])
                numbers.add(number)
            x += 1
        y += 1
    return numbers


for line in Lines:
    x=0
    for char in line:
        if not(char.isdigit()) and char != "." and x != len(line)-1:
            signList.append((x,y))
        if char == ".":
            dotList.append((x,y))
        x += 1
    y += 1

for sign in signList:
    neighborDigitList = (checkNeighborDigits(sign, dotList))
    numbers = numbers.union(parseNumbers(neighborDigitList))
print(sum(numbers))

