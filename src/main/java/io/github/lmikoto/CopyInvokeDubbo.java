package io.github.lmikoto;

import com.intellij.lang.jvm.JvmParameter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.*;

public class CopyInvokeDubbo extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = e.getDataContext().getData(CommonDataKeys.PSI_FILE);
        PsiElement referenceAt = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiElement parent = referenceAt.getParent();
        if(parent instanceof  PsiMethodImpl){
            PsiMethodImpl method = (PsiMethodImpl) parent;
            JvmParameter[] parameters = method.getParameters();
            for(JvmParameter p : parameters){
                PsiParameterImpl pImpl = (PsiParameterImpl)p;
                String name = pImpl.getName();
                PsiClassReferenceType type = (PsiClassReferenceType)pImpl.getType();

                PsiClassType.ClassResolveResult classResolveResult = type.resolveGenerics();
                PsiClassImpl typeClass = (PsiClassImpl)classResolveResult.getElement();

                PsiField[] allFields = typeClass.getAllFields();

                for (PsiField psiField: allFields){
                    PsiFieldImpl field = (PsiFieldImpl)psiField;
                    PsiType type2 = field.getType();
                    PsiType type1 = psiField.getType();
                }


                System.out.println(typeClass);
            }
            System.out.println(method);
        }
    }
}
