Hi
I used https://fakenews.squirro.com/ for get data
In this project i Used Room database for implement offline first behavior, repository will emit data
from database and after will update with data from network.
When data fetching shown loading state, because of fetching from data base first loading indicator
shown fast and disappears almost immediately
I used UseCase for separate logic
App supports basic dark mode according device state - for change to dark/light need to change device theme
For save time I used hardcoded strings and not used string resources
Implemented simple unit test case for view model - ArticleViewModelTest
For pass data from repository I used flow and used state flow in View model
Used MVI pattern for View and view model based state and state hoisting in compose UI, UiState - down to UI, Action - up to ViewModel 
Used compose navigation for navigation between screens
Implemented simple error handling in view model and view by using Result in repo
Used ArticleBridge in WebView what shows "Hi WalkMe" text and AlertDialog when loaded view
Not configured .gitignore file
For any questions please contact me 0544709905 or sergey.korchagin.il@gmail.com
Thank you