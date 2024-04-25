package Organizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TpbFileWriter {

    public static void writeTpbFile(String filePath, List<Float> spawnTimes, List<Float> beatTimes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("# Custom .tpb file containing spawn times and beat times\n");
            writer.write("# SpawnTime,BeatTime\n");

            for (int i = 0; i < spawnTimes.size() && i < beatTimes.size(); i++) {
                float spawnTime = spawnTimes.get(i);
                float beatTime = beatTimes.get(i);
                writer.write(spawnTime + "," + beatTime + "\n");
            }

            System.out.println("Custom .tpb file created: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "custom.tpb";

        // Sample spawn times and beat times
        List<Float> spawnTimes = new ArrayList<>();
        spawnTimes.add(0.0f);
        spawnTimes.add(1.0f);
        spawnTimes.add(2.0f);
        spawnTimes.add(3.0f);
        spawnTimes.add(4.0f);

        List<Float> beatTimes = new ArrayList<>();
        beatTimes.add(1.0f);
        beatTimes.add(2.0f);
        beatTimes.add(3.0f);
        beatTimes.add(3.5f);
        beatTimes.add(7.0f);

        writeTpbFile(filePath, spawnTimes, beatTimes);
    }
}