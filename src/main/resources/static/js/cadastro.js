//document.addEventListener('DOMContentLoaded', function () {
//    document.getElementById('registerForm').addEventListener('submit', async function (event) {
//        event.preventDefault(); // Evita o envio do formulário
//
//        const name = document.getElementById('name').value.trim();
//        const email = document.getElementById('email').value.trim();
//        const password = document.getElementById('password').value.trim();
//        const confirmPassword = document.getElementById('confirmPassword').value.trim();
//
//        // Validação dos campos
//        if (!name || !email || !password || !confirmPassword) {
//            alert('Por favor, preencha todos os campos.');
//            return;
//        }
//
//        if (password !== confirmPassword) {
//            alert('As senhas não coincidem. Tente novamente.');
//            return;
//        }
//
//        if (!validateEmail(email)) {
//            alert('E-mail inválido. Por favor, insira um e-mail válido.');
//            return;
//        }
//
//        // Envio para o back-end
//        try {
//            const response = await fetch('/auth/cadastro', {
//                method: 'POST',
//                headers: { 'Content-Type': 'application/json' },
//                body: JSON.stringify({ username: name, email, password })
//            });
//
//            if (response.ok) {
//                alert('Cadastro realizado com sucesso!');
//                window.location.href = '/auth/login'; // Redireciona para a página de login
//            } else {
//                alert('Erro ao realizar o cadastro. Tente novamente.');
//            }
//        } catch (error) {
//            alert('Erro ao conectar com o servidor.');
//        }
//    });
//
//    // Função para validar o formato do e-mail
//    function validateEmail(email) {
//        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//        return emailRegex.test(email);
//    }
//});
