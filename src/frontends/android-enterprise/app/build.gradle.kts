plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.dagger.hilt.android")
	id("com.google.devtools.ksp")
}

android {
	namespace = "org.strongswan.android.enterprise"
	compileSdk = 34

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	kotlin {
		jvmToolchain(17)
	}

	defaultConfig {
		applicationId = "org.strongswan.android.enterprise"

		minSdk = 21
		targetSdk = 34

		versionCode = 1
		versionName = "1.0"
		setProperty("archivesBaseName", "strongSwan-$versionName")

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		ksp {
			arg("room.schemaLocation", "$projectDir/schemas")
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	viewBinding {
		enable = true
	}
}

dependencies {
	//
	// AndroidX
	//
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.fragment.ktx)
	implementation(libs.androidx.recyclerview)
	//
	// AndroidX, Lifecycle
	//
	implementation(libs.androidx.lifecycle.process)
	implementation(libs.androidx.lifecycle.common)
	//
	// AndroidX, Room
	//
	implementation(libs.androidx.room.ktx)
	implementation(libs.androidx.room.runtime)
	ksp(libs.androidx.room.compiler)
	//
	// Coroutines
	//
	implementation(libs.jetbrains.kotlinx.coroutines.android)
	implementation(libs.jetbrains.kotlinx.coroutines.core)
	//
	// Google Material
	//
	implementation(libs.google.android.material)
	//
	// Hilt
	//
	implementation(libs.google.dagger.hilt.android)
	ksp(libs.google.dagger.hilt.compiler)
	//
	// Test dependencies, Android
	//
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.androidx.test.espresso.core)
	//
	// Test dependencies
	//
	testImplementation(libs.assertj.core)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.junit.jupiter.api)
	testImplementation(libs.junit.jupiter.params)
	testImplementation(libs.junit.vintage.engine)
	testImplementation(libs.mockito.core)
	testImplementation(libs.mockito.junit.jupiter)
	testImplementation(libs.robolectric)
}
