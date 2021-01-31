package io.github.lmikoto;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class EditeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel filedPanel;
    private JScrollPane sp;

    public EditeDialog(ClassEntity classEntity,String methodQualified) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        initListener(classEntity,methodQualified);
    }

    private void initListener(ClassEntity classEntity,String methodQualified) {
        JXTreeTable treeTable = new JXTreeTable(new FiledTreeTableModel(createData(classEntity)));
        treeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        treeTable.expandAll();
        treeTable.setCellSelectionEnabled(false);
        final DefaultListSelectionModel defaultListSelectionModel = new DefaultListSelectionModel();
        treeTable.setSelectionModel(defaultListSelectionModel);

        defaultListSelectionModel.setSelectionMode(SINGLE_SELECTION);
        defaultListSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                defaultListSelectionModel.clearSelection();
            }
        });
        treeTable.setRowHeight(30);
        sp.setViewportView(treeTable);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK(classEntity,methodQualified);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(ClassEntity classEntity,String methodQualified) {
        ClipboardUtils.putClipboard(DubboUtils.getInvoker(classEntity,methodQualified));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private TreeTableNode createData(ClassEntity classEntity) {
        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(classEntity);
        createDataNode(root,classEntity);
        return root;
    }

    private void createDataNode(DefaultMutableTreeTableNode root, ClassEntity classEntity) {
        for(FieldEntity field: classEntity.getFields()){
            DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(field);
            root.add(node);
        }
    }
}
