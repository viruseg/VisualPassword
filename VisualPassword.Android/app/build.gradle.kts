import java.text.SimpleDateFormat
import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

fun getVersionCode(): Int {
    val dateFormat = SimpleDateFormat("yyMMddHH", Locale.getDefault())
    return dateFormat.format(Date()).toInt()
}

fun getVersionName(): String {
    val version = "1.0"
    val dateFormat = SimpleDateFormat("yyMMdd", Locale.getDefault())
    return "$version.${dateFormat.format(Date())}"
}

android {
    namespace = "com.visualpassword.androidapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.visualpassword.androidapp"
        minSdk = 21
        targetSdk = 36
        versionCode = getVersionCode()
        versionName = getVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += setOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.nanohttpd)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material)
}

tasks.register("copyIndexHtml") {
    doLast {
        val sourceFile = File(project.rootDir, "../VisualPassword.Web/index.html")
        val targetDir = File(project.projectDir, "src/main/assets")
        val targetFile = File(targetDir, "index.html")

        println("=== Index.Html COPY TASK ===")
        println("Root dir: ${project.rootDir.absolutePath}")
        println("Project dir: ${project.projectDir.absolutePath}")
        println("Source: ${sourceFile.absolutePath}")
        println("Source exists: ${sourceFile.exists()}")

        if (sourceFile.exists()) {
            targetDir.mkdirs()
            sourceFile.copyTo(targetFile, overwrite = true)
            println("File copied successfully to: ${targetFile.absolutePath}")
        } else {
            println("ERROR: Source file not found!")
        }
    }
}

tasks.named("preBuild") {
    dependsOn("copyIndexHtml")
}