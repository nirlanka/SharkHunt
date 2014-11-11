import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.ArrayList;
import java.util.HashMap;

//[]// save (completely) to JSON file

/**
 * Created by Nirmal on 01/11/2014.
 */
public class Extractor {
    Pcap pcap;

    Extractor(Pcap _pcap, int nPkts) {
        try {
            pcap=_pcap;
            pcap.loop(nPkts, pktHandler, packets);
            //[]// loop number should be optional
            System.out.printf("%d packets: %d tcp (%d http), %d udp.\n", pktCount, tcpCountTotal, httpCount, udpCount);
        } finally {
            pcap.close();
            Display q=new Display(packets, tcpCountTotal+udpCount);
        }
    }

    Extractor(Pcap _pcap) {
        new Extractor(_pcap, pcap.LOOP_INFINITE);//256);   //[ok]// can use pcap.LOOP_INFINATE instead of 256
    }

    int pktCount=0,
        tcpCountTotal=0,
        httpCount=0,
        udpCount=0;

    long timestamp=0;

    ArrayList<HashMap<String, String>> packets=new ArrayList<HashMap<String, String>>();

    PcapPacketHandler<ArrayList> pktHandler=new PcapPacketHandler<ArrayList>() {

        final Ip4 ip=new Ip4();
        final Tcp tcp=new Tcp();
        final Http http=new Http();
        final Udp udp=new Udp();

        @Override
        public void nextPacket(PcapPacket pkt, ArrayList pckts) {

            // IP PACKET
            //      src, dest (addr), protocol, size, time [id (not continuous)]
            if (pkt.hasHeader(ip)) {
                HashMap<String, String> x=new HashMap<String, String>(3);

                x.put(SRC, ""+ FormatUtils.ip(ip.source()));
                x.put(DEST, ""+ FormatUtils.ip(ip.destination()));

                PcapHeader h=pkt.getCaptureHeader();
                x.put(SIZE, ""+h.wirelen());

                //[]// add port data

//                x.put(SRC_PORT, ""+ FormatUtils.ip(ip.))

//                if (timestamp==0) {+-
//                    x.put("time", "" + timestamp);
//                    timestamp=h.timestampInMillis();
//                }
//                else
//                    x.put("time", ""+(h.timestampInMillis()-timestamp));

                // TCP
                if (pkt.hasHeader(tcp)) {
                    tcpCountTotal++;
                    // HTTP
                    if (pkt.hasHeader(http)) {
                        x.put(TYPE, "HTTP");
                        httpCount++;
                    }
                    else
                        x.put(TYPE, "TCP");
                }
                // UDP
                else if (pkt.hasHeader(udp)) {
                    x.put(TYPE, "UDP");
                    udpCount++;
                }

//                System.out.println(x.toString());
                packets.add(pckts.size(),x);
            }

            pktCount++;

            // break on count (or on input)
//            if (pktCount>99) pcap.breakloop();

        }
    };

    // fieldnames
    static String SRC="src", DEST="dest", SIZE="size", TYPE="type", SRC_PORT="src-port", DEST_PORT="dest-port";

    // defaults
    enum Type {
        HTTPHeader, TCP, UDP
    }

    //// ?? another thread can do a Pcap.breakloop

}
