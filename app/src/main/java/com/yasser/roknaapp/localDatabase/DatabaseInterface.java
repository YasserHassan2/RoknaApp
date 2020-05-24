package com.yasser.roknaapp.localDatabase;

import androidx.annotation.NonNull;

public interface DatabaseInterface {

    void onStart();

    void onSuccess(@NonNull Boolean value);

    void onError(@NonNull Throwable throwable);
}
