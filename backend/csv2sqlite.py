import pandas as pd
import sqlite3

df = pd.read_csv('q_a.csv')
df.index.name = 'id'
df = df.reset_index()
df['hint'] = ''

df = df.rename(columns={'Question': 'question',
                        'ans1': 'answer1',
                        'ans2': 'answer2',
                        'ans3': 'answer3',
                        'ans4': 'answer4',
                        })

table_scheme = ['question', 'answer1', 'answer2', 'answer3', 'answer4',
                'correct', 'id', 'info', 'hint']

df = df[table_scheme]
with sqlite3.connect('backend-modul/quiz.sqlite') as con:
    entries = [tuple(x) for x in df.values]
    query = f"INSERT INTO qa VALUES ({', '.join('?' * len(df.columns))})"
    con.executemany(query, entries)
