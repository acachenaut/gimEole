package com.dutinfo.gimeole;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Activité qui permet de demander les permissions qui sont inscrites dans le manifests.
 */
public class DemandeDesAutorisationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askPermissions();
    }

    /**
     * Ask permissions.
     */
    void askPermissions(){
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if(report.areAllPermissionsGranted()){
                        Intent intent = new Intent(DemandeDesAutorisationsActivity.this, ScanDesPeripheriquesBluetoothActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(DemandeDesAutorisationsActivity.this, "Il faut accepter les permissions...", Toast.LENGTH_SHORT).show();
                        askPermissions();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
    }
}