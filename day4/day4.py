file = open('input.txt', 'r')
#file = open('example.txt', 'r')
Lines = file.readlines()
sum_of_cards = 0
forward_queue = []

for index, line in enumerate(Lines):
    left_part = line[line.find(":") + 2:line.find("|")]
    right_part = line[line.find("|")+1:]
    winningNumbers = left_part.split()
    restNumbers = right_part.split()
    if len(forward_queue) > 0:
        total_card = 1 + forward_queue[0]
    else:
        total_card = 1
    sum_of_cards += total_card
    forward_queue = forward_queue[1:] # go forward
    match = 0
    for number in restNumbers:
        if number in winningNumbers:
            match += 1
    #print(match)
    #print(total_card)
    for i in range(match):
        if i < len(forward_queue):
            forward_queue[i] += total_card
        else:
            forward_queue.append(total_card)
    #print(forward_queue)

print(sum_of_cards)



