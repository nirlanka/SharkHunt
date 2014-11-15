import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Nirmal on 08/11/2014.
 */

public class Display {
    ArrayList<Packet> pckt_list;
    int pcktCount;

    ArrayList<String> filters=new ArrayList<String>();

    boolean hasFilters=false; // this controls every check :)
    String groupBy=SRC; // default grouping

    // CONSTRUCTOR
    Display(ArrayList<Packet> pckt_list, int pcktCount) {
        this.pckt_list=pckt_list;
        this.pcktCount=pcktCount;

        showGroups(groupBy);
    }

    ArrayList<Packet> group;
    HashMap<String, ArrayList<Packet>> all_groups;
    int groupPcktCount,
        filteredPcktCount;
    String  groupValue,
            tmpValue;

    void showGroups(String field) {
        if (
                (!field.equals(SRC))
              &&(!field.equals(DEST))
              &&!(field.equals(SIZE))
              &&!(field.equals(TYPE))
            ) {
            getQuery();
            return;
        }

        //[ok]// sort by field
        Collections.sort(pckt_list, new __HashmapComparator(field));
        all_groups=new HashMap<String, ArrayList<Packet>>();

        //[ok]// copy into all_group

        groupValue=pckt_list.get(0).get(field);
        group=new ArrayList<Packet>();

        for (Packet pckt : pckt_list) {
            tmpValue=pckt.get(field);
            if (!(tmpValue.equals(groupValue))) {
                all_groups.put(groupValue, group);
                groupValue=tmpValue;
                group = new ArrayList<Packet>();
            }
            group.add(pckt);
        }
        all_groups.put(groupValue, group);

        // print each (groups)
        for (String g: all_groups.keySet()) {
            group=all_groups.get(g);

            showGroupTitle(g);

            // for each packet
            groupPcktCount=0;
            filteredPcktCount=0;
            for (Packet p : group) {
                if (showPacket(p, field)) {
                    filteredPcktCount++;
                }
                groupPcktCount++;
            }
            //[ok]// print filtered/count
            if (filteredPcktCount==groupPcktCount)
                showGroupCount(groupPcktCount);
            else
                showGroupCount(filteredPcktCount,groupPcktCount);
        }

        getQuery();
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    String[] cmds;

    void getQuery(){
        System.out.printf("%s\n$ ", long_line);
        try {
            line=br.readLine();
            System.out.printf("%s\n\n", long_line);

            if (line.equals("exit") || line.equals("q")) return;

            if (line.length()>0) {

                //[]// packet view syntax { =pstq }

                //[]// identify streams { streams }

                //[]// stream view syntax { #qepm } (read: how to identify stream in jnetpcap)

                //[?]// ip view syntax { 127...1 } { 127.0.0.1 }

                cmds = line.split(" ");
                filters=new ArrayList<String>();
                hasFilters=false;

                for (String c : cmds) {
                    if (!c.equals("")) {
                        if (c.charAt(0) == '/') {
                            filters.add(c.substring(1).toLowerCase());
                            hasFilters = true;
                        } else {
                            groupBy = c.toLowerCase();
                        }
                    }
                }
                showGroups(groupBy);
            }
            else
                getQuery();

        } catch (IOException e) {
            System.out.println("Alien query. :(");
        }

    }


    void showAll() {}

    boolean showPacket(Packet pckt, String field) {
        if (checkFilters(pckt)) {
            System.out.print("   ");

            if (!field.equals(SRC)) {
                if (!field.equals(DEST))
                    System.out.printf("%s >> %s  \t", pckt.getSource(), pckt.getDestination());
                else
                    System.out.printf("<< %s\t", pckt.getSource());
            } else {
                System.out.printf(">> %s\t", pckt.getDestination());
            }
            if (!field.equals(SIZE)) System.out.printf("  %d \t", pckt.getSize());

            if (!field.equals(TYPE)) System.out.printf("%s ", pckt.getType().toString());

            System.out.println();
            return true;
        }
        return false;
    }

    boolean showPacket(Packet pckt) {
        return showPacket(pckt, "");
    }

    boolean checkFilters(Packet pckt) {
        if (hasFilters)
            for (String f : filters)
                if (!(pckt.toString().toLowerCase().contains(f)))
                    return false;
        return true;
    }

    void showField(Packet pckt, String field) {
        System.out.printf("%s\t", pckt.get(field));
    }

    void showGroupTitle(String value) {
        System.out.printf(" %s\n", value);
    }

    void showGroupCount(int n, int N) {
        System.out.printf("[%d\\%d]%s\n\n", n, N, short_line);
    }
    void showGroupCount(int n) {
        System.out.printf("[%d]%s\n\n", n, short_line);
    }

    private class __HashmapComparator implements Comparator<Packet> {
        String key;
        public __HashmapComparator(String key) {
            this.key=key;
        }
        @Override
        public int compare(Packet one,Packet two) {
            if (key.equals(SIZE))
                return Integer.parseInt(one.getSize())-Integer.parseInt(two.getSize());
            return one.get(key).compareTo(two.get(key));
        }
    }


    // reusable text
    static String  long_line="--------------------------------------------------------------",
            short_line="";

    // field-names
    static String SRC="src", DEST="dest", SIZE="size", TYPE="type";


    // defaults
    enum Type {
        HTTPHeader, TCP, UDP
    }

}

