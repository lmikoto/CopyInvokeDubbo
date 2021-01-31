package io.github.lmikoto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassEntity implements CellProvider {

    private String className;

    private List<FieldEntity> fields = new ArrayList<>();

    private boolean generate = true;

    private boolean lock = false;

    public void addField(FieldEntity fieldEntity){
        fields.add(fieldEntity);
    }

    public String getQualifiedName() {
        return className;
    }

    @Override
    public String getCellTitle(int index) {
        String result = "";
        switch (index) {
            case 0:
                result = getClassName();
                break;
            case 1:
                result = getClassName();
                break;
        }
        return result;
    }

    @Override
    public void setValueAt(int column, String text) {
    }
}
