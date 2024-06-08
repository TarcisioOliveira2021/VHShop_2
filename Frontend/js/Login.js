$("#entrar").click(function (e) {
  e.preventDefault();

  var novoUser = {
    nome: $("#nome").val(),
    senha: $("#password").val(),
  };

  if(novoUser.nome == "" || novoUser.senha == "") {
    $("#password-error").text("Preencha todos os campos.");
    return;
  }
  
  var userJson = JSON.stringify(novoUser);

  $.ajax({
    url: "http://localhost:8080/login/autenticar",
    type: "POST",
    data: userJson,
    contentType: "application/json",

    success: function (response) {
      localStorage.setItem("role", response);
      showModal("Login realizado com sucesso!");
      setTimeout(function () {
        location.href = "/html/TelaDeAcoes.html";
      }, 1000);
    },
    error: function (error) {
      if (error.status == 404) {
          $("#password-error").text(
            "Usuário não encontrado, verifique o nome de usuário ou senha."
          );
          return;
      }

      showModal(error.statusText);
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
