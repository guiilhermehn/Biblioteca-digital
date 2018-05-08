function bookSearch(){
  var isbn13 = document.getElementById('isbn13').value
  console.log(isbn13)
  
  //Validação de ISBN
  var pattern = /^\d+$/;
  if(pattern.test(isbn13)){
  
	  $.ajax({
	    url:"https://www.googleapis.com/books/v1/volumes?q="+ isbn13,
	    dataType: "json",
	
	    success: function(data){
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
	    	  document.getElementById('autor').value = autores
	    	  
	    	  document.getElementById('anoPublicacao').value = data.items[i].volumeInfo.publishedDate
	    	  document.getElementById('sinopse').value = data.items[i].volumeInfo.description
	    	  document.getElementById('urlFoto').value = data.items[i].volumeInfo.imageLinks.thumbnail
	    	  document.getElementById('bookImage').src = data.items[i].volumeInfo.imageLinks.thumbnail
	    	  
	    	  console.log(data)
	      }
	    },
	
	    type: 'GET'
	  });
  }else{
	  document.getElementById('warning').innerText = "Por favor inserir apenas números!";
	  document.getElementById('div_warning').style.display = 'block';
		  
  }
}
 document.getElementById('btnIsbn13').addEventListener('click',bookSearch,false)
