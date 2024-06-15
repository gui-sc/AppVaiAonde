package com.example.vaiaonde.api;

import com.example.vaiaonde.database.model.ViagensModel;

public interface ViagemCallback {
    void onSuccess(ViagensModel viagem);
    void onFailure(Throwable t);
}