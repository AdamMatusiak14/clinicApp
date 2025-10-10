import pandas as pd
from deep_translator import GoogleTranslator
from tqdm import tqdm
import time
tqdm.pandas()

df = pd.read_csv("data/Symptom2Disease.csv")
batch_size = 2

df['label'] = df['label'].progress_apply(lambda x: GoogleTranslator(source='en', target='pl').translate(x))

translated_texts = []

for start in tqdm(range(0, len(df), batch_size)):
    batch = df['text'][start:start+batch_size]
    for i, text in batch.items():  # <-- poprawka tutaj
        if pd.isna(text) or text.strip() == "":
            translated_texts.append(text)
            print(f"Wiersz {i} jest pusty, pomijam")
        else:
            try:
                translated_texts.append(GoogleTranslator(source='en', target='pl').translate(text))
            except Exception:
                translated_texts.append(text)
                print(f"Błąd w wierszu {i}")
    time.sleep(0.5)

df['text'] = translated_texts  # zmieniłem nazwę kolumny na 'text_pl'

df = df.rename(columns={'label':'label_pl', 'text':'text_pl'})

df.to_csv("plik_tlumaczony.csv", index=False)
