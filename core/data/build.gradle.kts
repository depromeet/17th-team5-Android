plugins {
    alias(libs.plugins.depromeet.team5.pure.kotlin)
    alias(libs.plugins.depromeet.team5.pure.hilt)
}

dependencies {
    implementation(projects.core.domain)
}
