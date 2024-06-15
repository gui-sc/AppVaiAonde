package com.example.vaiaonde.api;

import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;

public interface ArrayViagemCallback {
    void onSuccess(ArrayList<ViagensModel> viagens);
    void onFailure(Throwable t);
}
