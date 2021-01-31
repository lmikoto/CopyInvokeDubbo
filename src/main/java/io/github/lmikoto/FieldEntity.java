package io.github.lmikoto;

import lombok.Data;

@Data
public class FieldEntity implements Selector, CellProvider {

    protected String key;

    protected String type;

    protected String fieldName;

    protected String value;

    protected ClassEntity targetClass;

    protected boolean generate = true;

    public String getFullNameType() {
        if (targetClass != null) {
            return targetClass.getQualifiedName();
        }
        return type;
    }


    @Override
    public void setSelect(boolean select) {
        setGenerate(select);
    }

    @Override
    public String getCellTitle(int index) {
        String result = "";
        switch (index) {
            case 0:
                result = getFieldName();
                break;
            case 1:
                result = getFullNameType();
                break;
            case 2:
                result = getValue();
                break;
        }
        return result;
    }

    @Override
    public void setValueAt(int column, String text) {
        switch (column) {
            case 0:
                setFieldName(text);
                break;
            case 2:
                setValue(text);
                break;
        }
    }
}
