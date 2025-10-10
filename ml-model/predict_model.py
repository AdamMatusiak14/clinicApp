import joblib
import sys

# 1. Wczytanie wytrenowanego modelu i vectorizera
#vectorizer = joblib.load(r"C:\Users\Admin\Projects\clinicApp\ml-model\models\vectorizer.pkl")
vectorizer, model = joblib.load(r"C:\Users\Admin\Projects\clinicApp\ml-model\models\disease_model.pkl")
#vectorizer, model = joblib.load("disease_model.pkl")

def predict_disease(symptom_text):

   

    # 2. Prosty preprocessing
    symptom_text = symptom_text.lower()
    
    # 3. Vectorization
    X = vectorizer.transform([symptom_text])
    
    # 4. Predykcja
    prediction = model.predict(X)
    return prediction[0]

# Przykład użycia
if __name__ == "__main__":

    symptoms = sys.stdin.read().strip()
   # print("Otrzymany tekst:", symptoms)

    disease = predict_disease(symptoms)
    print(disease)
