package ma.ensa.association_section.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ma.ensa.association_section.models.Association;
import ma.ensa.association_section.repositories.AssociationRepository;

// viewmodels/AssociationViewModel.java
public class AssociationViewModel extends ViewModel {
    private final MutableLiveData<List<Association>> associations = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final AssociationRepository repository;

    public AssociationViewModel() {
        repository = new AssociationRepository();
        chargerAssociations();
    }

    public LiveData<List<Association>> getAssociations() {
        return associations;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void chargerAssociations() {
        isLoading.setValue(true);
        repository.getAssociations(new AssociationRepository.AssociationsCallback() {
            @Override
            public void onSuccess(List<Association> associationsList) {
                isLoading.postValue(false);
                associations.postValue(associationsList);
            }

            @Override
            public void onError(String message) {
                isLoading.postValue(false);
                error.postValue(message);
            }
        });
    }
}
