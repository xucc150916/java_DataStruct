package myArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;



// ArrayList 顺序表的实现
public class MyArrayList<T> implements Iterable<T> {

	// 初始数组大小
	private static final int DEFAULT_CAPACITY = 10;
	// 当前数组大小
	private int theSize;
	// 存储数据的泛型数组
	private T[] theItems;
	
	
	// 无参构造，构造时初始化顺序表
	public MyArrayList() {
		doClear();
	}
	
	
	// 清空顺序表
	public void doClear() {
		clear();
	}
	// 将真正的clear()操作封装，提高程序的层次性
	private void clear() {
		this.theSize = 0;
		// 设置数组大小为初默认值
		ensureCapacity(DEFAULT_CAPACITY);
	}
	
	
	// 返回当前顺序表大小
	public int size() {
		return this.theSize;
	}
	// 当前顺序表是否为空
	public boolean isEmpty() {
		if(this.theSize == 0) {
			return true;
		} 
		return false;
	}
	
	
	// 自动调整当前数组大小，不浪费空间
	public void trimToSize() {
		// 将数组大小调整为当前数组大小
		ensureCapacity(this.theSize);
	}
	// 将数组扩容到指定大小
	@SuppressWarnings("unchecked")
	public void ensureCapacity(int len) {
		if(len < this.theSize) {
			return;
		}
		
		// 保存旧的数组引用
		T[] old = this.theItems;
		// theItems指向新的数组对象
		this.theItems = (T[])new Object[len];
		// 值的拷贝
		for(int i = 0; i < this.theSize; i++) {
			this.theItems[i] = old[i];
		}
	}
	
	
	// ArrayList的灵魂方法set/get
	// set方法设置数组指定下标的值并返回修改之前的值
	public T set(int index, T newValue) {
		// 数组下标越界，抛出异常
		if(index>=this.theSize || index<0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		T oldValue = this.theItems[index];
		this.theItems[index] = newValue;
		return oldValue;
	}
	public T get(int index) {
		// 数组下标越界
		if(index>=this.theSize || index<0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return this.theItems[index];
	}
	
	
	// 插入元素，尾插. 顺序表插入删除要移动数组进行数据拷贝，时间复杂度为O(n)，尾插是插入的一种特殊情况
	public boolean add(T newValue) {
		add(theSize, newValue);
		// 尾插一定会成功
		return true;
	}
	// 在指定下标处插入元素
	public void add(int index, T newValue) {
		// 数组已满，扩容
		if(this.theItems.length == this.theSize) {
			// 这里的扩容策略自定义，我设置为 当前数组大小*2+1
			ensureCapacity((this.theSize)*2+1);
		}
		// 从后向前拷贝数据
		for(int i = this.theSize; i-1>=index; i--) {
			this.theItems[i] = this.theItems[i-1]; 
		}
		this.theItems[index] = newValue;
		this.theSize++;
	}
	
	
	// 删除指定下标元素，返回删除的值
	public T remove(int index) {
		// 下标越界，抛出异常
		if(index>=this.theSize || index<0) {
			throw new IndexOutOfBoundsException();
		}
		// 保存删除的值
		T removeValue = this.theItems[index];
		// 从删除处向后移动数组
		for(int i = index; i < this.theSize-1; i--) {
			this.theItems[i] = this.theItems[i+1];
		}
		this.theSize--;
		return removeValue;
	}
	
	// 因为java.util.ArrayList包中提供的ArrayList实现了Iterator接口，覆写了其中的iterator方法，但是这部分内容与顺序表无关，有兴趣可以看一下
	@Override
	public Iterator<T> iterator() {
		// iterator方法返回一个一个Iterator的对象，这里直接返回下面内部类的对象
		return new MyArrayListIterator();
	}
	
	// 内部类
	private class MyArrayListIterator implements Iterator<T> {

		private int count = 0;
		
		@Override
		public boolean hasNext() {
			return theSize > count;
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return theItems[count++];
		}

		@Override
		public void remove() {
			MyArrayList.this.remove(count--);
		}	
	}

}
