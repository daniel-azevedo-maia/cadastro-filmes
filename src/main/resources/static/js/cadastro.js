document.addEventListener("DOMContentLoaded", () => {
    // Seleciona os campos do formulário
    const nameField = document.getElementById("name");
    const emailField = document.getElementById("email");
    const passwordField = document.getElementById("password");

    // Zera os valores dos campos
    if (nameField) nameField.value = "";
    if (emailField) emailField.value = "";
    if (passwordField) passwordField.value = "";
});
