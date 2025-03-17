# WalkMe Interview Project

## Overview
 Hi all . I used [Fake News API](https://fakenews.squirro.com/) to fetch data for this project.

## Features
- **Offline-first**: Implemented using Room Database. The repository first emits data from the local database and then updates it with network data.
- **Loading state**: Since the database fetch happens first, the loading indicator is displayed quickly and disappears almost immediately.
- **UseCase**: Separates business logic.
- **Dark mode support**: The app follows the device theme (to switch to dark/light mode, change the device theme). Basic implementation
- **Unit tests**: Implemented a simple unit test case for the ViewModel (`ArticleViewModelTest`).
- **State management**:
    - Used **Flow** in the repository to pass data.
    - Used **StateFlow** in the ViewModel.
    - Followed **MVI pattern** in Compose UI:
        - `UiState` → down to UI.
        - `Action` → up to ViewModel.
- **Navigation**: Implemented with **Compose Navigation**.
- **Error handling**:
    - Used `Result` in the repository.
    - Basic error handling is present in both the ViewModel and UI.
- **WebView**:
    - Implemented **ArticleBridge** in WebView.
    - Displays `"Hi WalkMe"` text inside the WebView.
    - Shows an **AlertDialog** when the WebView loads.

## Known Issues
- **`.gitignore` not configured** (may include unnecessary files in the repo).
- ** String resources**: To save time, hardcoded strings were used instead of string resources.

## Demo
Attached an `.mp4` file showing the app flow.

## Contact
For any questions, feel free to contact me at:
054-470-9905  
 sergey.korchagin.il@gmail.com

Thank you!
