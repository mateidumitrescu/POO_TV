package pages;

import java.util.ArrayList;

public abstract class Page {
    Page() {}
    public ArrayList<String> getAvailablePages() {
        return availablePages;
    }

    private ArrayList<String> availablePages;

    private ArrayList<String> availableEvents;

    public ArrayList<String> getAvailableEvents() {
        return availableEvents;
    }
}
