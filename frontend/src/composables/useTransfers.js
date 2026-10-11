import { ref } from 'vue'
import transferService from '../services/transferService'

export function useTransfers() {
    const transfers = ref([])
    const loading = ref(false)

    const fetchTransfers = async () => {
        loading.value = true
        try {
            const response = await transferService.getStatement()
            transfers.value = response.data
        } catch (error) {
            console.error('Erro ao buscar o extrato:', error)
        } finally {
            loading.value = false
        }
    }

    return { transfers, loading, fetchTransfers }
}
