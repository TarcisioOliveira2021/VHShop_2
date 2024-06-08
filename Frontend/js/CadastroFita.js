$(document).ready(function () {

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

    $('#enviar').click(function (e) {
        e.preventDefault(); 

        var novaFita = {
            nome: $('#nome').val(),
            genero: $('#genero').val(),
            ano: $('#ano').val()
        };

        var fitaJson = JSON.stringify(novaFita);

        $.ajax({
            url: 'http://localhost:8080/fita/cadastrarfita',
            type: 'POST',
            data: fitaJson,
            contentType: 'application/json',

            success: function () {
                showModal('Fita salva com sucesso. Você será redirecionado para a lista de fitas em 2 segundos');
                setTimeout(function() {
                    location.href = '/html/ListarFitas.html';
                }, 2000);
            },
            error: function (error) {
                console.log(error);
                showModal(error.statusText);
            }
        });
    });


function showModal(message) {
$('#modalMessage').text(message);
$('#myModal').show();
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