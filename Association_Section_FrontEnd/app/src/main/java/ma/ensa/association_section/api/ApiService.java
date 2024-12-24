package ma.ensa.association_section.api;

import java.util.List;

import ma.ensa.association_section.models.Association;
import ma.ensa.association_section.models.Don;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("associations")
    Call<List<Association>> getAssociations();

    @GET("associations/{id}")
    Call<Association> getAssociation(@Path("id") Long id);

    @POST("dons")
    Call<Don> creerDon(@Body Don don);

    @GET("dons/utilisateur/{userId}")
    Call<List<Don>> getDonsUtilisateur(@Path("userId") Long userId);
}
