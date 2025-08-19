import json
import firebase_admin
from firebase_admin import credentials, firestore

# Path to your Firebase service account key
SERVICE_ACCOUNT = "smart-harvest-config.json"

# Path to your JSON file (array format)
INPUT_FILE = "wholesale_forecast_30days.json"

# Firestore path
PARENT_COLLECTION = "PricePrediction"
PARENT_DOC = "YYZmCD6FuM6YroJSRZtx"
SUBCOLLECTION = "next30days"


def upload_to_firestore():
    # Initialize Firebase app
    cred = credentials.Certificate(SERVICE_ACCOUNT)
    firebase_admin.initialize_app(cred)
    db = firestore.client()

    # Load JSON array
    with open(INPUT_FILE, "r") as f:
        data = json.load(f)

    # Upload each item in the array as a new document (auto ID)
    for entry in data:
        doc_ref = db.collection(PARENT_COLLECTION).document(PARENT_DOC).collection(SUBCOLLECTION).document()
        doc_ref.set(entry)
        print(f"Uploaded document with ID: {doc_ref.id}")

    print("Upload completed!")


if __name__ == "__main__":
    upload_to_firestore()
