
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import za.ac.wits.snake.DevelopmentAgent;
public class MyAgent extends DevelopmentAgent {


    String [] appleCord;
    int nx;
    int ny,hx,hy,ax,ay;
    String [] val;
    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }
    public static List<DiagraphNode>getRandomGraph(int nodes, int arcs, Random random){
        List<DiagraphNode>graph=new ArrayList<>(nodes);
        for (int id=0;id<nodes;id++){
            graph.add(new DiagraphNode(id));
        }

        while (arcs>0){
            DiagraphNode tail=choose(graph,random);
            DiagraphNode head=choose(graph,random);
            tail.addNode(head);
            --arcs;
        }

        return graph;
    }
    public static Set<DiagraphNode> printSnakes(String snakes[],List<DiagraphNode>graph) {
        Set<DiagraphNode>head=new HashSet<>();
        for(int i = 0; i < snakes.length; ++i) {
            String[] cord = snakes[i].split(" ");
            if (i < 4) {
                head=printSnake(cord, 3,graph,head);
            } else {
                head=printSnake(cord, 0,graph,head);
                head=createDangerZones(cord[0],head,graph);
            }
        }
        return head;
    }

    private static <T> T choose(List<T>list,Random random){
        return list.get(random.nextInt(list.size()));
    }

    public static List<DiagraphNode> CreateGrid(List<DiagraphNode>graph, Set<DiagraphNode>Points){
        int counter=0;
        for(int i=0 ; i<50 ;i++){
            for(int j=0 ; j<50 ; j++) {
                if (Points.contains(graph.get(counter))) {
                    int k = 0;
                } else {
                    DiagraphNode node= graph.get(counter);
                    Point point=Transform1(counter);
                    if( isValid( new Point(point.x+1,point.y) ) && !isValid(graph,Points,new Point(point.x+1,point.y))){
                        node.addNode(graph.get(Transform1(point.x+1,point.y)));
                    }
                    if(isValid(new Point(point.x-1,point.y)) && !isValid(graph,Points,new Point(point.x-1,point.y))){
                        node.addNode(graph.get(Transform1(point.x-1,point.y)));
                    }
                    if(isValid(new Point(point.x,point.y+1)) && !isValid(graph,Points,new Point(point.x,point.y+1))){
                        node.addNode(graph.get(Transform1(point.x,point.y+1)));
                    }
                    if(isValid(new Point(point.x,point.y-1)) && !isValid(graph,Points,new Point(point.x,point.y-1))){
                        node.addNode(graph.get(Transform1(point.x,point.y-1)));

                    }

                }
                counter++;
            }

        }

        return graph;
    }

    public static boolean isValid(Point point){
        return point.x >=0 && point.x<50 && point.y>=0 && point.y<50;
    }
    public static boolean isValid(List<DiagraphNode>graph,Set<DiagraphNode>head,Point point){
        return head.contains(graph.get(Transform1(point.x,point.y)));
    }
    private static Set<DiagraphNode> createDangerZones(String string, Set<DiagraphNode>head, List<DiagraphNode>graph) {
        String[] h = string.split(",");
        int x = Integer.parseInt(h[0]);
        int y = Integer.parseInt(h[1]);
        if (x + 1 < 50 && y<50){
            int n=Transform1(x + 1,y);
            if(n>50 || n<0){
                int r = 0;
            }
            head.add(graph.get(n));
        }

        if (x -1 >=0 ){
            int n=Transform1(x - 1,y);

            head.add(graph.get(n));
        }

        if (y + 1 < 50){
            int n=Transform1(x ,y+1);

            head.add(graph.get(n));
        }
        if (y -1 >=0){
            int n=Transform1(x ,y-1);
            if(n>2500 || n<0){
                int r=0;
            }
            head.add(graph.get(n));
        }

        return head;

    }
    public static   Point Transform1(int n){
        int y = n%50;
        int x = (n-y)/50;
        return new Point(x,y);
    }
    public static Set<DiagraphNode> printSnake( String[] cord, int k,List<DiagraphNode>graph,Set<DiagraphNode>Points) {

        for(; k < cord.length; ++k) {
            String[] Ipoint = cord[k].split(",");
            int x = Integer.parseInt(Ipoint[0]);
            int y = Integer.parseInt(Ipoint[1]);
            Points.add(graph.get(Transform1(x,y)));
            if (k != cord.length - 1) {
                String[] Ipoint2 = cord[k + 1].split(",");
                int x2 = Integer.parseInt(Ipoint2[0]);
                int y2 = Integer.parseInt(Ipoint2[1]);
                Points.add(graph.get(Transform1(x2,y2)));
                int r;
                if (x == x2 && y - y2 > 0) {
                    for(r = y; r > y2; --r) {
                        Points.add(graph.get(Transform1(x,r)));
                    }
                } else if (x == x2 && y - y2 < 0) {
                    for(r = y2; r > y; --r) {
                        Points.add(graph.get(Transform1(x,r)));
                    }
                } else if (y == y2 && x - x2 < 0) {
                    for(r = x2; r > x; --r) {
                        Points.add(graph.get(Transform1(r,y)));
                    }
                } else if (y == y2 && x - x2 > 0) {
                    for(r = x; r > x2; --r) {
                        Points.add(graph.get(Transform1(r,y)));
                    }
                } else {
                }
            } else {
                Points.add(graph.get(Transform1(x,y)));
            }
        }
        return  Points;
    }
    public static int Transform1(int x,int y){
        return (x*50)+y;
    }
    public static DiagraphWeightFucntion
    getWeightFunction(List<DiagraphNode>graph,DiagraphCoordinates coordinates){

        DiagraphWeightFucntion diGraphWeightFunction=new DiagraphWeightFucntion();
        for (DiagraphNode node:graph){
            Point2D p1=coordinates.get(node);
            for (DiagraphNode child:node.getChildren()){
                Point2D p2=coordinates.get(child);
                double dist=p1.distance(p2);
                diGraphWeightFunction.set(node,child,1.2*dist);


            }
        }


        return diGraphWeightFunction;
    }


    public static DiagraphCoordinates getCoordinates(List<DiagraphNode>graph){
        DiagraphCoordinates coordinates=new DiagraphCoordinates();
        int counter=0;
        for(int i=0;i<50;i++){
            for(int j=0;j<50;j++){
                coordinates.put(graph.get(counter),new Point(i,j));
                counter++;
            }
        }

        return coordinates;
    }

    private static DiagraphCoordinates getCoordinates(List<DiagraphNode>graph,Random random){
        DiagraphCoordinates coordinates=new DiagraphCoordinates();
        for (DiagraphNode node:graph){
            coordinates.put(node,(Point2D.Double) randomPoint(1000,1000,random));
        }




        return coordinates;
    }

    public static Point2D randomPoint(int v, int v1, Random random) {
        return new Point(random.nextInt()%v,random.nextInt()%v1);
    }
    private void Move(Point p,Point head) {
        // TODO Auto-generated method stub
        if(p.x-head.x==0) {
            if(p.y-head.y>0) {
                System.out.println(1);
            }else {
                System.out.println(0);
            }
        }
        else {
            if(p.x-head.x>0) {
                System.out.println(3);
            }
            else {
                System.out.println(2);
            }
        }
    }
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            String[] Snakes=new String[10];
            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                String apple = line;
                //do stuff with apples
                appleCord = line.split(" ");
                for (int zombie = 0; zombie < 6; zombie++) {
                    String zombieLine = br.readLine();
                    Snakes[9-zombie]=zombieLine;
                }
                String myLine="";
                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) {
                        //hey! That's me :)
                        myLine+=snakeLine;
                        val = snakeLine.split(" ");
                        String head  = val[3];
                        ax = Integer.parseInt(appleCord[0]);
                        ay = Integer.parseInt(appleCord[1]);
                        String [] headCord = head.split(",");
                        hx = Integer.parseInt(headCord[0]);
                        hy = Integer.parseInt(headCord[1]);
                    }
                    Snakes[i]=snakeLine;
                }
                //finished reading, calculate move:
                List<DiagraphNode>graph=getRandomGraph(2500,0,new Random());
                Set<DiagraphNode>heat=printSnakes(Snakes,graph);
                DiagraphCoordinates coordinates=getCoordinates(graph);
                heat.remove(graph.get(Transform1(hx,hy)));
                heat.remove(graph.get(Transform1(ax,ay)));
                graph=CreateGrid(graph,heat);
                DiagraphWeightFucntion diGraphWeightFunction=getWeightFunction(graph,coordinates);
                DiagraphNode srs=graph.get(Transform1(hx,hy));
                DiagraphNode tgt=graph.get(Transform1(ax,ay));

                HeuristicFucntion heuristicFunction=new EuclideanHeuristicFunction(coordinates);
                List<DiagraphNode>path=DijkstraPathFinder.search
                        (srs,tgt,diGraphWeightFunction);

                DiagraphNode p;
                if(path.size()>0) {
                    p = path.get(1);
                    Point2D pp=coordinates.get(p);
                    Move(new Point((int)pp.getX(),(int) pp.getY()),new Point(hx, hy));
                }
                else {
                    System.out.println(6);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

