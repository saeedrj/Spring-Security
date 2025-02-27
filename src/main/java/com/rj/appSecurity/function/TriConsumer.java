package com.rj.appSecurity.function;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T,U,V> {

     void accept(T t, U u, V v);

    default org.apache.commons.lang3.function.TriConsumer<T, U, V> andThen(final org.apache.commons.lang3.function.TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);

        return (t, u, v) -> {
            accept(t, u, v);
            after.accept(t, u, v);
        };
    }
}
