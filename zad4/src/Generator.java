import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by Patryk on 2017-05-05.
 */

public class Generator {

    public static void main(String[] args) throws Exception {
        FileWriter fw = new FileWriter("data.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        String[] names = {"Kowalski","Nowak","Sielewski","Wisniewski","Lewandowski","Jankowski","Kwiatkowski","Wojciechowski","Nowicki","Dudek",
                        "Kaczmarek","Mazur","Krawczyk","Piotrowski","Grabowski","Nowakowski","Pawlowski", "Michalski","Adamczyk","Wieczorek"};


        for(int i = 0; i < 50000; i++){
            String examination = "";
            examination += names[(int)(Math.random() * 20)] + " ";
            examination += "d_" + names[(int)(Math.random() * 20)] + " ";
            examination += "w_" + names[(int)(Math.random() * 20)] + " ";
            examination += "2017-05-05" + " ";
            for(int j = 0; j < 5; j++){
                examination += (int)(Math.random() * 100) + " ";
            }
            for(int j = 0; j < 4; j++){
                examination += (Math.random() * 100) + " ";
            }

            pw.println(examination);
        }


        pw.close();
        bw.close();
        fw.close();
    }
}
