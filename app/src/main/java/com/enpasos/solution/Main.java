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
import ai.djl.ndarray.NDManager;
import ai.djl.pytorch.engine.PtGradientCollector;
import ai.djl.translate.TranslateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * An example of training an image classification (MNIST) model.
 *
 * <p>See this <a
 * href="https://github.com/deepjavalibrary/djl/blob/master/examples/docs/train_mnist_mlp.md">doc</a>
 * for information about this example.
 */
@Slf4j
public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException, TranslateException, InterruptedException {
        GCAttacher enh = new GCAttacher();
        try (NDManager manager = NDManager.newBaseManager();) {

            NDArray a = enh.wrap(manager.create(new float[]{1f}));
            PtGradientCollector.logManagedArrays();

            log.info("reference exists ...");
            log.info("is the weakHashMap empty: {}", enh.map.isEmpty());
            a = null;
            log.info("no reference exists, but likely not yet garbage collected ...");
            log.info("is the weakHashMap empty: {}", enh.map.isEmpty());

            System.gc();
            TimeUnit.SECONDS.sleep(1);

            log.info("no reference exists, and likely garbage collected ...");
            log.info("is the weakHashMap empty: {}", enh.map.isEmpty());
            PtGradientCollector.logManagedArrays();
        }
    }


}
