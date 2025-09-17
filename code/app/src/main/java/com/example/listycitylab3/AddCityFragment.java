package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment
{
    interface AddCityDialogListener
    {
        void addCity(City city);
        void updateCity(City city);
    }
    private AddCityDialogListener listener;

    static AddCityFragment newInstance(City city)
    {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        City existingCity = null;
        if (getArguments() != null) {
            existingCity = (City) getArguments().getSerializable("city");
        }

        if (existingCity != null) {
            editCityName.setText(existingCity.getName());
            editProvinceName.setText(existingCity.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        City finalExistingCity = existingCity;
        return builder.setView(view).setTitle("Add/Edit a city").setNegativeButton("Cancel", null).setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (finalExistingCity == null)
                    {
                        listener.addCity(new City(cityName, provinceName));
                    } else
                    {
                        finalExistingCity.setName(cityName);
                        finalExistingCity.setProvince(provinceName);
                        listener.updateCity(finalExistingCity);
                    }
                }).create();
    }
}
