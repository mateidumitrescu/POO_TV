package pages;

import java.util.ArrayList;

public abstract class Page {

    Page() {
        availablePages = new ArrayList<>();
        availableEvents = new ArrayList<>();
    }

    /**
     *
     * @return available pages for page
     */
    public ArrayList<String> getAvailablePages() {
        return availablePages;
    }

    private ArrayList<String> availablePages;

    private ArrayList<String> availableEvents;

    /**
     *
     * @return available events for page
     */
    public ArrayList<String> getAvailableEvents() {
        return availableEvents;
    }
}
