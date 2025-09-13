This is a Kotlin Multiplatform template targeting Android, iOS, Desktop and Web.
Runs on every platform on adding the run configurations, apart from Web as of now. Will add web in the future.

To add run configuration follow below, click + button near run button where already android related configuration will be there by default.
For Desktop: After clicking +, click on gradle task, give this command in the Run field -> ":composeApp:run" and then save.

For IOS: After clicking the +, select the iosApp.xcodeproj file which will be inside the iosApp folder precreated if you used kmp template, else you need to generate it. Then select a ios simulator target and click on apply.

For Web: Give "jsBrowserDevelopmentRun" in the run field in the gradle task of a new run configuration. (Although right now this only displays a blank text on the webpage it will successfully build and run.)


Template follows MVVM with CLEAN architecture.
Example Repository, RepositoryImpl, UseCases, HttpClient added.

This has base for the following dependencies,
1) Navigation: Voyager Navigation
2) Ktor client added: Common, Android and Desktop.
3) Coroutines: Common
4) Serialization (For passing data between composables): Common
5) Logging: Kermit
6) Image Loader: Kamel
7) Dependency Injection (Common, Android): Koin

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform

# KMP Template
