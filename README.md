# Медицинская история (Medical History)

REST API + React фронтенд для управления историями болезни пациентов.

## Требования
- **Java 17+**
- **Node.js 18+** (для сборки фронтенда)
- **PostgreSQL 15+** (localhost:5432, БД: `medical_history`, логин/пароль: `postgres`/`postgres`)
- **Consul** (localhost:8500)

## Установка и запуск

### 1. Установка Node.js (если не установлен)

```powershell
winget install OpenJS.NodeJS --accept-package-agreements --accept-source-agreements
```

После установки перезайдите в терминал.

### 2. Запуск приложения

```powershell
cd C:\Users\abelousov\IdeaProjects\TryGroovy
.\gradlew.bat bootRun
```

Gradle автоматически:
- Установит npm зависимости (`npm install`)
- Соберёт React приложение (`npm run build`)
- Запустит Spring Boot на порту 8080

### 3. Открытие в браузере

http://localhost:8080

### 4. Остановка

`Ctrl+C` в терминале.

## Разработка

### Структура проекта

```
TryGroovy/
├── frontend/                    # React приложение (Vite + TypeScript)
│   ├── src/
│   │   ├── components/          # React компоненты
│   │   ├── modal/              # Модальные окна
│   │   └── types/              # TypeScript типы
│   └── package.json
├── src/main/java/              # Java код
└── build.gradle                # Gradle конфигурация
```

### Сборка JAR

```powershell
.\gradlew.bat build
```

JAR файл: `build/libs/ms-medical-history-0.0.1-SNAPSHOT.jar`

## API эндпоинты

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/health/history/get/{patientId}` | Получить историю пациента |
| POST | `/health/history/create` | Создать запись |
| DELETE | `/health/history/delete/{id}` | Удалить запись |

### Пример запроса POST

```bash
curl -X POST http://localhost:8080/health/history/create \
  -H "Content-Type: application/json" \
  -d '{"patientId": 1, "diagnosis": "Грипп", "description": "ОРВИ"}'
```

## Технологии

- **Backend:** Spring Boot 2.7, Spring Data JPA, PostgreSQL, Liquibase, Consul, Feign
- **Frontend:** React 18, TypeScript, Vite
- **Build:** Gradle с npm integration