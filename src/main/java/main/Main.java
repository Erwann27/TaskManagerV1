package main;

import Director.XMLToDoListLoader;

import ToDoList.ToDoList;
import ToDoList.Task;
import ToDoList.ToDoListBuilder;
import ToDoList.ToDoListBuilderStd;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String fileName = args[0];
            ToDoListBuilder builder = new ToDoListBuilderStd();
            try {
                XMLToDoListLoader.load(fileName, builder);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
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
        } else {
            // TODO CALL GRAPHIC WINDOW
        }
    }
}
