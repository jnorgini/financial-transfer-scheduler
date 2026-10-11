import { ref, reactive } from "vue";
import transferService from "../services/transferService";

export function useTransferForm(emit) {
  const loading = ref(false);

  const form = reactive({
    sourceAccount: "",
    destinationAccount: "",
    amount: null,
    transferDate: "",
  });

  const errors = reactive({
    sourceAccount: "",
    destinationAccount: "",
    amount: "",
    transferDate: "",
  });

  const alert = reactive({
    message: "",
    type: "",
    title: "",
  });

  const clearAlert = () => {
    alert.message = "";
    alert.type = "";
    alert.title = "";
  };

  const validateField = (field) => {
    if (field === "sourceAccount" || field === "destinationAccount") {
      form[field] = form[field].replace(/\D/g, "");
      if (form[field].length !== 10) {
        errors[field] = "A conta deve possuir exatamente 10 dígitos.";
      } else {
        errors[field] = "";
      }
    }

    if (field === "amount") {
      if (!form.amount || form.amount <= 0) {
        errors.amount = "O valor da transferência deve ser maior que zero.";
      } else {
        errors.amount = "";
      }
    }

    if (field === "transferDate") {
      if (!form.transferDate) {
        errors.transferDate = "A data de transferência é obrigatória.";
      } else {
        errors.transferDate = "";
      }
    }
  };

  const isFormValid = () => {
    validateField("sourceAccount");
    validateField("destinationAccount");
    validateField("amount");
    validateField("transferDate");

    return (
      !errors.sourceAccount &&
      !errors.destinationAccount &&
      !errors.amount &&
      !errors.transferDate
    );
  };

  const handleSubmit = async () => {
    if (!isFormValid()) return;

    loading.value = true;
    clearAlert();

    try {
      const response = await transferService.schedule(form);

      alert.type = "success";
      alert.title = "Sucesso!";
      alert.message = `Agendamento realizado! Taxa calculada: R$ ${response.data.fee.toFixed(2).replace(".", ",")}`;

      form.sourceAccount = "";
      form.destinationAccount = "";
      form.amount = null;
      form.transferDate = "";

      emit("transfer-created");
    } catch (error) {
      alert.type = "danger";
      alert.title = "Erro no Agendamento!";

      if (
        error.response &&
        error.response.data &&
        error.response.data.message
      ) {
        alert.message = error.response.data.message;
      } else {
        alert.message = "Falha na comunicação com o servidor técnico.";
      }
    } finally {
      loading.value = false;
    }
  };

  return {
    form,
    errors,
    alert,
    loading,
    clearAlert,
    validateField,
    handleSubmit,
  };
}
