package com.xucc.datastruct.linkedlist;

import java.util.Iterator;

/**
 * 链表方法接口
 */
public interface Link<T> extends Iterable<T> {

    /**
     * 清空双链表
     */
    void clear();

    /**
     * 返回双链表长度
     * @return 双链表长度
     */
    int size();

    /**
     * 双链表是否为空
     * @return true双链表为空， false双链表不为空
     */
    boolean isEmpty();

    /**
     * 双链表尾插
     * @param x 尾插元素的值
     * @return 成功插入
     */
    boolean add(T x);

    /**
     * 在指定下标处添加元素
     * @param index 要插入节点的位置
     * @param x 要插入节点的值
     * @return 成功插入
     */
    boolean add(int index, T x);

    /**
     * 取指定下标节点的值
     * @param index 下标
     * @return 节点的值
     */
    T get(int index);

    /**
     * 设置指定下标的元素值，并返回旧的值
     * @param index 要设置的节点的位置
     * @param newValue 设置的值
     * @return 返回旧的值
     */
    T set(int index, T newValue);

    /**
     * 删除指定下标的元素，并返回删除元素的值
     * @param index 要删除的节点的位置
     * @return 删除节点的值
     */
    T remove(int index);

}
