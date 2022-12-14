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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public class DynamicInvocationHandler implements InvocationHandler {

    WeakHashMapWrapper map;
    UUID uuid;

    GCAttacher gcAttacher;

    public DynamicInvocationHandler(UUID uuid, WeakHashMapWrapper map, GCAttacher gcAttacher) {
        this.map = map;
        this.uuid = uuid;
        this.gcAttacher = gcAttacher;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object result = null;
        //log.info("Invoked method: {}", method.getName());
        //System.out.println("Before the proxy: ");
        result = method.invoke(map.get(uuid), args);

        if (result instanceof NDArray) {
            return gcAttacher.wrap((NDArray)result);
        }

             //System.out.println("After the proxy: ");
        return result;
    }

}
