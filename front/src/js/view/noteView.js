import { elements } from './base';

export const clearList = () => {
    elements.noteList.innerHTML = '';
    elements.notePageButtons.innerHTML = '';
};

const renderNoteForList = note => {
    const markup =
        `<li>
            <div class="note-link" id="note_${note.id}">
                <div>
                    <h3 class="notes-title">${note.title}</h3>
                    <p class="notes-date">${note.date}</p>
                </div>
            </div>
        </li>`;

    elements.noteList.insertAdjacentHTML('beforeend', markup);
};

export const renderNotes = (notes, page = 1, notePerPage = 8) => {
    notes
        .slice((page - 1) * notePerPage, page * notePerPage)
        .forEach(renderNoteForList);

    renderPageButtons(page, notes.length, notePerPage);
};

export const markSelected = id => {
    unmarkSelected();
    document.querySelector(`div[id="note_${id}"]`).classList.add('note-link-active');
};

export const unmarkSelected = () => {
    const res = Array.from(document.querySelectorAll('.note-link'));
    res.forEach(e => {
        e.classList.remove('note-link-active');
    });
};

const renderPageButtons = (page, noteNum, notePerPage) => {
    const pagesNum = Math.ceil(noteNum / notePerPage);

    let btn;

    if(page === 1 && pagesNum > 1) {
        btn = `
        <button type="button" class="btn-small page-next" data-goToPage=${page + 1}>
            <i class="ion-ios-arrow-dropright-circle"></i>
        </button>`
    } else if(page < pagesNum) {
        btn = `
        <button type="button" class="btn-small page-prev" data-goToPage=${page - 1}>
            <i class="ion-ios-arrow-dropleft-circle"></i>
        </button>
        <button type="button" class="btn-small page-next" data-goToPage=${page + 1}>
            <i class="ion-ios-arrow-dropright-circle"></i>
        </button>`
    } else if(page === pagesNum && pagesNum > 1) {
        btn = `
        <button type="button" class="btn-small page-prev" data-goToPage=${page - 1}>
            <i class="ion-ios-arrow-dropleft-circle"></i>
        </button>`
    }

    if(noteNum > notePerPage) {
        elements.notePageButtons.insertAdjacentHTML('afterbegin', btn);
    }
};

export const clearSearch = () => {
    elements.searchInput.value = '';
    elements.searchDateInput.value = ''; 
};

export const getSearch = () => elements.searchInput.value;
export const getDateSearch = () => elements.searchDateInput.value;

export const clearNoteForm = () => {
    elements.noteForm.innerHTML = '';
};

export const getTitleFromForm = () => document.getElementById('title-id').value;
export const getDateFromForm = () => document.getElementById('date-id').value;
export const getTextFromForm = () => document.getElementById('text-id').value;
export const getAddUpdateValue = () => document.getElementById('AddUpdate').innerHTML;

export const clearFormFields = () => {
    document.getElementById('title-id').value = '';
    if(document.getElementById('date-id')) {
        document.getElementById('date-id').value = '';
    }
    document.getElementById('text-id').value = '';
};

export const fillNoteForm = (title, date, text) => {
    document.getElementById('title-id').value = title;
    document.getElementById('date-id').value = date;
    document.getElementById('text-id').value = text;
};

export const fillAddNoteView = () => {
    elements.noteButtons.innerHTML = '';

    const markup = `
        <div class="row form-row">
            <div class="col form-label">
                <label>Tytuł</label>
            </div>
            <div class="col form-field">
                <input type="text" id="title-id" placeholder="Tytuł...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>Treść</label>
            </div>
            <div class="col form-field">
                <textarea form="note-form" id="text-id" rows="7" placeholder="Treść..."></textarea>
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>&nbsp;</label>
            </div>
            <div class="col form-field">
                <button class="btn add-btn">
                    <span id="AddUpdate">Dodaj</span>
                </button>
            </div>
        </div>`;
    elements.noteForm.insertAdjacentHTML('beforeend', markup);
};

export const fillAddNoteViewError = errorMsg => {
    clearNoteForm();
    fillAddNoteView();
    errorMsg.forEach(e => {
        const markup = `
            <div class="row">
                <p class="error">${e}</p>
            </div>`;
        elements.noteForm.insertAdjacentHTML('afterbegin', markup);
    });
};

export const fillEditNoteView = () => {
    elements.noteButtons.innerHTML = '';

    const markup = `
        <div class="row form-row">
            <div class="col form-label">
                <label>Tytuł</label>
            </div>
            <div class="col form-field">
                <input type="text" id="title-id" placeholder="Tytuł...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>Data</label>
            </div>
            <div class="col form-field">
                <input type="date" id="date-id">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>Treść</label>
            </div>
            <div class="col form-field">
                <textarea form="note-form" id="text-id" rows="7" placeholder="Treść..."></textarea>
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>&nbsp;</label>
            </div>
            <div class="col form-field">
                <button class="btn add-btn">
                    <span id="AddUpdate">Zapisz</span>
                </button>
            </div>
        </div>`;
    elements.noteForm.insertAdjacentHTML('beforeend', markup);
};

export const fillEditNoteViewError = errorMsg => {
    clearNoteForm();
    fillEditNoteView();
    errorMsg.forEach(e => {
        const markup = `
            <div class="row">
                <p class="error">${e}</p>
            </div>`;
        elements.noteForm.insertAdjacentHTML('afterbegin', markup);
    });
};

export const clearNoteView = () => {
    elements.noteShow.innerHTML = '';
};

export const fillShowNoteView = note => {
    let markup = `
        <button type="button" class="btn-small note-add">
            <i class="ion-ios-add-circle"></i>
        </button>
        <button type="button" class="btn-small note-delete">
            <i class="ion-ios-close-circle"></i>
        </button>
        <button type="button" class="btn-small note-edit">
            <i class="ion-ios-create"></i>
        </button>`;
    elements.noteButtons.innerHTML = '';
    elements.noteButtons.insertAdjacentHTML('beforeend', markup);

    markup =
        `<h2 class="row note-title">${note.title}</h2>
        <p class="row note-date">${note.date}</p>
        <div class="row note-content">
            <p class="note-text">
                ${note.text}
            </p>
        </div>`;
    elements.noteShow.insertAdjacentHTML('beforeend', markup);
};