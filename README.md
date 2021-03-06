# Социальная сеть

Сервис для социальной сети, имеющий следующие функции:

+ Pегистрация нового пользователя;
+ Авторизация существующего пользователя; 
+ Пользователь может искать других пользователей;
+ Пользователь может добавлять других пользователей в «друзья»;
+ Пользователь может удалять других пользователей из «друзей»;
+ Пользователь может получить список своих друзей;

# База данных

В качестве БД используется MySQL 8.0.24 с параметрами:

+ db_user: AlexKriv
+ db_pass: AlexKriv
+ db_host: localhost:3306
+ db_name: s7_social_network

# Запуск

Перед запуском необходимо настроить базу данных, согласно описанию выше или же установить 
свои значения для параметров в application.properties; 
+ spring.datasource.url = jdbc:mysql://localhost:3306/{db_name}?serverTimezone=UTC;
+ spring.datasource.username = {db_user}; 
+ spring.datasource.password = {db_pass};

Таблицы в БД сгенерируются автоматически при первом запуске приложения;

Запустить приложение можно из Intellij IDEA, клонировав проект из данного репозитория;

Протестировать можно в браузере через Swagger (http://localhost:8080/swagger-ui/index.html#/);
