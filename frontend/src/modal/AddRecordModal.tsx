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