$(document).ready(function() {
    var botaoEditar = document.getElementById('editar');
    var botaoExcluir = document.getElementById('excluir');
    var anoLancamento = document.getElementById('ano');

    if(localStorage.length == 0){
        document.getElementsByClassName('arrow')[0].style.display = 'none';
        document.getElementsByClassName('content-fitas')[0].innerHTML = 
        '<h2>Acesso negado</h2> <p>Você não tem permissão para acessar essa página</p>';
    }


    if(localStorage.getItem('role') == 'ADMIN'){
        $('#tabela-pessoas').DataTable();
        listarFitas();
    }else{       
        botaoExcluir.remove();
        botaoEditar.remove();
        anoLancamento.style.width = '90px';
        $('#tabela-pessoas').DataTable();       
        listarFitas();
    } 
});

function listarFitas() {
    $.ajax({
        url: 'http://localhost:8080/fita/list',
        type: 'GET',
        contentType: 'application/json',
        success: function(response) {
            $('#tabela-pessoas').DataTable().clear().draw();
            $.each(response, function(index, Fita) {

                var editIcon = '<i class="fas fa-edit" data-id="'+ Fita.id +'"></i>';
                var deleteIcon = '<i class="fas fa-trash" data-id="'+ Fita.id +'"></i>';
            
                if(localStorage.getItem('role') == 'ADMIN'){
                    $('#tabela-fitas').DataTable().row.add([
                            Fita.id,
                            Fita.nome,
                            Fita.genero,
                            Fita.ano,
                            editIcon,
                            deleteIcon
                    ]).draw(false);
                    return;     
                }

                $('#tabela-fitas').DataTable().row.add([
                    Fita.id,
                    Fita.nome,
                    Fita.genero,
                    Fita.ano
            ]).draw(false);
            });
        },
        error: function(error) {
            console.error('Erro na requisição AJAX:', error);
        }
    });
}

$(document).on('click', '.fa-trash', function() {
    var fitaId = $(this).data('id');

    $.ajax({   
        url: 'http://localhost:8080/fita/delete',
        type: 'DELETE',
        data: fitaId.toString(),
        contentType: 'text/plain',
        success: function(response) {
            console.log(response);
            showModal('Fita deletada com sucesso');
            setTimeout(() => {
                window.location.reload();
            }, 2000);
        },
        error: function(error) {
            console.error('Erro na requisição AJAX:', error);
        }
    });
});

$(document).on('click', '.fa-edit', function() {

    var row = $(this).closest('tr');

    var fita = {
        id: row.find('td:eq(0)').text(),
        nome: row.find('td:eq(1)').text(),
        genero: row.find('td:eq(2)').text(),
        ano: row.find('td:eq(3)').text()
    };
    
    localStorage.setItem('fita', JSON.stringify(fita));
    location.href = '/html/EditarFita.html';
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

