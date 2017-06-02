import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ouxuan on 2017/6/2.
 */
public class CIKM_data {
    public void new_data(String inputFile, String outfile) {
        File file = new File(inputFile);
        try {
            FileWriter fw = new FileWriter(outfile);
            try {
                if (file.isFile() && file.exists()) {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bf = new BufferedReader(read);
                    String linetxt = null;
                    String[][] array = new String[60][10221];
                    while ((linetxt = bf.readLine()) != null) {
                        array = deal(linetxt);
                        for (int i = 0; i < 60; i++) {
                            String mix = "";
                            for (int j = 0; j < 10220; j++) {
                                mix = mix + array[i][j] + ",";
                            }
                            mix = mix + array[i][10220] + "\r\n";
                            fw.write(mix);
                        }

                    }
                    read.close();
                } else {
                    System.out.print("not find");
                }
            } catch (Exception e) {
                System.out.print("flie error");
                e.printStackTrace();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] deal(String s) {
        String label = "";
        s = s.split(",",2)[1];
        String[] ss = s.split(",",2);
        label = ss[0];
        s = ss[1];
        String[] arr = s.split(" ");
        int c = 0;
        String[][] map_redar = new String[60][10221];
        for (int i = 0; i < 60; i = i + 4) {

            for (int j = 0; j < 10201; j++) {
                map_redar[i][j] = arr[c++];
            }
            map_redar[i][10201] = "1";
            map_redar[i][10205 + i / 4] = "1";
            map_redar[i][10220] = label;

            for (int j = 0; j < 10201; j++) {
                map_redar[i + 1][j] = arr[c++];
            }
            map_redar[i + 1][10202] = "1";
            map_redar[i + 1][10205 + i / 4] = "1";
            map_redar[i + 1][10220] = label;

            for (int j = 0; j < 10201; j++) {
                map_redar[i + 2][j] = arr[c++];
            }
            map_redar[i + 2][10203] = "1";
            map_redar[i + 2][10205 + i / 4] = "1";
            map_redar[i + 2][10220] = label;

            for (int j = 0; j < 10201; j++) {
                map_redar[i + 3][j] = arr[c++];
            }
            map_redar[i + 3][10204] = "1";
            map_redar[i + 3][10205 + i / 4] = "1";
            map_redar[i + 3][10220] = label;
        }
        return map_redar;
    }

}
