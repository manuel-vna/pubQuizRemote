# pubQuizRemote

<br>
Project status: <br>
In Progress <br>
Bug: bug found that destroys user login routine, currently (2022-07-04) no user login possible

<br>

### Description

This Android mobile app provides a quiz game.
It consists of different types of question rounds.
The submitted answers of the quiz players can be calculated automatically into points by an admin user.

The app doesn't serve as a service to host quiz games but it can be seen as a sample of building one's own quiz app.
The app is set up as following:

<br>

### Concepts / Architecture Components

- UI: ConstraintLayout, NavigationDrawer
- Data Storing: Firebase Database, SharedPreferences
- Architecture: MVVM (ViewModel), Activity/Fragments
- Login: GoogleSignIn

<br>

### Libraries

- #### Picasso
 Downloads the images from the Firebase database and caches them locally.

- #### Firebase
Google Cloud Database. No feature in use for encrypting or hiding the 'Firebase API key' since it serves as a resource locator only.
Access to the Firebase Realtime Database is handled and secured via 'Firebase Security Rules'.

<br>

### Details

- #### MVVM: Repository
Since there is only one data source (Firebase Realtime Database) there is no Repository class within the MVVM model in use.
This abstraction level seems particularly important when an app uses more than one data source or manages data locally in parallel.
