package scheduler;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class EventCollection implements Iterable<Event> {

	/*
	 * What must an EventCollection provide?
	 * 1. Fast insertion
	 * 2. Fast deletion
	 * 3. Fast ordered iteration of elements
	 * 4. Exclusion of conflicting events
	 * 
	 * Implementation:
	 * 
	 * The EventCollection is implemented using an AA tree as its underlying data structure.
	 * The AA tree stores elements in an ordered manner so that performing an ordered traversal
	 * is an O(n) time operation for n elements. Insertion and deletion run in O(log n). Checking
	 * for conflicts can also be done in O(log n) time.
	 */

	private class EventCollectionIterator implements Iterator<Event>
	{
		private int sourceVersion;
		private Stack<EventNode> parents;

		public EventCollectionIterator()
		{
			sourceVersion = version;
			parents = new Stack<EventNode>();
			pushLeftBranch(root, parents);
		}
		
		private void pushLeftBranch(EventNode node, Stack<EventNode> branch)
		{
			EventNode next = node;
			
			while (next != null)
			{
				branch.push(next);
				next = node.leftChild;
			}
		}
		
		public boolean hasNext() {
			if (sourceVersion != version)
				throw new ConcurrentModificationException("The underlying collection has been modified since the iterator was created.");

			return !parents.isEmpty();
		}

		public Event next() {
			if (parents.isEmpty())
				throw new NoSuchElementException("There are no more elements in the collection.");
			if (sourceVersion != version)
				throw new ConcurrentModificationException("The underlying collection has been modified since the iterator was created.");
			
			EventNode next = parents.pop();
			pushLeftBranch(next.getRightChild(), parents);
			
			return next.getEvent();
		}

		public void remove() {
			throw new UnsupportedOperationException("The remove operation is not supported by this iterator.");
		}
	}

	private static class EventNode
	{
		private EventNode leftChild;
		private EventNode rightChild;
		private Event event;
		private int level;

		public EventNode(int level, Event event) {

			this.level = level;
			this.event = event;
			this.leftChild = null;
			this.rightChild = null;
		}

		public EventNode getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(EventNode leftChild) {
			this.leftChild = leftChild;
		}

		public EventNode getRightChild() {
			return rightChild;
		}

		public void setRightChild(EventNode rightChild) {
			this.rightChild = rightChild;
		}

		public Event getEvent() {
			return event;
		}

		public void setEvent(Event event) {
			this.event = event;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int compareStart(Event event)
		{
			return this.event.compareTo(event);
		}

		public boolean conflictsWith(Event event)
		{
			return this.event.conflictsWith(event);
		}

		public int compareStart(Date time) {
			return this.event.getStart().compareTo(time);
		}

		public boolean containsTime(Date time) {
			return this.event.containsTime(time);
		}
	}

	private static class RemoveResult
	{
		private EventNode newRoot;
		private boolean removed;

		public RemoveResult(EventNode newRoot, boolean removed)
		{
			this.newRoot = newRoot;
			this.removed = removed;
		}

		public EventNode getNewRoot()
		{
			return newRoot;
		}

		public void setNewRoot(EventNode newRoot)
		{
			this.newRoot = newRoot;
		}

		public boolean wasRemoved()
		{
			return removed;
		}
	}

	private EventNode root;
	private int size;
	private int version;

	public EventCollection()
	{
		root = null;
		size = 0;
		version = 0;
	}
	
	private EventCollection(EventCollection source)
	{
		//This is a copy constructor
		root = copyTree(source.root);
		size = source.size;
		version = source.version;
	}
	
	private static EventNode copyTree(EventNode root)
	{
		if (root == null)
			return null;
		
		EventNode rootCopy;
		EventNode leftCopy;
		EventNode rightCopy;
		
		leftCopy = copyTree(root.getLeftChild());
		rightCopy = copyTree(root.getRightChild());
		rootCopy = new EventNode(root.getLevel(), root.getEvent());
		rootCopy.setLeftChild(leftCopy);
		rootCopy.setRightChild(rightCopy);
		return rootCopy;
	}

	/**
	 * 
	 * @param event
	 * @return true if the event was added; otherwise false if the event would cause a conflict
	 */
	public boolean add(Event event)
	{
		EventNode node = add(root, event);
		if (node != null)
		{
			root = node;
			size++;
			version++;
			return true;
		}
		else
		{
			return false;
		}
	}

	private static EventNode add(EventNode root, Event event)
	{
		if (root == null)
			return new EventNode(1, event);

		//Check for a conflict
		if (root.conflictsWith(event))
			return null;

		//The case of the new event and the current node's event having equal
		//starting times is ignored because it would obviously cause a conflict.
		if (root.compareStart(event) < 0)
		{
			//root.event < event
			EventNode right = add(root.getRightChild(), event);
			if (right == null)
				return null;

			root.setRightChild(right);
		}
		else
		{
			//root.event > event
			EventNode left = add(root.getLeftChild(), event);
			if (left == null)
				return null;

			root.setLeftChild(left);
		}

		root = skew(root);
		root = split(root);

		return root;
	}

	public boolean remove(Event event)
	{
		RemoveResult result = remove(root, event);
		if (result.wasRemoved())
		{
			root = result.getNewRoot();
			size--;
			version++;
			return true;
		}
		else
		{
			return false;
		}
	}

	private static RemoveResult remove(EventNode root, Event event)
	{
		//Implementation note: There are more optimized implementations of
		//this operation. However, this version (from Andersson's original
		//description in his paper on AA trees) is simpler to implement. The
		//worst-case time complexity remains the same regardless.

		Event replacement;
		RemoveResult result;

		//If we reach a null node, then the event is not in the tree.
		if (root == null)
			return new RemoveResult(root, false);

		//Check if the event in the current node is the target
		if (root.getEvent() == event)
		{
			//There are two possibilities: the current node is either
			//a leaf or an internal node.

			//Removing a leaf is as simple as removing the node.
			//Remove an internal node requires swapping the current node's
			//value with a successor or predecessor's value, then deleting
			//that node instead.

			if (root.getLeftChild() == null)
			{
				if (root.getRightChild() == null)
				{
					//The node is a leaf node
					root.setEvent(null);
					return new RemoveResult(null, true);
				}

				//The node is an internal node with a successor
				replacement = findSubtreeSuccessor(root).getEvent();
				root.setRightChild(
						remove(root.getRightChild(), replacement).getNewRoot() );
				root.setEvent(replacement);
			}
			else
			{
				//The node is an internal node with a predecessor
				replacement = findSubtreePredecessor(root).getEvent();
				root.setLeftChild(
						remove(root.getLeftChild(), replacement).getNewRoot() );
				root.setEvent(replacement);
			}

			//The removal was a success
			result = new RemoveResult(root, true);
		}
		else
		{
			//Descend further into the tree
			if (root.compareStart(event) < 0)
			{
				//root.event < event
				result = remove(root.getRightChild(), event);
				if (result.wasRemoved())
					root.setRightChild( result.getNewRoot() );
			}
			else
			{
				//event < root.event
				result = remove(root.getLeftChild(), event);
				if (result.wasRemoved())
					root.setLeftChild( result.getNewRoot() );
			}
		}

		if (result.wasRemoved())
		{
			//Now we must rebalance the tree.
			root = decreaseLevel(root);
			root = skew(root);
			root.setRightChild( skew(root.getRightChild()) );
			{
				//This section of the code is its own block as a precaution. It means
				//that rightChild is only declared within this block. This is to stop
				//anyone from reusing it below - tree transformations may update root's
				//right child directly, so rightChild would have an old value!
				EventNode rightChild = root.getRightChild();
				if (rightChild != null)
					rightChild.setRightChild( skew(rightChild.getRightChild()) );
			}
			root = split(root);
			root.setRightChild( split(root.getRightChild()) );

			//Pass the new subtree root up to the caller
			result.setNewRoot( root );
		}
		return result;
	}

	private static EventNode findSubtreeSuccessor(EventNode node)
	{
		EventNode current = node.getRightChild();
		EventNode next = current.getLeftChild();
		while (next != null)
		{
			current = next;
			next = next.getLeftChild();
		}
		return current;
	}

	private static EventNode findSubtreePredecessor(EventNode node)
	{
		EventNode current = node.getLeftChild();
		EventNode next = current.getRightChild();
		while (next != null)
		{
			current = next;
			next = next.getRightChild();
		}
		return current;
	}

	public Event getMinimum()
	{
		//Implementation note: This operation can be modified to run
		//in O(1) time by modifying the add and remove operations.

		if (root == null)
			return null;

		EventNode current = root;
		EventNode next = root.getLeftChild();
		while (next != null)
		{
			current = next;
			next = next.getLeftChild();
		}
		return current.getEvent();
	}

	public Event getMaximum()
	{
		//Implementation note: This operation can be modified to run
		//in O(1) time by modifying the add and remove operations.

		if (root == null)
			return null;

		EventNode current = root;
		EventNode next = root.getRightChild();
		while (next != null)
		{
			current = next;
			next = next.getRightChild();
		}
		return current.getEvent();
	}

	private static EventNode decreaseLevel(EventNode root)
	{
		int newLevel = root.getLevel();
		EventNode left = root.getLeftChild();
		EventNode right = root.getRightChild();
		
		if (left != null && newLevel < left.getLevel())
			newLevel = left.getLevel();
		if (right != null && newLevel < right.getLevel())
			newLevel = right.getLevel();
		
		if (newLevel < root.getLevel())
		{
			root.setLevel( newLevel );
			if (right != null && newLevel < right.getLevel())
				right.setLevel( newLevel );
		}
		return root;
	}

	public void clear()
	{
		//Clear the tree recursively. Unlinking all the nodes is useful because it causes
		//all active iterators to stop iterating further and may prevent some garbage
		//collection delays.

		clear(root);
		root = null;
		size = 0;
		version++;
	}

	private static void clear(EventNode root)
	{
		EventNode left = root.getLeftChild();
		EventNode right = root.getRightChild();

		if (left != null)
			clear(left);
		if (right != null)
			clear(right);

		root.setLeftChild(null);
		root.setRightChild(null);
		root.setEvent(null);
	}

	public Event find(Date time)
	{
		return find(root, time);
	}
	
	private static Event find(EventNode root, Date time)
	{
		if (root == null)
			return null;

		if (root.containsTime(time))
			return root.getEvent();

		if (root.compareStart(time) < 0)
		{
			//root.event.event.startTime < time
			return find(root.getRightChild(), time);
		}
		else
		{
			//time < root.event.startTime
			return find(root.getLeftChild(), time);
		}
	}
	
	public EventCollection clone()
	{
		return new EventCollection(this);
	}

	public boolean conflictsWith(Event event)
	{
		return conflictsWith(root, event);
	}

	private static boolean conflictsWith(EventNode root, Event event)
	{
		if (root == null)
			return false;

		if (root.conflictsWith(event))
			return true;

		if (root.compareStart(event) < 0)
		{
			//root.event < event
			return conflictsWith(root.getRightChild(), event);
		}
		else
		{
			//event < root.event
			return conflictsWith(root.getLeftChild(), event);
		}
	}

	private static EventNode skew(EventNode root)
	{
		if (root == null)
			return null;

		EventNode left = root.getLeftChild();
		if (left != null && left.getLevel() == root.getLevel())
		{
			root.setLeftChild( left.getRightChild() );
			left.setRightChild( root );
			
			return left;
		}
		else
		{
			return root;
		}
	}

	private static EventNode split(EventNode root)
	{
		if (root == null)
			return null;

		EventNode right = root.getRightChild();
		EventNode secondRight = (right != null) ? right.getLeftChild() : null;
		
		if (right != null && secondRight != null && root.getLevel() == secondRight.getLevel())
		{
			root.setRightChild( right.getLeftChild() );
			right.setLeftChild( root );
			right.setLevel( right.getLevel() + 1 );
			return right;
		}
		else
		{
			return root;
		}
	}

	public boolean isEmpty()
	{
		return (size == 0);
	}

	public int size()
	{
		return size;
	}

	public Iterator<Event> iterator() {
		return new EventCollectionIterator();
	}
}
