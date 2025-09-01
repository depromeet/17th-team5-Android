package com.depromeet.team5

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

internal fun <T> DependencyHandler.implementation(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("implementation", dependencyNotation.get())

internal fun <T> DependencyHandler.debugImplementation(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("debugImplementation", dependencyNotation.get())

internal fun <T> DependencyHandler.kapt(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("kapt", dependencyNotation.get())

internal fun DependencyHandler.ksp(
    dependencyNotation: Any,
): Dependency? = add("ksp", dependencyNotation)

internal fun <T> DependencyHandler.api(
    dependencyNotation: Provider<T>,
): Dependency? = add("api", dependencyNotation)
