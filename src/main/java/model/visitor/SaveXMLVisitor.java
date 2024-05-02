package model.visitor;

import model.toDoList.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A visitor which permits to save the ToDoList into an XML file.
 */
public class SaveXMLVisitor implements TaskVisitor {

    private final BufferedWriter writer;
    private final Queue<Task> offset;


    public SaveXMLVisitor(String fileName) throws IOException {
        offset = Collections.asLifoQueue(new ArrayDeque<>());
        writer = new BufferedWriter(new FileWriter(fileName, false));
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        String shift = updateShift();
        String result = shift + "<booleanTask finished=\"" + (booleanTask.isFinished()) + "\">\n";
        result += printTask(booleanTask);
        shift = updateShift();
        result += shift + "</booleanTask>" + "\n";
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        String shift = updateShift();
        String result = shift + "<progressiveTask progress=\"" + (progressiveTask.getProgress()) + "\">" + "\n";
        result += printTask(progressiveTask);
        shift = updateShift();
        result += shift + "</progressiveTask>" + "\n";
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        String shift = updateShift();
        String result = shift + "<complexTask>\n";
        offset.add(complexTask);
        shift = updateShift();
        result += shift + "<description text=\"" + complexTask.getDescription() + "\"/>\n" +
                shift + "<priority priority=\"" + complexTask.getPriority() + "\"/>\n";
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Task task : complexTask.getSubTasks()) {
            task.accept(this);
        }
        offset.remove(complexTask);
        shift = updateShift();
        result = shift + "</complexTask>" + "\n";
        try {
            writer.append(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * visitToDoList: transforms and saves the list content into an XML file.
     *
     * @param toDoList the ToDoList to be saved
     */
    public void visitToDoList(ToDoList toDoList) {
        try {
            writer.append("""
                        <?xml version="1.0" encoding="UTF-8"?>
                        <!DOCTYPE toDoList SYSTEM "src/main/resources/xml/toDoList.dtd">""" +
                    "\n\n<toDoList>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Task task: toDoList.getTasks()) {
            task.accept(this);
        }
        try {
            writer.append("</toDoList>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * updateShift: returns the amount of shifts needed to indent XML file
     * @return String
     */
    private String updateShift() {
        return "\t" +
                "\t".repeat(offset.size());
    }

    /**
     * printTask: prints the common fields of a task into XML fields, following the DTD.
     * @param task the task to be printed.
     * @return String
     */
    private String printTask(Task task) {
        offset.add(task);
        String shift = updateShift();
        offset.remove(task);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
        String date = formatter.format(task.getDeadline());
        return shift + "<description text=\"" + task.getDescription() + "\"/>\n" +
                shift + "<deadline date=\"" + date + "\"/>\n" +
                shift + "<priority priority=\"" + task.getPriority() + "\"/>\n" +
                shift + "<estimatedTime days=\"" + (task.getEstimatedTimeInDays()) + "\"/>\n";
    }
}
