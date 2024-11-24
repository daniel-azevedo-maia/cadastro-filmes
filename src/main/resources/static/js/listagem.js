// Abrir modal com os dados do filme
function openModal(id, titulo, dataAssistido, opiniao, estrelas) {
    // Preenche os dados do modal
    document.getElementById("filmeId").value = id || "";
    document.getElementById("titulo").value = titulo || "";
    document.getElementById("dataAssistido").value = dataAssistido || "";
    document.getElementById("opiniao").value = opiniao || "";
    document.getElementById("estrelas").value = estrelas || 1;

    // Configura a URL do formulário dinamicamente
    const form = document.getElementById("editForm");
    form.setAttribute("action", `/filmes/${id}`);

    // Exibe o modal
    const modal = document.getElementById("editModal");
    modal.style.display = "flex";
}

// Fechar modal
function closeModal() {
    const modal = document.getElementById("editModal");
    modal.style.display = "none";
}

// Fechar modal ao clicar fora dele
window.onclick = function (event) {
    const modal = document.getElementById("editModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
