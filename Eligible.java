package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
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
        int num = StdIn.readInt();
        ArrayList<String> pres = new ArrayList<>();
        ArrayList<String> posts = new ArrayList<>();

        for (int i = 0; i < num; i++)
            pres.add(StdIn.readString());

        for (int i = 0; i < pres.size(); i++)
            pres = preadder(pres.get(i), pres, prereqs);

        for (int i = 0; i < prereqs.size(); i++) {
            Boolean add = true;

            if (!(pres.contains(prereqs.get(i).get(0)))) {

                for (int j = 1; j < prereqs.get(i).size(); j++) {
                    if (!(pres.contains(prereqs.get(i).get(j))))
                        add = false;

                }
            }

            else
                add = false;

            if (add)
                posts.add(prereqs.get(i).get(0));

        }

        for (int i = 0; i < posts.size(); i++) {
            StdOut.print(posts.get(i));
            StdOut.println();
        }

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