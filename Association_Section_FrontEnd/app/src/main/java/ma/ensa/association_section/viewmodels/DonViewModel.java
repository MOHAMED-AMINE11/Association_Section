package ma.ensa.association_section.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ma.ensa.association_section.models.Don;
import ma.ensa.association_section.repositories.DonRepository;

// viewmodels/DonViewModel.java
public class DonViewModel extends ViewModel {
    private final MutableLiveData<Don> donLiveData = new MutableLiveData<>();
    private final DonRepository donRepository;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public DonViewModel() {
        donRepository = new DonRepository();
    }

    public LiveData<Don> getDonLiveData() {
        return donLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void creerDon(Don don) {
        isLoading.setValue(true);
        donRepository.creerDon(don, new DonRepository.DonCallback() {
            @Override
            public void onSuccess(Don nouveauDon) {
                isLoading.postValue(false);
                donLiveData.postValue(nouveauDon);
            }

            @Override
            public void onError(String message) {
                isLoading.postValue(false);
                error.postValue(message);
            }
        });
    }
}
