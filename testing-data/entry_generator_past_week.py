import os
import pandas as pd
import random
import datetime
from collections import OrderedDict
from operator import itemgetter

raw_df = pd.read_csv("food.csv")
fo = open("month_entry_partial_month_data.txt", "w",encoding='utf-8')
frequency = dict()


def generate_random_entry():
    total_calorie = 0
    global frequency
    outputList = []
    start = datetime.date(2021,11,1)
    end = datetime.date(2021, 11, 5)
    time_between_dates = (end - start).days
 
    for i in range(0, time_between_dates):
        date = start + datetime.timedelta(days=i) 

        breakfast = raw_df.loc[raw_df['calorie'] <= 400].sample()
        mealType = 'Breakfast'
        food = breakfast['food'].values[0]
        category = breakfast['category'].values[0]
        calorie = breakfast['calorie'].values[0]
        total_calorie += calorie
        if food in frequency:
            frequency[food] += 1
        else:
            frequency[food] = 1
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))
        
        
        lunch = raw_df.loc[raw_df['calorie'] >= 300].sample()
        mealType = 'Lunch'
        food = lunch['food'].values[0]
        if food in frequency:
            frequency[food] += 1
        else:
            frequency[food] = 1
        category = lunch['category'].values[0]
        calorie = lunch['calorie'].values[0]
        total_calorie += calorie
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))

        
        dinner = raw_df.loc[raw_df['calorie'] >= 300].sample()
        mealType = 'Dinner'
        food = dinner['food'].values[0]
        if food in frequency:
            frequency[food] += 1
        else:
            frequency[food] = 1
        category = dinner['category'].values[0]
        calorie = dinner['calorie'].values[0]
        total_calorie += calorie
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))


        snack_num = random.randint(0, 3)
        for j in range(0, snack_num):
            snack = raw_df.loc[raw_df['calorie'] <= 300].sample()
            mealType = 'Snack'
            food = snack['food'].values[0]
            if food in frequency:
                frequency[food] += 1
            else:
                frequency[food] = 1
            category = snack['category'].values[0]
            calorie = snack['calorie'].values[0]
            total_calorie += calorie
            outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))
            
    return "\n".join(outputList), total_calorie // time_between_dates


entryList, average = generate_random_entry()
fo.write(entryList)
fo.close()
print(OrderedDict(sorted(frequency.items(), key=itemgetter(1))))
print(average)

