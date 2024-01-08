import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class DiagraphCoordinates {
    private final Map<DiagraphNode, Point2D>map=new HashMap<>();
    public void put(DiagraphNode node,Point2D d){


        map.put(node,d);
    }
    public Point2D get(DiagraphNode node){


        return map.get(node);
    }

}
