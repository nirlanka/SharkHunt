import org.jnetpcap.Pcap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Nirmal on 01/11/2014.
 */
public class Reader {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String line;

    public static void main(String[] __args) {

//        new Extractor(getPcap("/dev/java/IdeaProjects/Cap/src/caps/test.pcap"));

        System.out.print(
                "                                SharkHunt\n" +
                "                                ---------\n" +
                "                                    |\n" +
                "                 [f] Open PCAP file | [c] Capture live traffic\n" +
                "                                    |\n" +
//                "file_path mac_packet_num\n" +
                "? "
        );

        try {
            while (
                    ! (line=br.readLine()).contains("f") && ! line.contains("c")
                    && ! line.contains("q") && ! line.contains("exit")
                    && ! line.contains("d") && ! line.contains("demo")
            ) {
                System.out.print("? ");
            }

            if (line.contains("d")) {
                new Extractor(getPcap("/dev/java/IdeaProjects/Cap/src/caps/test.pcap"));
            } else if (line.contains("f")) {
                openFile();
            } if (line.contains("c")) {
                //[]// run actual capture module
                new Extractor(getPcap("/dev/java/IdeaProjects/Cap/src/caps/test.pcap"));
            }

        } catch (IOException e) {
            System.out.println("The OS just went rogue. :(");
        }

    }

    private static void openFile() {
        try {
            System.out.printf("filename ? ");
            line=br.readLine();
            if (line.equals("demo"))
                line="/dev/java/IdeaProjects/Cap/src/caps/test.pcap";
            new Extractor(getPcap(line));
        } catch (IOException e) {
            System.out.println("Some I/O error.");
        } catch (NullPointerException e) {
            System.out.println("We need the full path for the file.");
        }
    }

    static Pcap getPcap(String fileName) {

        StringBuilder errbuff=new StringBuilder();
        Pcap pcap=Pcap.openOffline(fileName, errbuff);

        //[]// add initial filter if necessary
        
        return pcap;
    }

}
