# wholesale_lstm_30days_to_json.py

import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
import tensorflow as tf
from tensorflow.keras.models import Sequential, load_model
from tensorflow.keras.layers import LSTM, Dense
from tensorflow.keras.losses import MeanSquaredError
from datetime import datetime, timedelta
import json

# ---------------- 1. Load CSV Data ----------------
df = pd.read_csv("wholesale_prices_ml.csv")
df["date"] = pd.to_datetime(df["date"])
df = df.sort_values("date").reset_index(drop=True)

# ---------------- 2. Normalize Prices ----------------
prices = df[["Pettah", "Dambulla"]].values
scaler = MinMaxScaler()
prices_scaled = scaler.fit_transform(prices)

# ---------------- 3. Prepare Sequences ----------------
look_back = 5
X, y = [], []
for i in range(len(prices_scaled) - look_back):
    X.append(prices_scaled[i:i+look_back])
    y.append(prices_scaled[i+look_back])
X, y = np.array(X), np.array(y)

# ---------------- 4. Load or Train Model ----------------
try:
    model = load_model("wholesale_lstm_model.h5", compile=False)
    print("Loaded existing model.")
except:
    print("Training new model...")
    model = Sequential()
    model.add(LSTM(64, activation='relu', input_shape=(look_back, 2)))
    model.add(Dense(2))
    model.compile(optimizer='adam', loss=MeanSquaredError())
    model.fit(X, y, epochs=50, batch_size=8, validation_split=0.1, verbose=1)
    model.save("wholesale_lstm_model.h5")
    print("Model saved as wholesale_lstm_model.h5")

# ---------------- 5. Convert to TFLite ----------------
converter = tf.lite.TFLiteConverter.from_keras_model(model)
converter.target_spec.supported_ops = [
    tf.lite.OpsSet.TFLITE_BUILTINS,
    tf.lite.OpsSet.SELECT_TF_OPS
]
tflite_model = converter.convert()
with open("wholesale_lstm_model.tflite", "wb") as f:
    f.write(tflite_model)
print("TFLite model saved as wholesale_lstm_model.tflite")

# ---------------- 6. Forecast Next 30 Days ----------------
n_days = 30
forecast_scaled = []
last_sequence = prices_scaled[-look_back:].copy()

for _ in range(n_days):
    input_seq = last_sequence.reshape((1, look_back, 2))
    pred_scaled = model.predict(input_seq, verbose=0)
    forecast_scaled.append(pred_scaled[0])
    last_sequence = np.vstack([last_sequence[1:], pred_scaled])

# ---------------- 7. Inverse transform and clip to min 100 ----------------
forecast = scaler.inverse_transform(forecast_scaled)
forecast = np.clip(forecast, 100, None)

# ---------------- 8. Build JSON data ----------------
start_date = datetime.today()
forecast_list = []

for i in range(n_days):
    date_str = (start_date + timedelta(days=i+1)).strftime('%Y-%m-%d')
    forecast_list.append({
        "date": date_str,
        "Pettah": round(float(forecast[i][0]), 2),
        "Dambulla": round(float(forecast[i][1]), 2)
    })

# Save JSON file
with open("wholesale_forecast_30days.json", "w") as f:
    json.dump(forecast_list, f, indent=4)

print("Forecast JSON saved as wholesale_forecast_30days.json")
print(forecast_list[:5])  # preview first 5 entries
