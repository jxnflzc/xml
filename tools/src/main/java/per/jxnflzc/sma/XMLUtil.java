package per.jxnflzc.sma;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class XMLUtil {
    public static ClassDiagram getClassDiagramById(List<ClassDiagram> classDiagramList, String id) {
        for (ClassDiagram cd : classDiagramList) {
            if (cd.getId().equals(id)) {
                return cd;
            }
        }
        return null;
    }

    public static String getClassDiagramNameById(List<ClassDiagram> classDiagramList, String id) {
        for (ClassDiagram cd : classDiagramList) {
            if (cd.getId().equals(id)) {
                return cd.getName();
            }
        }
        return null;
    }

    public static int getClassDiagramIndexById(List<ClassDiagram> classDiagramList, String id) {
        for (int i = 0; i < classDiagramList.size(); i++) {
            if (classDiagramList.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    public static ClassDiagram attribute(NodeList nodeList, ClassDiagram cd) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node attribute = nodeList.item(i);
            if (attribute.getNodeType() == Node.ELEMENT_NODE) {
                NodeList attributeChild = attribute.getChildNodes();
                for (int j = 0; j < attributeChild.getLength(); j++) {
                    Node node = attributeChild.item(j);
                    switch (node.getNodeName()) {
                        case "a:Name":
                            cd.addAttribute(node.getFirstChild().getNodeValue());
                            break;
                        case "a:DataType":
                            //System.out.println("\tType: " + node.getFirstChild().getNodeValue());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return cd;
    }

    public static ClassDiagram operation(NodeList nodeList, ClassDiagram cd) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node operation = nodeList.item(i);
            if (operation.getNodeType() == Node.ELEMENT_NODE) {
                NodeList operationChild = operation.getChildNodes();


                Operation op = new Operation();
                for (int j = 0; j < operationChild.getLength(); j++) {
                    Node node = operationChild.item(j);
                    switch (node.getNodeName()) {
                        case "a:Name":
                            op.setName(node.getFirstChild().getNodeValue());
                            cd.addOperation(op);
                            break;
                        case "a:ReturnType":
                            //System.out.println("\tReturnType: " + node.getFirstChild().getNodeValue());
                            break;
                        case "c:Parameters":
                            op = parameter(node.getChildNodes(), op);
                            cd.setOperation(cd.getOperations().size() - 1, op);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return cd;
    }

    public static Operation parameter(NodeList nodeList, Operation operation) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node parameter = nodeList.item(i);
            if (parameter.getNodeType() == Node.ELEMENT_NODE) {
                NodeList parameterChild = parameter.getChildNodes();

                for (int j = 0; j < parameterChild.getLength(); j++) {
                    Node node = parameterChild.item(j);
                    switch (node.getNodeName()) {
                        case "a:Name":
                            operation.addParameters(node.getFirstChild().getNodeValue());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return operation;
    }

    public static List<ClassDiagram> getClass(Document doc, List<ClassDiagram> classDiagramList){
        NodeList nl = doc.getElementsByTagName("c:Classes");
        Node cClasses = nl.item(0);

        if (cClasses != null) {
            NodeList oClasses = cClasses.getChildNodes();

            for (int i = 0; i < oClasses.getLength(); i++) {
                Node oClass = oClasses.item(i);
                if (oClass.getNodeType() == Node.ELEMENT_NODE) {
                    ClassDiagram cd = new ClassDiagram();
                    cd.setId(((Element)oClass).getAttribute("Id"));//获取类对应Id
                    NodeList oClassChild = oClass.getChildNodes();
                    for (int j = 0; j < oClassChild.getLength(); j++) {
                        Node node = oClassChild.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            switch (node.getNodeName()) {
                                case "a:Name":
                                    cd.setName(node.getFirstChild().getNodeValue());//获取类名
                                    break;
                                case "c:Attributes":
                                    cd = XMLUtil.attribute(node.getChildNodes(), cd);//获取类的所有属性
                                    break;
                                case "c:Operations":
                                    cd = XMLUtil.operation(node.getChildNodes(), cd);//获取类的所有方法
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    classDiagramList.add(cd);
                }
            }
        }

        return classDiagramList;
    }

    public static List<ClassDiagram> getDit(Document doc, List<ClassDiagram> classDiagramList){
        NodeList nl = doc.getElementsByTagName("c:Generalizations");
        Node cGeneralizations = nl.item(0);

        if (cGeneralizations != null) {
            NodeList cGeneralization = cGeneralizations.getChildNodes();

            for (int i = 0; i < cGeneralization.getLength(); i++) {
                Node oGeneralization = cGeneralization.item(i);
                if (oGeneralization.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList oGeneralizationChildren = oGeneralization.getChildNodes();
                    String parentId = null, childId = null;
                    for (int j = 0; j < oGeneralizationChildren.getLength(); j++) {
                        Node oGeneralizationChild = oGeneralizationChildren.item(j);
                        if (oGeneralizationChild.getNodeType() == Node.ELEMENT_NODE) {
                            switch (oGeneralizationChild.getNodeName()) {
                                case "c:Object1":
                                    parentId = ((Element)oGeneralizationChild.getChildNodes().item(1)).getAttribute("Ref");
                                    break;
                                case "c:Object2":
                                    childId = ((Element)oGeneralizationChild.getChildNodes().item(1)).getAttribute("Ref");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    for (ClassDiagram cd : classDiagramList) {
                        if (cd.getId().equals(childId)) {
                            ClassDiagram parent = getClassDiagramById(classDiagramList, parentId);
                            cd.setParentId(parent.getId());
                            cd.setParentName(parent.getName());
                            cd.setDit(parent.getDit() + 1);
                        }
                    }

                }
            }
        }

        return classDiagramList;
    }

    public static List<ClassDiagram> getNoc(List<ClassDiagram> classDiagramList){
        for (ClassDiagram cd : classDiagramList) {
            for (ClassDiagram cdNew : classDiagramList) {
                if (cd.getId().equals(cdNew.getParentId()))
                    cd.setNoc(cd.getNoc() + 1);
            }
        }

        return classDiagramList;
    }

    public static List<ClassDiagram> getCbo(Document doc, List<ClassDiagram> classDiagramList){
        classDiagramList = getCboByDependencyType(doc, classDiagramList, "c:Associations");
        classDiagramList = getCboByDependencyType(doc, classDiagramList, "c:Dependencies");
        return classDiagramList;
    }

    private static List<ClassDiagram> getCboByDependencyType(Document doc, List<ClassDiagram> classDiagramList, String dependencyType){
        NodeList nl = doc.getElementsByTagName(dependencyType);
        Node cDependencies = nl.item(0);

        if (cDependencies != null) {
            NodeList cDependency = cDependencies.getChildNodes();

            for (int i = 0; i < cDependency.getLength(); i++) {
                Node oDependency = cDependency.item(i);
                if (oDependency.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList oDependencyChildren = oDependency.getChildNodes();
                    String dependencyId1 = null, dependencyId2 = null;

                    for (int j = 0; j < oDependencyChildren.getLength(); j++) {
                        if (oDependency.getNodeType() == Node.ELEMENT_NODE) {
                            Node oDependencyChild = oDependencyChildren.item(j);
                            switch (oDependencyChild.getNodeName()) {
                                case "c:Object1":
                                    dependencyId1 = ((Element)oDependencyChild.getChildNodes().item(1)).getAttribute("Ref");
                                    break;
                                case "c:Object2":
                                    dependencyId2 = ((Element)oDependencyChild.getChildNodes().item(1)).getAttribute("Ref");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    for (ClassDiagram cd : classDiagramList) {
                        if (cd.getId().equals(dependencyId1) || cd.getId().equals(dependencyId2)) {
                            cd.setCbo(cd.getCbo() + 1);
                        }
                    }
                }
            }
        }

        return classDiagramList;
    }
}
