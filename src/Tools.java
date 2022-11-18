import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static configuration.Configuration.*;

public class Tools {
    public static void formatList() throws IOException {
        //read file
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Pattern pattern = Pattern.compile(CONTENT_MATCHER);
        Matcher matcher = pattern.matcher(content);

        //convert to format
        while (matcher.find()) {
            String tvgName = matcher.group(1);
            String tvgLogo = matcher.group(2);

            String groupTitle = matcher.group(3);
            ////remove (,),/,.(dot sign) from groupTitle
            groupTitle = groupTitle.replaceAll("[()/.]", "");
            //replace + to Plus in groupTitle
            groupTitle = groupTitle.replaceAll("\\+", "Plus");
            //remove more than two empty spaces in groupTitle
            groupTitle = groupTitle.replaceAll("\\s{2,}", " ");

            String title = matcher.group(4);
            //remove /,.,|,:,",',$,● characters from title
            //remove more than two empty spaces
            title = title.replaceAll("[/.|:\"'$●]", "").replaceAll("\\s{2,}", " ");
            //title = title.replaceAll("[\\(\\)\\./\\|:\"'\\$●]", "").replaceAll("\\s{2,}", " ");
            //remove ?,¿,!,¡,*,#,\ characters from title
            title = title.replaceAll("[\\?\\¿\\!\\¡\\*\\#\\\\]", "");

            //replace
            content = content.replaceFirst(CONTENT_MATCHER, "#EXTINF:0 group-title=\"" + groupTitle + "\",HD : " + title + System.lineSeparator() +"#EXTGRP:" + groupTitle );

            //write in file
            Files.write(Paths.get(FILE_PATH), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

            //print counter off while loop
            System.out.println("added: " + title);
        }
    }

    public static void createListByCategory(String category) throws IOException {
        //read file
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Pattern pattern = Pattern.compile(MATCH_FORMATTED_CONTENT);
        Matcher matcher = pattern.matcher(content);
        //get a list of group-title
        //there must be no repeated elements
        while (matcher.find()) {
            //contains group-title="x"
            String groupTitle = matcher.group(1);

            //delete the rest of the lines that do not belong to the group-title="x"
            if (!groupTitle.equals(category)) {
                //delete line and next line and next line
                content = content.replaceFirst("#EXTINF:0 group-title="+"\""+groupTitle+"\",HD : (.*)\\r?\\n#EXTGRP:"+groupTitle+"\\r?\\n(.*)\\r?\\n", "");
                //write in file
                Files.write(Paths.get(FILE_PATH_OUTPUT), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                //print counter off while loop
                System.out.println("deleted: " + groupTitle);
            }
        }
    }

    public static void ShowCategoryList () throws IOException {
        //read file
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Pattern pattern = Pattern.compile(MATCH_FORMATTED_CONTENT);
        Matcher matcher = pattern.matcher(content);
        //get a list of group-title
        //there must be no repeated elements
        String listGroup = "";
        while (matcher.find()) {
            String groupTitle = matcher.group(1);
            if (!listGroup.contains(groupTitle)) {
                listGroup = listGroup + groupTitle + System.lineSeparator();
            }
        }
        //order listGroup
        String[] listGroupArray = listGroup.split(System.lineSeparator());
        for (int i = 0; i < listGroupArray.length; i++) {
            for (int j = i + 1; j < listGroupArray.length; j++) {
                if (listGroupArray[i].compareTo(listGroupArray[j]) > 0) {
                    String temp = listGroupArray[i];
                    listGroupArray[i] = listGroupArray[j];
                    listGroupArray[j] = temp;
                }
            }
        }
        //System.out.printf("listGroup: %s", listGroup);
        //print ordered listGroup
        for (int i = 0; i < listGroupArray.length; i++) {
            System.out.println(listGroupArray[i]);
        }
    }

    public static void deleteCategory (String contentDelete) throws IOException {
        //read file
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Pattern pattern = Pattern.compile(MATCH_FORMATTED_CONTENT);
        Matcher matcher = pattern.matcher(content);
        //then delete line and next line and next line in all file
        while (matcher.find()) {
            //contains English
            String groupTitle = matcher.group(1);
            if (groupTitle.equals(contentDelete)) {
                //delete line and next line and next line
                content = content.replaceFirst("#EXTINF:0 group-title="+"\""+contentDelete+"\",HD : (.*)\\r?\\n#EXTGRP:"+contentDelete+"\\r?\\n(.*)\\r?\\n", "");
                //write in file
                Files.write(Paths.get(FILE_PATH), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                //print counter off while loop
                System.out.println("deleted: " + groupTitle);
            }
        }
    }

    public static void makeFolders () throws IOException {

        //read file
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Pattern pattern = Pattern.compile(MATCH_FORMATTED_CONTENT);
        Matcher matcher = pattern.matcher(content);

        //get a list of group-title
        String listGroup = "";
        while (matcher.find()) {
            String groupTitle = matcher.group(1);
            if (!listGroup.contains(groupTitle)) {
                listGroup = listGroup + groupTitle + System.lineSeparator();
            }
        }

        //order listGroup
        String[] listGroupArray = listGroup.split(System.lineSeparator());
        for (int i = 0; i < listGroupArray.length; i++) {
            for (int j = i + 1; j < listGroupArray.length; j++) {
                if (listGroupArray[i].compareTo(listGroupArray[j]) > 0) {
                    String temp = listGroupArray[i];
                    listGroupArray[i] = listGroupArray[j];
                    listGroupArray[j] = temp;
                }
            }
        }

        //create folders
        for (int i = 0; i < listGroupArray.length; i++) {
            //make a folder for each category
            File file = new File("src/Resourses/" + listGroupArray[i]);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
        }

        //read file
        Pattern pattern2 = Pattern.compile("#EXTINF:0 group-title=\"(.*)\",HD : (.*)\\r?\\n#EXTGRP:(.*)\\r?\\n(.*)\\r?\\n");
        Matcher matcher2 = pattern2.matcher(content);

        //create files.strm
        while (matcher2.find()) {

            String groupTitle = matcher2.group(1);
            String nameMovie2 = matcher2.group(2);
            //remove characters from nameMovie2 for create file in windows
            nameMovie2 = nameMovie2.replaceAll("[\\\\/:*?\"<>|]", "");
            String pathMovie = matcher2.group(4);

            //make a file.strm in each directory
            File file = new File("src/Resourses/" + groupTitle + "/" + nameMovie2 + ".strm");

            //print log create file
            System.out.println(nameMovie2);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File is created!" + file);
                } else {
                    System.out.println("Failed to create file!");
                }
            }

            //write in files.strm the path to the movie
            Files.write(Paths.get("src/resources/" + groupTitle + "/" + nameMovie2 + ".strm"), pathMovie.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
