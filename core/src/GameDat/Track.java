package GameDat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class Track {
    private String songName;
    private String artistName;
    private String coverImagePath;
    private String difficulty;
    private String songFilePath;

    public Track(String songName, String artistName, String coverImagePath, int difficulty, String songFilePath) {
        this.songName = songName;
        this.artistName = artistName;
        this.coverImagePath = coverImagePath;
        switch(difficulty){
            case 1:
                this.difficulty = "Typer";
                break;
            case 2:
                this.difficulty = "Coder";
                break;
            case 3:
                this.difficulty = "Typemaster";
                break;
        }
        this.songFilePath=songFilePath;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getSongFilePath() {
        return songFilePath;
    }
}
