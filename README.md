
# Backend: Книга рецептов

Тема №15: Книга рецептов. REST API сервис, который представляет собой цифровую кулинарную книгу. Он позволяет удобно создавать, хранить, структурировать и искать рецепты любимых блюд.

| | |
| --- | --- |
| Главная сущность | Рецепт(Recipe) |
| Связанная сущность (1:N) | ... |
| Фишка | ... |
| Стек | Java 27, Spring Boot 4, Spring Web MVC, Hibernate Validator, Springdoc OpenAPI 3.1.0, Maven |

## Как запустить

```bash
# 1. Клонировать репозиторий
git clone https://github.com/Nira1y/backend_project.git
cd backend_project
cd backend
# 2. Запустить сервер (macOS/Linux)
chmod +x mvnw
./mvnw spring-boot:run
# 2.1 Для Windows(PowerShell)
.\mvnw.cmd spring-boot:run 
(Если при запуске на Windows возникает ошибка пути, можно использовать установленный Maven: mvn spring-boot:run)
``` 
Сервис работает на `http://localhost:8080`. Запросы для демонстрации лежат в `requests.http`.

## Эндпоинты

| Метод | Путь | Что делает | Ответ |
| --- | --- | --- | --- |
| GET | `/health` | Проверка, что сервис жив | 200 `{"status":"ok"}` |
| POST | `/api/recipes` | Создать рецепт | 201 и объект с `id`; 400 при ошибке |
| GET | `/api/recipes` | Список рецептов (с пагинацией и фильтром) | 200 |
| GET | `/api/recipes/{id}` | Получить один рецепт | 200; 404 |
| PATCH | `/api/recipes/{id}` | Изменить рецепт | 200; 400; 404 |
| DELETE | `/api/recipes/{id}` | Удалить рецепт | 204; 404 |

## ER-диаграмма

<!-- С лабы 2. Блок mermaid GitHub рисует сам. -->

```mermaid
erDiagram
    parents ||--o{ children : "имеет"
    parents {
        int id PK
        varchar title
    }
    children {
        int id PK
        int parent_id FK
    }
```

## Интерфейс

<!-- С лабы 4: скриншот, например ![Интерфейс](docs/screenshot.png) -->
