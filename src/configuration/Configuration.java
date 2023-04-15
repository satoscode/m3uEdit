package configuration;

public class Configuration {
    public static final String FILE_PATH = "src/resources/completo-list.m3u";
    public static final String FILE_PATH_OUTPUT = "src/resources/movieList.m3u";
    public static final String CONTENT_MATCHER = "#EXTINF:-1 tvg-id=\"\" tvg-name=\"(.*?)\" tvg-logo=\"(.*?)\" group-title=\"(.*?)\",(.*)";
    public static final String MATCH_FORMATTED_CONTENT = "#EXTINF:0 group-title=\"(.*?)\",HD : (.*)";

}
