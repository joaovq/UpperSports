// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.9.10") apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.android.test") version "8.1.3" apply false
    id("androidx.baselineprofile") version "1.2.3" apply false
}