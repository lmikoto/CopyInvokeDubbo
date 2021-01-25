package io.github.lmikoto;

import com.intellij.lang.jvm.JvmParameter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.intellij.psi.impl.source.PsiParameterImpl;
import org.jetbrains.annotations.NotNull;

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
                String typeClassName = type.getClassName();
                System.out.println(pImpl);
            }
            System.out.println(method);
        }
    }
}
