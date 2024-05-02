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
        list.getTasks().removeIf(task -> task.getDeadline().getTime() <= new Date().getTime()
                                    || task.getProgress() == 100);
        list.getTasks().sort(Comparator.comparingLong((Task task) -> task.getDeadline().getTime()));
        int size = Math.min(list.getTasks().size(), 5);
        for (int i = 0; i < size; ++i) {
            Task task = list.getTasks().get(i);
            System.out.println(task.getDeadline() + task.getDescription());
        }
    }
}
