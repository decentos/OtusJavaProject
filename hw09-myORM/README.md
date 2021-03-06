# Homework №9
Самодельный ORM

Работа должна использовать базу данных H2.
Используйте предложенный на вебинаре api (пакет core). 

Создайте в базе таблицу User с полями:

- id bigint(20) NOT NULL auto_increment
- name varchar(255)
- age int(3)

Создайте свою аннотацию @Id

Создайте класс User (с полями, которые соответствуют таблице, поле id отметьте аннотацией).

Напишите JdbcMapper, который умеет работать с классами, в которых есть поле с аннотацией @Id.
JdbcMapper должен сохранять объект в базу и читать объект из базы.
Фактически, это надстройка над DbExecutor<T>, которая по заданному классу умеет генерировать sql-запросы.
А DbExecutor<T> должен выполнять сгенерированные запросы.

Имя таблицы должно соответствовать имени класса, а поля класса - это колонки в таблице.

Методы JdbcTemplate'а:
- void create(T objectData);
- void update(T objectData);
- void createOrUpdate(T objectData); // опционально.
- <T> T load(long id, Class<T> clazz);

Проверьте его работу на классе User.

Метод createOrUpdate - необязательный.
Он должен "проверять" наличие объекта в таблице и создавать новый или обновлять.

Создайте еще одну таблицу Account:
- no bigint(20) NOT NULL auto_increment
- type varchar(255)
- rest number

Создайте для этой таблицы класс Account и проверьте работу JdbcMapper на этом классе.
