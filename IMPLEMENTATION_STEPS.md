# Project Phases: Sefer Diary App

## Phase 1: Basic Design and Implementation (Offline Mode)

In this phase, we focused on setting up the local storage and a functional UI for the diary's offline capabilities.

### 1. Dependency Management
- Added **Room Persistence Library** for local database management.
- Integrated **KSP (Kotlin Symbol Processing)** for Room annotation processing.
- Added **ViewModel Compose** and **Navigation Compose** for UI state and future navigation.
- Updated `libs.versions.toml` and `build.gradle.kts` to include these libraries.

### 2. Data Model and Storage
- **DiaryEntry**: Created a data class annotated as a Room `@Entity` with `id`, `title`, `content`, and `timestamp`.
- **DiaryDao**: Defined the Data Access Object interface with Room annotations for `@Insert`, `@Update`, `@Delete`, and `@Query` (fetching all entries ordered by time).
- **DiaryDatabase**: Implemented the abstract Room database class with a singleton pattern to ensure only one instance exists.

### 3. Repository Pattern
- **DiaryRepository**: Created an interface to abstract data operations.
- **OfflineDiaryRepository**: Implemented the interface using the `DiaryDao`, providing a clean API for the ViewModel to interact with the database.

### 4. Dependency Injection (Manual)
- Created `DiaryApplication` and `AppContainer` to manage the lifecycle of the repository and database, providing them to the rest of the app.
- Registered the custom Application class in `AndroidManifest.xml`.

### 5. UI Layer (Jetpack Compose)
- **DiaryViewModel**: Handles the logic for fetching data (using Kotlin Flows) and performing CRUD operations. It uses a `Factory` to inject the repository.
- **DiaryScreen**: A modern Compose UI featuring:
    - A `Scaffold` with a `TopAppBar` and `FloatingActionButton`.
    - A `LazyColumn` to display diary entries.
    - `EntryDialog` for both adding and editing entries.
    - Delete functionality directly from the list items.
- **MainActivity**: Set as the entry point, hosting the `DiaryScreen` within the app's theme.

---
**Next Steps:**
- Phase 2: Online synchronization (Firebase or API integration).
- Phase 3: Advanced UI features (Search, Categories, Media attachments).
