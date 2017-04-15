package com.chair.manager.redis;

public interface IRedisFunction<E, T> {

    public T callBack(E e);

}
