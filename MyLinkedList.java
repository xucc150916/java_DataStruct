package myLinkedList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;



// LinkedList 双链表的实现
public class MyLinkedList<T> implements Iterable<T> {

    /**
     * 内部属性
     */
    // 链表长度
    private int theSize;
    // 链表修改次数，主要用于iterator对象中方法的使用
    private int modCount = 0;
    // 存储链表头节点，方便操作
    private Node<T> head;
    // 存储链表尾节点，同上
    private Node<T> tail;


    /**
     * 由内部类来存储链表节点数据
     */
	private static class Node<T> {
	    private T value;
	    private Node<T> pre;
	    private Node<T> next;

	    public Node(T value, Node<T> pre, Node<T> next) {
	        this.value = value;
	        this.pre = pre;
	        this.next = next;
        }
    }

    // 无参构造
    public MyLinkedList() {
	    // 调用构造方法时，初始化双链表
	    doClear();
    }



    /*********************** MyLinkedList 对外显示的方法 ***************************/

    /**
     * 清空双链表
     */
    public void clear() {
	    doClear();
    }

    /**
     * 返回双链表长度
     * @return 双链表长度
     */
    public int size() {
	    return this.theSize;
    }

    /**
     * 双链表是否为空
     * @return true双链表为空， false双链表不为空
     */
    public boolean isEmpty() {
	    return size() == 0;
    }

    /**
     * 双链表尾插
     * @param x 尾插元素的值
     * @return 成功插入
     */
    public boolean add(T x) {
	    add(size(), x);
	    return true;
    }

    /**
     * 在指定下标处添加元素
     * @param index 要插入节点的位置
     * @param x 要插入节点的值
     * @return 成功插入
     */
    public boolean add(int index, T x) {
	    addBefore(getNode(index, 0, size()), x);
        return true;
	}

    /**
     * 取指定下标节点的值
     * @param index 下标
     * @return 节点的值
     */
	public T get(int index) {
	    return getNode(index).value;
    }

    /**
     * 设置指定下标的元素值，并返回旧的值
     * @param index 要设置的节点的位置
     * @param newValue 设置的值
     * @return 返回旧的值
     */
    public T set(int index, T newValue) {
	    Node<T> node = getNode(index);
	    T old = node.value;
	    node.value = newValue;
	    return old;
    }

    /**
     * 删除指定下标的元素，并返回删除元素的值
     * @param index 要删除的节点的位置
     * @return 删除节点的值
     */
    public T remove(int index) {
	    return remove(getNode(index));
    }

    /*********************** MyLinkedList 内部方法 ***************************/


    /**
     * 清空双链表
     */
    private void doClear() {
        this.head = new Node<>(null, null, null);
        this.tail = new Node<>(null, head, null);
        head.next = tail;

        this.theSize = 0;
        this.modCount++;
    }


    /**
     * 在指定节点前插入值为x的元素
     * @param node 位置
     * @param x 插入节点的值
     */
    private void addBefore(Node<T> node, T x) {
	    Node<T> newNode = new Node<>(x, node.pre, node);
	    newNode.pre.next = newNode;
	    newNode.next.pre = newNode;
	    this.theSize++;
	    this.modCount++;
    }

    /**
     * 删除指定元素
     * @param node 要删除的节点
     * @return 删除节点的值
     */
    private T remove(Node<T> node) {
	    node.pre.next = node.next;
	    node.next.pre = node.pre;

	    this.theSize++;
	    this.modCount++;
        return node.value;
	}

    /**
     * 根据指定下标得到对应的节点
     * @param index 下标
     * @return 指定下标的节点
     */
    private Node<T> getNode(int index) {
	    return getNode(index, 0, size()-1);
    }

    /**
     * 在指定范围内根据下标找到对应节点
     * @param index 要查找的节点下标
     * @param lower 范围下界
     * @param upper 范围上界
     * @return 对应下标的节点
     */
    private Node<T> getNode(int index, int lower, int upper) {
	    Node<T> cur = null;

	    if(index<lower || index>upper) {
	        throw new IndexOutOfBoundsException();
        }

        // 根据index的大小，分别从头或尾出发，这对于链表的查找来说能够提高效率
        if(index < size()/2) {
	        cur = this.head;
	        for(int i = 0; i < index; i++) {
	            cur = cur.next;
            }
        } else {
	        cur = this.tail;
	        for(int i = size(); i > index; i--) {
	            cur = cur.pre;
            }
        }
        return cur;
    }


    /**
     * 模拟实现LinkedList类的iterator方法
     * @return Iterator的一个对象
     */
	@Override
	public Iterator<T> iterator() {
	    // 这里我们返回一个继承了 iterator 接口的嵌套类
		return new LinkedListIterator();
	}

    /**
     * 继承了Iterator接口的实现类
     */
	private class LinkedListIterator implements Iterator<T> {

	    private Node<T> currentNode = head;
	    private int expectedModCount = modCount;
	    private boolean canRemove = false;

        @Override
        public boolean hasNext() {
	        return currentNode !=  tail;
        }

        @Override
        public T next() {
	        if(modCount != expectedModCount) {
	            throw new ConcurrentModificationException();
            }
            if(!hasNext()) {
	            throw new NoSuchElementException();
            }
            T nextValue = currentNode.value;
	        currentNode = currentNode.next;
	        canRemove = true;
	        return nextValue;
        }

        @Override
        public void remove() {
            if(modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if(!canRemove) {
                throw new IllegalStateException();
            }

            MyLinkedList.this.remove(currentNode.pre);
            expectedModCount++;
            canRemove = false;
        }
    }
	
	

}
