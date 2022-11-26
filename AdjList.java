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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then
 * listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
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

        for (

                int i = 0; i < prereqs.size(); i++) {
            for (int j = 0; j < prereqs.get(i).size(); j++) {
                StdOut.print(prereqs.get(i).get(j) + " ");
            }
            StdOut.println();
        }

    }

    // WRITE YOUR CODE HERE
}
