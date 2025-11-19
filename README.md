# CrikStats - Dynamic Feature Module Demo

A demonstration Android app showcasing **Dynamic Feature Modules (DFM)** with **Dagger Hilt** dependency injection.

## Architecture

- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** with Repository pattern
- **Dependency Injection** with Dagger Hilt
- **Modular Architecture** with Dynamic Feature Modules

## Modules

### 1. `app` (Base Module)
- **Purpose**: Base application with shared dependencies
- **Contains**:
    - Application entry point with `@HiltAndroidApp`
    - Hilt DI setup (Network, API, Repository)
    - Network layer (Ktor client)
    - Repository interfaces and implementations
    - Home screen with download functionality
    - Dynamic feature download logic (Play Core)
    - Exposes `FeatureEntryPoint` for the feature module

### 2. `feature-player` (Dynamic Feature Module)
- **Purpose**: On-demand player statistics feature
- **Contains**:
    - Player stats UI
    - PlayerStatsActivity
    - A plain Dagger component (`FeatureComponent`)
    - Uses `EntryPointAccessors` to access dependencies from the app module

## Tech Stack

| Technology            | Purpose                  |
|-----------------------|--------------------------|
| Kotlin                | Programming language     |
| Jetpack Compose       | UI framework             |
| Dagger Hilt           | Dependency injection     |
| Ktor Client           | HTTP client              |
| Kotlinx Serialization | JSON serialization       |
| Play Core API         | Dynamic feature delivery |
| Coroutines & Flow     | Asynchronous programming |
| MVVM                  | Architecture pattern     |

## Setup & Configuration

### 1. Dynamic Feature Module Setup

#### `app/build.gradle.kts`
```kotlin
android {
    dynamicFeatures += setOf(":feature-player")
}
```

#### `feature-player/build.gradle.kts`
```kotlin
plugins {
    id("com.android.dynamic-feature")
}

dependencies {
    implementation(project(":app"))
}
```

#### `settings.gradle.kts`
```kotlin
include(":app")
include(":feature-player")
```

### 2. Hilt Dependency Sharing

**Shared dependencies are provided in the `app` module:**

- `NetworkModule`: Provides `HttpClient`, `Json`, base URL
- `ApiModule`: Provides `CricketApiService`
- `RepositoryModule`: Binds `PlayerRepository` interface to implementation

Example entrypoint exposed to the feature module:

```kotlin
@EntryPoint
@InstallIn(SingletonComponent::class)
interface FeatureEntryPoint {
    fun playerRepository(): PlayerRepository
}
```

**Feature module defines a plain Dagger component:**
```kotlin
@Component(dependencies = [FeatureEntryPoint::class])
interface FeatureComponent {
    fun inject(activity: PlayerStatsActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(deps: FeatureEntryPoint): Builder
        fun build(): FeatureComponent
    }
}
```

**Usage inside PlayerStatsActivity:**
```kotlin
val deps = EntryPointAccessors.fromApplication(
    applicationContext,
    FeatureEntryPoint::class.java
)

val component = DaggerFeatureComponent.builder()
    .appDependencies(deps)
    .build()

component.inject(this)
```
java -jar C:/Users/sarfr/Downloads/bundletool.jar build-apks --bundle=/MyApp/my_app.aab --output=/MyApp/my_app.apks --local-testing


## How to Test

### Important: Regular "Run" Won't Work for Dynamic Features

Dynamic features require **App Bundle** testing:

### Method 1: Using bundletool (Local Testing)

1. **Build the App Bundle .aab:**
Use Android Studio and the Android Gradle plugin to build and sign an Android App Bundle.
```bash
   ./gradlew :app:bundleDebug
```

2. **Download [bundletool.jar](https://github.com/google/bundletool/releases):**

Run this on terminal:
```bash
java -jar C:/Path/To/Where/bundletool.jar build-apks --bundle=/Path/To/Where/your_app.aab --output=/Path/To/Save/your_app.apks --local-testing
```

3. **Generate APK set:**
```bash
   java -jar bundletool-all.jar build-apks \
     --bundle=app/build/outputs/bundle/debug/app-debug.aab \
     --output=app.apks \
     --local-testing
```

4. **Install on device:**

If you want to deploy the APKs to a device, you also need to include your app's signing information, as shown in the following command. If you don't specify signing information, bundletool attempts to sign your APKs with a debug key for you.
```bash
   bundletool build-apks --bundle=/MyApp/my_app.aab --output=/MyApp/my_app.apks
    --ks=/MyApp/keystore.jks
    --ks-pass=file:/MyApp/keystore.pwd
    --ks-key-alias=MyKeyAlias
    --key-pass=file:/MyApp/key.pwd
```

### Method 2: Google Play Console Internal Testing

1. Upload AAB to Play Console
2. Create Internal Testing track
3. Install via Play Store
4. Test dynamic module download

## User Flow

1. **Launch App** → Home screen displays
2. **Tap "Download Player Stats Module"** → Dynamic feature download begins
3. **Progress Indicator** → Shows download/installation progress
4. **Auto-Navigation** → Opens Player Stats screen after successful download
5. **View Data** → Displays cricket player statistics from API
6. **Back Button** → Returns to Home screen



