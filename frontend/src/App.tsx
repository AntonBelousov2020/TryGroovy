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