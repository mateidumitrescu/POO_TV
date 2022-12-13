package pages;

import java.util.ArrayList;

public abstract class Page {
    public void setAvailablePages(ArrayList<String> availablePages) {
        this.availablePages = availablePages;
    }

    public void setAvailableEvents(ArrayList<String> availableEvents) {
        this.availableEvents = availableEvents;
    }

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
