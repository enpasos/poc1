/*
 *  Copyright (c) 2022 enpasos GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.enpasos.solution;

import ai.djl.ndarray.NDArray;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.*;

@Slf4j
public class WeakHashMapWrapper<K, V> implements Map<K,V> {
    private final WeakHashMap<K,V> map = new WeakHashMap<K,V>();
    private final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    private final List<WeakReferenceWrapper<K,V>> weakReferenceWrapperList = new ArrayList<>();

    private void checkQueue() {
        for (Reference<?> ref; (ref = queue.poll()) != null; ) {
            synchronized (queue) {
                WeakReferenceWrapper<K,V> ref2 = (WeakReferenceWrapper<K,V>)ref;
                V value = ref2.getValue();
                if (value instanceof NDArray) {  // just as one example
                    log.info("NDArray is being collected");
                    ((NDArray)value).close();
                }
            }
        }
    }




    // implement all methods of Map<K,V> interface by calling corresponding methods of WeakHashMap<K,V> instance map

    @Override public int size() {
        checkQueue();
        return map.size();
    }

    @Override public boolean isEmpty() {
        checkQueue();
        return map.isEmpty();
    }

    @Override public boolean containsKey(Object key) {
        checkQueue();
        return map.containsKey(key);
    }

    @Override public boolean containsValue(Object value) {
        checkQueue();
        return map.containsValue(value);
    }

    @Override public V get(Object key) {
        checkQueue();
        return map.get(key);
    }

    @Override public V put(K key, V value) {
        weakReferenceWrapperList.add(new WeakReferenceWrapper<K, V>(key, value, queue));
        return map.put(key, value);
    }

    @Override public V remove(Object key) {
        checkQueue();
        return map.remove(key);
    }


    @Override public void putAll(Map<? extends K, ? extends V> m) {
        checkQueue();
        map.putAll(m);
    }

    @Override public void clear() {
        checkQueue();
        map.clear();
    }

    @Override public Set<K> keySet() {
        checkQueue();
        return map.keySet();
    }

    @Override public Collection<V> values() {
        checkQueue();
        return map.values();
    }

    @Override public Set<Entry<K, V>> entrySet() {
        checkQueue();
        return map.entrySet();
    }

    @Override public boolean equals(Object o) {
        checkQueue();
        return map.equals(o);
    }

    @Override public int hashCode() {
        return map.hashCode();
    }

    @Override public String toString() {
        return map.toString();
    }


}
