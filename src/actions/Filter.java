package actions;

public class Filter {
    private Sort sort;

    private Contain contains;

    public Contain getContains() {
        return contains;
    }

    public void setContains(Contain contains) {
        this.contains = contains;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
