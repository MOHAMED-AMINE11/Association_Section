package ma.ensa.association_section.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ma.ensa.association_section.R;
import ma.ensa.association_section.models.Don;

// views/fragments/ConfirmationFragment.java
public class ConfirmationFragment extends Fragment {
    private Button btnRetourAccueil;
    private Button btnVoirMesDons;
    private Don donEffectue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Récupérer les arguments si on a passé le don
        if (getArguments() != null) {
            donEffectue = (Don) getArguments().getSerializable("don_effectue");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        initializeViews(view);
        setupListeners();
        afficherDetailsDon();

        return view;
    }

    private void initializeViews(View view) {
        btnRetourAccueil = view.findViewById(R.id.btnRetourAccueil);
        btnVoirMesDons = view.findViewById(R.id.btnVoirMesDons);

        // Initialiser les autres vues pour afficher les détails du don
        TextView tvTypeAliment = view.findViewById(R.id.tvTypeAliment);
        TextView tvQuantite = view.findViewById(R.id.tvQuantite);
        TextView tvAssociation = view.findViewById(R.id.tvAssociation);
        TextView tvDateDon = view.findViewById(R.id.tvDateDon);
    }
                                              ///////ATTENTIONNNNNNNNNNNNNNNN navigate
    private void setupListeners() {
        btnRetourAccueil.setOnClickListener(v -> {
            // Naviguer vers la page d'accueil
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_associationsListFragment_to_donFormFragment);
        });

        btnVoirMesDons.setOnClickListener(v -> {
            // Naviguer vers la liste des dons de l'utilisateur
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_associationsListFragment_to_donFormFragment);
        });
    }

    private void afficherDetailsDon() {
        if (donEffectue != null) {
            TextView tvTypeAliment = requireView().findViewById(R.id.tvTypeAliment);
            TextView tvQuantite = requireView().findViewById(R.id.tvQuantite);

            tvTypeAliment.setText(donEffectue.getTypeAliment());
            tvQuantite.setText(String.format("%s %s",
                    donEffectue.getQuantite().toString(),
                    donEffectue.getUnite()));

            // Afficher la date actuelle
            TextView tvDateDon = requireView().findViewById(R.id.tvDateDon);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            tvDateDon.setText(sdf.format(new Date()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Masquer le bouton retour dans l'ActionBar si nécessaire
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
