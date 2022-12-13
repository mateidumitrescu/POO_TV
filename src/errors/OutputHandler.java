package errors;

import application.Application;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import movies.Movie;
import users.User;

import java.util.ArrayList;

public class OutputHandler {

    public ObjectNode standardError() {
        //System.out.println("LALALKCKDKFDKSC");
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("error", "Error");
        objectNode.putArray("currentMoviesList");
        objectNode.put("currentUser", (JsonNode) null);
        return objectNode;
    }

    public void createMovieNodes(ObjectNode objectNode, String message, ArrayList<Movie> movies) {
        ArrayNode movieNodes = objectNode.putArray(message);
        for (Movie movie : movies) {
            ObjectNode node = new ObjectMapper().createObjectNode();
            node.put("name", movie.getName());
            node.put("year", movie.getYear());
            node.put("duration", movie.getDuration());
            ArrayNode nodeGenres = node.putArray("genres");
            for (String genre : movie.getGenres()) {
                nodeGenres.add(genre);
            }
            ArrayNode nodeActors = node.putArray("actors");
            for (String actor : movie.getActors()) {
                nodeActors.add(actor);
            }
            ArrayNode nodeCountriesBanned = node.putArray("countriesBanned");
            for (String country : movie.getCountriesBanned()) {
                nodeCountriesBanned.add(country);
            }
            node.put("numlikes", movie.getNumLikes());
            node.put("rating", movie.getRating());
            node.put("numRatings", movie.getNumRatings());
            movieNodes.add(node);
        }
    }

    public ObjectNode userOutput(String page, User user) {
        //System.out.println("?????????????????");
        Application application = Application.getInstance();
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("error", (JsonNode) null);
        if (!page.equals("movies")) {
            ArrayList<Movie> nullMovies = new ArrayList<>();
            createMovieNodes(objectNode, "currentMoviesList", nullMovies);
        } else {
            createMovieNodes(objectNode, "currentMoviesList", application.getMovies());
        }
        ObjectNode currentUser = new ObjectMapper().createObjectNode();
        ObjectNode credentials = new ObjectMapper().createObjectNode();
        credentials.put("name", user.getCredentials().getName());
        credentials.put("password", user.getCredentials().getPassword());
        credentials.put("accountType", user.getCredentials().getAccountType());
        credentials.put("country", user.getCredentials().getCountry());
        credentials.put("balance", user.getCredentials().getBalance());
        currentUser.put("credentials", credentials);

        currentUser.put("tokensCount", user.getTokensCount());
        currentUser.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
        createMovieNodes(currentUser, "purchasedMovies", user.getPurchasedMovies());
        createMovieNodes(currentUser, "watchedMovies", user.getWatchedMovies());
        createMovieNodes(currentUser, "likedMovies", user.getLikedMovies());
        createMovieNodes(currentUser, "ratedMovies", user.getRatedMovies());

        objectNode.put("currentUser", currentUser);

        return objectNode;
    }
}
