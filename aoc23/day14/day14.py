filename = 'input.txt' #'input.txt' 'example.txt'
with open(filename) as f:
    Lines = f.read().splitlines()
    
def process_column(column):
    waiting_O = []
    for index, obj in enumerate(column):
        if obj == 'O':
            waiting_O.append(index)
        elif obj == '#':
            for i in waiting_O:
                column[i] = '.'
            for j in range(len(waiting_O)):
                column[index-j-1] = 'O'
            waiting_O.clear()
    for i in waiting_O:
        column[i] = '.'
    for j in range(len(waiting_O)):
        column[len(column) - j - 1] = 'O'
    waiting_O.clear()
    return column

def parse_to_image(Lines):
    x_dimension_size = len(Lines[0])
    y_dimension_size = len(Lines)
    image = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                image[y_index][x_index] = 'O'
            if obj == '#':
                image[y_index][x_index] = '#'
    return image

def image_from_north(Lines):
    y_dimension_size = len(Lines[0])
    x_dimension_size = len(Lines)
    image = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for x_index, line in enumerate(Lines):
        for y_index, obj in enumerate(line):
            if obj == 'O':
                image[y_dimension_size-y_index-1][x_index] = 'O'
            if obj == '#':
                image[y_dimension_size-y_index-1][x_index] = '#'
    return image

def image_from_south(Lines):
    y_dimension_size = len(Lines[0])
    x_dimension_size = len(Lines)
    image = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for x_index, line in enumerate(Lines):
        for y_index, obj in enumerate(line):
            if obj == 'O':
                image[y_index][x_index] = 'O'
            if obj == '#':
                image[y_index][x_index] = '#'
    return image

def image_from_east(Lines):
    y_dimension_size = len(Lines[0])
    x_dimension_size = len(Lines)
    image = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                image[y_index][x_index] = 'O'
            if obj == '#':
                image[y_index][x_index] = '#'
    return image

def image_from_west(Lines):
    y_dimension_size = len(Lines[0])
    x_dimension_size = len(Lines)
    image = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                image[y_index][x_dimension_size-x_index-1] = 'O'
            if obj == '#':
                image[y_index][x_dimension_size-x_index-1] = '#'
    return image
def parse_for_north(Lines):
    x_dimension_size = len(Lines[0])
    y_dimension_size = len(Lines)
    dish = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                dish[x_index][y_dimension_size - y_index - 1] = 'O'
            if obj == '#':
                dish[x_index][y_dimension_size - y_index - 1] = '#'
    return dish

def parse_for_south(Lines):
    x_dimension_size = len(Lines[0])
    y_dimension_size = len(Lines)
    dish = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                dish[x_index][y_index] = 'O'
            if obj == '#':
                dish[x_index][y_index] = '#'
    return dish

def parse_for_east(Lines):
    x_dimension_size = len(Lines[0])
    y_dimension_size = len(Lines)
    dish = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                dish[y_index][x_index] = 'O'
            if obj == '#':
                dish[y_index][x_index] = '#'
    return dish

def parse_for_west(Lines):
    x_dimension_size = len(Lines[0])
    y_dimension_size = len(Lines)
    dish = [['.'] * y_dimension_size for i in range(x_dimension_size)]
    for y_index, line in enumerate(Lines):
        for x_index, obj in enumerate(line):
            if obj == 'O':
                dish[y_index][x_dimension_size - x_index - 1] = 'O'
            if obj == '#':
                dish[y_index][x_dimension_size - x_index - 1] = '#'
    return dish

def calculate_load(dish):
    total_sum = 0
    for column in dish:
        for index, obj in enumerate(column):
            if obj == 'O':
                total_sum += index + 1
    return total_sum

def cycle(image):
    dish = parse_for_north(image)
    #print("parsed north...")

    for column in dish:
        column = process_column(column)
    #print("processed north...")

    image = image_from_north(dish)
    #print("image after north ... ")
    #for column in image:
        #print(column)

    dish = parse_for_west(image)
    # print("parsed west...")

    for column in dish:
        column = process_column(column)
    # print("processed west ...")

    image = image_from_west(dish)
    # print("image after west ... ")
    # for column in image:
    # print(column)

    dish = parse_for_south(image)
    #print("parsed south...")

    for column in dish:
        column = process_column(column)
    #print("processed south ...")

    image = image_from_south(dish)
    #print("image after south ... ")
    #for column in image:
        #print(column)

    dish = parse_for_east(image)
    # print("parsed east...")

    for column in dish:
        column = process_column(column)
    # print("processed east ...")

    image = image_from_east(dish)
    # print("image after east ... ")
    # for column in image:
    # print(column)

    return image


##print("first parse ... ")
#for column_first in image_first:
    ##print(column_first)

total_sum_set = set()
total_sum = 0
image_first = parse_to_image(Lines)
for i in range(102):
    image_first = cycle(image_first)
    dish = parse_for_north(image_first)
    total_sum = calculate_load(dish)
    print(i, total_sum)
for i in range((1000000000-102) % 38):
    image_first = cycle(image_first)
    dish = parse_for_north(image_first)
    total_sum = calculate_load(dish)
    print(i, total_sum)
    total_sum_set.add(total_sum)

#print(len(total_sum_set))
#print(total_sum_set)
#print(total_sum)


