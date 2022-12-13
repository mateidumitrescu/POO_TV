package application;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;
import movies.Movie;
import pages.*;
import users.User;

import java.util.ArrayList;

public class Application {

    private ArrayList<Action> actions;

    private static Page currentPage;

    private ArrayList<User> users;

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public static Page getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(Page currentPage) {
        Application.currentPage = currentPage;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    private ArrayList<Movie> movies;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private static Application instance = null;

    private static HomePageUnauth homePageUnauth;
    private static HomePageAuth homePageAuth;
    private static LoginPage loginPage;
    private static LogoutPage logoutPage;
    private static MoviesPage moviesPage;
    private static RegisterPage registerPage;
    private static SeeDetailsPage seeDetailsPage;
    private static UpgradesPage upgradesPage;

    public void addUser(User user) {
        getUsers().add(user);
    }

    private boolean userLoggedIn;

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public static HomePageUnauth getHomePageUnauth() {
        return homePageUnauth;
    }

    public void setHomePageUnauth(HomePageUnauth homePageUnauth) {
        Application.homePageUnauth = homePageUnauth;
    }

    public static HomePageAuth getHomePageAuth() {
        return homePageAuth;
    }

    public void setHomePageAuth(HomePageAuth homePageAuth) {
        Application.homePageAuth = homePageAuth;
    }

    public static LoginPage getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        Application.loginPage = loginPage;
    }

    public static LogoutPage getLogoutPage() {
        return logoutPage;
    }

    public void setLogoutPage(LogoutPage logoutPage) {
        Application.logoutPage = logoutPage;
    }

    public static MoviesPage getMoviesPage() {
        return moviesPage;
    }

    public void setMoviesPage(MoviesPage moviesPage) {
        Application.moviesPage = moviesPage;
    }

    public static RegisterPage getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(RegisterPage registerPage) {
        Application.registerPage = registerPage;
    }

    public static SeeDetailsPage getSeeDetailsPage() {
        return seeDetailsPage;
    }

    public void setSeeDetailsPage(SeeDetailsPage seeDetailsPage) {
        Application.seeDetailsPage = seeDetailsPage;
    }

    public static UpgradesPage getUpgradesPage() {
        return upgradesPage;
    }

    public void setUpgradesPage(UpgradesPage upgradesPage) {
        Application.upgradesPage = upgradesPage;
    }

    private Application(Input inputData) {
        setActions(inputData.getActions());
        setMovies(inputData.getMovies());
        setUsers(inputData.getUsers());
        homePageAuth = new HomePageAuth();
        homePageUnauth = new HomePageUnauth();
        loginPage = new LoginPage();
        logoutPage = new LogoutPage();
        moviesPage = new MoviesPage();
        registerPage = new RegisterPage();
        seeDetailsPage = new SeeDetailsPage();
        upgradesPage = new UpgradesPage();
    }

    public static Application getInstance(Input inputData) {
        if (instance == null) {
            instance = new Application(inputData);
        }
        return instance;
    }

    public static Application getInstance() {
        return instance;
    }

    public static void setApplication(Application instance) {
        Application.instance = instance;
    }

    public void startActions(ArrayNode output) {
        setCurrentPage(homePageUnauth);
        this.setUserLoggedIn(false);
        int counter  = 1;
        for (Action action : this.getActions()) {
            //System.out.println("\nCurrent page is " + currentPage + " before command " + counter);
            switch (action.getType()) {
                case "change page" -> action.changePage(output, this);
                case "on page" -> action.onPage(output, this);
            }
            //System.out.println("Current user is " + currentUser + " after command " + counter);
            //System.out.println("Current page is " + currentPage + " after command " + counter);
            counter++;
        }
    }
}
