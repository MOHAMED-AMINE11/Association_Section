package ma.ensa.association_section.views.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensa.association_section.R;
import ma.ensa.association_section.models.Association;
import ma.ensa.association_section.viewmodels.AssociationViewModel;
import ma.ensa.association_section.views.adapters.AssociationAdapter;

// views/fragments/AssociationsListFragment.java
public class AssociationListFragment extends Fragment implements AssociationAdapter.OnAssociationClickListener {
    private RecyclerView recyclerView;
    private AssociationAdapter adapter;
    private AssociationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associations_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAssociations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AssociationAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AssociationViewModel.class);
        viewModel.getAssociations().observe(getViewLifecycleOwner(), associations -> {
            adapter.setAssociations(associations);
        });

        return view;
    }

    @Override
    public void onAssociationClick(Association association) {
        // Navigation vers le formulaire de don
        Bundle bundle = new Bundle();
        bundle.putLong("association_id", association.getId());
        Navigation.findNavController(getView())
                .navigate(R.id.action_associationsListFragment_to_donFormFragment, bundle);
    }
}
