file = open('input.txt', 'r')
#file = open('example.txt', 'r')
Lines = file.readlines()
total_sum = 0

for line in Lines:
    left_part = line[line.find(":") + 2:line.find("|")]
    right_part = line[line.find("|")+1:]
    winningNumbers = left_part.split()
    restNumbers = right_part.split() # 0 1 2 4 8 16
    isZeroFlag = True
    point = 1
    for number in restNumbers:
        if number in winningNumbers:
            if not isZeroFlag:
                point *= 2
            isZeroFlag = False
    if not isZeroFlag:
        #print(point)
        total_sum += point
print(total_sum)



