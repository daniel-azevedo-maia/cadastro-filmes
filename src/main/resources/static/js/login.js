//document.addEventListener('DOMContentLoaded', function () {
//    document.getElementById('loginForm').addEventListener('submit', async function (event) {
//        event.preventDefault(); // Evita o envio do formulário padrão
//
//        const username = document.getElementById('username').value.trim(); // Corrigido para "username"
//        const password = document.getElementById('password').value.trim();
//
//        // Verificação se os campos estão preenchidos
//        if (!username || !password) {
//            alert('Por favor, preencha todos os campos.');
//            return;
//        }
//
//        // Envio para o backend
//        try {
//            const response = await fetch('/auth/login', {
//                method: 'POST',
//                headers: { 'Content-Type': 'application/json' },
//                body: JSON.stringify({ username, password }) // Corrigido para enviar "username" e "password"
//            });
//
//            if (response.ok) {
//                alert('Login realizado com sucesso!');
//                window.location.href = '/home'; // Redireciona para a página inicial
//            } else {
//                alert('Usuário ou senha inválidos.');
//            }
//        } catch (error) {
//            alert('Erro ao conectar com o servidor.');
//        }
//    });
//});
