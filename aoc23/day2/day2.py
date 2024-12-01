file = open('input.txt', 'r')
#file = open('trial.txt', 'r')
Lines = file.readlines()
total_sum = 0

def isValid(set):
    color_counter = summationRGB(set)
    if color_counter[0] > 12:
        return False
    if color_counter[1] > 13:
        return False
    if color_counter[2] > 14:
        return False
    return True


def summationRGB(set):
    colorCounter = [0, 0, 0]  # r,g,b
    indiceOfGreen = set.index("green") if "green" in set else None
    if indiceOfGreen is not None:
        if set[indiceOfGreen-3:indiceOfGreen-1].isnumeric():
            colorCounter[1] += int(set[indiceOfGreen-3:indiceOfGreen])
        else:
            colorCounter[1] += int(set[indiceOfGreen-2:indiceOfGreen])
    indiceOfRed = set.index("red") if "red" in set else None
    if indiceOfRed is not None:
        if set[indiceOfRed-3:indiceOfRed-1].isnumeric():
            colorCounter[0] += int(set[indiceOfRed-3:indiceOfRed])
        else:
            colorCounter[0] += int(set[indiceOfRed-2:indiceOfRed])
    indiceOfBlue = set.index("blue") if "blue" in set else None
    if indiceOfBlue is not None:
        if set[indiceOfBlue-3:indiceOfBlue-1].isnumeric():
            colorCounter[2] += int(set[indiceOfBlue-3:indiceOfBlue])
        else:
            colorCounter[2] += int(set[indiceOfBlue-2:indiceOfBlue])
    return colorCounter


for line in Lines:
    game_index = int(line[line.index(" "):line.index(":")])
    valid = True
    set_count = line.count(";")
    restPart = line[line.index(":") + 2:]
    firstSet = restPart[:restPart.index(";")]
    if(not(isValid(firstSet))):
        valid = False
    for i in range(set_count - 1):
        restPart = restPart[restPart.index(";") + 2:]
        tempSet = restPart[:restPart.index(";")]
        if (not(isValid(tempSet))):
            valid = False
    lastSet = restPart[restPart.index(";") + 2:]
    if (not (isValid(lastSet))):
        valid = False
    if valid:
        total_sum += game_index
print(total_sum)