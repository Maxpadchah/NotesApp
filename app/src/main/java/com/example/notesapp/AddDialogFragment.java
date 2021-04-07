package com.example.notesapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddDialogFragment extends AppCompatDialogFragment {
    private static String KEY_NAME = "name";
    private static String KEY_DESCRIPTION = "description";
    private static String KEY_DATE = "date";
    private static String KEY_LISTENER = "listener";

    private EditText mName, mDescription, mDate;
    private DialogListener mListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.edit_dialog_fragment, null);
        mName = view.findViewById(R.id.task_name);
        mDescription = view.findViewById(R.id.task_description);
        mDate = view.findViewById(R.id.task_date);
        if (savedInstanceState != null) {
            mName.setText(savedInstanceState.getString(KEY_NAME));
            mDescription.setText(savedInstanceState.getString(KEY_DESCRIPTION));
            mDate.setText(savedInstanceState.getString(KEY_DATE));
            mListener = (DialogListener) savedInstanceState.getSerializable(KEY_LISTENER);
        }
        alertDialog.setView(view)
                .setTitle(getResources().getString(R.string.save))
                .setNegativeButton(R.string.no, (dialog, i) -> {
                    dialog.dismiss();
                }).setPositiveButton(R.string.yes, (dialog, i) -> {
            String name = mName.getText().toString();
            String description = mDescription.getText().toString();
            String date = mDate.getText().toString();
            if (!checkFields(name, description, date)) {
                Toast.makeText(getActivity(), getResources().getString(R.string.message_if_empty_fields), Toast.LENGTH_SHORT).show();
            } else {

                mListener.getData(name, description, date);
            }
        });
        mDate.setOnClickListener((v) -> {
            DatePickerDialog.OnDateSetListener dateSetListener =
                    (viewDialog, year, month, day) -> {
                        mDate.setText(year + "-" + month + "-" + day);


                    };

            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.setOnDateSetListener(dateSetListener);
            datePickerFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");

        });


        return alertDialog.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME, mName.getText().toString());
        outState.putString(KEY_DESCRIPTION, mName.getText().toString());
        outState.putString(KEY_DATE, mName.getText().toString());
        outState.putSerializable(KEY_LISTENER, mListener);
    }

    private boolean checkFields(String name, String description, String date) {
        if (name.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.message_if_empty_fields), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setListener(DialogListener dialogListener) {
        this.mListener = dialogListener;
    }
}
