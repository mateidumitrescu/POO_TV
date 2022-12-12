package application;

import actions.Action;
import input.Input;
import movies.Movie;
import pages.*;
import users.User;

import java.util.ArrayList;

public class Application {

    private ArrayList<Action> actions;

    private Page currentPage;

    private ArrayList<User> users;

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
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

    private static Application instance = null;

    private HomePageUnauth homePageUnauth;
    private HomePageAuth homePageAuth;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private MoviesPage moviesPage;
    private RegisterPage registerPage;
    private SeeDetailsPage seeDetailsPage;
    private UpgradesPage upgradesPage;

    public HomePageUnauth getHomePageUnauth() {
        return homePageUnauth;
    }

    public void setHomePageUnauth(HomePageUnauth homePageUnauth) {
        this.homePageUnauth = homePageUnauth;
    }

    public HomePageAuth getHomePageAuth() {
        return homePageAuth;
    }

    public void setHomePageAuth(HomePageAuth homePageAuth) {
        this.homePageAuth = homePageAuth;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public LogoutPage getLogoutPage() {
        return logoutPage;
    }

    public void setLogoutPage(LogoutPage logoutPage) {
        this.logoutPage = logoutPage;
    }

    public MoviesPage getMoviesPage() {
        return moviesPage;
    }

    public void setMoviesPage(MoviesPage moviesPage) {
        this.moviesPage = moviesPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    public SeeDetailsPage getSeeDetailsPage() {
        return seeDetailsPage;
    }

    public void setSeeDetailsPage(SeeDetailsPage seeDetailsPage) {
        this.seeDetailsPage = seeDetailsPage;
    }

    public UpgradesPage getUpgradesPage() {
        return upgradesPage;
    }

    public void setUpgradesPage(UpgradesPage upgradesPage) {
        this.upgradesPage = upgradesPage;
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
        this.setCurrentPage(homePageUnauth);

    }

    public static Application getInstance(Input inputData) {
        if (instance == null) {
            instance = new Application(inputData);
        }
        return instance;
    }

}
