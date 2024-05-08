package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TBPFileReader {

    public static BeatmapData readTBPFile(String filePath) {
        BeatmapData beatmapData = new BeatmapData();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                if (line.startsWith("audioPath:")) {
                    beatmapData.setAudioPath(line.substring("audioPath:".length()).trim());
                } else if (line.startsWith("backgroundPath:")) {
                    beatmapData.setBackgroundPath(line.substring("backgroundPath:".length()).trim());
                } else if (line.startsWith("musicID:")) {
                    beatmapData.setMusicID(Integer.parseInt(line.substring("musicID:".length()).trim()));
                } else if (line.equals("sT, bT, letter")) {
                    // Start reading beatmap data
                    List<BeatData> beatDataList = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            float spawnTime = Float.parseFloat(parts[0].trim());
                            float beatTime = Float.parseFloat(parts[1].trim());
                            char letter = parts[2].trim().charAt(0);
                            BeatData beatData = new BeatData(spawnTime, beatTime, letter);
                            beatDataList.add(beatData);
                        }
                    }
                    beatmapData.setBeatDataList(beatDataList);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return beatmapData;
    }

    public static class BeatmapData {
        private String audioPath;
        private String backgroundPath;
        private int musicID;
        private List<BeatData> beatDataList;

        public String getAudioPath() {
            return audioPath;
        }

        public void setAudioPath(String audioPath) {
            this.audioPath = audioPath;
        }

        public String getBackgroundPath() {
            return backgroundPath;
        }

        public void setBackgroundPath(String backgroundPath) {
            this.backgroundPath = backgroundPath;
        }

        public int getMusicID() {
            return musicID;
        }

        public void setMusicID(int musicID) {
            this.musicID = musicID;
        }

        public List<BeatData> getBeatDataList() {
            return beatDataList;
        }

        public void setBeatDataList(List<BeatData> beatDataList) {
            this.beatDataList = beatDataList;
        }
    }

    public static class BeatData {
        private float spawnTime;
        private float beatTime;
        private char letter;

        public BeatData(float spawnTime, float beatTime, char letter) {
            this.spawnTime = spawnTime;
            this.beatTime = beatTime;
            this.letter = letter;
        }

        public float getSpawnTime() {
            return spawnTime;
        }

        public float getBeatTime() {
            return beatTime;
        }

        public char getLetter() {
            return letter;
        }
    }
}
