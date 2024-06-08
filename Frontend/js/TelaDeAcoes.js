$(document).ready(function() {
    var botaoCadastrar = document.getElementById('cadastrar');

    if(localStorage.length == 0){
        document.getElementsByClassName('arrow')[0].style.display = 'none';
        document.getElementsByClassName('arrow')[0].style.display = 'none';
        document.getElementsByClassName('content')[0].style.padding = '190px';
        document.getElementsByClassName('content')[0].innerHTML = 
        '<h2>Acesso negado</h2> <p>Você não tem permissão para acessar essa página</p>';
        botaoCadastrar.style.display = 'none';
    }


    if(localStorage.getItem('role') == 'ADMIN'){
        botaoCadastrar.style.display = 'block';
        return;
    }

    botaoCadastrar.style.display = 'none';
});