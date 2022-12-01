# Assignment: 
- Design a mobile app that displays NASA’sAstronomy picture of the day

# Basic features provided -
- Allow users to search for the picture for a date of their choice
- Allow users to create a list of &quot;favorite&quot; listings
- Display date, explanation, Title and the image / video of the day
- App should cache information and should display last updated information in case ofnetwork unavailability.

 # Project Description:
 - Initially Today picture will be fetched from server and shown to the user
 - url: https://api.github.com/repos/freeCodeCamp/freeCodeCamp/pulls?state=closed
 - After loading data from server, data get saved in DB.
 - So next time onwards, if Internet is not available then data will be fetched from local storage.
 - user can maek picture as favourite by clicking red heart on the top
 - user can select date by clicking calander floating icon in the bottom
 - user can see favourite pictures by clicking heart floating icon in the bottom
 
 # Tech Stack
 - Architecture Pattern: MVVM with Repository Pattern, Flow, Coroutines, Lifecycle, DataBinding
 - Dependency Injection: Dagger 2
 - Database: Room
 - Network: Retrofit
 - Foundation: Kotlin, KTX, AndroidX, AppCompat
 - Behavior: Offline database
 - Other third party libraries: Moshi,Glide
 - testing: Junit,mockito
 
 #
 #
 
