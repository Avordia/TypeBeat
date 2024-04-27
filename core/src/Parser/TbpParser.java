package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TbpParser {

    public static TbpData parseTbpFile(String tbpFilePath) throws IOException {
        List<Float> spawntimes = new ArrayList<>();
        List<Float> beattimes = new ArrayList<>();
        String musicPath = null;
        String imagePath = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(tbpFilePath))) {
            String line;
            boolean inTimeSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (!inTimeSection) {
                    // Check for musicPath and imagePath headers
                    if (line.startsWith("musicPath:")) {
                        musicPath = line.substring("musicPath:".length()).trim();
                    } else if (line.startsWith("imagePath:")) {
                        imagePath = line.substring("imagePath:".length()).trim();
                    } else if (line.startsWith("Time:")) {
                        inTimeSection = true; // Switch to reading time data
                    }
                } else {
                    // Parse beattime and spawntime from the time data section
                    String[] tokens = line.split(",");
                    if (tokens.length >= 2) {
                        float beattime = Float.parseFloat(tokens[0].trim());
                        float spawntime = Float.parseFloat(tokens[1].trim());
                        beattimes.add(beattime);
                        spawntimes.add(spawntime);
                    }
                }
            }
        }

        return new TbpData(spawntimes, beattimes, musicPath, imagePath);
    }

    public static void main(String[] args) {
        String tbpFilePath = "path/to/your.tbp";
        try {
            TbpData tbpData = parseTbpFile(tbpFilePath);
            System.out.println("Parsed data: " + tbpData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TbpData {
    private List<Float> spawntimes;
    private List<Float> beattimes;
    private String musicPath;
    private String imagePath;

    public TbpData(List<Float> spawntimes, List<Float> beattimes, String musicPath, String imagePath) {
        this.spawntimes = spawntimes;
        this.beattimes = beattimes;
        this.musicPath = musicPath;
        this.imagePath = imagePath;
    }

    public List<Float> getBeattimes() {
        return beattimes;
    }

    public List<Float> getSpawntimes() {
        return spawntimes;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
