package com.tohoieva.mathtests.configuration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DOMManager {

    private ArrayList<Task> tasks;
    private String root;
    private String wrapper;
    private File file;

    public DOMManager() {
        this.tasks = new ArrayList<>();
        this.root = "tasks";
        this.wrapper = "task";
    }

    public DOMManager(String root, String wrapper) {
        this.root = root;
        this.wrapper = wrapper;
    }

    public DOMManager(ArrayList<Task> tasks, String root, String wrapper) {
        this.tasks = tasks;
        this.root = root;
        this.wrapper = wrapper;
    }

    public String getWrapper() {
        return wrapper;
    }

    public void setWrapper(String wrapper) {
        this.wrapper = wrapper;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void createXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootEl = document.createElement(root);
            document.appendChild(rootEl);

            for (Task task : tasks) {
                Element taskEl = document.createElement(wrapper);
                rootEl.appendChild(taskEl);

                Element questionEl = document.createElement("question");
                questionEl.appendChild(document.createTextNode(task.getQuestion()));
                taskEl.appendChild(questionEl);

                Element answerEl = document.createElement("answer");
                answerEl.appendChild(document.createTextNode(task.getAnswer()));
                taskEl.appendChild(answerEl);

                Element gradeEl = document.createElement("grade");
                gradeEl.appendChild(document.createTextNode(task.getGrade()));
                taskEl.appendChild(gradeEl);

                Element topicEl = document.createElement("topic");
                topicEl.appendChild(document.createTextNode(task.getTopic()));
                taskEl.appendChild(topicEl);

                Element levelEl = document.createElement("level");
                levelEl.appendChild(document.createTextNode(task.getLevel()));
                taskEl.appendChild(levelEl);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource doms = new DOMSource(document);

            file = new File("tasks.xml");
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(doms, streamResult);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeXml(File file) {
        if (file.exists()) {
            try {
                this.file = file;
                ArrayList<Task> readTask = readXml(file);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                Element rootEl = document.createElement(root);
                document.appendChild(rootEl);

                for (Task task : tasks) {
                    Element taskEl = document.createElement(wrapper);
                    rootEl.appendChild(taskEl);

                    Element questionEl = document.createElement("question");
                    questionEl.appendChild(document.createTextNode(task.getQuestion()));
                    taskEl.appendChild(questionEl);

                    Element answerEl = document.createElement("answer");
                    answerEl.appendChild(document.createTextNode(task.getAnswer()));
                    taskEl.appendChild(answerEl);

                    Element gradeEl = document.createElement("grade");
                    gradeEl.appendChild(document.createTextNode(task.getGrade()));
                    taskEl.appendChild(gradeEl);

                    Element topicEl = document.createElement("topic");
                    topicEl.appendChild(document.createTextNode(task.getTopic()));
                    taskEl.appendChild(topicEl);

                    Element levelEl = document.createElement("level");
                    levelEl.appendChild(document.createTextNode(task.getLevel()));
                    taskEl.appendChild(levelEl);
                }

                for (Task task : readTask) {
                    Element taskEl = document.createElement(wrapper);
                    rootEl.appendChild(taskEl);

                    Element questionEl = document.createElement("question");
                    questionEl.appendChild(document.createTextNode(task.getQuestion()));
                    taskEl.appendChild(questionEl);

                    Element answerEl = document.createElement("answer");
                    answerEl.appendChild(document.createTextNode(task.getAnswer()));
                    taskEl.appendChild(answerEl);

                    Element gradeEl = document.createElement("grade");
                    gradeEl.appendChild(document.createTextNode(task.getGrade()));
                    taskEl.appendChild(gradeEl);

                    Element topicEl = document.createElement("topic");
                    topicEl.appendChild(document.createTextNode(task.getTopic()));
                    taskEl.appendChild(topicEl);

                    Element levelEl = document.createElement("level");
                    levelEl.appendChild(document.createTextNode(task.getLevel()));
                    taskEl.appendChild(levelEl);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource doms = new DOMSource(document);

                StreamResult streamResult = new StreamResult(file);
                transformer.transform(doms, streamResult);
            } catch (ParserConfigurationException | TransformerException ex) {
                Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createXml();
        }
    }

    public ArrayList<Task> readXml(File file) {
        ArrayList<Task> data = new ArrayList<>();

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            setFile(file);
            setRoot(document.getDocumentElement().getNodeName());

            setWrapper(wrapper);
            NodeList list = document.getElementsByTagName(wrapper);

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String question = element.getElementsByTagName("question").item(0).getTextContent();
                    String answer = element.getElementsByTagName("answer").item(0).getTextContent();
                    String grade = element.getElementsByTagName("grade").item(0).getTextContent();
                    String topic = element.getElementsByTagName("topic").item(0).getTextContent();
                    String level = element.getElementsByTagName("level").item(0).getTextContent();
                    data.add(new Task(question, answer, grade, topic, level));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }
}
