# 1. Education_system

REST API для управления школьным расписанием.

Работа с преподавателями, классами, расписанием.

## 2. Technologies

- Java 17
- Spring Boot 2.7
- Spring Data JPA (Hibernate)
- Spring Validation
- PostgreSQL
- Docker Compose
- Maven
- Lombok
- Swagger / OpenAPI

## 3. Как запустить

- Требования (Java, Docker, PostgreSQL)
- Запуск БД: docker-compose up -d
- Запуск приложения: mvn spring-boot:run
- Адрес: http://localhost:8080

## 4. Api эндпоинты с примерами

Преподаватели:
- GET /api/teachers - получить всех
- GET /api/teachers/{id} - получить  по ID
- POST /api/teachers - создать


    {
    "firstName": "Мария",
    "middleName": "Евгеньевна",
    "lastName": "Петрова"
    }
- PATCH /api/teachers/{id} - обновить


    {
    "firstName": "Александра"
    }
- DELETE /api/teachers/{id} - удалить

Классы:
- GET /api/classes - получить всех
- GET /api/classes/{id} - получить  по ID
- POST /api/classes - создать


    {
    "className": "5Б"
    }
- PATCH /api/classes/{id} - обновить


    {
    "className": "6Б"
    }
- DELETE /api/classes/{id} - удалить

Расписание:
- GET /api/classes/{id}/schedule?date=2024-01-15 - расписание класса
- GET /api/teacher/{id}/schedule?date=2024-01-15 - расписание учителя
- POST /api/schedule - создать занятие


    {
    "subject": "Литература",
    "dayOfWeek": "MONDAY",
    "startTime": "09:00",
    "endTime": "10:30",
    "roomNumber": "201",
    "teacherId": 5,
    "schoolClassId": 3
    }
- PATCH /api/schedule/{id} - обновить
- DELETE /api/schedule/{id} - удалить

## 5. Коды ошибок
Код Описание

400 Неверный запрос (ошибка валидации)

404 Ресурс не найден

409 Конфликт (например, при удалении учителя/класса с расписанием)

500 Внутренняя ошибка сервера

Пример ответа с ошибкой:

    {
    "error": "Такого учителя не существует"
    }
## 6. Swagger документация
Интерактивная документация API доступна по адрессу:

http://localhost:8080/swagger-ui/index.html

## 7. Запуск тестов
Тесты сервисного слоя

    mvn test



