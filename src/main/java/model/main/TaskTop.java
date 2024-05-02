package model.main;

import model.director.XMLToDoListLoader;

import model.toDoList.ToDoList;
import model.toDoList.Task;
import model.toDoList.ToDoListBuilder;
import model.toDoList.ToDoListBuilderStd;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.Date;

/**
 * The command-line application displaying the 5 most urgent tasks not finished.
 */
public class TaskTop {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You must provide exactly one XML file");
            return;
        }
        String fileName = args[0];
        ToDoListBuilder builder = new ToDoListBuilderStd();
        try {
            XMLToDoListLoader.load(fileName, builder);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("Error: All tasks must have a description length of 20 max!");
            return;
        }
        ToDoList list = builder.createToDoList();
        list.getTasks().removeIf(task -> task.getProgress() == 100);
        list.getTasks().sort(Comparator.comparingLong((Task task) -> task.getDeadline().getTime()));
        int size = Math.min(list.getTasks().size(), 5);
        for (int i = 0; i < size; ++i) {
            Task task = list.getTasks().get(i);
            displayTask(task);
        }
    }

    /**
     * displayTask : display on the shell all the information about a task
     * @param task the task to display
     */
    private static void displayTask(Task task) {
        String delay = task.getDeadline().compareTo(new Date()) > 0 ? "ends on: " : "should be ended since: ";
        System.out.println(task.getPriority().toString() + "\n"
        + task.getDescription() + "\n"
        + "completion: " + task.getProgress() + "%" +"\n"
        + "duration: " + task.getEstimatedTimeInDays() + " days" + "\n"
        + delay + task.getDeadline() + "\n"
        + "\n"
        );
    }
}
