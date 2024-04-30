file1 = open('trial', 'r')
file2 = open('input', 'r')
Lines = file2.readlines()
total_sum = 0
digits = {'one': 1, 'two': 2, 'three': 3, 'four': 4, 'five': 5, 'six': 6, 'seven': 7, 'eight': 8, 'nine': 9}
for line in Lines:
    first_digit = (0,9999)
    last_digit = (0,-1)
    index = 0
    first_flag_set = False
    temp_sum = 0
    for letter in line:
        if letter.isdigit() and int(letter) != 0:
            if not first_flag_set:
                first_flag_set = True
                first_digit = (int(letter),index)
                last_digit = (int(letter),index)
            else:
                last_digit = (int(letter),index)
        index += 1
    first_index = 9999
    first_index_digit = 0
    last_index = -1
    last_index_digit = 0
    for key in digits.keys():
        if key in line:
            if line.find(key) < first_index:
                first_index = line.find(key)
                first_index_digit = digits[key]
            if len(line) - len(key) - (line[::-1].find(key[::-1])) > last_index:
                last_index = len(line) - len(key) - (line[::-1].find(key[::-1]))
                last_index_digit = digits[key]
    if first_index < first_digit[1]:
        temp_sum += first_index_digit * 10
    else:
        temp_sum += first_digit[0] * 10
    if not(last_index == -1 and last_digit[1] == -1):
        if last_index < last_digit[1]:
            temp_sum += last_digit[0]
        else:
            temp_sum += last_index_digit
    else:
        if first_index < first_digit[1]:
            temp_sum += first_index_digit
        else:
            temp_sum += first_digit[0]
    total_sum += temp_sum

print(total_sum)
