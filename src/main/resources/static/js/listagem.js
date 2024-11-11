// Valida se o ano possui apenas 4 dígitos
function validateYear(input) {
    const dateValue = input.value; // yyyy-MM-dd
    if (dateValue.length >= 4) {
        const year = dateValue.split('-')[0];
        if (year.length > 4) {
            // Remove os dígitos extras
            input.value = dateValue.slice(0, dateValue.length - (year.length - 4));
        }
    }
}

const editarModal = document.getElementById('editarModal');
editarModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget; // Botão que abriu o modal

    // Extrai os dados do botão
    const id = button.getAttribute('data-id');
    const titulo = button.getAttribute('data-titulo');
    const dataAssistido = button.getAttribute('data-data-assistido');
    const opiniao = button.getAttribute('data-opiniao');
    const estrelas = button.getAttribute('data-estrelas');

    // Preenche os campos do formulário no modal
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-titulo').value = titulo;
    document.getElementById('edit-dataAssistido').value = dataAssistido.split('/').reverse().join('-'); // yyyy-MM-dd
    document.getElementById('edit-opiniao').value = opiniao;
    document.getElementById('edit-estrelas').value = estrelas;

    // Configura a ação do formulário
    const editarForm = document.getElementById('editarForm');
    editarForm.setAttribute('action', `/api/v1/filmes/${id}`);
});