package model.director;

import model.toDoList.Priority;
import model.toDoList.ToDoListBuilder;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * The loader responsible to parse an XML file and load its content into a ToDoList with a builder
 */
public class XMLToDoListLoader {


    /**
     * load: parse the given file to load ToDoList content into the builder
     *
     * @param XMLFileName the absolute path of the XML file to parse
     * @param builder the builder responsible for creating and containing the ToDoList
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void load(String XMLFileName, ToDoListBuilder builder) throws ParserConfigurationException,
            SAXException, IOException {
        InputSource is = new InputSource(new BufferedInputStream(Files.newInputStream(Paths.get(XMLFileName))));
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        HandlerImpl handler = new HandlerImpl(builder);
        xr.setContentHandler(handler);
        spf.setValidating(true);
        xr.setErrorHandler(handler);
        try {
            xr.parse(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Class parsing the XML file
     */
    private static class HandlerImpl extends DefaultHandler implements ContentHandler, ErrorHandler {
        private final ToDoListBuilder builder;

        public HandlerImpl(ToDoListBuilder builder) {
            this.builder = builder;
        }

        public void endElement(String uri, String localName, String qName) {

            if (qName.equalsIgnoreCase("booleanTask")) {
                builder.createBooleanTask();
            } else if (qName.equalsIgnoreCase("progressiveTask")) {
                builder.createProgressiveTask();
            } else if (qName.equalsIgnoreCase("complexTask")) {
                builder.createComplexTask();
            }
        }

        public void startElement(String uri, String localName, String qName, Attributes att) {

            if (qName.equalsIgnoreCase("booleanTask")) {
                builder.startBooleanTask();
                builder.setFinished(Boolean.valueOf(att.getValue("finished")));
            } else if (qName.equalsIgnoreCase("progressiveTask")) {
                builder.startProgressiveTask();
                builder.setProgress(Double.valueOf(att.getValue("progress")));
            } else if (qName.equalsIgnoreCase("complexTask")) {
                builder.startComplexTask();
            } else if (qName.equalsIgnoreCase("description")) {
                builder.setDescription(att.getValue("text"));
            } else if (qName.equalsIgnoreCase("deadline")) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
                try {
                    builder.setDeadline(formatter.parse(att.getValue("date")));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (qName.equalsIgnoreCase("priority")) {
                builder.setPriority(Priority.valueOf(att.getValue("priority")));
            } else if (qName.equalsIgnoreCase("estimatedTime")) {
                builder.setEstimatedTime(Integer.valueOf(att.getValue("days")));
            }
        }
    }
}
