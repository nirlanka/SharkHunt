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

    Packet() {}


    // defaults
    enum Type {
        HTTPHeader, TCP, UDP
    }

    String getSource() {return source;}
    String getDestination() {return destination;}
    int getSize() {return size;}
    Type getType() {return type;}

    String get(String key) {
        if (key.equals("src")) return source;
        else if (key.equals("dest")) return destination;
        else if (key.equals("size")) return ""+size;
        else if (key.equals("type")) return type.toString();

        return "~";
    }

    @Override
    public String toString() {
        return source+destination+size+type;
    }
}
