package cc.domovoi.tools.test.tree;

import cc.domovoi.tools.tree.RelaxedTreeInterface;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeB implements RelaxedTreeInterface<TreeC, TreeB> {

    private String key;

    private List<String> list;

    private List<TreeC> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeB treeCS = (TreeB) o;
        return Objects.equals(key, treeCS.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "TreeB{" +
                "key='" + key + '\'' +
                ", list=" + list +
                ", children=" + children +
                '}';
    }

    public TreeB() {
    }

    public TreeB(String key, List<String> list, TreeC... children) {
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
    public List<TreeC> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<TreeC> children) {
        this.children = children;
    }
}
