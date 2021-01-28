package io.github.lmikoto;

import com.intellij.psi.PsiClass;
import lombok.Data;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassEntity implements Selector, CellProvider {

    private PsiClass psiClass;

    private String fieldTypeSuffix;

    private String className;

    private List<FieldEntity> fields = new ArrayList<>();

    private String packName;

    private boolean generate = true;

    private boolean lock = false;


    public String getQualifiedName() {
        String fullClassName;
        if (!TextUtils.isEmpty(packName)) {
            fullClassName = packName + "." + className;
        } else {
            fullClassName = className;
        }

        return fullClassName;
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
                result = getClassName();
                break;
            case 3:
                result = getClassName();
                break;
        }
        return result;
    }

    @Override
    public void setValueAt(int column, String text) {
    }
}
