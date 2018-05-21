function bookSearch(){
	//Procura e pega o valor do input 'isbn13' na pagina html
  var isbn13 = document.getElementById('isbn13').value
  
  //Esconde a div de alerta da pagina, caso esteja aparecendo
  document.getElementById('div_warning').style.display = 'none';
  
  //Chama a função ClearForm() para limpar todos os campos da pagina
  clearForm();
  
  //Validação de ISBN
  //Verfica com o padrão de expressão regular para limitar que o usuario apenas utilize numeros no ISBN
  var pattern = /^\d+$/;
  if(pattern.test(isbn13)){
	  //A chamada ajax abaixo chama a API do google atrás do isbn e traz as informações do livro em formato json
	  $.ajax({
	    url:"https://www.googleapis.com/books/v1/volumes?q=isbn:"+ isbn13,
	    dataType: "json",
	    
	    //Validação e mensagem caso ocorra erro
	    error: function(data){
	    	document.getElementById('warning_isbn').innerText = "ISBN não encontrado!";
	  	  	document.getElementById('div_warning').style.display = 'block';
	    },
	
	    success: function(data){
	    	//Validação para caso traga informações nula ou de muitos livros
	    	//Apenas aceita se trouxer um unico livro
	      if(data.totalItems == 1){
	    	  //Concatena os autores em uma unica String
	    	  for(i=0; i < data.items.length; i++){
		    	  document.getElementById('titulo').value = data.items[i].volumeInfo.title
		    	  var autores = ""
		    		  
		    	  //Pega a quantidade de autores no livro
		    	  var autoresLength = data.items[i].volumeInfo.authors.length
		    	  //Verifica se há mais de 1 autor
		    	  if( autoresLength > 1){ 
			    	  for(a=0; a<data.items[i].volumeInfo.authors.length-1; a++){
			    		  	  //Insere até o penultimo autor junto com ";"
			    			  autores += data.items[i].volumeInfo.authors[a]  + "; " 
			    	  }
		    	  }
		    	  //Insere o ultimo autor na variavel
		    	  autores += data.items[i].volumeInfo.authors[autoresLength-1]
		    	  
		    	  //Insere as informações trazidas do livro no formulario
		    	  document.getElementById('isbn13').value = isbn13
		    	  document.getElementById('autor').value = autores
		    	  
		    	  document.getElementById('anoPublicacao').value = data.items[i].volumeInfo.publishedDate
		    	  document.getElementById('sinopse').value = data.items[i].volumeInfo.description
		    	  document.getElementById('urlFoto').value = data.items[i].volumeInfo.imageLinks.thumbnail
		    	  document.getElementById('bookImage').src = data.items[i].volumeInfo.imageLinks.thumbnail
		    	  
		    	  console.log(data)
		      }
	      }else{
	    	  	//Caso não haja livro ou haja mais de um cadastrado com o mesmo ISBN, apresentará a mensagem abaixo
		    	document.getElementById('warning_isbn').innerText = "ISBN não encontrado!";
		  	  	document.getElementById('div_warning').style.display = 'block';
	      }
	    },
	
	    type: 'GET'
	  });
  }else{
	  document.getElementById('warning_isbn').innerText = "Por favor inserir apenas números!";
	  document.getElementById('div_warning').style.display = 'block';
		  
  }
}

//Limpa todo o formulario
function clearForm(){

	  document.getElementById('form_new_livro').reset();
}

 document.getElementById('btnIsbn13').addEventListener('click',bookSearch,false)
