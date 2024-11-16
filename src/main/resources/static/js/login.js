document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('loginForm').addEventListener('submit', async function (event) {
        event.preventDefault(); // Evita o envio do formulário

        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value.trim();

        // Envio para o back-end
        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                alert('Login realizado com sucesso!');
                window.location.href = '/dashboard'; // Redireciona para a página inicial após login
            } else {
                alert('E-mail ou senha incorretos.');
            }
        } catch (error) {
            alert('Erro ao conectar com o servidor.');
        }
    });
});
