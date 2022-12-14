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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceWrapper<K,V> extends WeakReference<Object> {
        V value;

        WeakReferenceWrapper(K key, V value,
                             ReferenceQueue<Object> queue) {
            super(key, queue);
            this.value = value;
        }

        public V getValue() {
            return value;
        }

    }
