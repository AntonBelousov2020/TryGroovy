# React Frontend Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Добавить React CRUD-интерфейс для медицинской истории в существующий Spring Boot проект.

**Architecture:** React (Vite + TypeScript) интегрируется в Spring Boot через Gradle: npm build результат копируется в `src/main/resources/static/`. Spring Boot автоматически раздаёт статику на `/`.

**Tech Stack:** Vite, React 18, TypeScript, CSS (vanilla)

---

## File Structure

```
TryGroovy/
├── frontend/                          # React приложение
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── index.html
│   └── src/
│       ├── main.tsx
│       ├── App.tsx
│       ├── App.css
│       ├── components/
│       │   └── HealthHistoryTable.tsx
│       ├── modal/
│       │   └── AddRecordModal.tsx
│       └── types/
│           └── HealthHistory.ts
├── build.gradle                      # Модификация
└── src/main/resources/static/        # Сюда копируется билд
```

---

## Task 1: Настройка фронтенд-инфраструктуры

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/vite.config.ts`
- Create: `frontend/tsconfig.json`
- Create: `frontend/index.html`
- Modify: `build.gradle` (добавить task для npm build)

**Interfaces:**
- Produces: `frontend/` — готовое React приложение

- [ ] **Step 1: Создать package.json**

```json
{
  "name": "medical-history-frontend",
  "private": true,
  "version": "0.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "tsc && vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0"
  },
  "devDependencies": {
    "@types/react": "^18.2.0",
    "@types/react-dom": "^18.2.0",
    "@vitejs/plugin-react": "^4.0.0",
    "typescript": "^5.0.0",
    "vite": "^5.0.0"
  }
}
```

- [ ] **Step 2: Создать vite.config.ts**

```typescript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  base: './',
  build: {
    outDir: '../src/main/resources/static',
    emptyOutDir: true
  }
})
```

- [ ] **Step 3: Создать tsconfig.json**

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "useDefineForClassFields": true,
    "lib": ["ES2020", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "skipLibCheck": true,
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react-jsx",
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true
  },
  "include": ["src"],
  "references": [{ "path": "./tsconfig.node.json" }]
}
```

- [ ] **Step 4: Создать tsconfig.node.json**

```json
{
  "compilerOptions": {
    "composite": true,
    "skipLibCheck": true,
    "module": "ESNext",
    "moduleResolution": "bundler",
    "allowSyntheticDefaultImports": true
  },
  "include": ["vite.config.ts"]
}
```

- [ ] **Step 5: Создать index.html**

```html
<!DOCTYPE html>
<html lang="ru">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Медицинская история</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
```

- [ ] **Step 6: Модифицировать build.gradle** — добавить tasks для npm build перед processResources

```groovy
def frontendDir = "${projectDir}/frontend"

tasks.register('npmInstall', Exec) {
    workingDir(frontendDir)
    commandLine 'npm', 'install'
}

tasks.register('npmBuild', Exec) {
    workingDir(frontendDir)
    commandLine 'npm', 'run', 'build'
    dependsOn npmInstall
}

processResources {
    dependsOn npmBuild
}
```

- [ ] **Step 7: Запустить npm install для проверки**

Run: `npm install` в папке `frontend/`

---

## Task 2: React компоненты

**Files:**
- Create: `frontend/src/main.tsx`
- Create: `frontend/src/App.tsx`
- Create: `frontend/src/App.css`
- Create: `frontend/src/types/HealthHistory.ts`
- Create: `frontend/src/components/HealthHistoryTable.tsx`
- Create: `frontend/src/modal/AddRecordModal.tsx`

**Interfaces:**
- Consumes: API `/health/history/get/{patientId}`, `/health/history/create`, `/health/history/delete/{id}`
- Produces: CRUD интерфейс

- [ ] **Step 1: Создать types/HealthHistory.ts**

```typescript
export interface HealthHistory {
  id: number;
  patientId: number;
  diagnosis: string;
  description: string;
  createdAt: string;
}

export interface HealthHistoryRequest {
  patientId: number;
  diagnosis: string;
  description: string;
}
```

- [ ] **Step 2: Создать main.tsx**

```typescript
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './App.css'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
```

- [ ] **Step 3: Создать App.css**

```css
body {
  font-family: Arial, sans-serif;
  margin: 20px;
  background-color: #f5f5f5;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

h1 {
  color: #333;
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.toolbar input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.toolbar button {
  padding: 8px 16px;
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.toolbar button:hover {
  background: #45a049;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background: #f8f8f8;
  font-weight: bold;
}

button.delete-btn {
  padding: 6px 12px;
  background: #f44336;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button.delete-btn:hover {
  background: #da190b;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}

.modal h2 {
  margin-top: 0;
}

.modal form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.modal input, .modal textarea {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.modal-buttons {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 10px;
}

.modal-buttons button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.modal-buttons .cancel {
  background: #9e9e9e;
  color: white;
}

.modal-buttons .submit {
  background: #4CAF50;
  color: white;
}
```

- [ ] **Step 4: Создать AddRecordModal.tsx**

