let formElem = document.getElementById('formElem');
formElem.onsubmit = async (e) => {
    e.preventDefault();

    let elementById = document.getElementById('url').value;
    let urls = elementById.split('\n');

    let elementById1 = document.getElementById('term').value;
    let terms = elementById1.split('\n');

    let visit = document.getElementById('visit').value;
    let depth = document.getElementById('depth').value;

    let url = new FormData();

    url.append("urls", urls);
    url.append("terms", terms);
    url.append("visit", visit);
    url.append("depth", depth);

    let response = await fetch('./api', {
        method: 'POST',
        body: url
    });


    let result = await response.json();
    showTable(result);
};


async function showTable(result) {
    let tbody = document.getElementById('tbody');
    let tr, td;
    tbody.textContent = '';

    result.forEach((el) => {

        tbody = document.getElementById('tbody');

        tr = document.createElement('tr');
        tr.classList.add('table__tr');


        td = document.createElement('td');
        td.textContent = el.url;
        tr.appendChild(td);


        td = document.createElement('td');
        for (let i = 0; i < el.termDTOList.length; i++) {
            td.textContent += el.termDTOList[i].term + ' ';
            tr.appendChild(td);
        }

        td = document.createElement('td');
        for (let i = 0; i < el.termDTOList.length; i++) {
            td.textContent += el.termDTOList[i].count + ' ';
            tr.appendChild(td);

        }

        tbody.appendChild(tr);
    });
}







