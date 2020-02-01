Сравнение разных сборщиков мусора:

**G1:**

1. VM options: -Xms256m -Xmx256m:

    1.1. Default, time: 426 sec, Yong = 3 810, Full = 429, Avg build time = 92 ms

    1.2. -XX:MaxGCPauseMillis=100000, time: 403 sec, Yong = 3 937, Full = 402, Avg build time = 48 ms

    1.3. -XX:MaxGCPauseMillis=10, time: 431 sec, Yong = 7 879, Full = 459, Avg build time = 29 ms

2. VM options: -Xms1024m -Xmx1024m, time: 354 sec, Yong = 1 165, Full = 0, Avg build time = 156 ms
3. VM options: -Xms2048m -Xmx2048m, time: 397 sec, Yong = 899, Full = 0, Avg build time = 233 ms

**Parallel GC:**

1. VM options: -Xms256m -Xmx256m:

    1.1. Default, time: 913 sec, Yong = 2 918, Full = 999, Avg build time = 205 ms

    1.2. -XX:MaxGCPauseMillis=100000, time: 993 sec, Yong = 2 918, Full = 999, Avg build time = 176 ms

    1.3. -XX:MaxGCPauseMillis=10, time: 913 sec, Yong = 2 918, Full = 999, Avg build time = 184 ms

2. VM options: -Xms1024m -Xmx1024m, time: 150 sec, Yong = 825, Full = 0, Avg build time = 12 ms
3. VM options: -Xms2048m -Xmx2048m, time: 129 sec, Yong = 220, Full = 0, Avg build time = 57 ms

**Сравнительная таблица:**

![GC Compare Table](https://github.com/decentos/OtusJavaProject/tree/hw03-garbageCollector/hw03-garbageCollector/GcCompareTable-2.0.png)

**Выводы:**

При сравнении двух Garbage Collector (G1 и Parallel GC) можно сделать следующий вывод: 
1. Программа быстрее завершила работу при использовании G1, если под работу JVM выделено небольшое количество оперативной памяти (тестировалась работа на 256m).
2. Если под работу JVM выделяется 1024m и более, то программа завершает выполнение завершается быстрее при использовании Parallel GC.
3. При использовании Parallel GC реже происходит сборка мусора Young Generation вне зависимости от переданных параметров в JVM.
4. При использовании G1 реже происходит сборка мусора Old (Full) Generation, если под работу JVM выделено небольшое количество оперативной памяти.
5. Если под работу JVM выделяется до 256m, то среднее время сборки мусора у G1 в несколько раз меньше, чем у Parallel GC.
6. Если под работу JVM выделяется 1024m и более, то среднее время сборки мусора у Parallel GC в несколько раз меньше, чем у G1.

**Итог:**

При использовании небольшого (до 256m) количества оперативной памяти в приложении рекомендуется использовать G1, 
так как он показал себя намного эффективнее, чем Parallel GC. 
При использовании 1024m и более оперативной памяти в приложении следует воспользоваться Parallel GC.