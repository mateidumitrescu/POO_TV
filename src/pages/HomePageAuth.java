package pages;

import java.util.ArrayList;

public class HomePageAuth extends Page {
    public HomePageAuth() {
        super.getAvailablePages().add("logout");
        super.getAvailablePages().add("upgrades");
        super.getAvailablePages().add("movies");
    }
}
