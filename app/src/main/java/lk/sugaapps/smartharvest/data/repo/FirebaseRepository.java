package lk.sugaapps.smartharvest.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Singleton;

import jakarta.inject.Inject;
import lk.sugaapps.smartharvest.data.model.CropHandBookModel;
import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.remote.model.VegetableResponse;

@Singleton
public class FirebaseRepository {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firestore) {
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    public LiveData<Resource<UserModel>> getUserDetailsFormFireStore() {
        MutableLiveData<Resource<UserModel>> result = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        result.setValue(Resource.loading(null));
        if (firebaseUser == null) {
            result.setValue(Resource.error("User not authenticated", null,401));
            return result;
        }

        result.setValue(Resource.loading(null));

        firestore.collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            UserModel userModel = document.toObject(UserModel.class);
                           result.setValue(Resource.success(userModel,200));
                        } else {
                            result.setValue(Resource.error("User document not found", null,500));
                        }
                    } else {
                        result.setValue(Resource.error("Failed to fetch data: " + Objects.requireNonNull(task.getException()).getMessage(), null,500));
                    }
                });

        return result;
    }
    public LiveData<Resource<List<CropHandBookModel>>> getCropHandBook() {
        MutableLiveData<Resource<List<CropHandBookModel>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        firestore.collection("CropHandbooks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<CropHandBookModel> cropList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CropHandBookModel model = document.toObject(CropHandBookModel.class);
                            cropList.add(model);
                        }

                        if (cropList.isEmpty()) {
                            result.setValue(Resource.error("No documents found", null, 404));
                        } else {
                            result.setValue(Resource.success(cropList, 200));
                        }
                    } else {
                        result.setValue(Resource.error("Failed: " + task.getException().getMessage(), null, 500));
                    }
                });

        return result;

    }
    public LiveData<Resource<List<VegetableResponse>>> getVegetableDetails() {
        MutableLiveData<Resource<List<VegetableResponse>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        firestore.collection("Vegetables")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<VegetableResponse> vegetableList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            VegetableResponse model = document.toObject(VegetableResponse.class);
                            vegetableList.add(model);
                        }

                        if (vegetableList.isEmpty()) {
                            result.setValue(Resource.error("No documents found", null, 404));
                        } else {
                            result.setValue(Resource.success(vegetableList, 200));
                        }
                    } else {
                        result.setValue(Resource.error("Failed: " + task.getException().getMessage(), null, 500));
                    }
                });

        return result;

    }


}
