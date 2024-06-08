$(document).ready(function() {
    if(localStorage.length == 0){
        document.getElementsByClassName('arrow')[0].style.display = 'none';
        document.getElementsByClassName('content')[0].innerHTML = 
        '<h2>Acesso negado</h2> <p>Você não tem permissão para acessar essa página</p>';
    }
        
    
const $selectAno = $('#ano');
const anoAtual = new Date().getFullYear();
const anoInicial = 1900; // Ano inicial
const generos = [
    'Ação',
    'Aventura',
    'Animação',
    'Comédia',
    'Criminal',
    'Documentário',
    'Drama',
    'Fantasia',
    'Ficção Científica',
    'Musical',
    'Romance',
    'Terror'
];
const $selectGenero = $('#genero');

for (let ano = anoAtual; ano >= anoInicial; ano--) {
    $selectAno.append($('<option>', {
        value: ano,
        text: ano
    }));
}

generos.forEach(function (genero) {
    $selectGenero.append($('<option>', {
        value: genero,
        text: genero
    }));
});

var fitaRegistrada = JSON.parse(localStorage.getItem('fita'));

if (fitaRegistrada) {
    $('#ano').val(fitaRegistrada.ano);
    $('#nome').val(fitaRegistrada.nome);
    $('#genero').val(fitaRegistrada.genero);
}

localStorage.removeItem('fita');

$('#enviar').click(function (e) {
    e.preventDefault(); 

    var novaFita = {
        id: fitaRegistrada.id,
        nome: $('#nome').val(),
        genero: $('#genero').val(),
        ano: $('#ano').val()
    };

    var fitaJson = JSON.stringify(novaFita);

    $.ajax({
        url: 'http://localhost:8080/fita/update', 
        type: 'PUT', 
        data: fitaJson, 
        contentType: 'application/json', 

        success: function (response) {
            showModal('Fita atualizada com sucesso. Você será redirecionado para a lista de fitas em 2 segundos');
            setTimeout(function() {
                location.href = '/html/ListarFitas.html';
            }, 1800);
        },
        error: function (error) {           
            if(error.status == 1){
                showModal('Erro ao atualizar a fita. Verifique os campos e tente novamente');
                return;   
            }
            showModal(error.statusText);
        }
});

});
function showModal(message) {
$('#modalMessage').text(message);
$('#myModal').show();
}

function ehAdim() {
    if (localStorage.getItem('role') !== 'ADMIN') {
        var errorMessage = document.createElement('div');
        errorMessage.textContent = 'Acesso negado';
        errorMessage.style.color = 'red';
        document.body.appendChild(errorMessage);
        throw new Error('Acesso negado');
    }
}


function closeModal() {
$('#myModal').hide();
}

$('.close').click(function() {
closeModal();
});

$(window).click(function(event) {
var modal = $('#myModal');
if (event.target == modal[0]) {
closeModal();
}
});

});