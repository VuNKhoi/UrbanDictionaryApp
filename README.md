Urban Dictinary App

Kotlin , Retrofit, Koin, Junit, Mockito, Room Db, MVVM

Main points
- Edit text for input and a search button
- 2 radio buttons for sorting either by thumbs up or down
- A clear button for clearing the edit text and the radio buttons
- API response is shown in the recycler view, each has a definition and counts of thumbs up and down
- Show progress indicator when loading, visibility changed with livedata with databinding
- Unit test for testing if successful API call reflect in live data, mocking the repo
- Instrumented test for testing if database is working or not
- Room database for storing data incase unable to fetch (some exception)
- Configuration change handled by onSaveInstanceState callback
- Dependency injection using koin for repo, services and database
-------------------------------------------------------------------------------

Few things that can be imporved
- Callbacks using onClick in XML instead of using setOnClickListener, since this is MVVM
- Many UI things, such as each recycler view item, can be cards with elevation....
- A bunch of test cases, such as the one testing to see if the progress bar is showing, unable to test that one since internet is always too fast even when changing the emulator speed to gprs
- ....


