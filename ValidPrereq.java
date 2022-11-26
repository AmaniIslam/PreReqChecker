package prereqchecker;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);
        int coursenum = StdIn.readInt();

        ArrayList<ArrayList<String>> prereqs = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < coursenum; i++)
            prereqs.add(new ArrayList<>(Arrays.asList(StdIn.readString())));

        int size = StdIn.readInt();

        for (int i = 0; i < size; i++) {
            String course = StdIn.readString();
            String prereq = StdIn.readString();
            for (int j = 0; j < coursenum; j++) {
                if (course.equals(prereqs.get(j).get(0))) {
                    prereqs.get(j).add(prereq);
                    break;
                }
            }
        }

        StdIn.setFile(args[1]);

        String pre = StdIn.readString();
        String post = StdIn.readString();
        ArrayList<String> pres = new ArrayList<>();
        pres = preadder(post, pres, prereqs);

        if (pres.contains(pre))
            StdOut.print("NO");

        else
            StdOut.print("YES");

        // WRITE YOUR CODE HERE
    }

    public static ArrayList<String> preadder(String post, ArrayList<String> pres,
            ArrayList<ArrayList<String>> prereqs) {
        for (int i = 0; i < prereqs.size(); i++) {
            if (prereqs.get(i).get(0).equals(post) && prereqs.get(i).size() > 1) {
                for (int j = 1; j < prereqs.get(i).size(); j++) {
                    if (!pres.contains(prereqs.get(i).get(j))) {
                        pres.add(prereqs.get(i).get(j));
                        preadder(prereqs.get(i).get(j), pres, prereqs);
                    }
                }
                break;
            }
        }
        return pres;
    }
    /*
     * public static int valid(String pre, String post, ArrayList<ArrayList<String>>
     * prereqs, int check) {
     * System.out.println();
     * 
     * System.out.println(post + " " + check);
     * 
     * for (int i = 0; i < prereqs.size(); i++) {
     * 
     * System.out.print(" i:" + i);
     * 
     * if (prereqs.get(i).get(0).equals(post)) {
     * if (prereqs.get(i).size() != 1) {
     * for (int j = 1; j < prereqs.get(i).size(); j++) {
     * System.out.println();
     * System.out.print("j:" + j);
     * 
     * if (prereqs.get(i).get(j).equals(pre)) {
     * valid(pre, prereqs.get(i).get(j), prereqs, check + 1);
     * 
     * }
     * 
     * else
     * valid(pre, prereqs.get(i).get(j), prereqs, check);
     * }
     * }
     * break;
     * }
     * }
     * System.out.println();
     * 
     * System.out.print("end loop");
     * return check;
     * }
     */
}
