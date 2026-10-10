<template>
  <div class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-dark bg-dark shadow-sm mb-4">
      <div class="container">
        <span class="navbar-brand mb-0 h1 d-flex align-items-center">
          <i class="bi bi-bank me-2"></i> Sistema de Agendamento Financeiro
        </span>
        <span class="badge bg-secondary font-monospace">v1.0.0</span>
      </div>
    </nav>

    <div class="container flex-grow-1">
      <div class="row">
        <div class="col-lg-5 col-md-12 mb-4">
          <TransferForm @transfer-created="refreshTable" />
        </div>

        <div class="col-lg-7 col-md-12">
          <TransferTable ref="tableRef" />
        </div>
      </div>
    </div>

    <footer class="bg-light text-center text-muted py-3 mt-auto border-top">
      <div class="container">
        <small>&copy; 2026 Norgini Soluções Financeiras</small>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import TransferForm from './components/TransferForm.vue'
import TransferTable from './components/TransferTable.vue'

const tableRef = ref(null)

const refreshTable = () => {
  if (tableRef.value) {
    tableRef.value.fetchTransfers()
  }
}
</script>

<style>
body {
  background-color: #f8f9fa;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
  margin: 0;
  padding: 0;
}

.card {
  border: none;
}
</style>
