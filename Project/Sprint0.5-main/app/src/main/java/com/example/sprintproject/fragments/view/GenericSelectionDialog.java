package com.example.sprintproject.fragments.view;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.sprintproject.R;

import java.util.List;

public class GenericSelectionDialog<T> extends DialogFragment {

    private SelectionListener<T> listener;
    private List<T> options;
    private String title;

    public interface SelectionListener<T> {
        void onItemSelected(T selectedItem);
    }

    public GenericSelectionDialog(List<T> options, String title, SelectionListener<T> listener) {
        this.options = options;
        this.title = title;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_generic_selection);

        // Set title
        dialog.setTitle(title);

        Spinner spinner = dialog.findViewById(R.id.spinnerOptions);
        Button btnSave = dialog.findViewById(R.id.btnSaveSelection);

        // Populate the spinner with options
        ArrayAdapter<T> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            T selectedItem = (T) spinner.getSelectedItem();
            if (listener != null) {
                listener.onItemSelected(selectedItem);
            }
            dismiss();
        });

        return dialog;
    }
}
