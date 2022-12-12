package pages;

public class SeeDetailsPage extends Page {
    public SeeDetailsPage() {
        super.getAvailablePages().add("homepage authenticated");
        super.getAvailablePages().add("movies");
        super.getAvailablePages().add("upgrades");
        super.getAvailablePages().add("logout");
        super.getAvailableEvents().add("purchase");
        super.getAvailableEvents().add("watch");
        super.getAvailableEvents().add("like");
        super.getAvailableEvents().add("rate");
    }
}
