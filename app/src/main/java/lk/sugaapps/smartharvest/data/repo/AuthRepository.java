package lk.sugaapps.smartharvest.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import javax.inject.Inject;
import javax.inject.Singleton;

import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.model.UserModel;

@Singleton
public class AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;
    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firestore) {
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    public LiveData<Resource<FirebaseUser>> login(String email, String password) {
        MutableLiveData<Resource<FirebaseUser>> result = new MutableLiveData<>();

        result.setValue(Resource.loading(null));

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue(Resource.success(firebaseAuth.getCurrentUser(),200));
                    } else {
                        result.setValue(Resource.error(
                                task.getException() != null ?
                                        task.getException().getMessage() : "Login failed",
                                null,401));
                    }
                });

        return result;
    }
    public LiveData<Resource<UserModel>> registerUser(String name, String email, String password,
                                                      String mobile, GeoPoint location) {
        MutableLiveData<Resource<UserModel>> result = new MutableLiveData<>();

        result.setValue(Resource.loading(null));

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            UserModel user = new UserModel(firebaseUser.getUid(), name, email, mobile, location);
                            saveUserToFirestore(user, result);
                        } else {
                            result.setValue(Resource.error("User creation failed", null,500));
                        }
                    } else {
                        result.setValue(Resource.error(
                                task.getException() != null ?
                                        task.getException().getMessage() : "Registration failed",
                                null,500));
                    }
                });

        return result;
    }

    private void saveUserToFirestore(UserModel user, MutableLiveData<Resource<UserModel>> result) {
        firestore.collection("users")
                .document(user.getUid())
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue(Resource.success(user,201));
                    } else {
                        result.setValue(Resource.error(
                                task.getException() != null ?
                                        task.getException().getMessage() : "Failed to save user details",
                                null,500));
                    }
                });
    }
}