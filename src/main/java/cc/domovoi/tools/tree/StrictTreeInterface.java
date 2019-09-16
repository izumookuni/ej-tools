package cc.domovoi.tools.tree;

import org.jooq.lambda.function.Consumer2;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StrictTreeInterface<E extends StrictTreeInterface<E>> extends GeneralTreeInterface<E>, RelaxedTreeInterface<E, E> {

    @SuppressWarnings("unchecked")
    default <R> List<R> flatMapTree(Function<? super E, ? extends R> mapper, Predicate<? super E> filter, Boolean breakIfFilterNot) {
        List<R> result = new ArrayList<>();
        List<E> currentTree = new ArrayList<>();
        currentTree.add((E) this);
        while (!currentTree.isEmpty()) {
            List<E> buffer = new ArrayList<>();
            currentTree.forEach(tree -> {
                boolean flag = filter.test(tree);
                if (flag) {
                    result.add(mapper.apply(tree));
                }
                if (tree.hasChildren() && (flag || !breakIfFilterNot)) {
                    buffer.addAll(tree.getChildren());
                }
            });
            currentTree.clear();
            currentTree.addAll(buffer);
        }
        return result;
    }

    default <R> List<R> flatMapTree(Function<? super E, ? extends R> mapper, Predicate<? super E> filter) {
        return flatMapTree(mapper, filter, false);
    }

    default <R> List<R> flatMapTree(Function<? super E, ? extends R> mapper) {
        return flatMapTree(mapper, e -> true, false);
    }

    default List<E> flatMapTree(Predicate<? super E> filter) {
        return flatMapTree(Function.identity(), filter, false);
    }

    default List<E> flatMapTree() {
        return flatMapTree(Function.identity(), e -> true, false);
    }

    @SuppressWarnings("unchecked")
    default <E2 extends StrictTreeInterface<E2>> Optional<E> maskTree(E2 mask, Function<? super E2, ? extends E> f, Consumer2<? super E, ? super E> op, Function2<? super E, ? super E, ? extends Boolean> equalsFunction) {
        E innerMask = f.apply(mask);
        if (!equalsFunction.apply((E) this, innerMask)) {
            return Optional.empty();
        }
        List<Tuple2<E, E>> currentTree = new ArrayList<>();
        currentTree.add(new Tuple2<>((E) this, innerMask));
        while (!currentTree.isEmpty()) {
            List<Tuple2<E, E>> buffer = new ArrayList<>();
            currentTree.forEach(t2 -> {
                List<Tuple2<E, E>> innerBuffer = new ArrayList<>();
                List<E> deleteBuffer = new ArrayList<>();
                op.accept(t2.v1(), t2.v2());
                if (t2.v1().hasChildren() && t2.v2().hasChildren()) {
                    t2.v1().getChildren().forEach(v1Child -> {
                        Optional<E> v2ChildOptional = t2.v2().getChildren().stream().filter(v2Child -> equalsFunction.apply(v1Child, v2Child)).findFirst();
                        if (v2ChildOptional.isPresent()) {
                            innerBuffer.add(new Tuple2<>(v1Child, v2ChildOptional.get()));
                        }
                        else {
                            deleteBuffer.add(v1Child);
                        }
                    });
                    t2.v1().getChildren().removeAll(deleteBuffer);
                    buffer.addAll(innerBuffer);
                }
                else if (t2.v1().hasChildren()) {
                    t2.v1().getChildren().clear();
                }

            });
            currentTree.clear();
            currentTree.addAll(buffer);
        }
        return Optional.of((E) this);
    }

    default Optional<E> maskTree(E mask, Consumer2<? super E, ? super E> op, Function2<? super E, ? super E, ? extends Boolean> equalsFunction) {
        return maskTree(mask, Function.identity(), op, equalsFunction);
    }

    default Optional<E> maskTree(E mask, Consumer2<? super E, ? super E> op) {
        return maskTree(mask, op, Objects::equals);
    }

    default Optional<E> maskTree(E mask) {
        return maskTree(mask, (t1, t2) -> {});
    }

    @SuppressWarnings("unchecked")
    default <E2 extends StrictTreeInterface<E2>> E mergeTree(E2 other, Function<? super E2, ? extends E> f, Consumer2<? super E, ? super E> op, Function2<? super E, ? super E, ? extends Boolean> equalsFunction) {
        E e = f.apply(other);
        List<Tuple2<E, E>> currentTree = new ArrayList<>();
        currentTree.add(new Tuple2<>((E) this, e));
        while (!currentTree.isEmpty()) {
            List<Tuple2<E, E>> buffer = new ArrayList<>();
            currentTree.forEach(t2 -> {
                op.accept(t2.v1(), t2.v2());
                if (t2.v1().hasChildren() && t2.v2().hasChildren()) {
                    List<Tuple2<E, E>> innerBuffer = new ArrayList<>();
                    t2.v2().getChildren().forEach(v2Child -> {
                        Optional<E> v1ChildOptional = t2.v1().getChildren().stream().filter(v1Child -> equalsFunction.apply(v1Child, v2Child)).findFirst();
                        if (v1ChildOptional.isPresent()) {
                            innerBuffer.add(new Tuple2<>(v1ChildOptional.get(), v2Child));
                        }
                        else {
                            t2.v1().addChild(v2Child);
                        }
                    });
                    buffer.addAll(innerBuffer);
                }
                else if (t2.v2().hasChildren()) {
                    t2.v1().addChildren(t2.v2().getChildren());
                }
            });
            currentTree.clear();
            currentTree.addAll(buffer);
        }
        return (E) this;
    }

    default E mergeTree(E other, Consumer2<? super E, ? super E> op, Function2<? super E, ? super E, ? extends Boolean> equal) {
        return mergeTree(other, Function.identity(), op, equal);
    }

    default E mergeTree(E other, Consumer2<? super E, ? super E> op) {
        return mergeTree(other, op, Objects::equals);
    }

    default E mergeTree(E other) {
        return mergeTree(other, (b, t) -> {});
    }

    @Override
    default Iterator<E> iterator() {
        return flatMapTree().iterator();
    }
}
