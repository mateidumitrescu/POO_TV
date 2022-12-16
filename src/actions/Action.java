package actions;

import application.Application;
import com.fasterxml.jackson.databind.node.ArrayNode;
import errors.OutputHandler;
import movies.Movie;
import pages.LoginPage;
import pages.Page;
import users.Credentials;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Action {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;

    private int rate;

    public int getRate() {
        return rate;
    }

    private int count;

    private Filter filters;

    private String movie;

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public Filter getFilters() {
        return filters;
    }

    public int getCount() {
        return count;
    }

    private String startsWith;

    public String getStartsWith() {
        return startsWith;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     *
     * @return new user added
     */
    public User setNewUser() {
        User newUser = new User();
        newUser.setCredentials(this.getCredentials());
        newUser.setAvailableMovies(Application.getInstance().getMovies());
        return newUser;
    }

    /**
     *
     * @param movieName to search if exists
     * @return found movie
     */
    public Movie searchForMovie(final String movieName) {
        for (Movie movie : Application.getSeeDetailsPage().getFilteredListMovies()) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }
        return null;
    }

    /**
     *
     * @param output -
     * @param application main object
     */
    public void changePage(final ArrayNode output,
                           final Application application) {
        Page currentPage = Application.getCurrentPage();
        OutputHandler outputHandler = new OutputHandler();
        if (!currentPage.getAvailablePages().contains(this.getPage())) {
            output.add(outputHandler.standardError());
        } else {
            if (this.getPage().equals("logout")) {
                application.setCurrentUser(null);
            }
            if (this.getPage().equals("movies")) {
                Application.getSeeDetailsPage().setFilteredListMovies(Application.getInstance().getCurrentUser().getAvailableMovies());
                output.add(outputHandler.userOutput("movies",application.getCurrentUser()));
            }
            if (this.getPage().equals("see details")) {
                Movie foundMovie = searchForMovie(this.getMovie());
                if (foundMovie != null) {
                    Application.getSeeDetailsPage().setCurrentMovie(foundMovie);
                    output.add(outputHandler.oneMovie(Application.getInstance().getCurrentUser().getAvailableMovies(),
                               Application.getSeeDetailsPage().getCurrentMovie()));
                } else {
                    output.add(outputHandler.standardError());
                    return;
                }
            }
            switchPage(this.getPage());
        }
    }

    /**
     *
     * @param genres array
     * @param movies array
     */
    public void filterWithGenres(final ArrayList<String> genres,
                                 final ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            boolean foundGenre = false;
            for (String genre : movies.get(i).getGenres()) {
                if (genres.contains(genre)) {
                    foundGenre = true;
                    break;
                }
            }
            if (!foundGenre) {
                movies.remove(movies.get(i));
                i--;
            }
        }
    }

    /**
     *
     * @param actors array
     * @param movies array
     */
    public void filterWithActors(final ArrayList<String> actors,
                                 final ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            boolean foundActor = false;
            for (String actor : movies.get(i).getActors()) {
                if (actors.contains(actor)) {
                    foundActor = true;
                    break;
                }
            }
            if (!foundActor) {
                movies.remove(movies.get(i));
                i--;
            }
        }
    }

    /**
     *
     * @param movies array
     */
    public void filterWithContains(final ArrayList<Movie> movies) {
        ArrayList<String> actorsToFind = this.getFilters().getContains().getActors();
        ArrayList<String> genresToFind = this.getFilters().getContains().getGenre();

        if (actorsToFind != null)
            filterWithActors(actorsToFind, movies);

        if (genresToFind != null)
            filterWithGenres(genresToFind, movies);
    }

    /**
     *
     * @param movies array
     */
    public void filterWithDuration(final ArrayList<Movie> movies) {
        if (this.getFilters().getSort().getDuration().equals("decreasing")) {
            movies.sort((o1, o2) -> o2.getDuration() - o1.getDuration());
        } else {
            movies.sort(Comparator.comparingInt(Movie::getDuration));
        }
    }

    /**
     *
     * @param movies array
     */
    public void filterWithRating(final ArrayList<Movie> movies) {
        if (this.getFilters().getSort().getRating().equals("decreasing")) {
            movies.sort((o1, o2) -> (int) (o2.getRating() - o1.getRating()));
        } else {
            movies.sort((o1, o2) -> (int) (o1.getRating() - o2.getRating()));
        }
    }

    /**
     *
     * @param movies array to sort
     */
    public void filterWithBothSort(final ArrayList<Movie> movies) {
        Sort sort = this.getFilters().getSort();
        if (sort.getDuration().equals("decreasing")) {
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if (o1.getDuration() != o2.getDuration()) {
                        return o2.getDuration() - o1.getDuration();
                    } else {
                        if (sort.getRating().equals("decreasing")) {
                            return (int) (o2.getRating() - o1.getRating());
                        } else {
                            return (int) (o1.getRating() - o2.getRating());
                        }
                    }
                }
            });
        } else {
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if (o1.getDuration() != o2.getDuration()) {
                        return o1.getDuration() - o2.getDuration();
                    } else {
                        if (sort.getRating().equals("decreasing")) {
                            return (int) (o2.getRating() - o1.getRating());
                        } else {
                            return (int) (o1.getRating() - o2.getRating());
                        }
                    }
                }
            });
        }
    }

    /**
     * filter movies
     */
    public void filterMovies() {
        ArrayList<Movie> filteredMovies = new ArrayList<>(Application.getInstance().getCurrentUser().getAvailableMovies());

        if (this.getFilters().getContains() != null) {
            filterWithContains(filteredMovies);
        }

        if (this.getFilters().getSort() != null) {
            if (this.getFilters().getSort().getRating() != null &&
                this.getFilters().getSort().getDuration() != null) {
                filterWithBothSort(filteredMovies);
            } else if (this.getFilters().getSort().getRating() != null) {
                filterWithRating(filteredMovies);
            } else {
                filterWithDuration(filteredMovies);
            }
        }
        Application.getSeeDetailsPage().setFilteredListMovies(filteredMovies);
    }

    /**
     *
     * @param user current
     * @param movieToPurchase for user
     * @return if movie was already purchased
     */
    public boolean alreadyPurchased(final User user,
                                    final Movie movieToPurchase) {
        for (Movie movie : user.getPurchasedMovies()) {
            if (movie.getName().equals(movieToPurchase.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param movie to add to array
     */
    public void addMovieToPurchased(final Movie movie) {
        Application.getInstance().getCurrentUser().getPurchasedMovies().add(movie);
    }

    /**
     *
     * @param user current
     * @param movieToWatch -
     * @return if movie was already watched
     */
    public boolean alreadyWatched(final User user,
                                  final Movie movieToWatch) {
        for (Movie movie : user.getPurchasedMovies()) {
            if (movie.getName().equals(movieToWatch.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param movieToAdd to watched movies
     */
    public void addMovieToWatched(final Movie movieToAdd) {
        Application.getInstance().getCurrentUser().getWatchedMovies().add(movieToAdd);
    }

    /**
     *
     * @param movieName to add to liked movies
     */
    public void addMovieToLiked(final String movieName) {
        for (Movie movie : Application.getInstance().getCurrentUser().getWatchedMovies()) {
            if (movie.getName().equals(movieName)) {
                Application.getInstance().getCurrentUser().getLikedMovies().add(movie);
                movie.increaseNumLikes();
                return;
            }
        }
    }

    /**
     *
     * @param movieToAdd to rated array
     */
    public void addMovieToRated(final Movie movieToAdd) {
        Application.getInstance().getCurrentUser().getRatedMovies().add(movieToAdd);
        movieToAdd.setRating(this.getRate());
        movieToAdd.calculateRating(this.rate);
    }

    /**
     *
     * @return if user has enough balance
     */
    public boolean userHasEnoughBalance() {
        int balance = Integer.parseInt(Application.getInstance().getCurrentUser().getCredentials().getBalance());
        return balance >= this.getCount();
    }

    /**
     *
     * @param tokens amount
     * @return if tokens are enough
     */
    public boolean userHasEnoughTokens(final int tokens) {
        return Application.getInstance().getCurrentUser().getTokensCount() >= tokens;
    }

    /**
     *
     * @param output -
     * @param application main object
     */
    public void onPage(final ArrayNode output, final Application application) {
        Page currentPage = Application.getCurrentPage();
        OutputHandler outputHandler = new OutputHandler();
        if (!currentPage.getAvailableEvents().contains(this.getFeature())) {
            output.add(outputHandler.standardError());
        } else {
            switch (this.getFeature()) {
                case "login" -> {
                    boolean userExists = false;
                    User user = null;
                    for (int i = 0; i < application.getUsers().size(); i++) {
                        user = application.getUsers().get(i);
                        if (user.getCredentials().getName().equals(this.getCredentials().getName())
                                && user.getCredentials().getPassword().equals(this.getCredentials().getPassword())) {
                            userExists = true;
                            break;
                        }
                    }
                    if (userExists) {
                        Application.getSeeDetailsPage().setFilteredListMovies(user.getAvailableMovies());
                        switchPage("homepage authenticated");
                        application.setCurrentUser(user);
                        output.add(outputHandler.userOutput("homepage authenticated", application.getCurrentUser()));
                    } else {
                        output.add(outputHandler.standardError());
                        switchPage("homepage unauthenticated");
                    }
                }
                case "register" -> {
                    String name = this.getCredentials().getName();
                    String password = this.getCredentials().getPassword();
                    boolean userExists = false;
                    for (int i = 0; i < application.getUsers().size(); i++) {
                        User user = application.getUsers().get(i);
                        if (user.getCredentials().getName().equals(name) &&
                                user.getCredentials().getPassword().equals(password)) {
                            userExists = true;
                            break;
                        }
                    }
                    if (userExists) {
                        output.add(outputHandler.standardError());
                    } else {
                        User newUser = setNewUser();
                        application.addUser(newUser);
                        switchPage("homepage authenticated");
                        application.setCurrentUser(newUser);
                        output.add(outputHandler.userOutput("homepage authenticated", newUser));
                        Application.getSeeDetailsPage().setFilteredListMovies(newUser.getAvailableMovies());
                    }
                }
                case "logout" -> {
                    switchPage("homepage unauthenticated");
                    application.setCurrentUser(null);
                }
                case "search" -> output.add(outputHandler.searchMovies(application.getCurrentUser(), this.getStartsWith()));
                case "filter" -> {
                    filterMovies();
                    output.add(outputHandler.filteredMovies(Application.getInstance().getCurrentUser()));
                }
                case "purchase" -> {
                    if (alreadyPurchased(Application.getInstance().getCurrentUser(),
                            Application.getSeeDetailsPage().getCurrentMovie())) {
                        output.add(outputHandler.standardError());
                    } else {
                        int numFreePremiumMovies = Application.getInstance().getCurrentUser().getNumFreePremiumMovies();

                        if (numFreePremiumMovies < 1 || Application.getInstance().getCurrentUser().getCredentials().getAccountType().equals("standard")) {
                            if (userHasEnoughTokens(2)) {
                                Application.getInstance().getCurrentUser().setTokensCount(Application.getInstance().getCurrentUser().getTokensCount() - 2);
                            }
                        } else {
                            Application.getInstance().getCurrentUser().setNumFreePremiumMovies(numFreePremiumMovies - 1);
                        }

                        addMovieToPurchased(Application.getSeeDetailsPage().getCurrentMovie());

                        output.add(outputHandler.oneMovie(Application.getSeeDetailsPage().getFilteredListMovies(),
                                   Application.getSeeDetailsPage().getCurrentMovie()));
                    }
                }
                case "watch" -> {
                    if (!alreadyPurchased(Application.getInstance().getCurrentUser(),
                            Application.getSeeDetailsPage().getCurrentMovie())) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    addMovieToWatched(Application.getSeeDetailsPage().getCurrentMovie());
                    output.add(outputHandler.oneMovie(Application.getSeeDetailsPage().getFilteredListMovies(),
                               Application.getSeeDetailsPage().getCurrentMovie()));
                }
                case "like" -> {
                    if (!alreadyWatched(Application.getInstance().getCurrentUser(), Application.getSeeDetailsPage().getCurrentMovie())) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    addMovieToLiked(this.getMovie());
                    output.add(outputHandler.oneMovie(Application.getSeeDetailsPage().getFilteredListMovies(),
                                                      Application.getSeeDetailsPage().getCurrentMovie()));
                }
                case "rate" -> {
                    if (this.getRate() > 5) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    if (!alreadyWatched(Application.getInstance().getCurrentUser(),
                            Application.getSeeDetailsPage().getCurrentMovie())) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    addMovieToRated(Application.getSeeDetailsPage().getCurrentMovie());
                    output.add(outputHandler.oneMovie(Application.getSeeDetailsPage().getFilteredListMovies(),
                                Application.getSeeDetailsPage().getCurrentMovie()));
                }
                case "buy tokens" -> {
                    if (!userHasEnoughBalance()) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    Application.getInstance().getCurrentUser().increaseTokens(this.getCount());
                    Application.getInstance().getCurrentUser().decreaseBalance(this.getCount());
                }
                case "buy premium account" -> {
                    if (!userHasEnoughTokens(10)) {
                        output.add(outputHandler.standardError());
                        break;
                    }
                    Application.getInstance().getCurrentUser().getCredentials().setAccountType("premium");
                    Application.getInstance().getCurrentUser().decreaseTokens(10);
                }
            }
        }
    }

    /**
     *
     * @param page to change
     */
    public static void switchPage(final String page) {
        switch (page) {
            case "login" -> {
                Application.setCurrentPage(Application.getLoginPage());
                Application.getSeeDetailsPage().setCurrentMovie(null);
            }
            case "register" -> {
                Application.setCurrentPage(Application.getRegisterPage());
                Application.getSeeDetailsPage().setCurrentMovie(null);
            }
            case "movies" -> {
                Application.setCurrentPage(Application.getMoviesPage());
                Application.getSeeDetailsPage().setCurrentMovie(null);
            }
            case "logout", "homepage unauthenticated" -> {
                Application.setCurrentPage(Application.getHomePageUnauth());
                Application.getSeeDetailsPage().setCurrentMovie(null);
                Application.getInstance().setCurrentUser(null);
            }
            case "see details" -> Application.setCurrentPage(Application.getSeeDetailsPage());
            case "upgrades" -> {
                Application.setCurrentPage(Application.getUpgradesPage());
                Application.getSeeDetailsPage().setCurrentMovie(null);
            }
            case "homepage authenticated" -> {
                Application.setCurrentPage(Application.getHomePageAuth());
                Application.getSeeDetailsPage().setCurrentMovie(null);
            }
        }
    }
}
