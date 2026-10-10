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
import { ref, reactive, defineEmits } from 'vue'
import transferService from '../services/transferService'

const emit = defineEmits(['transfer-created'])

const loading = ref(false)

const form = reactive({
    sourceAccount: '',
    destinationAccount: '',
    amount: null,
    transferDate: ''
})

const errors = reactive({
    sourceAccount: '',
    destinationAccount: '',
    amount: '',
    transferDate: ''
})

const alert = reactive({
    message: '',
    type: '',
    title: ''
})

const clearAlert = () => {
    alert.message = ''
    alert.type = ''
    alert.title = ''
}

const validateField = (field) => {
    if (field === 'sourceAccount' || field === 'destinationAccount') {
        form[field] = form[field].replace(/\D/g, '') // Permite apenas números
        if (form[field].length !== 10) {
            errors[field] = 'A conta deve possuir exatamente 10 dígitos.'
        } else {
            errors[field] = ''
        }
    }

    if (field === 'amount') {
        if (!form.amount || form.amount <= 0) {
            errors.amount = 'O valor da transferência deve ser maior que zero.'
        } else {
            errors.amount = ''
        }
    }

    if (field === 'transferDate') {
        if (!form.transferDate) {
            errors.transferDate = 'A data de transferência é obrigatória.'
        } else {
            errors.transferDate = ''
        }
    }
}

const isFormValid = () => {
    validateField('sourceAccount')
    validateField('destinationAccount')
    validateField('amount')
    validateField('transferDate')

    return !errors.sourceAccount && !errors.destinationAccount && !errors.amount && !errors.transferDate
}

const handleSubmit = async () => {
    if (!isFormValid()) return

    loading.value = true
    clearAlert()

    try {
        const response = await transferService.schedule(form)

        alert.type = 'success'
        alert.title = 'Sucesso!'
        alert.message = `Agendamento realizado! Taxa calculada: R$ ${response.data.fee.toFixed(2).replace('.', ',')}`

        form.sourceAccount = ''
        form.destinationAccount = ''
        form.amount = null
        form.transferDate = ''

        emit('transfer-created')

    } catch (error) {
        alert.type = 'danger'
        alert.title = 'Erro no Agendamento!'

        if (error.response && error.response.data && error.response.data.message) {
            alert.message = error.response.data.message
        } else {
            alert.message = 'Falha na comunicação com o servidor técnico.'
        }
    } finally {
        loading.value = false
    }
}
</script>
