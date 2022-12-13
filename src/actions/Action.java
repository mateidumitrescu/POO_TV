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

public class Action {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;

    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    private int count;

    private Filter filters;

    private String movie;

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String startsWith;

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
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

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public User setNewUser() {
        User newUser = new User();
        newUser.setCredentials(this.getCredentials());
        return newUser;
    }

    public void changePage(ArrayNode output, Application application) {
        Page currentPage = Application.getCurrentPage();
        OutputHandler outputHandler = new OutputHandler();
        if (!currentPage.getAvailablePages().contains(this.getPage())) {
            output.add(outputHandler.standardError());
        } else {
            switchPage(this.getPage());
            if (this.getPage().equals("logout")) {
                application.setCurrentUser(null);
            }
        }
    }

    public void onPage(ArrayNode output, Application application) {
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
                    }
                }
                case "logout" -> {
                    switchPage("homepage unauthenticated");
                    application.setCurrentUser(null);
                }
            }
        }
    }

    public static void switchPage(String page) {
        switch (page) {
            case "login" -> Application.setCurrentPage(Application.getLoginPage());
            case "register" -> Application.setCurrentPage(Application.getRegisterPage());
            case "movies" -> Application.setCurrentPage(Application.getMoviesPage());
            case "logout", "homepage unauthenticated" -> Application.setCurrentPage(Application.getHomePageUnauth());
            case "see details" -> Application.setCurrentPage(Application.getSeeDetailsPage());
            case "upgrades" -> Application.setCurrentPage(Application.getUpgradesPage());
            case "homepage authenticated" -> Application.setCurrentPage(Application.getHomePageAuth());
        }
    }
}
