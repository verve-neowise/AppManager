package com.axtech.appmanager

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Scaffold
import androidx.compose.ui.graphics.Color
import com.axtech.appmanager.ui.presentation.navigation.NavigationScreen
import com.axtech.appmanager.ui.theme.EvaluationAssignmentTheme

class MainActivity : ComponentActivity() {

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (!it) {
            Toast.makeText(this, "Notification permission is not granted. Download is not continue on background", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            EvaluationAssignmentTheme {
                Scaffold(backgroundColor = Color.White) {
                    NavigationScreen()
                }
            }
        }
    }
}