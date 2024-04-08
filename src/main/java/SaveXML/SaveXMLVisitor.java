package SaveXML;

import Director.XMLToDoListLoader;
import ToDoList.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SaveXMLVisitor implements TaskVisitor {

    private final List<String> tasks;

    private final Queue<Task> offset;
    public SaveXMLVisitor() {
        tasks = new ArrayList<>();
        offset = Collections.asLifoQueue(new ArrayDeque<>());
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        String shift = updateShift();
        String result = shift + "<booleanTask finished=\"" + (booleanTask.isFinished()) + "\">\n";
        offset.add(booleanTask);
        shift = updateShift();
        result += shift + "<description text=\"" + booleanTask.getDescription() + "\"/>\n" +
                shift + "<deadline date=\"" + booleanTask.getDeadline() + "\">\n" +
                shift + "<priority priority=\"" + booleanTask.getPriority() + "\"/>\n" +
                shift + "<estimatedTime days=\"" + (booleanTask.getEstimatedTimeInDays()) + "\"/>\n";
        offset.remove(booleanTask);
        shift = updateShift();
        result += shift + "</booleanTask>";
        System.out.println(result);
    }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        String shift = updateShift();
        String result = shift + "<progressiveTask progress=\"" + (progressiveTask.getProgress()) + "\">\n";
        offset.add(progressiveTask);
        shift = updateShift();
        result += shift + "<description text=\"" + progressiveTask.getDescription() + "\"/>\n" +
                shift + "<deadline date=\"" + progressiveTask.getDeadline() + "\">\n" +
                shift + "<priority priority=\"" + progressiveTask.getPriority() + "\"/>\n" +
                shift + "<estimatedTime days=\"" + (progressiveTask.getEstimatedTimeInDays()) + "\"/>\n";
        offset.remove(progressiveTask);
        shift = updateShift();
        result += shift + "</progressiveTask>";
        System.out.println(result);
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        String shift = updateShift();
        String result = shift + "<complexTask>\n";
        offset.add(complexTask);
        shift = updateShift();
        result += shift + "<description text=\"" + complexTask.getDescription() + "\"/>\n" +
                shift + "<priority priority=\"" + complexTask.getPriority() + "\"/>";
        System.out.println(result);
        for (Task task : complexTask.getSubTasks()) {
            task.accept(this);
        }
        offset.remove(complexTask);
        shift = updateShift();
        result = shift + "</complexTask>";
        System.out.println(result);
    }

    @Override
    public void saveFile() {

    }
    
    private String updateShift() {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        for (int i = 0; i < offset.size(); ++i) {
            result.append("\t");
        }
        return result.toString();
    }
    
    public static void main(String[] args) {
        ToDoListBuilder builder = new ToDoListBuilderStd();
        try {
            XMLToDoListLoader.load("xml/toDoList.xml", builder);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        ToDoList list = builder.createToDoList();
        SaveXMLVisitor save = new SaveXMLVisitor();
        System.out.println("<toDoList>");
        for (Task task: list.getTasks()) {
            task.accept(save);
        }
        System.out.println("</toDoList>");
    }

}
