# Technical task 

A user can select one favourite artist and see top 5 albums of that artist. The app will have two pages:
* Search and save a favourite artist
* View top 5 albums of a selected favourite artist

# Tools used for the task
* Java 11 / Spring
* Spock
* Gradle

# Itunes Api in project:
* Searching for artists by keyword: POST https://itunes.apple.com/search?entity=allArtist&term=abba
* Getting top5 albums by id: POST https://itunes.apple.com/lookup?id=3492&entity=album&limit=5
