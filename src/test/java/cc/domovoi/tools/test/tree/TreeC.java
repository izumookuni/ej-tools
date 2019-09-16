package cc.domovoi.tools.test.tree;

import cc.domovoi.tools.tree.EmptyTreeInterface;

import java.util.List;
import java.util.Objects;

public class TreeC implements EmptyTreeInterface, Cloneable {

    private String key;

    private List<String> list;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeC that = (TreeC) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "TreeC{" +
                "key='" + key + '\'' +
                ", list=" + list +
                '}';
    }

    public TreeC() {
    }

    public TreeC(String key, List<String> list) {
        this.key = key;
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
