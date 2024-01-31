import com.android.build.gradle.internal.scope.ProjectInfo.Companion.getBaseName

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) version libs.versions.ksp apply false
   alias(libs.plugins.ktlint) version libs.versions.ktlint apply false
}
subprojects {
   apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
}

buildscript {
    dependencies {
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${libs.versions.ksp}")
    }
}