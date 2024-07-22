fetch('api/resource', {
	method: 'POST',
	headers: {
		'Content-Type': 'application/json'
	},
	body: JSON.stringify({key: 'value'})
}).then(response => response.json)
.then(data => console.log("DATA: ", data))
.then(error => console.error("ERROR: ", error));

document.addEventListener('DOMContentLoaded', function(){
	const url = 'api';
	fetch(url,{
		headers: {
			"Accept": document.querySelector(".format_select").value
		}
	})
	.then(response => response.json())
	.then(films => {
		const tableBody = document.querySelector('#filmsTable tbody');
		// Clear existing entries if any
		tableBody.innerHTML = '';
		let row = "";
		films.forEach(film => {
			row += `<tr>
				<td>${film.id}</td>
                <td>${film.title}</td>
                <td>${film.year}</td>
                <td>${film.director}</td>
                <td>${film.stars}</td>
                <td>${film.review}</td>
                <td>
                	<div  class="film_actions">
                		<a href="UpdateFilm?id=${film.id}"><i class="fa-regular fa-pen-to-square"></i></a>
                		<a href="DeleteFilm?filmIdToDelete=${film.id}"><i class="fa-solid fa-trash-can"></i></a>
                	</div> 
                </td>
			</tr>`
		})
		tableBody.innerHTML = row;
	})
})

// xml format
document.addEventListener('DOMContentLoaded', function(){
	const url = 'api';
	fetch(url, {
		headers: {
			"Accept": document.querySelector(".format_select").value
		}
	})
	.then(response => response.text())
	.then(films => {
		let xml = new DOMParser().parseFromString(films,"application/xml");
		
		const tableBody = document.querySelector('#filmsTable tbody');
		tableBody.innerHTML = '';  // Clear existing entries
		let row = "";
		let sel = xml.querySelectorAll("film")
		for(let i = 0; i < sel; i++){
			let film = sel[i];
			row += `<tr>
				<td>${film.querySelector("id").innerHTML}</td>
                <td>${film.querySelector("title").innerHTML}</td>
                <td>${film.querySelector("year").innerHTML}</td>
                <td>${film.querySelector("director").innerHTML}</td>
                <td>${film.querySelector("stars").innerHTML}</td>
                <td>${film.querySelector("review").innerHTML}</td>
                <td>
                	<div class="film_actions">
                		<a href="UpdateFilm?id=${film.querySelector("id").innerHTML}"><i class="fa-regular fa-pen-to-square"></i></a>
                		<a href="DeleteFilm?filmIdToDelete=${film.querySelector("id").innerHTML}"><i class="fa-solid fa-trash-can"></i></a>
                	</div> 
                </td>
			</tr>`;
		}
		tableBody.innerHTML = row;
	})
	.catch(error => console.error("ERROR: ", error));  // Handle any errors in the fetch chain
});

// text format
document.addEventListener('DOMContentLoaded', function(){
	// Converting the films from text to json and back to json
	const SpliceFunction =()=>{
		const newLine = "\n";
		const spaceSplit = "\t";
		
		return inputString.split(spaceSplit).join(newLine);
	}
	
	const url = 'api';
	fetch(url, {
		headers: {
			"Accept": document.querySelector(".format_select").value
		}
	})
	.then(response => response.text())
	.then(films => {
		let text = new DOMParser().parseFromString(films,"text/html");
		
		const tableBody = document.querySelector('#filmsTable tbody');
		tableBody.innerHTML = '';  // Clear existing entries
		let row = "";
		let sel = text.querySelectorAll("film")
		for(let i = 0; i < sel; i++){
			let film = sel[i];
			row += `<tr>
				<td>${film.querySelector("id").innerHTML}</td>
                <td>${film.querySelector("title").innerHTML}</td>
                <td>${film.querySelector("year").innerHTML}</td>
                <td>${film.querySelector("director").innerHTML}</td>
                <td>${film.querySelector("stars").innerHTML}</td>
                <td>${film.querySelector("review").innerHTML}</td>
                <td>
                	<div class="film_actions">
                		<a href="UpdateFilm?id=${film.querySelector("id").innerHTML}"><i class="fa-regular fa-pen-to-square"></i></a>
                		<a href="DeleteFilm?filmIdToDelete=${film.querySelector("id").innerHTML}"><i class="fa-solid fa-trash-can"></i></a>
                	</div> 
                </td>
			</tr>`;
		}
		tableBody.innerHTML = row;
	})
	.catch(error => console.error("ERROR: ", error));  // Handle any errors in the fetch chain
});
