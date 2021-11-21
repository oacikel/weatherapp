Weather Logger App Readme Document
===========================================================
Hello and welcome to the documentation of Weather Logger an app that shows you the weather data around your position and stores it locally.

Introduction
-------------

### Functionality
The app has two main fragments: 
    Main screen where user gets current weather data
    Stored Weather screen to view saved weather events

### Feature List
● Compatibility with Android 4.1 and onwards
● Code quality, readability and consistent code style
● Best UI practices (Material design)
● Local data storage
● UI layout optimized for both Phone and Tablets screens
● Implement ‘More details’ feature (with ability to view more detailed information
about weather data returned from API)
● Fetching and processing weather data for more locations
● Refresh the weather data periodically
● Custom animations, transitions between screens
● All CRUD operations
● Use Kotlin instead of Java for MainActivity

note 1:In order to stop having Network request limitation issues, refresh feature has been commented out. 
Please call viewModel.requestLocationUpdates() if you want to utilize this feature.

note 2:Normally API keys are stored within the global properties file for security reasons.
For this project this file is stored within the project directory to help provide a smoother experience. 


### Building
You can open the project in Android studio and press run.
note: You may need to change the sdk.dir directory in local.properties file

### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide

Developer Notes
-------------
[architecture]: https://developer.android.com/jetpack/guide#recommended-app-arch
[coroutines]: https://developer.android.com/kotlin/coroutines/coroutines-best-practices#coroutines-data-layer
[coroutines-dev-summit]: https://medium.com/androiddevelopers/lessons-learnt-using-coroutines-flow-4a6b285c0d06

### Tips

* [fragments][lifecycleOwner & Fragments]
  Fragments should always use the `viewLifecycleOwner` to trigger UI updates.
  However, that’s not the case for DialogFragments which might not have a View sometimes.
  For `DialogFragments`, you can use the `lifecycleOwner`.
* [fragments][lifecycleOwner & Fragments]

* [flows][safely collect]
  Use the Lifecycle.repeatOnLifecycle or Flow.flowWithLifecycle APIs to safely collect flows from the UI layer in Android.
* [flows][safely collect]