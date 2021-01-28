package io.github.lmikoto;


public interface CellProvider {

    String getCellTitle(int index);

    void setValueAt(int column, String text);
}
