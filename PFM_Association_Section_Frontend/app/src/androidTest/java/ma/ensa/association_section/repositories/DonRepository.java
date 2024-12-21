package ma.ensa.association_section.repositories;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ma.ensa.association_section.api.ApiService;
import ma.ensa.association_section.api.RetrofitClient;
import ma.ensa.association_section.models.Don;

public class DonRepository {
    private final ApiService apiService;

    public interface DonCallback {
        void onSuccess(Don don);
        void onError(String message);
    }

    public interface DonsCallback {
        void onSuccess(List<Don> dons);
        void onError(String message);
    }

    public DonRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public void creerDon(Don don, DonCallback callback) {
        apiService.creerDon(don).enqueue(new Callback<Don>() {
            @Override
            public void onResponse(Call<Don> call, Response<Don> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur lors de la création du don");
                }
            }

            @Override
            public void onFailure(Call<Don> call, Throwable t) {
                callback.onError("Erreur réseau: " + t.getMessage());
            }
        });
    }

    public void getDonsUtilisateur(Long userId, DonsCallback callback) {
        apiService.getDonsUtilisateur(userId).enqueue(new Callback<List<Don>>() {
            @Override
            public void onResponse(Call<List<Don>> call, Response<List<Don>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur lors de la récupération des dons");
                }
            }

            @Override
            public void onFailure(Call<List<Don>> call, Throwable t) {
                callback.onError("Erreur réseau: " + t.getMessage());
            }
        });
    }
}
