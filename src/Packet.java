import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Nirmal on 15/11/2014.
 */
public class Packet {
    private final StringProperty source, destination, size, type;

    //constructors
    public Packet(String src, String dst, String sz, String tp) {
        this.source=new SimpleStringProperty(src);
        this.destination=new SimpleStringProperty(dst);
        this.size=new SimpleStringProperty(sz);
        this.type=new SimpleStringProperty(tp);
    }
    public Packet() {
        this("","","","");
    }

    //setters
    public void setSource(String source) {
        this.source.set(source);
    }
    public void setDestination(String source) {
        this.source.set(source);
    }
    public void setSize(String size) {
        this.size.set(size);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    //getters
    public String getSource() {
        return source.get();
    }
    public String getDestination() {
        return destination.get();
    }
    public String getSize() {
        return size.get();
    }
    public String getType() {
        return type.get();
    }

    //property--getters (for tableview columns)
    public StringProperty sourceProperty() {
        return source;
    }
    public StringProperty destinationProperty() {
        return destination;
    }
    public StringProperty sizeProperty() {
        return size;
    }
    public StringProperty typeProperty() {
        return type;
    }

    public String get(String key) {
        if (key.equals("src")) return getSource();
        else if (key.equals("dest")) return getDestination();
        else if (key.equals("size")) return getSize()+"";
        else if (key.equals("type")) return getType();

        return "~";
    }

    @Override
    public String toString() {
        return getSource()+getDestination()+getSize()+getType();
    }

}
