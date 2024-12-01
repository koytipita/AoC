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

def getMins(set,min_colors):
    color_counter = summationRGB(set)
    min_colors[0] = max(min_colors[0],color_counter[0])
    min_colors[1] = max(min_colors[1], color_counter[1])
    min_colors[2] = max(min_colors[2], color_counter[2])
    return min_colors

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
    min_colors = [0,0,0]
    set_count = line.count(";")
    restPart = line[line.index(":") + 2:]
    firstSet = restPart[:restPart.index(";")]
    min_colors = getMins(firstSet,min_colors)
    for i in range(set_count - 1):
        restPart = restPart[restPart.index(";") + 2:]
        tempSet = restPart[:restPart.index(";")]
        min_colors = getMins(tempSet,min_colors)
    lastSet = restPart[restPart.index(";") + 2:]
    min_colors = getMins(lastSet,min_colors)
    total_sum += min_colors[0]*min_colors[1]*min_colors[2]
print(total_sum)