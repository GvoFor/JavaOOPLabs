package ua.mpumnia.matrix;

import java.util.Objects;

public class Dimension {

    public int rowsN;
    public int columnsN;

    public Dimension(int rowsN, int columnsN) {
        this.rowsN = rowsN;
        this.columnsN = columnsN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimension dimension = (Dimension) o;
        return rowsN == dimension.rowsN && columnsN == dimension.columnsN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowsN, columnsN);
    }

    @Override
    public String toString() {
        return rowsN + "x" + columnsN;
    }

}
