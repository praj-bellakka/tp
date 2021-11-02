import os
import pandas as pd
import random
import datetime

raw_df = pd.read_csv("food.csv")
fo = open("entry.txt", "w",encoding='utf-8')


def generate_random_entry():
    outputList = []
    start = datetime.date(2021,9,1)
    end = datetime.date(2021, 10, 31)
    time_between_dates = (end - start).days
 
    for i in range(0, time_between_dates):
        date = start + datetime.timedelta(days=i) 

        breakfast = raw_df.loc[raw_df['calorie'] <= 400].sample()
        mealType = 'Breakfast'
        food = breakfast['food'].values[0]
        category = breakfast['category'].values[0]
        calorie = breakfast['calorie'].values[0]
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))
        
        
        lunch = raw_df.loc[raw_df['calorie'] >= 300].sample()
        mealType = 'Lunch'
        food = lunch['food'].values[0]
        category = lunch['category'].values[0]
        calorie = lunch['calorie'].values[0]
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))

        
        dinner = raw_df.loc[raw_df['calorie'] >= 300].sample()
        mealType = 'Dinner'
        food = dinner['food'].values[0]
        category = dinner['category'].values[0]
        calorie = dinner['calorie'].values[0]
        outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))


        snack_num = random.randint(0, 3)
        for j in range(0, snack_num):
            snack = raw_df.loc[raw_df['calorie'] <= 300].sample()
            mealType = 'Snack'
            food = snack['food'].values[0]
            category = snack['category'].values[0]
            calorie = snack['calorie'].values[0]
            outputList.append("{} | {} | {} | {} | {}".format(mealType, food, str(calorie), str(date), category))
            
    return "\n".join(outputList)


entryList = generate_random_entry()
fo.write(entryList)
fo.close()

