Сравнение разных сборщиков мусора:

**G1:**

1. При передаче "VM options: -Xms128m -Xmx128m" произошло java.lang.OutOfMemoryError: Java heap space
2. VM options: -Xms256m -Xmx256m:

    2.1. Default, time: 426 sec, Yong = 5 555, Full = 423, Avg build time = 75 ms

    2.2. -XX:MaxGCPauseMillis=100000, time: 443 sec, Yong = 5 559, Full = 420, Avg build time = 47 ms

    2.3. -XX:MaxGCPauseMillis=10, time: 616 sec, Yong = 12 730, Full = 999, Avg build time = 28 ms

3. VM options: -Xms2048m -Xmx2048m, time: 377 sec, Yong = 1 294, Full = 1, Avg build time = 226 ms
4. VM options: -Xms5120m -Xmx5120m, time: 647 sec, Yong = 555, Full = 0, Avg build time = 940 ms

**ConcMarkSweep:**

1. При передаче "VM options: -Xms128m -Xmx128m" произошло java.lang.OutOfMemoryError: Java heap space
2. VM options: -Xms256m -Xmx256m:

    2.1. Default, time: 632 sec, Yong = 1 999, Full = 999, Avg build time = 159 ms

    2.2. -XX:MaxGCPauseMillis=100000, time: 634 sec, Yong = 1 999, Full = 999, Avg build time = 307 ms

    2.3. -XX:MaxGCPauseMillis=10, time: 624 sec, Yong = 1 999, Full = 999, Avg build time = 296 ms

3. VM options: -Xms2048m -Xmx2048m, time: 183 sec, Yong = 502, Full = 10, Avg build time = 234 ms
4. VM options: -Xms5120m -Xmx5120m, time: 229 sec, Yong = 502, Full = 3, Avg build time = 159 ms

**Сравнительная таблица:**

![GC Compare Table](https://github.com/decentos/OtusJavaProject/tree/hw03-garbageCollector/hw03-garbageCollector/GcCompareTable.png)

**Выводы:**

При сравнении двух Garbage Collector (G1 и ConcMarkSweep) можно сделать следующий вывод: 
1. Программа быстрее завершила работу при использовании G1, если под работу JVM выделено небольшое количество оперативной памяти (тестировалась работа на 256m).
2. Если под работу JVM выделяется более 2048m, то программа завершает выполнение быстрее при использовании ConcMarkSweep.
3. При использовании ConcMarkSweep реже происходит сборка мусора Young Generation вне зависимости от переданных параметров в JVM.
4. При использовании G1 реже происходит сборка мусора Old (Full) Generation вне зависимости от переданных параметров в JVM.
5. Если под работу JVM выделяется до 256m, то среднее время сборки мусора у G1 в несколько раз меньше, чем у ConcMarkSweep.
6. Если под работу JVM выделяется более 5120m, то среднее время сборки мусора у ConcMarkSweep в несколько раз меньше, чем у G1.

**Итог:**

При использовании небольшого (до 256m) количество оперативной памяти в приложении рекомендуется использовать G1, 
так как он показал себя намного эффективнее, чем ConcMarkSweep. 
При использовании большого (от 2048m) количество оперативной памяти в приложении следует воспользоваться ConcMarkSweep.