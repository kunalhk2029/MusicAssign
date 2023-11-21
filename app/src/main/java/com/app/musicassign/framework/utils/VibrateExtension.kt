package com.app.musicassign.framework.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VibrateExtension
@Inject
constructor(
    context: Context,
) {

    private lateinit var vibrator: Vibrator
    private lateinit var vibratorManager: VibratorManager

    init {
        if (Build.VERSION.SDK_INT >= 31) {
            vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        } else {
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun vibrate() {
        startvibrate()
    }

    fun hasVibrator(): Boolean {
        if (Build.VERSION.SDK_INT >= 31) {
            return vibratorManager.defaultVibrator.hasVibrator()

        } else {
            return vibrator.hasVibrator()
        }
    }

    private fun startvibrate() {

        if (hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 31) {
                val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                vibratorManager.defaultVibrator.vibrate(effect)
            } else {
                if (Build.VERSION.SDK_INT >= 29) {
                    val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                    vibrator.vibrate(effect)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50L,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }
            }
        }
    }
}