package Parser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

public class TbpFileProcessor {

    public static void main(String[] args) {
        String mapFolderPath = "Beatmap";

        // Get a FileHandle to the map folder within LibGDX assets
        FileHandle mapFolder = Gdx.files.internal(mapFolderPath);

        for (FileHandle file : mapFolder.list()) {
            if (file.extension().equalsIgnoreCase("tbp")) {
                String tbpFilePath = file.path();
                try {
                    TbpParser.TbpData tbpData = TbpParser.parseTbpFile(tbpFilePath);
                    System.out.println("Parsed data for file " + tbpFilePath + ": " + tbpData);
                } catch (IOException e) {
                    System.err.println("Error parsing file: " + tbpFilePath);
                    e.printStackTrace();
                }
            }
        }
    }
}
