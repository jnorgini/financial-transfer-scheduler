<template>
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-success text-white">
            <h5 class="mb-0">Agendar Nova Transferência</h5>
        </div>

        <div class="card-body">
            <div v-if="alert.message"
                :class="['alert alert-dismissible fade show', alert.type === 'success' ? 'alert-success' : 'alert-danger']"
                role="alert">
                <strong>{{ alert.title }}</strong> {{ alert.message }}
                <button type="button" class="btn-close" @click="clearAlert" aria-label="Close"></button>
            </div>

            <form @submit.prevent="handleSubmit" novalidate>
                <div class="row g-3">

                    <div class="col-md-6">
                        <label for="sourceAccount" class="form-label">Conta de Origem</label>
                        <input type="text" id="sourceAccount" v-model="form.sourceAccount"
                            :class="['form-control font-monospace', errors.sourceAccount ? 'is-invalid' : '']"
                            placeholder="XXXXXXXXXX" maxlength="10" @input="validateField('sourceAccount')" />
                        <div class="invalid-feedback">{{ errors.sourceAccount }}</div>
                    </div>

                    <div class="col-md-6">
                        <label for="destinationAccount" class="form-label">Conta de Destino</label>
                        <input type="text" id="destinationAccount" v-model="form.destinationAccount"
                            :class="['form-control font-monospace', errors.destinationAccount ? 'is-invalid' : '']"
                            placeholder="XXXXXXXXXX" maxlength="10" @input="validateField('destinationAccount')" />
                        <div class="invalid-feedback">{{ errors.destinationAccount }}</div>
                    </div>

                    <div class="col-md-6">
                        <label for="amount" class="form-label">Valor (R$)</label>
                        <input type="number" id="amount" v-model.number="form.amount"
                            :class="['form-control', errors.amount ? 'is-invalid' : '']" placeholder="0.00" step="0.01"
                            min="0.01" @input="validateField('amount')" />
                        <div class="invalid-feedback">{{ errors.amount }}</div>
                    </div>

                    <div class="col-md-6">
                        <label for="transferDate" class="form-label">Data da Transferência</label>
                        <input type="date" id="transferDate" v-model="form.transferDate"
                            :class="['form-control', errors.transferDate ? 'is-invalid' : '']"
                            @change="validateField('transferDate')" />
                        <div class="invalid-feedback">{{ errors.transferDate }}</div>
                    </div>
                </div>

                <div class="mt-4 text-end">
                    <button type="submit" class="btn btn-success px-4" :disabled="loading">
                        <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status"
                            aria-hidden="true"></span>
                        Agendar Transferência
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { defineEmits } from 'vue'
import { useTransferForm } from '../composables/useTransferForm'

const emit = defineEmits(['transfer-created'])

const {
    form,
    errors,
    alert,
    loading,
    clearAlert,
    validateField,
    handleSubmit
} = useTransferForm(emit)
</script>
