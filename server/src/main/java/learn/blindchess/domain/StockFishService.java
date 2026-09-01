package learn.blindchess.domain;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class StockFishService {

    private static final String STOCKFISH_PATH = "src/main/resources/stockfish/stockfish.exe";

    public String getStockFishMove(String fen, int targetElo) {

        ProcessBuilder processBuilder = new ProcessBuilder(STOCKFISH_PATH);

        Process process = null;

        try {
            process = processBuilder.start();

            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))
                    ) {
                writer.write("uci\n");

                if (targetElo >= 1320) {

                    int clampedElo = Math.min(2500, targetElo);
                    writer.write("setoption name UCI_LimitStrength value true \n");
                    writer.write("setoption name UCI_Elo value " + clampedElo + "\n");
                } else if (targetElo >= 1000) {

                    int skillLevel = (int) Math.round((targetElo - 1000) / 60.0);
                    writer.write("setoption name UCI_LimitStrength value false\n");
                    writer.write("setoption name Skill Level value " + skillLevel + "\n");

                } else {

                    writer.write("setoption name UCI_LimitStrength value false\n");
                    writer.write("setoption name Skill Level value 0\n");

                }

                writer.write("isready\n");
                writer.flush();

                String line;
                while ((line = reader.readLine()) != null) {
                    if ("readyok".equals(line)) break;
                }

                writer.write("position fen " + fen + "\n");

                if (targetElo < 800) {
                    writer.write("go depth 1\n");
                } else if (targetElo < 1320) {
                    writer.write("go depth 4\n");
                } else {
                    writer.write("go depth 10\n");
                }
                writer.flush();

                String bestMove = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("bestmove")) {
                        bestMove = line.split(" ")[1];
                        break;
                    }
                }

                writer.write("quit\n");
                writer.flush();

                return bestMove;

            }
        } catch (IOException e) {
            throw new RuntimeException("Stockfish process communication failure.");
        } finally {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        }
    }
}
