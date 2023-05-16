package com.example.sms_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_app.ui.theme.SmsAppTheme
import com.example.sms_app.SmsPermissionChecker
import com.example.sms_app.SmsSendingScreen
import com.example.sms_app.SmsSendingViewModel

class MainActivity : ComponentActivity() {
    private lateinit var smsPermissionChecker: SmsPermissionChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        smsPermissionChecker = SmsPermissionChecker(this)
        smsPermissionChecker.checkSmsPermission()

        setContent {
            SmsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val smsSendingViewModel: SmsSendingViewModel = viewModel()
                    val smsPermissionGranted = remember { mutableStateOf(smsPermissionChecker.checkSmsPermission()) }

                    smsPermissionChecker.permissionResultListener = { granted ->
                        smsPermissionGranted.value = granted
                    }

                    if (smsPermissionGranted.value) {
                        SmsSendingScreen(onSendAllMessagesClick = {
                            smsSendingViewModel.sendAllMessages()
                        })
                    }
                }
            }
        }
    }
}
