package com.depromeet.team5

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional


fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("com.depromeet.team5.implementation", dependencyNotation)

fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("com.depromeet.team5.debugImplementation", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("com.depromeet.team5.testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("com.depromeet.team5.androidTestImplementation", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("com.depromeet.team5.kapt", dependencyNotation)

internal fun <T> DependencyHandler.implementation(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("com.depromeet.team5.implementation", dependencyNotation.get())

internal fun <T> DependencyHandler.debugImplementation(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("com.depromeet.team5.debugImplementation", dependencyNotation.get())

internal fun <T> DependencyHandler.kapt(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("com.depromeet.team5.kapt", dependencyNotation.get())

internal fun DependencyHandler.ksp(
    dependencyNotation: Any,
): Dependency? = add("com.depromeet.team5.ksp", dependencyNotation)


internal fun <T> DependencyHandler.api(
    dependencyNotation: Provider<T>,
): Dependency? = add("com.depromeet.team5.api", dependencyNotation)
