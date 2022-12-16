package pages;

import java.util.ArrayList;

public abstract class Page {

    Page() {
        availablePages = new ArrayList<>();
        availableEvents = new ArrayList<>();
    }
    public ArrayList<String> getAvailablePages() {
        return availablePages;
    }

    private ArrayList<String> availablePages;

    private ArrayList<String> availableEvents;

    public ArrayList<String> getAvailableEvents() {
        return availableEvents;
    }
}
