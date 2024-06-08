$(document).ready(function () {

  $("#password").on("input", function () {
    var password = $(this).val();
    var regex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}$/;

    if (password.length < 8) {
      $("#error").text("A senha deve ter pelo menos 8 caracteres.");
      document.getElementById("cadastrar").disabled = true;
    } else if (!regex.test(password)) {
      $("#error").text(
        "A senha deve ter pelo menos 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial."
      );
      document.getElementById("cadastrar").disabled = true;
    } else {
      $("#error").text("");
      document.getElementById("cadastrar").disabled = false;
    }
  });

  $("#nome").on("input", function () {
    var nome = $(this).val();
    var regex = /^[a-zA-Z0-9]+$/;

    if (nome.length < 3) {
      $("#error").text("O nome do usuário deve ter ao menos 3 letras.");
      document.getElementById("cadastrar").disabled = true;
    } else if (!regex.test(nome)) {
      $("#error").text(
        "O nome do usuário não deve conter caracteres especiais."
      );
      document.getElementById("cadastrar").disabled = true;
    } else {
      $("#error").text("");
      document.getElementById("cadastrar").disabled = false;
    }
  });
});

$("#cadastrar").click(function (e) {
  e.preventDefault();

  var novoUser = {
    nome: $("#nome").val(),
    senha: $("#password").val(),
  };

  if (novoUser.nome == "" || novoUser.senha == "") {
    $("#password-error").text("Preencha todos os campos.");
    return;
  }

  var userJson = JSON.stringify(novoUser);

  $.ajax({
    url: "http://localhost:8080/cadastrarUsuario",
    type: "POST",
    data: userJson,
    contentType: "application/json",

    success: function () {
      showModal(
        "Usuário cadastrado com sucesso. Você será redirecionado para a página inicial em 2 segundos"
      );
      setTimeout(function () {
        location.href = "/index.html";
      }, 2000);
    },
    error: function (error) {
      console.log(error);
      showModal(error.responseText);
    },
  });

  function showModal(message) {
    $("#modalMessage").text(message);
    $("#myModal").show();
  }

  function closeModal() {
    $("#myModal").hide();
  }

  $(".close").click(function () {
    closeModal();
  });

  $(window).click(function (event) {
    var modal = $("#myModal");
    if (event.target == modal[0]) {
      closeModal();
    }
  });
});
