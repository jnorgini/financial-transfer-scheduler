<template>
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h5 class="mb-0">Extrato de Agendamentos</h5>
            <button class="btn btn-light btn-sm" @click="fetchTransfers">
                <i class="bi bi-arrow-clockwise"></i> Atualizar
            </button>
        </div>

        <div class="card-body">
            <div v-if="loading" class="text-center my-4">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Carregando...</span>
                </div>
            </div>

            <div v-else-if="transfers.length === 0" class="alert alert-info mb-0 text-center">
                Nenhum agendamento de transferência encontrado.
            </div>

            <div v-else class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Origem</th>
                            <th>Destino</th>
                            <th>Valor</th>
                            <th>Taxa</th>
                            <th>Data Transferência</th>
                            <th>Data Agendamento</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="transfer in transfers" :key="transfer.id">
                            <td><span class="badge bg-secondary">#{{ transfer.id }}</span></td>
                            <td class="font-monospace">{{ transfer.sourceAccount }}</td>
                            <td class="font-monospace">{{ transfer.destinationAccount }}</td>
                            <td class="fw-bold text-success">{{ formatCurrency(transfer.amount) }}</td>
                            <td class="text-danger">{{ formatCurrency(transfer.fee) }}</td>
                            <td>{{ formatDate(transfer.transferDate) }}</td>
                            <td>{{ formatDate(transfer.scheduledDate) }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, defineExpose } from 'vue'
import { useTransfers } from '../composables/useTransfers'
import { formatCurrency, formatDate } from '../utils/formatters'

const { transfers, loading, fetchTransfers } = useTransfers()

onMounted(() => {
    fetchTransfers()
})

defineExpose({
    fetchTransfers
})
</script>