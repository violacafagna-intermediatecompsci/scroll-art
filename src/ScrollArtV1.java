import java.util.Random;

public class ScrollArtV1 {
    static final int WIDTH = getTerminalWidth() - 1;
    static final int FISH_WIDTH = 7;
    static final int FISH_HEIGHT = 9;
    static final int TURTLE_WIDTH = 11;
    static final int TURTLE_HEIGHT = 9;
    static final Random rand = new Random();

    public static void main(String[] args) throws InterruptedException {

        char[][] nextRows = new char[34][WIDTH]; // store upcoming rows

        for (int i = 0; i < nextRows.length; i++) {
            nextRows[i] = emptyRow();
        }

         while (true) {
            // At each column on the top row, 1% chance to add a new image
         for (int x = 0; x < WIDTH - TURTLE_WIDTH + 1; x++) {
                if (rand.nextDouble() < 0.01 && isAreaEmpty(nextRows, x)) {
                    loadNextRowsWithImage(nextRows, x);
                    x = x + (TURTLE_WIDTH - 1); 
                 }
             }
             // Print and remove the top row
            System.out.println(new String(nextRows[0]));
             // Shift all rows up
             shiftRowsUp(nextRows);
             Thread.sleep(40); // Delay in ms
         }
    }

    private static void loadNextRowsWithImage(char[][] nextRows, int x) {
        char[][] img;
        if (rand.nextBoolean()) {
            img = getFish();
        } else {
            img = getTurtle();
        }
        for (int iy = 0; iy < img.length; iy++) {
            for (int ix = 0; ix < img[iy].length; ix++) {
                 nextRows[iy][x + ix] = img[iy][ix];
            }
        }
    }

    static void shiftRowsUp(char[][] nextRows) {
        for (int i = 1; i < nextRows.length; i++) {
            nextRows[i - 1] = nextRows[i];
        }
        nextRows[nextRows.length - 1] = emptyRow();
    }

    static char[] emptyRow() {
        char[] row = new char[WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            row[i] = ' ';
        }
        return row;
    }

    static boolean isAreaEmpty(char[][] nextRows, int x) { //i added this isAreaEmpty
        for (int y = 0; y < TURTLE_HEIGHT; y++) {
            for (int ix = x; ix < x + TURTLE_WIDTH; ix++) {
                if (nextRows[y][ix] != ' ') {
                    return false;
                
                }
            }
        }
        return true; 
    }

    // this is my first ascii creation a fish!!
    static char[][] getFish() {
        char[][] img = new char[FISH_HEIGHT][FISH_WIDTH];
        // fill with empty space
        for (int y = 0; y < FISH_HEIGHT; y++) {
            for (int x = 0; x < FISH_WIDTH; x++) {
                img[y][x] = ' ';
            }
        }
        // then fill individual characters
        img[0][2] = 'O';
        img[1][3] = 'O';
        img[2][2] = '/';
        img[2][3] = '\\';
        img[2][4] = '/';
        img[2][5] = '\\';
        img[3][1] = '/';
        img[3][3] = '@';
        img[3][6] = '\\';
        img[4][1] = '\\';
        img[4][6] = '/';
        img[5][2] = '\\';
        img[5][5] = '/';
        img[6][5] = '\\';
        img[6][2] = '/';
        img[7][1] = '/';
        img[7][6] = '\\';
        img[8][1] = '-';
        img[8][2] = '-';
        img[8][3] = '-';
        img[8][4] = '-';
        img[8][5] = '-';
        img[8][6] = '-';

        return img;
    }

    // this is my second ascii creation a turtle!!
    static char[][] getTurtle() {
        char[][] img = new char[TURTLE_HEIGHT][TURTLE_WIDTH];
        // fill with empty space
        for (int y = 0; y < TURTLE_HEIGHT; y++) {
            for (int x = 0; x < TURTLE_WIDTH; x++) {
                img[y][x] = ' ';
            }
        }
        // then fill individual characters
        img[0][4] = '_';
        img[0][5] = '_';
        img[0][6] = '_';
        img[1][3] = '/';
        img[1][4] = '@';
        img[1][6] = '@';
        img[1][7] = '\\';

        img[2][3] = '\\';
        img[2][7] = '/';
        img[3][1] = '_';
        img[3][2] = '_';
        img[3][3] = '/';
        img[3][4] = '/';
        img[3][5] = '|';
        img[3][6] = '\\';
        img[3][7] = '\\';
        img[3][8] = '_';

        img[4][0] = '/';
        img[4][2] = '/';
        img[4][3] = '\\';
        img[4][4] = '\\';
        img[4][5] = '|';
        img[4][6] = '/';
        img[4][7] = '/';
        img[4][8] = '\\';
        img[4][10] = '\\';

        img[5][0] = '\\';
        img[5][1] = '/';
        img[5][2] = '\\';
        img[5][3] = '/';
        img[5][4] = '/';
        img[5][5] = '|';
        img[5][6] = '\\';
        img[5][7] = '\\';
        img[5][8] = '/';
        img[5][9] = '\\';
        img[5][10] = '/';

        img[6][3] = '\\';
        img[6][4] = '\\';
        img[6][5] = '|';
        img[6][6] = '/';
        img[6][7] = '/';

        img[7][4] = '/';
        img[7][5] = '/';
        img[7][6] = '\\';
        img[7][7] = '\\';
        img[8][4] = '-';
        img[8][7] = '-';



        return img;
    }

    public static int getTerminalWidth() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return getUnixTerminalWidth();
        } else {
            return 80; // fallback for unknown OS
        }
    }

    private static int getUnixTerminalWidth() {
        try {
            // Try to get terminal size from environment variables first
            String columns = System.getenv("COLUMNS");
            if (columns != null && !columns.isEmpty()) {
                return Integer.parseInt(columns);
            }

            // Fallback to stty command
            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty size </dev/tty");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            if (output != null && !output.isEmpty()) {
                String[] parts = output.trim().split(" ");
                return Integer.parseInt(parts[1]); // columns
            }
        } catch (Exception ignored) {
            // Silently ignore errors and fall back to default
        }
        return 80; // fallback
    }

}