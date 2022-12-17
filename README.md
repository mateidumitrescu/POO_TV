# POO TV

This project implements a backend for a platform similar to Netflix or HBO.

Class Application is the main class that would handle the operations and commands from input. Class Input is used to get all the commands and database from input json files, and then all data is parsed to Application instance.

### Example of Application Fields

- HomePageAuth
- HomePageUnauth
- LoginPage
- LogoutPage
- MoviesPage
- Page
- RegisterPage
- SeeDetailsPage
- UpgradesPage

All of those pages are child classes of Page class and all of them store available pages that user can reach and available events that user can access. For example, MoviesPage can reach only SeeDetailsPage, LogoutPage, MoviesPage (refresh) and can use only Search event and Filter event.

Also, Application has a method startActions which will handle "on page" and "change page" situations. Class Action will handle all possible "events" for "change page" and "on page" and also fields that are parsed from Input.

There are also other very important classes used for this project, such as Movie and OutputHandler.

### Movie class

This class stores characteristics of a movie, such as rating, number of likes, name, or actors.

### OutputHandler

This class is used to add all results after Action methods to "output" ArrayNode in order to add it to output json files.

### Feedback

This project was a very interesting one to implement, but I want to say that a lot of output errors and output situations were needed to be understood from ref files and the main context and scenary were not very specific.