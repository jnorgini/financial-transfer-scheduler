<template>
  <div class="d-flex flex-column min-vh-100">

    <TheNavbar />

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

    <TheFooter />

  </div>
</template>

<script setup>
import { ref } from 'vue'
import TheNavbar from './components/TheNavbar.vue'
import TheFooter from './components/TheFooter.vue'
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
