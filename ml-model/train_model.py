import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report
import joblib

# 1. Wczytanie danych
data = pd.read_csv(r"C:\Users\Admin\Projects\clinicApp\ml-model\data\plik_tlumaczony.csv")  # kolumny: 'symptoms', 'disease'

# 2. Preprocessing (prosty)
data['text_pl'] = data['text_pl'].str.lower().str.replace('[^a-zA-Z0-9 ]', '', regex=True)

# 3. Vectorization
vectorizer = TfidfVectorizer()
X = vectorizer.fit_transform(data['text_pl'])
y = data['label_pl']

# 4. Podział na train/test
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 5. Trenowanie modelu
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# 6. Ewaluacja
y_pred = model.predict(X_test)
print(classification_report(y_test, y_pred))

# 7. Zapis modelu i vectorizera
joblib.dump((vectorizer, model), "models\disease_model.pkl") 

