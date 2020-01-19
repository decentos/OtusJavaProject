package me.decentos;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] array = (T[]) new Object[10];
    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean add(T t) {
        if (array.length == size) {
            final T[] oldArray = array;
            array = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldArray, 0, array, 0, oldArray.length);
        }
        array[size++] = t;
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (array.length == size) {
            final T[] oldArray = array;
            array = (T[]) new Object[this.size() * 2];

            if (index != 0) {
                System.arraycopy(oldArray, 0, array, 0, index);
            }
            if (index != size) {
                System.arraycopy(oldArray, index, array, index + 1, size - index);
            }
        }
        else {
            if (index != size) {
                System.arraycopy(array, index, array, index + 1, size - index);
            }
        }
        array[index] = element;
        size++;
    }

    private String MyToString() {
        StringBuilder sb = new StringBuilder();
        for (Object element : this) {
            if (element != null) {
                sb.append(element).append(", ");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    @Override
    public String toString() {
        if (this.size == 0) {
            return super.toString();
        }
        else {
            return "[" + MyToString() + "]";
        }
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        array[index] = element;
        return element;
    }

    @Override
    public T remove(int index) {
        final T element = array[index];
        if (index != this.size() - 1) {
            System.arraycopy(array, index + 1, array, index, this.size - index);
        }
        array[size] = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        final T[] newArray = (T[]) new Object[this.size()];
        System.arraycopy(array, 0, newArray, 0, this.size());
        return newArray;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(index);
    }

    class MyListIterator implements ListIterator<T> {
        private int index;
        private int last = -1;

        public MyListIterator() {
            this.index = 0;
        }

        public MyListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return MyArrayList.this.size() > index;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = index;
            return MyArrayList.this.array[index++];
        }

        @Override
        public void add(final T element) {
            MyArrayList.this.add(index, element);
        }

        @Override
        public void set(final T element) {
            if (last == -1) {
                throw new IllegalStateException();
            }
            array[last] = element;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            last = --index;
            return array[index];
        }

        @Override
        public void remove() {
            if (last == -1) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(last);
            index--;
            last = -1;
        }
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }
}