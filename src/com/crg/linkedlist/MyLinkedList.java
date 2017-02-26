package com.crg.linkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *  实现一个自定义的双链表数据结构的　LinkedList
 * @author crg
 *
 * @param <AnyType>
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>{
	
	/**
	 *  该链表当前的　size
	 */
	private int mTheSize;
	
	/**
	 *  链表被修改的次数的合计，添加节点，删除节点的总数
	 */
	private int mModCount;
	
	/**
	 *  链表的头，空节点
	 */
	private Node<AnyType> mBeginMarker;
	
	/**
	 * 　链表的尾，空节点
	 */
	private Node<AnyType> mEndMarker;
	
	/**
	 *  一个嵌套类，链表的每一个节点都是该类的一个对象
	 * @author crg
	 *
	 * @param <AnyType>
	 */
	private static class Node<AnyType>{
		public AnyType mData;
		public Node<AnyType> mPrev;
		public Node<AnyType> mNext;
		public Node(AnyType data, Node<AnyType> prev, Node<AnyType> next){
			mData = data;
			mPrev = prev;
			mNext = next;
		}
	}
	
	/**
	 *  构造一个空链表
	 */
	public MyLinkedList(){
		clear();
	}
	
	/**
	 *  置空链表
	 */
	public void clear(){
		mBeginMarker = new Node<AnyType>(null, null, null);
		mEndMarker = new Node<AnyType>(null, mBeginMarker, null);
		mBeginMarker.mNext = mEndMarker;
		mTheSize = 0;
		mModCount ++;
	}
	
	/**
	 *  获得当前链表节点的数量
	 * @return
	 */
	public int size(){
		return mTheSize;
	}
	
	public boolean isEmpty(){
		return mTheSize == 0;
	}
	
	
	
	
	/**
	 *  在　p 节点的前面添加一个　数据为　data　的节点
	 * @param p
	 * @param data
	 */
	private void addBefore(Node<AnyType> p, AnyType data){
		Node<AnyType> newNode = new Node<AnyType>(data, p.mPrev, p);
		newNode.mPrev.mNext = newNode;
		p.mPrev = newNode;
		mTheSize ++;
		mModCount ++;
	}
	
	/**
	 *  在链表末尾添加　一个 AnyType
	 * @param x
	 */
	public void add(AnyType x){
		add(size(), x);
	}
	
	/**
	 *  在链表指定位置添加　一个 AnyType
	 * @param idx
	 * @param x
	 */
	public void add(int idx, AnyType x){
		addBefore(getNode(idx), x);
	}
	
	/**
	 *  获得　idx 对应的节点
	 * @param idx
	 * @return
	 */
	private Node<AnyType> getNode(int idx){
		Node<AnyType> result;
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (idx < size()/2) {
			result = mBeginMarker.mNext;
			for (int i = 0; i < idx; i++) {
				result = result.mNext;
			}
		} else {
			result = mEndMarker;
			for (int i = size(); i > idx; i--) {
				result = result.mPrev;
			}
		}
		
		return result;
	}
	
	/**
	 *  替换某个节点的值
	 * @param idx
	 * @param newVal
	 * @return
	 */
	public AnyType set(int idx, AnyType newVal){
		Node<AnyType> oldNode = getNode(idx);
		AnyType oldVal = oldNode.mData;
		oldNode.mData = newVal;
		return oldVal;
	}
	
	/**
	 *  移除指定位置的　数据
	 * @param idx
	 * @return
	 */
	public AnyType remove(int idx){
		return remove(getNode(idx));
	}
	
	/**
	 *  移除一个指定的节点　p
	 * @param p
	 * @return
	 */
	private AnyType remove(Node<AnyType> p){
		p.mPrev.mNext = p.mNext;
		p.mNext.mPrev = p.mPrev;
		mTheSize --;
		mModCount --;
		return p.mData;
	}
	@Override
	public Iterator<AnyType> iterator() {
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements Iterator<AnyType>{
		private Node<AnyType> current = mBeginMarker.mNext;
		private int expectedModCount = mModCount;
	    private boolean okToRemove = false;
	    
		@Override
		public boolean hasNext() {
			return current != mEndMarker;
		}

		@Override
		public AnyType next() {
			if (expectedModCount != mModCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			AnyType nextItem = current.mData;
			current = current.mNext;
			okToRemove = true;
			return nextItem;
		}
		
		@Override
		public void remove() {
			if (expectedModCount != mModCount) {
				throw new ConcurrentModificationException();
			}
			
			if (!okToRemove) {
				throw new IllegalStateException();
			}
			MyLinkedList.this.remove(current.mPrev);
			okToRemove = false;
			expectedModCount --;
		}
	}
}
