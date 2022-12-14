# proof of concept 

The DJL resource is automatically closed when it is no longer in use.

## build

```
    gradlew build
```

## run

``` 
    java -jar app/build/libs/app-0.0.1-SNAPSHOT.jar  
```

Example output: 
``` 
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of inter-op threads is 8
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of intra-op threads is 8
\--- NDManager(1cc84121325a, gpu(0)) resource count: 1
    \--- NDManager(ffe917c627a8, gpu(0)) resource count: 1
        \--- NDArray(2530116915904, Shape(1))
[main] INFO com.enpasos.solution.Main - reference exists ...
[main] INFO com.enpasos.solution.Main - is the weakHashMap empty: false
[main] INFO com.enpasos.solution.Main - no reference exists, but likely not yet garbage collected ...
[main] INFO com.enpasos.solution.Main - is the weakHashMap empty: false
[main] INFO com.enpasos.solution.Main - no reference exists, and likely garbage collected ...
[main] INFO com.enpasos.solution.WeakHashMapWrapper - NDArray is being collected
[main] INFO com.enpasos.solution.Main - is the weakHashMap empty: true
\--- NDManager(1cc84121325a, gpu(0)) resource count: 1
    \--- NDManager(ffe917c627a8, gpu(0)) resource count: 0

```
One array is created and automatically closed when no more used.
(a method to remove NDManager without resources could easily be added)
 




**Stack:**
- Java
  - DJL: 0.21.0-SNAPSHOT  (13.12.2022)
  - Java: Corretto-17.0.3.6.1
- PYTORCH: 1.13.0
- CUDA
  - CUDNN: cudnn-windows-x86_64-8.7.0.84_cuda11-
  - CUDA SDK: 11.7.1
- OS
  - GPU Driver: 527.27
  - OS: Edition	Windows 11 Pro
- HW
  - GPU: NVIDIA Quadro RTX 5000
  - CPU: Intel Xeon E-2286M
  - RAM: 64 GB

