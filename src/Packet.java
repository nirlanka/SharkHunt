/**
 * Created by Nirmal on 11/11/2014.
 */
public class Packet {
    String source, destination;
    int size;
    Type type;

    Packet(String src, String dest, int sz, Type t) {
        type=t;
        source=src;
        destination=dest;
        size=sz;
    }


    // defaults
    enum Type {
        HTTPHeader, TCP, UDP
    }
}
