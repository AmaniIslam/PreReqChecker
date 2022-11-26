package prereqchecker;

import java.util.*;

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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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
        String target = StdIn.readString();
        int num = StdIn.readInt();
        ArrayList<String> courses = new ArrayList<>();

        for (int i = 0; i < num; i++)
            courses.add(StdIn.readString());

        for (int i = 0; i < courses.size(); i++)
            courses = preadder(courses.get(i), courses, prereqs);

        ArrayList<String> pres = new ArrayList<>();
        pres = preadder(target, pres, prereqs);
        pres.removeAll(courses);

        for (int i = 0; i < pres.size(); i++) {
            StdOut.print(pres.get(i));
            StdOut.println();
        }
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
}
