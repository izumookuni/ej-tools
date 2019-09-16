package cc.domovoi.tools.test.tree;

import cc.domovoi.tools.tree.RelaxedTreeInterface;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeA implements RelaxedTreeInterface<TreeB, TreeA> {

    private String key;

    private List<String> list;

    private List<TreeB> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeA treeBS = (TreeA) o;
        return Objects.equals(key, treeBS.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "TreeA{" +
                "key='" + key + '\'' +
                ", list=" + list +
                ", children=" + children +
                '}';
    }

    public TreeA() {
    }

    public TreeA(String key, List<String> list, TreeB... children) {
        this.key = key;
        this.list = list;
        this.children = Stream.of(children).collect(Collectors.toList());
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

    @Override
    public List<TreeB> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<TreeB> children) {
        this.children = children;
    }
}
