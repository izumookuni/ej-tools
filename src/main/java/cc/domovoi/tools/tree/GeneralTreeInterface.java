package cc.domovoi.tools.tree;

import java.util.*;

public interface GeneralTreeInterface<E> extends Iterable<E> {

    List<E> getChildren();

    void setChildren(List<E> children);

    default void addChild(E child) {
        if (Objects.isNull(getChildren())) {
            setChildren(new ArrayList<>());
        }
        getChildren().add(child);
    }

    default void addChildren(Collection<? extends E> children) {
        if (Objects.isNull(getChildren())) {
            setChildren(new ArrayList<>());
        }
        getChildren().addAll(children);
    }
    
    default void addChildren(E... children) {
        if (Objects.isNull(getChildren())) {
            setChildren(new ArrayList<>());
        }
        for (E child : children) {
            getChildren().add(child);
        }
    }

    default Boolean hasChildren() {
        return Objects.nonNull(getChildren()) && !getChildren().isEmpty();
    }

    @Override
    default Iterator<E> iterator() {
        return hasChildren() ? getChildren().iterator() : Collections.emptyIterator();
    }
}
