document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form"); // Seleciona o formulário
    const nomeField = document.getElementById("nome");
    const usernameField = document.getElementById("username");
    const emailField = document.getElementById("email");
    const passwordField = document.getElementById("password");
    const confirmPasswordField = document.getElementById("confirmPassword"); // Campo de confirmação de senha

    // Adiciona um listener no formulário para validar ao submeter
    form.addEventListener("submit", (event) => {
        // Reseta mensagens de erro
        clearErrors();

        let isValid = true;

        // Validação do campo "nome"
        if (!nomeField.value.trim()) {
            showError(nomeField, "O nome é obrigatório.");
            isValid = false;
        } else if (nomeField.value.length < 3 || nomeField.value.length > 150) {
            showError(nomeField, "O nome deve ter entre 3 e 150 caracteres.");
            isValid = false;
        }

        // Validação do campo "username"
        if (!usernameField.value.trim()) {
            showError(usernameField, "O nome de usuário é obrigatório.");
            isValid = false;
        } else if (usernameField.value.length < 3 || usernameField.value.length > 50) {
            showError(usernameField, "O nome de usuário deve ter entre 3 e 50 caracteres.");
            isValid = false;
        }

        // Validação do campo "email"
        if (!emailField.value.trim()) {
            showError(emailField, "O e-mail é obrigatório.");
            isValid = false;
        } else if (!validateEmail(emailField.value)) {
            showError(emailField, "O e-mail informado não é válido.");
            isValid = false;
        }

        // Validação do campo "password"
        if (!passwordField.value.trim()) {
            showError(passwordField, "A senha é obrigatória.");
            isValid = false;
        } else if (passwordField.value.length < 6) {
            showError(passwordField, "A senha deve ter pelo menos 6 caracteres.");
            isValid = false;
        }

        // Validação do campo "confirmPassword"
        if (confirmPasswordField.value.trim() !== passwordField.value.trim()) {
            showError(confirmPasswordField, "As senhas não coincidem.");
            isValid = false;
        }

        // Se alguma validação falhar, cancela o envio
        if (!isValid) {
            event.preventDefault(); // Impede o envio do formulário
        }
    });

    // Função para exibir mensagens de erro
    function showError(input, message) {
        const errorElement = document.createElement("div");
        errorElement.classList.add("error-message");
        errorElement.innerText = message;
        input.parentElement.appendChild(errorElement);
        input.classList.add("input-error"); // Adiciona uma classe para destacar o campo com erro
    }

    // Função para limpar mensagens de erro
    function clearErrors() {
        const errorMessages = document.querySelectorAll(".error-message");
        errorMessages.forEach((error) => error.remove());
        const inputs = document.querySelectorAll(".input-error");
        inputs.forEach((input) => input.classList.remove("input-error"));
    }

    // Função para validar formato de e-mail
    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
});
