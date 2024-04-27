package Organizer;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class TpbmCreator {

    public static void createTpbmArchive(String musicFilePath, String photoFilePath, List<Float> spawnTimes, List<Float> beatTimes, String outputFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // Add music file to the archive
            addFileToZip(musicFilePath, "music.mp3", zos);

            // Add photo file to the archive
            addFileToZip(photoFilePath, "photo.png", zos);

            // Create and add metadata (.tbp) file to the archive
            String tbpContent = generateTbpContent(spawnTimes, beatTimes, "music.mp3", "photo.png");
            addStringToZip(tbpContent, "metadata.tbp", zos);
        }
    }

    private static void addFileToZip(String sourceFilePath, String entryName, ZipOutputStream zos) throws IOException {
        File file = new File(sourceFilePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }

            zos.closeEntry();
        }
    }

    private static void addStringToZip(String content, String entryName, ZipOutputStream zos) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);
        zos.write(content.getBytes());
        zos.closeEntry();
    }

    private static String generateTbpContent(List<Float> spawnTimes, List<Float> beatTimes, String musicFilePath, String photoFilePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("MusicFilePath: ").append(musicFilePath).append("\n");
        sb.append("PhotoPath: ").append(photoFilePath).append("\n");
        sb.append("SpawnTimes: ").append(spawnTimes).append("\n");
        sb.append("BeatTimes: ").append(beatTimes).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Example usage
        String musicFilePath = "assets/beatmaps/song.mp3";
        String photoFilePath = "assets/beatmaps/cover.png";
        List<Float> spawnTimes = Arrays.asList(0.5f, 1.0f, 1.5f);
        List<Float> beatTimes = Arrays.asList(3.0f, 4.0f, 5.0f);
        String outputFilePath = "output/map.tpbm";

        try {
            createTpbmArchive(musicFilePath, photoFilePath, spawnTimes, beatTimes, outputFilePath);
            System.out.println("Successfully created .tpbm archive.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
