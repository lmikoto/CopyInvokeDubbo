package io.github.lmikoto;

import com.intellij.lang.jvm.JvmParameter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.*;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;

public class CopyInvokeDubbo extends AnAction {

    private final String INVOKE = "invoke ";

    private final String DOT = ".";

    private final String LEFT = "(";

    private final String RIGHT = ")";

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

            if(parameters.length == 0){
                PsiClassImpl clazz = (PsiClassImpl) method.getContainingClass();
                String qualifiedName = clazz.getQualifiedName();
                putClipboard(INVOKE + qualifiedName + DOT + method.getName() + LEFT + RIGHT);
                return;
            }


            EditeDialog editeDialog = new EditeDialog();
            editeDialog.setSize(800,500);
            editeDialog.setLocationRelativeTo(null);
            editeDialog.setVisible(true);

            
            PsiParameterImpl pImpl = (PsiParameterImpl)parameters[0];
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
    }

    public static void putClipboard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = new StringSelection(text);
        clipboard.setContents(trans, null);
    }
}
