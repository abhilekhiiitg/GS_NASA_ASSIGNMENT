# Assignment: 
- Design a mobile app that displays NASA’sAstronomy picture of the day
- Assignment question is available on this link ->>
[Coding challenge (APOD) - L2.docx](https://github.com/abhilekhiiitg/GS_NASA_ASSIGNMENT/files/10214040/Coding.challenge.APOD.-.L2.docx)



# Basic features provided 
- Allow users to search for the picture for a date of their choice
- Allow users to create a list of &quot;favorite&quot; listings
- Display date, explanation, Title and the image / video of the day
- App should cache information and should display last updated information in case ofnetwork unavailability.

 # Project Description:
 - Initially Today's picture will be fetched from server and shown to the user.
 - url: https://api.nasa.gov/planetary/apod?api_key=rCu2dOglgtb1RcjzeutQbeDo4FRbwL8oyuBhJr6h
 - After loading data from server, data get saved in DB.
 - So next time onwards, if Internet is not available then data will be fetched from local storage.
 - user can make picture as favourite by clicking red heart on the top.
 - user can select date by clicking calander floating icon in the bottom.
 - user can see favourite pictures by clicking heart floating icon in the bottom.
 
 # Tech Stack
 - Architecture Pattern: MVVM with Repository Pattern, Flow, Coroutines, Lifecycle, DataBinding
 - Dependency Injection: Dagger 2
 - Database: Room
 - Network: Retrofit
 - Foundation: Kotlin, KTX, AndroidX, AppCompat
 - Behavior: Offline database
 - Other third party libraries: Moshi,Glide
 - testing: Junit,mockito

 # Note :
 - Video support is not provided in this version.
 - Dark mode support.
 
 ![WhatsApp Image 2022-12-01 at 11 28 43 PM](https://user-images.githubusercontent.com/6941625/205126406-cb481fe8-27a5-4190-a4da-d2ab8cbd6e21.jpeg)
 ![WhatsApp Image 2022-12-01 at 11 28 14 PM](https://user-images.githubusercontent.com/6941625/205126428-2092cbd3-ee1c-4ca0-8828-7a972f46a2fe.jpeg)
 ![WhatsApp Image 2022-12-01 at 11 28 42 PM](https://user-images.githubusercontent.com/6941625/205126414-3525e43b-015b-4f76-8b50-00852e62944c.jpeg)



