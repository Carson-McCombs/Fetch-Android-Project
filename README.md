## About the Project

Currently in closed testing before release on Google Playstore. Written in Kotlin Compose and Android Studio Koala. Developed for Android SDK 35 ( minimum SDK 28 ). Uses Kotlin Rooms and SQLite for referencing the local database and Ktor for HTTP Requests. Primarily uses Kotlin flows to transmit sequental and asynchronous data ( instead of something like LiveData ).

### Built With:

*![Kotlin](https://img.shields.io/badge/Kotlin-White?style=flat&logo=kotlin&logoColor=%23FFFFFF&labelColor=White&color=%237F52FF&link=https%3A%2F%2Fkotlinlang.org%2F)

*![Ktor](https://img.shields.io/badge/Ktor-%23ff46ed?style=flat&logo=ktor&logoColor=%23FFFFFF&link=https%3A%2F%2Fktor.io%2F)

*![SQLite](https://img.shields.io/badge/SQLite-White?style=flat&logo=SQLite&logoColor=White&labelColor=White&color=%23003B57&link=https%3A%2F%2Fwww.sqlite.org%2F)

*![Android Studio](https://img.shields.io/badge/Android%20Studio-White?style=flat&logo=Android%20Studio&logoColor=%23FFFFFF&labelColor=White&color=%233DDC84&link=https%3A%2F%2Fdeveloper.android.com%2Fstudio%3Fgad_source%3D1%26gclid%3DCjwKCAjwm_SzBhAsEiwAXE2CvyjF97QCqUSAjRnHcpGvpea9KoFZH47o7-JQ5qlhE_3XpPBIRs7d-RoCRJsQAvD_BwE%26gclsrc%3Daw.ds)

### Features:

*Loads an array of JSONs from "https://fetch-hiring.s3.amazonaws.com/hiring.json"
*Saves into local database for future use ( i.e. next time the application launches )
*Allows user to reload data from URL
*Light debugging and error handling ( such as if there is no internet connection )
*Sorts items first by listId, then by name

![image](https://github.com/user-attachments/assets/f3cc7ad4-e76b-4e70-be47-75e894f85376)
![image](https://github.com/user-attachments/assets/f87e8fda-df72-49f7-8679-9f4a2fb558b1)
![image](https://github.com/user-attachments/assets/1bbfb389-22f6-41e4-901d-b2179e603a26)
