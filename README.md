# Movie Covers Gallery

### Install

1. clone this repository
2. import to android studio
3. execute app

### Requirements

Android 5.1 or superior

Tested in Samsung Galaxy S6 (6.0.1) and Oneplus One (5.1.1).

### Description and features

Image gallery app that displays movie covers using [the movie db](https://www.themoviedb.org). 

Features in the requirements:

- List of recommended covers by popular movies (it changes every week).
  - Endless scroll, every time the user reach the bottom of the current set a new set is loaded.
  - Lazy load and caching of images using [Picasso](http://square.github.io/picasso/).
  - Transition animation when open detail.
  - Pull to refresh
- Detail: display full size image along with the details of the movie.
  - Parallax animation to scroll down.

Extra features: 

- Live search functionality
  - Search by title.
  - Get results while typing.
  - Display details of the movie in the list.
  - Animation when opening the detail.


### Left Behind

**(Extra functionality) Favourites management**
I only left behind some extra functionality that I planned at the beginning. 
The empty second tab in the main screen was intended to display user's favourites. Due lack of time this hasn't been implemented yet.
Although the architecture is ready to add this functionality effortless. (See repositories in `data` package) 

NOTE: I will keep  working on this feature from Thursday 07/07/2016 in a separate git branch. This code challenge is delivered only in the `master` branch.