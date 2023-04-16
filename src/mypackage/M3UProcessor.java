package mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class M3UProcessor {

    private static final String BASE_PATH_TV = "TV";
    private static final String BASE_PATH_MOVIES = "Movies";

    public static void processM3UFile(String inputFilePath, String outputPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        String line;
        Pattern titlePattern = Pattern.compile("tvg-name=\"(.*?)\"");
        Pattern groupTitlePattern = Pattern.compile("group-title=\"(.*?)\"");
        Pattern seriesPattern = Pattern.compile(".*S(\\d{1,2}) E(\\d{1,2})");

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#EXTINF")) {
                Matcher titleMatcher = titlePattern.matcher(line);
                Matcher groupTitleMatcher = groupTitlePattern.matcher(line);

                if (titleMatcher.find()) {
                    String title = titleMatcher.group(1);
                    String groupTitle = groupTitleMatcher.find() ? groupTitleMatcher.group(1) : "";
                    title = sanitizeFileName(title);
                    groupTitle = sanitizeFileName(groupTitle);
                    Matcher seriesMatcher = seriesPattern.matcher(title);

                    line = reader.readLine(); // Read next line with URL

                    if (seriesMatcher.find()) {
                        // Series
                        if (line.contains("/series/")) {
                            String seriesName = title.substring(0, seriesMatcher.start()).trim();
                            seriesName = sanitizeFileName(seriesName);
                            String seasonNumber = seriesMatcher.group(1);
                            String episodeNumber = seriesMatcher.group(2);
                            String seriesDirectory = String.format("%s/%s/%s/%s/Season %s", outputPath, BASE_PATH_TV, groupTitle, seriesName, seasonNumber);
                            createDirectory(seriesDirectory);

                            String fileName = String.format("%s/%s S%sE%s.strm", seriesDirectory, seriesName, seasonNumber, episodeNumber);
                            writeFile(fileName, line);
                        }
                    } else {
                        // Movies
                        if (line.contains("/movie/")) {
                            String movieDirectory = String.format("%s/%s/%s/%s", outputPath, BASE_PATH_MOVIES, groupTitle, title);
                            createDirectory(movieDirectory);

                            String fileName = createUniqueMovieFileName(movieDirectory, title);
                            writeFile(fileName, line);
                        }
                    }
                }
            }
        }
        reader.close();
    }

    private static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static String createUniqueMovieFileName(String movieDirectory, String movieTitle) {
        String baseFileName = String.format("%s/%s", movieDirectory, movieTitle);
        String fileName = baseFileName + ".strm";
        int counter = 1;

        while (new File(fileName).exists()) {
            fileName = String.format("%s-%d.strm", baseFileName, counter);
            counter++;
        }

        return fileName;
    }

    private static String sanitizeFileName(String input) {
        String invalidCharacters = "[\\\\/:*?\"<>|]";
        input = input.replaceAll(invalidCharacters, "");
        input = input.replaceAll("^\\.+|\\.+$", ""); // Remove leading and trailing dots
        return input.trim();
    }

    private static void writeFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            Files.write(Paths.get(filePath), content.getBytes());
        }
    }
}
