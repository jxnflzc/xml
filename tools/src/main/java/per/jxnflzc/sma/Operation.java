package per.jxnflzc.sma;

import java.util.ArrayList;
import java.util.List;

public class Operation {
    private String name;
    private List<String> parameters;

    public Operation() {
        this.parameters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void addParameters(String parameter) {
        this.parameters.add(parameter);
    }

    public void setParameters(int index, String parameter) {
        this.parameters.set(index, parameter);
    }

    @Override
    public String toString() {
        String result = this.name + "\tParameters: ";
        for (int i = 0; i < parameters.size(); i++) {
            result += this.parameters.get(i) + "\t";
        }
        return  result;
    }
}
