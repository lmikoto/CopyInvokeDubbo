package io.github.lmikoto;

import com.intellij.lang.jvm.JvmParameter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.impl.compiled.ClsClassImpl;
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
            if(parameters.length > 1){
                Messages.showMessageDialog("目前不支持多参数的接口","错误",Messages.getInformationIcon());
                return;
            }

            PsiClassImpl clazz = (PsiClassImpl) method.getContainingClass();
            String qualifiedName = clazz.getQualifiedName();

            if(parameters.length == 0){
                ClipboardUtils.putClipboard(DubboUtils.getEmptyInvoker(qualifiedName,method.getName()));
                return;
            }

            PsiParameterImpl pImpl = (PsiParameterImpl)parameters[0];
            PsiClassImpl typeClass = (PsiClassImpl) getPsiClass(pImpl.getType());
            PsiField[] allFields = typeClass.getAllFields();

            ClassEntity classEntity = getClassEntity(pImpl);

            for (PsiField psiField: allFields){
                PsiFieldImpl field = (PsiFieldImpl)psiField;
                FieldEntity filedEntity = getFiledEntity(field);
                classEntity.addField(filedEntity);
            }

            EditeDialog editeDialog = new EditeDialog(classEntity, qualifiedName + "." + method.getName());
            editeDialog.setSize(800,500);
            editeDialog.setLocationRelativeTo(null);
            editeDialog.setVisible(true);
        }
    }

    private ClassEntity getClassEntity(PsiParameterImpl param) {
        ClassEntity classEntity = new ClassEntity();
        PsiClassImpl psiClass = (PsiClassImpl)getPsiClass(param.getType());

        classEntity.setClassName(psiClass.getQualifiedName());
        return classEntity;
    }

    private FieldEntity getFiledEntity(PsiFieldImpl field){
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setFieldName(field.getName());
        PsiClass typeClass = getPsiClass(field.getType());
        fieldEntity.setType(typeClass.getQualifiedName());
        // todo 暂时不支持嵌套
        return fieldEntity;
    }

    private PsiClass getPsiClass(PsiType psiType){
        PsiClassReferenceType type = (PsiClassReferenceType)psiType;
        PsiClassType.ClassResolveResult resolveResult = type.resolveGenerics();
        return resolveResult.getElement();
    }
}
