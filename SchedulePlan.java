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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the
 * course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
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
        ArrayList<String> taken = new ArrayList<>();
        ArrayList<String> pres = new ArrayList<>();

        for (int i = 0; i < num; i++)
            taken.add(StdIn.readString());

        for (int i = 0; i < taken.size(); i++)
            taken = preadder(taken.get(i), taken, prereqs);

        pres = preadder(target, pres, prereqs);
        pres.removeAll(taken);

        int sems = 0;
        ArrayList<ArrayList<String>> totake = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < pres.size() - 1; i++) {
            for (int j = i + 1; j < pres.size(); j++) {
                ArrayList<String> iarr = new ArrayList<>();
                ArrayList<String> jarr = new ArrayList<>();
                if (preadder(pres.get(i), iarr, prereqs).size() > preadder(pres.get(j), jarr, prereqs).size()) {
                    String temp = pres.get(i);
                    pres.set(i, pres.get(j));
                    pres.set(j, temp);
                }
            }
        }

        for (int i = 0; i < pres.size(); i++) {
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> takensem = new ArrayList<>();

            for (int j = i; j < pres.size(); j++) {
                if (taken.containsAll(preadder(pres.get(j), arr, prereqs))) {
                    takensem.add(pres.get(j));
                    i = j;
                } else
                    break;
            }

            totake.add(takensem);
            taken.addAll(takensem);
            sems++;

        }

        StdOut.print(sems);
        for (int i = 0; i < totake.size(); i++) {
            StdOut.println();
            for (int j = 0; j < totake.get(i).size(); j++) {
                StdOut.print(totake.get(i).get(j) + " ");
            }
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
