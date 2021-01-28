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

    public String getRealType() {
        if (targetClass != null) {
            return targetClass.getClassName();
        }
        return type;
    }

    public String getBriefType() {
        if (targetClass != null) {
            return targetClass.getClassName();
        }
        int i = type.indexOf(".");
        if (i > 0) {
            return type.substring(i);
        }
        return type;
    }

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
                result = getKey();
                break;
            case 1:
                result = getValue();
                break;
            case 2:
                result = getBriefType();
                break;
            case 3:
                result = getFieldName();
                break;
        }
        return result;
    }

    @Override
    public void setValueAt(int column, String text) {

    }
}
