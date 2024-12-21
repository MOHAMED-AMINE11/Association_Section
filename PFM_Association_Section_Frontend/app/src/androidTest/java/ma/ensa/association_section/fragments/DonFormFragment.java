package ma.ensa.association_section.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ma.ensa.association_section.R;
import ma.ensa.association_section.models.Don;
import ma.ensa.association_section.viewmodels.DonViewModel;

// views/fragments/DonFormFragment.java
public class DonFormFragment extends Fragment {
    private DonViewModel viewModel;
    private Long associationId;
    private EditText etTypeAliment, etQuantite, etDatePeremption, etDescription;
    private Spinner spinnerUnite;
    private Button btnSoumettreDon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DonViewModel.class);

        if (getArguments() != null) {
            associationId = getArguments().getLong("association_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_form, container, false);

        initializeViews(view);
        setupSpinner();
        setupDatePicker();
        setupObservers();
        setupSubmitButton();

        return view;
    }

    private void initializeViews(View view) {
        etTypeAliment = view.findViewById(R.id.etTypeAliment);
        etQuantite = view.findViewById(R.id.etQuantite);
        etDatePeremption = view.findViewById(R.id.etDatePeremption);
        etDescription = view.findViewById(R.id.etDescription);
        spinnerUnite = view.findViewById(R.id.spinnerUnite);
        btnSoumettreDon = view.findViewById(R.id.btnSoumettreDon);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.unites_mesure,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnite.setAdapter(adapter);
    }

    private void setupDatePicker() {
        etDatePeremption.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        etDatePeremption.setText(sdf.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
    }

    private void setupObservers() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            btnSoumettreDon.setEnabled(!isLoading);
            // Optionally show/hide a progress indicator
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getDonLiveData().observe(getViewLifecycleOwner(), don -> {
            if (don != null) {
                // Don créé avec succès, naviguer vers un écran de confirmation
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_donFormFragment_to_confirmationFragment);
            }
        });
    }

    private void setupSubmitButton() {
        btnSoumettreDon.setOnClickListener(v -> {
            if (validateForm()) {
                Don don = createDonFromForm();
                viewModel.creerDon(don);
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (etTypeAliment.getText().toString().trim().isEmpty()) {
            etTypeAliment.setError("Ce champ est requis");
            isValid = false;
        }

        if (etQuantite.getText().toString().trim().isEmpty()) {
            etQuantite.setError("Ce champ est requis");
            isValid = false;
        }

        if (etDatePeremption.getText().toString().trim().isEmpty()) {
            etDatePeremption.setError("Ce champ est requis");
            isValid = false;
        }

        return isValid;
    }

    private Don createDonFromForm() {
        Don don = new Don();
        don.setIdAssociation(associationId);
        don.setTypeAliment(etTypeAliment.getText().toString().trim());
        don.setQuantite(Double.parseDouble(etQuantite.getText().toString().trim()));
        don.setUnite(spinnerUnite.getSelectedItem().toString());
        don.setDescription(etDescription.getText().toString().trim());
        // Conversion de la date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            don.setDatePeremption(sdf.parse(etDatePeremption.getText().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return don;
    }
}