```typescript
import { useState } from 'react'
import { HealthHistoryRequest } from '../types/HealthHistory'

interface Props {
  isOpen: boolean
  onClose: () => void
  onSubmit: (data: HealthHistoryRequest) => void
}

export default function AddRecordModal({ isOpen, onClose, onSubmit }: Props) {
  const [patientId, setPatientId] = useState('')
  const [diagnosis, setDiagnosis] = useState('')
  const [description, setDescription] = useState('')

  if (!isOpen) return null

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSubmit({
      patientId: parseInt(patientId),
      diagnosis,
      description
    })
    setPatientId('')
    setDiagnosis('')
    setDescription('')
    onClose()
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        <h2>Добавить запись</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="number"
            placeholder="ID пациента"
            value={patientId}
            onChange={e => setPatientId(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Диагноз"
            value={diagnosis}
            onChange={e => setDiagnosis(e.target.value)}
            required
          />
          <textarea
            placeholder="Описание"
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
          <div className="modal-buttons">
            <button type="button" className="cancel" onClick={onClose}>
              Отмена
            </button>
            <button type="submit" className="submit">
              Сохранить
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
```

- [ ] **Step 5: Создать HealthHistoryTable.tsx**

```typescript
import { HealthHistory } from '../types/HealthHistory'

interface Props {
  records: HealthHistory[]
  onDelete: (id: number) => void
}

export default function HealthHistoryTable({ records, onDelete }: Props) {
  if (records.length === 0) {
    return <p>Записей не найдено</p>
  }

  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>ID Пациента</th>
          <th>Диагноз</th>
          <th>Описание</th>
          <th>Дата создания</th>
          <th>Действия</th>
        </tr>
      </thead>
      <tbody>
        {records.map(record => (
          <tr key={record.id}>
            <td>{record.id}</td>
            <td>{record.patientId}</td>
            <td>{record.diagnosis}</td>
            <td>{record.description}</td>
            <td>{new Date(record.createdAt).toLocaleString('ru-RU')}</td>
            <td>
              <button
                className="delete-btn"
                onClick={() => onDelete(record.id)}
              >
                Удалить
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  )
}
```

- [ ] **Step 6: Создать App.tsx**

```typescript
import { useState, useEffect } from 'react'
import HealthHistoryTable from './components/HealthHistoryTable'
import AddRecordModal from './modal/AddRecordModal'
import { HealthHistory, HealthHistoryRequest } from './types/HealthHistory'

const API_BASE = '/health/history'

export default function App() {
  const [records, setRecords] = useState<HealthHistory[]>([])
  const [patientIdFilter, setPatientIdFilter] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)

  const fetchRecords = async () => {
    const url = patientIdFilter
      ? `${API_BASE}/get/${patientIdFilter}`
      : `${API_BASE}/get/0`
    
    const response = await fetch(url)
    if (response.ok) {
      const data = await response.json()
      setRecords(Array.isArray(data) ? data : [])
    } else {
      setRecords([])
    }
  }

  useEffect(() => {
    fetchRecords()
  }, [])

  const handleSearch = () => {
    fetchRecords()
  }

  const handleAddRecord = async (data: HealthHistoryRequest) => {
    await fetch(`${API_BASE}/create`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
    fetchRecords()
  }

  const handleDelete = async (id: number) => {
    if (confirm('Удалить эту запись?')) {
      await fetch(`${API_BASE}/delete/${id}`, { method: 'DELETE' })
      fetchRecords()
    }
  }

  return (
    <div className="container">
      <h1>Медицинская история</h1>
      
      <div className="toolbar">
        <input
          type="number"
          placeholder="ID пациента"
          value={patientIdFilter}
          onChange={e => setPatientIdFilter(e.target.value)}
        />
        <button onClick={handleSearch}>Поиск</button>
        <button onClick={() => setIsModalOpen(true)}>Добавить</button>
      </div>

      <HealthHistoryTable records={records} onDelete={handleDelete} />

      <AddRecordModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddRecord}
      />
    </div>
  )
}
```

---

## Task 3: Проверка работоспособности

**Files:**
- Modify: `src/main/resources/static/index.html` (создаётся после билда)

**Interfaces:**
- Consumes: Готовые компоненты из Task 2

- [ ] **Step 1: Запустить ./gradlew build**

Run: `./gradlew build`
Expected: npm install → npm build → processResources → BUILD SUCCESSFUL

- [ ] **Step 2: Запустить приложение**

Run: `./gradlew bootRun`
Expected: Spring Boot запускается на порту 8080

- [ ] **Step 3: Открыть http://localhost:8080**

Expected: Видна таблица с записями (или пустая таблица)
Expected: Работают кнопки "Добавить", "Поиск", "Удалить"

---

## Verification

- [ ] Таблица отображает данные с бэкенда
- [ ] Модальное окно открывается и закрывается
- [ ] Форма создаёт новую запись
- [ ] Кнопка удаления работает с подтверждением
- [ ] Фильтрация по Patient ID работает
- [ ] `./gradlew build` проходит успешно