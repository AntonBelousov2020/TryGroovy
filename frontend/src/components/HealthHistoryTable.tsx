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