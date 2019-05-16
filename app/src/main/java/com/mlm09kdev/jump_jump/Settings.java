package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.FileIO;
import com.mlm09kdev.jump_jump.GameObjects.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Settings {
    public static boolean soundEnabled = false;
    public final static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
    public final static String file = ".jump_jump_settings.txt";

    public static void load(FileIO files) {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            World.level = Integer.parseInt(in.readLine());
            World.score = Integer.parseInt(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(file)));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            out.write(Integer.toString(World.level));
            out.write("\n");
            out.write(Integer.toString(World.score));
            out.write("\n");
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
                out.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}
