//pou-up para salvar um novo assunto

async function salvarAssunto(){
    const descricao = document.getElementById("descricaoAssunto").value;
    const ativo = document.getElementById("ativoAssunto").checked;
    const erro = document.getElementById("erroAssunto");
    erro.innerHTML = "";
    if(descricao.trim() === ""){
        erro.innerHTML = "Informe a descrição.";
        return;
    }

    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;

    const response = await fetch("/assunto/ajax",{
        method:"POST",
        headers:{
            "Content-Type":"application/json",
            [header]:token
        },
        body:JSON.stringify({
            descricao:descricao,
            ativo:ativo
        })
    });

    if(!response.ok){
        erro.innerHTML = await response.text();
        return;
    }
    const assunto = await response.json();
    adicionarAssuntoNoSelect(assunto);
}

function adicionarAssuntoNoSelect(assunto){
    const select = document.getElementById("assuntosIds");
    const option = document.createElement("option");
    option.value = assunto.idAssunto;
    option.text = assunto.descricao;
    option.selected = true;
    select.appendChild(option);
    document.getElementById("descricaoAssunto").value = "";
    document.getElementById("ativoAssunto").checked = true;
    document.getElementById("erroAssunto").innerHTML = "";
    bootstrap.Modal.getInstance(
        document.getElementById("modalAssunto")
    ).hide();
}

/*pou-up para salvar uma nova editora*/
async function salvarEditora() {
    const nome = document.getElementById("nomeEditora").value;
    const cidade = document.getElementById("cidadeEditora").value;
    const estado = document.getElementById("estadoEditora").value;
    const ativo = document.getElementById("ativoEditora").checked;
    const erro = document.getElementById("erroEditora");

    erro.innerHTML = "";

    if (nome.trim() === "") {
        erro.innerHTML = "Informe o nome da editora.";
        return;
    }

    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;

    const response = await fetch("/editora/ajax", {

        method: "POST",
        headers: {"Content-Type": "application/json", [header]: token},

        body: JSON.stringify({
            nome: nome,
            ativo: ativo,
            localizacao: {
                cidade: cidade,
                estado: estado
            }
        })

    });

    if (!response.ok) {erro.innerHTML = await response.text();
        return;
    }
    const editora = await response.json();
    adicionarEditoraNoSelect(editora);
}

function adicionarEditoraNoSelect(editora) {
    const select = document.getElementById("editora");
    const option = document.createElement("option");
    option.value = editora.idEditora;
    option.text = editora.nome;
    option.selected = true;
    select.appendChild(option);
    document.getElementById("nomeEditora").value = "";
    document.getElementById("cidadeEditora").value = "";
    document.getElementById("estadoEditora").value = "";
    document.getElementById("ativoEditora").checked = true;
    document.getElementById("erroEditora").innerHTML = "";
    bootstrap.Modal.getInstance(
        document.getElementById("modalEditora")
    ).hide();
}

//COLABORADOR

async function salvarColaborador(){
    const nome = document.getElementById("nomeColaborador").value;
    const sobrenome = document.getElementById("sobrenomeColaborador").value;
    const nacionalidade = document.getElementById("nacionalidadeColaborador").value;
    const tipo = document.getElementById("tipoColaborador").value;
    const ativo = document.getElementById("ativoColaborador").checked;
    const erro = document.getElementById("erroColaborador");
    erro.innerHTML = "";
    if(nome.trim() === ""){
        erro.innerHTML = "Informe o nome.";
        return;
    }
    if(sobrenome.trim() === ""){
        erro.innerHTML = "Informe o sobrenome.";
        return;
    }
    if(tipo === ""){
        erro.innerHTML = "Selecione o tipo.";
        return;
    }
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const response = await fetch("/colaborador/ajax",{
        method:"POST",
        headers:{
            "Content-Type":"application/json",
            [header]:token
        },
        body:JSON.stringify({
            nome:nome,
            sobrenome:sobrenome,
            nacionalidade:nacionalidade,
            tipo:tipo,
            ativo:ativo
        })
    });
    if(!response.ok) {
        erro.innerHTML = await response.text();
        return;
    }
    const colaborador = await response.json();
    adicionarColaboradorNoSelect(colaborador);
}

function adicionarColaboradorNoSelect(colaborador){

    const select = document.getElementById("colaboradoresIds");
    const option = document.createElement("option");
    option.value = colaborador.idColaborador;
    option.text =
        colaborador.nome +
        " " +
        colaborador.sobrenome +
        " (" +
        colaborador.tipo +
        ")";

    option.selected = true;
    select.appendChild(option);
    document.getElementById("nomeColaborador").value = "";
    document.getElementById("sobrenomeColaborador").value = "";
    document.getElementById("nacionalidadeColaborador").value = "";
    document.getElementById("tipoColaborador").value = "";
    document.getElementById("ativoColaborador").checked = true;
    document.getElementById("erroColaborador").innerHTML = "";
    bootstrap.Modal.getInstance(
        document.getElementById("modalColaborador")
    ).hide();
}