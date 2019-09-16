package cc.domovoi.tools.tree;

import org.jooq.lambda.function.Consumer2;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * RelaxedTreeInterface
 *
 * @param <E> Children type
 */
public interface RelaxedTreeInterface<E extends RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, T extends RelaxedTreeInterface<E, T>> extends GeneralTreeInterface<E> {

    default <R> List<R> flatMapTreeGeneral(Function<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends R> mapper, Predicate<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> filter, Boolean breakIfFilterNot) {

        List<R> result = new ArrayList<>();
        List<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> currentTree = new ArrayList<>();
        currentTree.add(this);
        while (!currentTree.isEmpty()) {
            List<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> buffer = new ArrayList<>();
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

    default <R> List<R> flatMapTreeGeneral(Function<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends R> mapper, Predicate<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> filter) {
        return flatMapTreeGeneral(mapper, filter, false);
    }

    default <R> List<R> flatMapTreeGeneralWithMapper(Function<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends R> mapper) {
        return flatMapTreeGeneral(mapper, e -> true, false);
    }

    default List<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> flatMapTreeGeneralWithFilter(Predicate<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> filter) {
        return flatMapTreeGeneral(Function.identity(), filter, false);
    }

    default List<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> flatMapTreeGeneral() {
        return flatMapTreeGeneral(Function.identity(), e -> true, false);
    }

    @SuppressWarnings("unchecked")
    default <T2 extends T> Optional<T> maskTreeGeneral(T2 mask, Function<? super T2, ? extends T> f, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op, Function2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends Boolean> equalsFunction) {
        T innerMask = f.apply(mask);
        if (!equalsFunction.apply((T) this, innerMask)) {
            return Optional.empty();
        }
        List<Tuple2<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>>> currentTree = new ArrayList<>();
        currentTree.add(new Tuple2<>(this, innerMask));
        while (!currentTree.isEmpty()) {
            List<Tuple2<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>>> buffer = new ArrayList<>();
            currentTree.forEach(t2 -> {
                List<Tuple2<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>>> innerBuffer = new ArrayList<>();
                List<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> deleteBuffer = new ArrayList<>();
                op.accept(t2.v1(), t2.v2());
                if (t2.v1().hasChildren() && t2.v2().hasChildren()) {
                    t2.v1().getChildren().forEach(v1Child -> {
                        Optional<? extends RelaxedTreeInterface> v2ChildOptional = t2.v2().getChildren().stream().filter(v2Child -> equalsFunction.apply(v1Child, v2Child)).findFirst();
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
        return Optional.of((T) this);
    }

    default <T2 extends T> Optional<T> maskTreeGeneral(T2 mask, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op, Function2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends Boolean> equalsFunction) {
        return maskTreeGeneral(mask, Function.identity(), op, equalsFunction);
    }

    default <T2 extends T> Optional<T> maskTreeGeneral(T2 mask, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op) {
        return maskTreeGeneral(mask, op, Objects::equals);
    }

    default <T2 extends T> Optional<T> maskTreeGeneral(T2 mask) {
        return maskTreeGeneral(mask, (t1, t2) -> {});
    }

    @SuppressWarnings("unchecked")
    default <T2 extends T> T mergeTreeGeneral(T2 other, Function<? super T2, ? extends T> f, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op, Function2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends Boolean> equalsFunction) {
        T e = f.apply(other);
        List<Tuple2<RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>, RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>>> currentTree = new ArrayList<>();
        currentTree.add(new Tuple2<>((RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>) this, (RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>) e));
        while (!currentTree.isEmpty()) {
            List<Tuple2<RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>, RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>>> buffer = new ArrayList<>();
            currentTree.forEach(t2 -> {
                op.accept(t2.v1(), t2.v2());
                if (t2.v1().hasChildren() && t2.v2().hasChildren()) {
                    List<Tuple2<RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>, RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>>> innerBuffer = new ArrayList<>();
                    t2.v2().getChildren().forEach(v2Child -> {
                        Optional<RelaxedTreeInterface<?, ?>> v1ChildOptional = t2.v1().getChildren().stream().filter(v1Child -> equalsFunction.apply(v1Child, v2Child)).findFirst();
                        if (v1ChildOptional.isPresent()) {
                            innerBuffer.add(new Tuple2<>((RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>) v1ChildOptional.get(), (RelaxedTreeInterface<RelaxedTreeInterface<?, ?>, ?>) v2Child));
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
        return (T) this;
    }

    default <T2 extends T> T mergeTreeGeneral(T2 other, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op, Function2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? extends Boolean> equal) {
        return mergeTreeGeneral(other, Function.identity(), op, equal);
    }

    default <T2 extends T> T mergeTreeGeneral(T2 other, Consumer2<? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>, ? super RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> op) {
        return mergeTreeGeneral(other, op, Objects::equals);
    }

    default <T2 extends T> T mergeTreeGeneral(T2 other) {
        return mergeTreeGeneral(other, (b, t) -> {});
    }

    default Iterator<RelaxedTreeInterface<? extends RelaxedTreeInterface, ?>> iteratorGeneral() {
        return flatMapTreeGeneral().iterator();
    }

    @Override
    default Iterator<E> iterator() {
        if (this.hasChildren()) {
            List<Class<?>> classList = this.getChildren().stream().map(E::getClass).collect(Collectors.toList());
            return flatMapTreeGeneral(relaxedTreeInterfaces -> (E) relaxedTreeInterfaces, relaxedTreeInterfaces -> classList.contains(relaxedTreeInterfaces.getClass())).iterator();
        }
        else {
            return Collections.emptyIterator();
        }

    }
}
