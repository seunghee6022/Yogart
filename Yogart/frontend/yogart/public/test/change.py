# f = open('json.js', 'rt', encoding='UTF8')
# lines = f.readlines()
# count = 0
# for line in lines:
#     if '{' in line:
#         print(line)
# f.close()

import fileinput
import sys
f = open('json.js', 'r', encoding='UTF8')
nf = open('json_new.js', 'w', encoding='UTF8')
lines = f.readlines()
cnt = 0
for line in lines:
    if 'id:' in line:
        new_line ='\tid: '+str(cnt)+', \n'
        nf.write(new_line)
        cnt += 1
    else:
        nf.write(line)
f.close()
nf.close()


    # sys.stdout.write(line)



