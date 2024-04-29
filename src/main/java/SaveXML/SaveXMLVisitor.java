package SaveXML;

import ToDoList.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class SaveXMLVisitor implements TaskVisitor {

    private BufferedWriter writer;
    private final Queue<Task> offset;


    public SaveXMLVisitor() {
        offset = Collections.asLifoQueue(new ArrayDeque<>());
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

    @Override
    public void saveFile(String fileName, ToDoList toDoList) throws IOException {
        writer = new BufferedWriter(new FileWriter("test.xml", false));
        try {
            writer.append("""
                        <?xml version="1.0" encoding="UTF-8"?>
                        <!DOCTYPE toDoList SYSTEM "xml/toDoList.dtd">""" +
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
        writer.close();
    }

    /**
     * @return
     * returns the amount of shifts needed to indent XML file
     */
    private String updateShift() {
        return "\t" +
                "\t".repeat(offset.size());
    }

    /**
     * @param task Task
     * @return String
     * print the common fields of a task
     */
    private String printTask(Task task) {
        offset.add(task);
        String shift = updateShift();
        offset.remove(task);
        return shift + "<description text=\"" + task.getDescription() + "\"/>\n" +
                shift + "<deadline date=\"" + task.getDeadline() + "\"/>\n" +
                shift + "<priority priority=\"" + task.getPriority() + "\"/>\n" +
                shift + "<estimatedTime days=\"" + (task.getEstimatedTimeInDays()) + "\"/>\n";
    }
}
