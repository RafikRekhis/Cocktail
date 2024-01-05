# Cocktail App Readme

* **Developed by:** Mohamed BAHLOUL and Rafik REKHIS

## Libraries and Technologies Used

- **Navigation Component:** One activity contains multiple fragments instead of creating multiple activities.
- **Retrofit:** Making HTTP connection with the REST API and converting cocktail JSON files to Kotlin/Java objects.
- **SharedPreferences:** Saving cocktails in the local database.
- **MVVM & LiveData:** Separating logic code from views and saving the state in case the screen configuration changes.
- **View Binding:** Instead of inflating views manually, view binding will take care of that.
- **Glide:** Caching images and loading them into ImageView.

## Features

In our application, we have three pages that can be accessed from the bottom navigation bar: Home, Favorites, and Categories.
There is also an options menu, accessed by the app bar, allowing navigation to five pages: Search, Settings, Ingredients, Glass, and Cocktail Type.

<img src="images/options_menu.jpg" alt="Options Menu" height="500">

We have also added a loader while waiting for the data to be loaded from the API. For example, we have added a circular progress indicator in the Home page and a linear progress indicator in the Cocktail Details page.

<img src="images/loaders.png" alt="Loaders" height="500">

- **Splash Screen:**
  Contains the application logo and name. It is displayed for 2 seconds before navigating to the Home page.

<img src="images/splash.jpg" alt="Splash Screen" height="500">

- **Home Page:**
  Contains a random cocktail suggestion, a list of the most recently viewed cocktails, and a list of cocktail categories.

<img src="images/home.jpg" alt="Home Page" height="500">

Tapping on a cocktail image navigates to the Cocktail Details page where details can be viewed and the cocktail can be added to favorites.

<img src="images/details.jpg" alt="Cocktail Details Page" height="500">

- **Favorites Page:**
  Contains a list of favorite cocktails.

<img src="images/favorites.jpg" alt="Favorites Page" height="500">

- **Categories Page:**
  Contains a list of cocktail categories.

<img src="images/categories.jpg" alt="Categories Page" height="500">

Tapping on a category navigates to the Cocktails by Category page, where cocktails of the selected category and their total number can be viewed.

<img src="images/cocktails_by_category.jpg" alt="Cocktails by Category Page" height="500">

- **Ingredients Page, Glass Page, and Cocktail Type Page:**
  Similar to the Categories page, these pages contain lists of ingredients, glasses, and cocktail types, respectively.

<img src="images/filters.png" alt="Filter Pages" height="500">

Tapping on an item navigates to the corresponding Cocktails by Ingredient, Cocktails by Glass, and Cocktails by Cocktail Type pages, where cocktails of the selected item and their total number can be viewed.

<img src="images/cocktails_by_filter.png" alt="Cocktails by Filter Page" height="500">

- **Search Page:**
  Contains a search bar for searching cocktails by name. Writing the name in the search bar automatically shows cocktails containing the written name.

<img src="images/search.jpg" alt="Search Page" height="500">

Filtering by category, ingredient, glass, and cocktail type is also possible.

<img src="images/search_filters.jpg" alt="Search Filters" height="500">

- **Settings Page:**
  Contains a text view to change the number of cocktails in the last-seen list.

<img src="images/settings.png" alt="Settings Page" height="500">

a switch button to enable/disable dark mode.

<img src="images/dark_mode.jpg" alt="Dark Mode" height="500">

## Difficulties Encountered

We did not encounter blocking difficulties during the development of this application. However, the following features took the longest time to implement:
- Back, option menu (considering many scenarios to ensure smooth navigation).
- Search and favorites pages (implementing many improvements and tweaks for a positive user experience).
- Design (making numerous refinements for an attractive and user-friendly interface).