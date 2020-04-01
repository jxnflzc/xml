package per.jxnflzc.sma;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {
    private String id;
    private String name;
    private String parentId;
    private String parentName;
    private int dit;
    private int cbo;
    private int noc;
    private List<String> attributes;
    private List<Operation> operations;

    public ClassDiagram() {
        this.dit = 0;
        this.cbo = 0;
        this.noc = 0;
        this.attributes = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }

    public int getCbo() {
        return cbo;
    }

    public void setCbo(int cbo) {
        this.cbo = cbo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getDit() {
        return dit;
    }

    public void setDit(int dit) {
        this.dit = dit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public void setOperation(int index, Operation operation) {
        this.operations.set(index, operation);
    }

    @Override
    public String toString() {
        String result = "======\t" + this.name + "\t======";
        result += "\n\tId: " + this.id;
        result += "\n\tDIT: " + this.dit;
        result += "\n\tCBO: " + this.cbo;
        result += "\n\tNOC: " + this.noc;
        result += "\n\tParentId: " + this.parentId;
        result += "\n\tParentName: " + this.parentName;

        for (int i = 0; i < this.attributes.size(); i++) {
            result += "\n\tAttribute: " + this.attributes.get(i);
        }

        for (int i = 0; i < this.operations.size(); i++) {
            result += "\n\tOperation: " + this.operations.get(i).toString();
        }
        return  result;
    }
}
