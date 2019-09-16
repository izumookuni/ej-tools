package cc.domovoi.tools.tree;

import java.util.Collections;
import java.util.List;

public interface EmptyTreeInterface extends StrictTreeInterface<EmptyTreeInterface> {

    @Override
    default List<EmptyTreeInterface> getChildren() {
        return Collections.emptyList();
    }

    @Override
    default void setChildren(List<EmptyTreeInterface> children) {
        // nothing
    }
}
