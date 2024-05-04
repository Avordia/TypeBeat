package Parser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TbpParser {

    public static TbpData parseTbpFile(String tbpFilePath) throws IOException {
        List<Float> spawntimes = new ArrayList<>();
        List<Float> beattimes = new ArrayList<>();
        List<Character> characters = new ArrayList<>();
        String musicPath = null;
        String imagePath = null;

        // Get a FileHandle to the TBP file within the LibGDX assets
        FileHandle fileHandle = Gdx.files.internal(tbpFilePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileHandle.read()))) {
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
                    // Parse beattime, spawntime, and character from the time data section
                    String[] tokens = line.split(",");
                    if (tokens.length >= 3) {
                        float beattime = Float.parseFloat(tokens[0].trim().replace("f", ""));
                        float spawntime = Float.parseFloat(tokens[1].trim().replace("f", ""));
                        char character = tokens[2].trim().charAt(0); // Get the first character
                        beattimes.add(beattime);
                        spawntimes.add(spawntime);
                        characters.add(character);
                    }
                }
            }
        }

        return new TbpData(spawntimes, beattimes, characters, musicPath, imagePath);
    }

    public static class TbpData {
        private List<Float> spawntimes;
        private List<Float> beattimes;
        private List<Character> characters;
        private String musicPath;
        private String imagePath;

        public TbpData(List<Float> spawntimes, List<Float> beattimes, List<Character> characters, String musicPath, String imagePath) {
            this.spawntimes = spawntimes;
            this.beattimes = beattimes;
            this.characters = characters;
            this.musicPath = musicPath;
            this.imagePath = imagePath;
        }

        public List<Float> getSpawntimes() {
            return spawntimes;
        }

        public List<Float> getBeattimes() {
            return beattimes;
        }

        public List<Character> getCharacters() {
            return characters;
        }

        public String getMusicPath() {
            return musicPath;
        }

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public String toString() {
            return "TbpData{" +
                    "spawntimes=" + spawntimes +
                    ", beattimes=" + beattimes +
                    ", characters=" + characters +
                    ", musicPath='" + musicPath + '\'' +
                    ", imagePath='" + imagePath + '\'' +
                    '}';
        }
    }
}
